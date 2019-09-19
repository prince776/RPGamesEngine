package dev.prince.rpgGameEngine.entities.creatures;

import java.net.InetAddress;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.states.GameState;

public class PlayerMP extends Player{
	
	public InetAddress ipAddress;
	public int port;
	public float oldX,oldY;
	
	private String level="null",location="null";
	

	public PlayerMP(Handler handler, float x, float y,String username,InetAddress ipAddress,int port) {
		super(handler, x, y,username);
		this.ipAddress=ipAddress;
		this.port=port;
		oldX=x;
		oldY=y;
		this.location = GameState.currentLocation;
		this.level=GameState.currentLevel;
	}
	public PlayerMP(Handler handler, float x, float y,String username,InetAddress ipAddress,int port,String location,String level) {
		this(handler,x,y,username,ipAddress,port);
		this.location=location;
		this.level=level;
	}
	@Override
	public void tick(){
		//xMove and yMove settled by the packet
		checkSwim();
		if(location.equalsIgnoreCase(GameState.currentLocation)){
			if(level.equalsIgnoreCase(GameState.currentLevel)){
				playSound();
			}
		}
		if(isSwimming){
			sheet = Assets.playerSwimSheet;

		}else{
			isSwimming = false;
			sheet = Assets.playerSheet;
		}
		if(yMove>0){
			animDown.animTick();
			direction=2;
		}
		if(xMove<0){
			animLeft.animTick();
			direction=3;
		}
		if(xMove>0){
			direction=1;
			animRight.animTick();
		}
		if(yMove<0){
			direction=0;
			animUp.animTick();
		}
		if(!getEntityCollision(xMove, 0))
			moveX();
		if(!getEntityCollision(0,yMove))
			moveY();
	
	}
	
	@Override
	public void render(){
		if(location.equalsIgnoreCase(GameState.currentLocation)){
			if(level.equalsIgnoreCase(GameState.currentLevel)){
				super.render();
			}
		}
	}
	
	//GETTERS AND SETTERS
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
}
