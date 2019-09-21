package dev.prince.rpgGameEngine.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.fonts.Fonts;
import dev.prince.rpgGameEngine.inputs.KeyManager;
import dev.prince.rpgGameEngine.utils.Utils;

public class UIPrompt extends UIObject{
	
	public boolean focused=false;
	
	
	private String text="_";
	
	
	public UIPrompt(Handler handler, float x, float y) {
		super(handler, x, y,TextArea.getWidth(), TextArea.getHeight());
		this.bounds=new Rectangle((int)x,(int)y,(int)width,(int)height);
	}

	@Override
	public void tick() {
		//BACKSPACE WORKING
		
		
		
		width = TextArea.xOffset + Fonts.font.getWidth(text) + TextArea.xOffset;
		height = TextArea.yOffset + Fonts.font.getHeight(text) + TextArea.yOffset;
		bounds.setWidth(width);
		bounds.setHeight(height);
		                 
		if(focused){
			if(Utils.parseInt(KeyManager.getInput(false)) == Keyboard.KEY_BACK){
				if(text.length()>1)
					text=text.substring(0, text.length()-1);
				
				
			}
			//ENTERING TEXT
			if(  Utils.parseInt(KeyManager.getInput(false)) != Keyboard.KEY_BACK){
					text=text.substring(0, text.length()-1);
					text+=KeyManager.getInput(true)+"_";
				
			}
			if(KeyManager.value == Keyboard.KEY_SPACE){
				text=text.substring(0, text.length()-1);
				text+=" ";
			}
			if(Utils.parseInt(KeyManager.getInput(false)) == Keyboard.KEY_RETURN){
				text="_";
			}
			KeyManager.alphabet="";
			
			KeyManager.value=0;
		}
		onMouseMove();
		onMouseClick();
		unFocusprompt();
	}

	@Override
	public void render() {
		if(focused)
			TextArea.renderTextArea(x, y, text, Color.black, new float[]{1f,1f,1f,0.7f}, new float[]{0,0,0,0.8f},false);	
		else
			TextArea.renderTextArea(x, y, text, Color.black, new float[]{1f,1f,1f,0.3f}, new float[]{0,0,0,0.4f},false);	
	}

	@Override
	public void onClick() {
		focused=true;
		KeyManager.alphabet="";
	}
	
	public void unFocusprompt(){
		if(!hovering &&( Mouse.isButtonDown(0) ||Mouse.isButtonDown(1) ))
			focused = false;
	}
	
	public String getPromptText(){
		return text.substring(0, text.length()-1);
	}
	
	public void setPromptText(String message){
		text="";
		text=message +"_";
	}
	public void appendText(String message){
		text=text.substring(0, text.length()-1);
		text+=message+"_";
	}
}
