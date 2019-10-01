package dev.prince.rpgGameEngine.creations;

import org.lwjgl.input.Keyboard;

import dev.prince.rpgGameEngine.Game;
import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.inputs.KeyManager;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.utils.Utils;

public class EffectsCreation extends Creation{

	private float light=0f;
	private boolean setLight=false,thunder=false;
	private int rain =0;
	private int thunderDuration = 0;
	private long lastTime= 0;
	
	public EffectsCreation(Handler handler) {
		super(handler);
	}
	
	public void tick() {
		
		String command = GameState.prompt.getPromptText().toLowerCase();
		
		//LIGHT
		if(command.startsWith("set light")){
			if(GameState.prompt.getPromptText().length() > 10){
				setLight=true;
				try{
				light = Float.parseFloat((GameState.prompt.getPromptText().substring(10, GameState.prompt.getPromptText().length())));
				}catch(NumberFormatException e){
					light=100;
				}
			}
		}else if(command.equalsIgnoreCase("reset light")){
			setLight =false;
		}
		
		//TIME
		if(command.startsWith("set time speed")){
			if(GameState.prompt.getPromptText().length() > 15){
				try{
					handler.getClock().interval = Float.parseFloat((GameState.prompt.getPromptText().substring(15, GameState.prompt.getPromptText().length())));
				}catch(NumberFormatException e){
					handler.getClock().interval = Game.UPS;	
				}
			}
		}else if(command.equalsIgnoreCase("reset time speed")){
			handler.getClock().interval = Game.UPS;
		}

		//RAIN
		if(command.startsWith("start rain")){
			String[] tokens = GameState.prompt.getPromptText().split("\\s+");
			if(tokens.length == 3){
				if(KeyManager.value==Keyboard.KEY_RETURN){
					rain=Utils.parseInt(tokens[2]);
					if(rain<0) rain *= -1;
				}
			}
			
		}
		if(command.equalsIgnoreCase("Stop Rain")){
			if(KeyManager.value==Keyboard.KEY_RETURN)
				rain=0;
			
		}
		
		//THUNDER
		if(command.startsWith("start thunder")){
			String[] tokens = GameState.prompt.getPromptText().split("\\s+");
			if(tokens.length == 3){
				if(KeyManager.value==Keyboard.KEY_RETURN){
					thunder=true;
					thunderDuration = Utils.parseInt(tokens[2]);
					lastTime = System.currentTimeMillis();
				}
			}
		}
	}

	public void render() {
		
		if(setLight){
			setLight(light);
		}
		if(rain!=0){
			handler.getWeather().getRain().rain(rain);
		}
		if(thunder){
			thunder = handler.getWeather().getRain().thunder(lastTime,thunderDuration);
		}
		lastTime = System.currentTimeMillis();
	}
	
	//EFFECT FUNCTIONS
	
	public void setLight(float value){
		Renderer.setColor(0, 0, 0, 1-value/100);
		Renderer.renderQuad(0, 0, handler.getWidth(),handler.getHeight());
	}
	
	public boolean isSetLight() {
		return setLight;
	}
	
}
