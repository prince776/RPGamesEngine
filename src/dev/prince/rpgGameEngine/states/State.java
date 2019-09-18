package dev.prince.rpgGameEngine.states;

import dev.prince.rpgGameEngine.Handler;

public abstract class State {
	
	protected Handler handler;
	
	public State(Handler handler){
		this.handler=handler;
	}
	
	public abstract void tick();
	public abstract void render();
	
	///////STATE MANAGER CODE/////////
	private static State currentState;
	
	public static void setState(State s){
		currentState = s;
	}
	
	public static State getState(){
		return currentState;
	}
	
}
