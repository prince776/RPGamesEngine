package dev.prince.rpgGameEngine.ui;

import org.newdawn.slick.Color;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Renderer;

public class UIButton extends UIObject {
	
	private String message=" aa";
	private ClickListener clicker;
	private Color strColor;
	private float R,G,B;
	private boolean big=false;
	private int length;
	
	public UIButton(Handler handler,float x,float y , int width , int height,String message,Color strColor,float R,float G,float B,boolean big,int length,ClickListener clicker){
		super(handler,x,y,width,height);
		this.message=message;
		this.clicker=clicker;
		this.strColor = strColor;
		this.R=R;
		this.G=G;
		this.B=B;
		this.big=big;
		this.length=length;
	}

	@Override
	public void tick() {
		onMouseMove();
		onMouseClick();
	}

	@Override
	public void render() {
		//DRAW BOX
		if(hovering){
			Renderer.setColor(R, G, B, 1f);
			Renderer.renderQuad(x, y, width, height);
		}
		else if (!hovering){
			Renderer.setColor(R, G, B, 0.7f);
			Renderer.renderQuad(x, y, width, height);
		}
		int toAdd=(length-message.length())/2;
		
		//DRAW TEXT
		if(hovering)
			Renderer.renderString(x+(toAdd*16), y+5f, message,strColor.darker(),big);
		else
			Renderer.renderString(x+(toAdd*16), y+5f, message,strColor,big);

	}

	@Override
	public void onClick() {
		clicker.onClick();
	}
	
}
