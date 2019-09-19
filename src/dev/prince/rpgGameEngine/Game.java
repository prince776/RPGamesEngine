package dev.prince.rpgGameEngine;


import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;

import javax.swing.JOptionPane;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.SoundStore;

import dev.prince.rpgGameEngine.display.DisplayClass;
import dev.prince.rpgGameEngine.fonts.Fonts;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.GameCamera;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.inputs.KeyManager;
import dev.prince.rpgGameEngine.inputs.MouseManager;
import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;
import dev.prince.rpgGameEngine.net.packets.Packet00Login;
import dev.prince.rpgGameEngine.net.packets.Packet01Disconnect;
import dev.prince.rpgGameEngine.sounds.SoundEffects;
import dev.prince.rpgGameEngine.states.CreditState;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.states.MenuState;
import dev.prince.rpgGameEngine.states.State;
import dev.prince.rpgGameEngine.ui.TextArea;
import dev.prince.rpgGameEngine.utils.Utils;


public class Game{

	
	private String title;
	private int width,height;
	public static int UPS=60,FPS=0;
	public int Frames=0,Updates=0;
	public static boolean joinServer=false,isServer=false;
	public boolean vsync = false,start=false;
	
	///FONT
	Font awtFont ;
	TrueTypeFont font;
	
	//HANDLER
	private Handler handler;
	
	//STATES
	private GameState gameState;
	private MenuState menuState;
	private CreditState creditState;
	
	//INPUTS
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//GAME CAMERA
	private GameCamera camera;
	Assets assets;
	//Sounds
	private SoundEffects soundEffects;
//	private Thread t;
	
	//GAME CLIENTS AND SERVER
	GameClient client;
	GameServer server;
	public Game(String title,int width,int height){
		this.title=title;
		this.width=width;
		this.height=height;
		
	}
	
	///////INIT
	public void init(){
	
		handler=new Handler(this);
		
		assets = new Assets();
		assets.init();//BIG DEAL
		Renderer.init(handler);

		Fonts.init();
		Utils.init();
		soundEffects = new SoundEffects();;

		soundEffects.init();
		
		keyManager = new KeyManager(handler);
		mouseManager = new MouseManager(handler);
		
		camera=new GameCamera(handler);
		
		gameState=new GameState(handler);
		menuState = new MenuState(handler);
		creditState = new CreditState(handler);
		State.setState(gameState);
		
		
	}

	public void start(){
		
		//INITs
		DisplayClass.initGL(title,width,height);
		init();
		
		
		//FPS SET UP
		long lastTime = Sys.getTime()*1000000000/Sys.getTimerResolution();
		long now;
		double delta=0;
		double nsPerTick = 1000000000/UPS;
		long timer=0;
		int frames=0,ticks=0;
		 

		if(JOptionPane.showConfirmDialog(null, this,"Do You Want To Run The Server?",1)==0){
			server = new GameServer(handler);
			handler.getPlayer().setUsername(JOptionPane.showInputDialog( this, "Enter Username:"));
			GameServer.serverName=handler.getPlayer().getUsername();
			if(new File("res/multiplayerData/"+handler.getPlayer().getUsername()+".server").exists()){
				String password = JOptionPane.showInputDialog(this, "Enter Password Of The Associated Server: ");
				password = password.replaceAll("\\s", "");
				String passCode = Utils.loadFileAsString("res/multiplayerData/"+GameServer.serverName+".server");
				passCode = passCode.replaceAll("\\s", "");
				GameServer.passCode=passCode;
				if(password.equals(passCode)){
					server.start();
				}else{
					JOptionPane.showMessageDialog(null, "WRONG PASSWORD..EXECUTION TERMINATED!   " +passCode+"|");
					System.exit(1);
				}
			}
			else{
				Formatter f=null;
				try {
					f = new Formatter("res/multiplayerData/"+handler.getPlayer().getUsername()+".server");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				String passCode = JOptionPane.showInputDialog(this, "Set Password Of The Associated Server: ");
				f.format(passCode+ " ");
				f.close();
				server.start();
			}
			start=true;
			joinServer=false;
			isServer=true;
		}else{
			
			if(JOptionPane.showConfirmDialog(null, this, "Do You Want To Join Server", 1)==0){
				Packet00Login login;
				String address = JOptionPane.showInputDialog(null, "Enter IPAddress: ");
				client = new GameClient(handler,address);
				client.start();
				address=null;
				handler.getPlayer().setUsername(JOptionPane.showInputDialog( this, "Enter Username:"));
				login = new Packet00Login(handler.getPlayer().getUsername());//We will get x and y from previous session and its data will be stored in separate files
				login.sendToServer(client);
				joinServer=true;
				isServer=false;
				start=true;
			}
		}if(!start){
			start=true;
		}
		
		
		while(!Display.isCloseRequested() && start){
			
			
			now=Sys.getTime()*1000000000/Sys.getTimerResolution();
			delta+=(now-lastTime)/nsPerTick;
			timer+=(now-lastTime);
			lastTime=now;
			
			

			if(delta>=1){
			
				tick();
				ticks++;
				delta--;
			}
		
			render();
			
	
			frames++;
			
			if(timer >= 1000000000){
				Game.FPS= Frames;
				Display.setTitle(title + " With : "+frames+" FPS");
				Frames=frames;
				Updates = ticks;
				timer=0;
				frames=0;
				ticks=0;
			}
			Display.update();
		}
		if(joinServer){
			disconnectMP();
		}
		closeProgram();
		
	}

	
	
	/////TICK
	public void tick(){
		
		mouseManager.tick();
		if(State.getState() !=null){
			State.getState().tick();
		}
		keyManager.endTick();

		
	}
	
	////RENDER
	public void render(){
		//glClear(GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		//GL11.glClearColor(0, 0, 0, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 0);
		Color.white.bind();
		keyManager.render();

		if(State.getState() !=null){
			State.getState().render();
			
		}		
			
			TextArea.renderTextArea(handler.getWidth()-200, 33-25,"FPS: "+Frames+" UPS: "+Updates, Color.black);
			TextArea.renderTextArea(handler.getWidth()-200, 33,"Vsync: "+vsync, Color.black);

			SoundStore.get().poll(0);
			
	}
	
	//CLOSE
	public static void closeProgram(){	
		
		Renderer.destroy();
		SoundEffects.destroy();
		Display.destroy();
		System.exit(0);
		
		
		
	}
	
	//DISCONNECTION FOR MULTIPLAYER
	public  void disconnectMP(){
		Packet01Disconnect disconnect = new Packet01Disconnect(handler.getPlayerX(),handler.getPlayerY(),handler.getPlayer().getUsername());
		disconnect.sendToServer(client);
		disconnect=null;
	}

	///////////////GETTERS AND SETTERS///////////////////
	
	public int getWidth() {
		
		return width;
	}

	public void setVsync(boolean vsync) {
		this.vsync = vsync;
	}

	public int getHeight() {
		return height;
	}

	public int getFrames() {
		return Frames;
	}

	public boolean isVsync() {
		return vsync;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public GameState getGameState() {
		return gameState;
	}
	public MenuState getMenuState() {
		return menuState;
	}
	public CreditState getCreditState() {
		return creditState;
	}
	public GameCamera getCamera() {
		return camera;
	}

	public SoundEffects getSoundEffects() {
		return soundEffects;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public GameClient getSocketClient() {
		return client;
	}

	public GameServer getSocketServer() {
		return server;
	}

	
	
}


