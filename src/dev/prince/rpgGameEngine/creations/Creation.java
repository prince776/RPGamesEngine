package dev.prince.rpgGameEngine.creations;

import dev.prince.rpgGameEngine.Handler;

public abstract class Creation {
	
	protected Handler handler;
	
	public Creation(Handler handler){
		this.handler=handler;
	}
	
	public abstract void tick();
	public abstract void render();
	
	private static Creation currentCreation=null;
	
	public static void setCreation(Creation c){
		currentCreation=c;
	}
	
	public static Creation getCreation(){
		return currentCreation;
	}
	
}
