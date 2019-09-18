package dev.prince.rpgGameEngine.inputs;

import org.lwjgl.input.Mouse;

import dev.prince.rpgGameEngine.Handler;

public class MouseManager {
	public  static int value;
	public static float mouseX,mouseY;
	private Handler handler;
	
	public MouseManager(Handler handler){
		this.handler=handler;
	}
	
	public void tick(){
		 mouseX=(int)((handler.getGameCamera().getxOffset()+Mouse.getX()));
		 mouseY=(int)(((handler.getHeight()-Mouse.getY()) + handler.getGameCamera().getyOffset()));
	}
	
}
