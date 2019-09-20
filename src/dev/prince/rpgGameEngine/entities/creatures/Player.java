package dev.prince.rpgGameEngine.entities.creatures;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import dev.prince.rpgGameEngine.Game;
import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.features.Inventory;
import dev.prince.rpgGameEngine.fonts.Fonts;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.inputs.KeyManager;
import dev.prince.rpgGameEngine.net.packets.Packet02Move;
import dev.prince.rpgGameEngine.states.GameState;

public class Player extends Creature {
	
	
	protected Texture sheet;
	private Inventory inventory;
	private boolean useInventory=false;
	private Packet02Move movePacket = new Packet02Move((byte)0,(byte)0);
	private float xOld,yOld;
	private String username;
	private byte countMP=0;
	private String[] items,quantities;
	
	public Player(Handler handler, float x, float y,String username) {
		super(handler, x, y,DEFAULT_WIDTH,DEFAULT_HEIGHT);
		bounds.setX(18 +2);
		bounds.setY(38+3 );
		bounds.setWidth(17 + 8);
		bounds.setHeight(12 + 5);
		
		this.username = username;
		sheet = Assets.playerSheet;
		items = new String[]{"TNT","Dynamite","Bombs","C-4 Charges","TNT","Dynamite","Bombs","C-4 Charges","TNT","Dynamite","Bombs","C-4 Charges","TNT","Dynamite","Bombs","C-4 Charges","TNT","Dynamite","Bombs","C-4 Charges"};
		quantities = new String[]{"21","10","79","4","21","10","79","4","21","10","79","4","21","10","79","4","21","10","79","4"};
		inventory = new Inventory(handler,items,quantities);

	}
	
	@Override
	public void tick() {
			getInput();
	
			if(KeyManager.value == Keyboard.KEY_E && !GameState.prompt.focused){
				useInventory=!useInventory;
				KeyManager.value=0;
			}
				if(useInventory){
					inventory.tick();
					return;
				}
				zIndex=0;
				checkSwim();
				if(isSwimming){
					sheet = Assets.playerSwimSheet;
	
				}else{
					isSwimming = false;
					sheet = Assets.playerSheet;
				}
				
				
				//RUN
				if(!isSwimming){
					if(Keyboard.isKeyDown(Keyboard.KEY_X)){
						run();
						animDown.setAnimSpeed(animSpeed/2+10);
						animUp.setAnimSpeed(animSpeed/2+10);
						animLeft.setAnimSpeed(animSpeed/2+10);
						animRight.setAnimSpeed(animSpeed/2+10);
					}else{
						animDown.setAnimSpeed(animSpeed);
						animUp.setAnimSpeed(animSpeed);
						animLeft.setAnimSpeed(animSpeed);
						animRight.setAnimSpeed(animSpeed);
					}
				}
				
				if(!GameState.createWorld){
					move();
					speed=DEFAULT_SPEED;
				}
				else{
					x+=xMove;
					y+=yMove;
					speed=(byte) (DEFAULT_SPEED*6);
				}		
				
				if(yMove>0)
					animDown.animTick();
				if(xMove<0)
					animLeft.animTick();
				if(xMove>0)
					animRight.animTick();
				if(yMove<0)
					animUp.animTick();
				
				checkOutOfBounds();
				playSound();
			
	
		handler.getGameCamera().centerOnEntity(handler.getPlayer());
		sendMoveData();
			
	}
	

	public void sendMoveData(){
		if(Game.joinServer){
			countMP++;
			if(countMP>=120){
				countMP=0;
				for(PlayerMP p : handler.getClient().getConnectedPlayers()){
					handler.getClient().sendData(("4"+" "+(int)x+" "+(int)y+" ").getBytes(), p.ipAddress, p.port);
					//System.out.println(username+": "+"Connected Ports: " + p.port);
				}
				return;
			}	
			movePacket.setX((byte)xMove);
			movePacket.setY((byte)yMove);
			//SEND TO OTHER CLIENTS
			for(PlayerMP p:handler.getClient().getConnectedPlayers()){
				handler.getClient().sendData(movePacket.getData(), p.ipAddress, p.port);
			}
		}else if(Game.isServer){
			countMP++;
			if(countMP>=120){
					countMP=0;                                                                                       
					for(PlayerMP p:handler.getServer().getConnectedPlayers()){
						handler.getServer().sendData(("4"+" "+(int)x+" "+(int)y+" ").getBytes(), p.ipAddress, p.port);
						//System.out.println(username+": "+"Connected Ports: " + p.port);
					}
					return;
				}	
				movePacket.setX((byte)xMove);
				movePacket.setY((byte)yMove);
				//SEND TO OTHER CLIENTS
				for(PlayerMP p:handler.getServer().getConnectedPlayers()){
					handler.getServer().sendData(movePacket.getData(), p.ipAddress, p.port);
				}
			}

	}
	
	@Override
	public void render() {
		
			Renderer.renderString(x-handler.getGameCamera().getxOffset() -Fonts.font.getWidth(username)/2+width/2, y-handler.getGameCamera().getyOffset()-20, username, Color.white, false);
			Renderer.setColor(1, 1, 1, 1);
			Renderer.renderSubImage(sheet,(x-handler.getGameCamera().getxOffset()),(y-handler.getGameCamera().getyOffset()),width,height,getCurrentAnimationFrame(), 1f);
			
			/*
		 	Renderer.setColor(1, 1, 1, 0.51f);
			Renderer.renderQuad((x-handler.getGameCamera().getxOffset())+bounds.getX(), (y-handler.getGameCamera().getyOffset())+bounds.getY(), bounds.getWidth(), bounds.getHeight());
		*/
			
	
	}
	
	//HELPER METHODS//
	public void getInput(){
		
		
		xMove=0;
		yMove=0;
		
		
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			direction=0;
			yMove= -speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			direction=1;
			xMove=speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			direction=2;
			yMove=speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			direction=3;
			xMove= -speed;
		}
	
	}

	public Inventory getInventory() {
		return inventory;
	}

	public boolean isUseInventory() {
		return useInventory;
	}
	
	public void setUsername(String name){
		this.username=name;
	}

	public String getUsername() {
		return username;
	}
	
}
