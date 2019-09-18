package dev.prince.rpgGameEngine.inputs;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.display.ResolutionManager;
import dev.prince.rpgGameEngine.states.GameState;

public class KeyManager {
	
	private Handler handler;
	
	public static String alphabet="";
	public static int value;
	
	public KeyManager(Handler handler){
		this.handler=handler;
		
	}
	
	public void render(){
	
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){
				//RESOLUTION
				if(GameState.prompt.getPromptText().equalsIgnoreCase("Change DisplayMode") && Keyboard.isKeyDown(Keyboard.KEY_RETURN)|| Keyboard.isKeyDown(Keyboard.KEY_TAB)){
					ResolutionManager.changeResolution(handler.getWidth(),handler.getHeight(),!Display.isFullscreen());
				}
				//KEYS AND CHARS
				if((Keyboard.getEventCharacter()) != 0)
						alphabet=String.valueOf(Keyboard.getEventCharacter());
				value = Keyboard.getEventKey();
				//VSYNC
				if(GameState.prompt.getPromptText().equalsIgnoreCase("Change VSync") && Keyboard.isKeyDown(Keyboard.KEY_RETURN)){	
					handler.setVsync(!handler.isVsync());
				}
			}
		}
		
		if(!Display.isFullscreen()){
			handler.setVsync(false);
		}
		Display.setVSyncEnabled(handler.isVsync());
	}
	
	public  void endTick(){
		value=0;
	}
	
	public static String getInput(boolean string){
		if(string)
			return KeyManager.alphabet;
		else
			return String.valueOf(KeyManager.value);
	}
}
