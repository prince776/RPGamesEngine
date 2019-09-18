package dev.prince.rpgGameEngine.ui;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.Rectangle;

import dev.prince.rpgGameEngine.Handler;

public abstract class UIObject {
	
	protected float x,y;
	protected int width,height;
	
	protected boolean hovering = false;
	
	protected Rectangle bounds;
	protected Handler handler;
	
	public UIObject(Handler handler,float x ,float y,int width,int height){
		this.x=x;
		this.y=y;
		this.width =width;
		this.height=height;
		this.bounds=new Rectangle((int)x,(int)y,(int)width,(int)height);
		this.handler=handler;
	}
	
	//FUNCTIONS..
	
	public void onMouseMove(){//CHECK HOVERING

		if (handler.getHeight()-Mouse.getY() > bounds.getY() && handler.getHeight()-Mouse.getY() < bounds.getY()+bounds.getHeight()){
			if(Mouse.getX() > bounds.getX() && Mouse.getX() < bounds.getX() + bounds.getWidth()){
					hovering = true;
			}
		}
		else
			hovering = false;
	}
	
	public void onMouseClick(){
		if(Mouse.isButtonDown(0)){
			if(hovering){
				onClick();
				return;
			}
		}
	}
	
	
	//ABSTRACT METHODS..
	public abstract void tick();
	public abstract void render();
	public abstract void onClick();
	
}
