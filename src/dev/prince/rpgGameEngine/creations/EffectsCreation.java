package dev.prince.rpgGameEngine.creations;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import dev.prince.rpgGameEngine.Game;
import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.inputs.KeyManager;
import dev.prince.rpgGameEngine.net.packets.Packet05Thunder;
import dev.prince.rpgGameEngine.states.GameState;

public class EffectsCreation extends Creation{

	private float light=0f;
	private byte count=0;
	private float  rainY1=0,countI=0;
	private int checkThunder;
	private float[] rainX,rainY;
	private Random rand;
	private boolean setLight=false,thunder=false,rain=false,clientThunder=false;
	
	private Packet05Thunder thunderPacket;
	
	public EffectsCreation(Handler handler) {
		super(handler);
		rand = new Random();
		rainX=new float[handler.getWidth()/8] ;
		rainY = new float[handler.getHeight()/4];
		
		for(int i=0;i<rainX.length;i++){
				rainX[i] =rand.nextInt(handler.getWidth());			
		}
		
		for(int i=0;i<rainY.length;i++){
			rainY[i] =rand.nextInt(handler.getHeight()*2)-handler.getHeight();			
		}
		thunderPacket=new Packet05Thunder(clientThunder);
	}
	
	public void tick() {
		
		//LIGHT
		if(GameState.prompt.getPromptText().toLowerCase().startsWith("set light")){
			if(GameState.prompt.getPromptText().length() > 10){
				setLight=true;
				try{
				light = Float.parseFloat((GameState.prompt.getPromptText().substring(10, GameState.prompt.getPromptText().length())));
				}catch(NumberFormatException e){
					light=100;
				}
			}
		}else{
			setLight =false;
		}
		
		//TIME
		if(GameState.prompt.getPromptText().toLowerCase().startsWith("set time speed")){
			if(GameState.prompt.getPromptText().length() > 15){
				try{
					handler.getClock().interval = Float.parseFloat((GameState.prompt.getPromptText().substring(15, GameState.prompt.getPromptText().length())));
				}catch(NumberFormatException e){
					handler.getClock().interval = Game.UPS;	
				}
			}
		}
		
		//THUNDER
		if(GameState.prompt.getPromptText().equalsIgnoreCase("Start Thunder")){
			if(KeyManager.value==Keyboard.KEY_RETURN){
				thunder=true;
				handler.getSFX().playSoundEffect(handler.getSFX().thunder,1,1,0);
			}
		}
		
		//RAIN
		if(GameState.prompt.getPromptText().equalsIgnoreCase("Start Rain")){
			if(KeyManager.value==Keyboard.KEY_RETURN)
				rain=true;
				
		}
		if(GameState.prompt.getPromptText().equalsIgnoreCase("Stop Rain")){
			if(KeyManager.value==Keyboard.KEY_RETURN)
				rain=false;
				
		}
		

	}

	public void render() {
		
		if(setLight){
			setLight(light);
		}
		if(thunder){
			count++;
			thunder((count<55) || (count>65));
			if(count>=Game.UPS*2){
				thunder=false;
				count=0;
			}
		}
		
		if(rain){
			rain();
		}
		
	}
	
	
	//EFFECT FUNCTIONS
	
	public void setLight(float value){
		Renderer.setColor(0, 0, 0, 1-light/100);
		Renderer.renderQuad(0, 0, handler.getWidth(),handler.getHeight());
	}
	
	public void thunder(boolean a){
		if(a){
			Renderer.setColor(1, 1, 1, 0.88f);
			Renderer.renderQuad(0, 0, handler.getWidth(), handler.getHeight());
		}
	}
	
	public void rain(){
		if(!handler.getSFX().walk.get(3).isPlaying()){
			handler.getSFX().playSoundEffect(handler.getSFX().walk.get(3), false, 1, 1, 0);
		}
		
		//THUNDER
		if(Game.isServer){

			thunderPacket.setStartThunder(clientThunder);
			thunderPacket.sendToAllClients(handler.getServer());
			if(checkThunder<50){	
				clientThunder=true;
				if(!handler.getSFX().thunder.isPlaying()){
					handler.getSFX().playSoundEffect(handler.getSFX().thunder, false, 1, 1, 0);
				}
				count++;
				thunder((count<55) || (count>65));
				if(count>=Game.UPS*2){
					count=0;
					checkThunder  = rand.nextInt(400);
				}
				
			}else{
				clientThunder=false;
				countI++;
				if(countI>=Game.UPS*10){
					checkThunder  =rand.nextInt(200);
					countI=0;
				}
			}
		}
		if(Game.joinServer){
			if(clientThunder){
				if(!handler.getSFX().thunder.isPlaying()){
					handler.getSFX().playSoundEffect(handler.getSFX().thunder, false, 1, 1, 0);
				}
				count++;
				thunder((count<55) || (count>65));
				if(count>=Game.UPS*2){
					count=0;
					//checkThunder  = rand.nextInt(400);
				}
				
			}
		}
		//
		
		//Renderer.renderImage( Assets.rain,0, rainY1,1000,1500,1f);
		if(handler.getWidth() >=1280 || handler.getHeight()>=720){
			rainY1+=5;
		}
		Renderer.renderImage( Assets.rain,0,rainY1-handler.getHeight(),handler.getWidth(),handler.getHeight(), 1f);
		Renderer.renderImage( Assets.rain,0,rainY1,handler.getWidth(),handler.getHeight(), 1f);
		
		
		
		rainY1+=1;
		if(rainY1>handler.getHeight())
			rainY1=0;
	}

	public boolean isSetLight() {
		return setLight;
	}

	public boolean isClientThunder() {
		return clientThunder;
	}

	public void setClientThunder(boolean clientThunder) {
		this.clientThunder = clientThunder;
	}

	
	
	
}
