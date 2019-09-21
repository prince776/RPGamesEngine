package dev.prince.rpgGameEngine;

import dev.prince.rpgGameEngine.entities.creatures.Player;
import dev.prince.rpgGameEngine.features.Clock;
import dev.prince.rpgGameEngine.features.weathers.Weather;
import dev.prince.rpgGameEngine.gfx.GameCamera;
import dev.prince.rpgGameEngine.inputs.KeyManager;
import dev.prince.rpgGameEngine.inputs.MouseManager;
import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;
import dev.prince.rpgGameEngine.sounds.SoundEffects;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.worlds.World;

public class Handler {
	
	private Game game;
	private World world;
	
	public  Handler(Game game){
		this.game=game;
	}
	
	public GameCamera getGameCamera(){
		return game.getCamera();
	}
	
	public void setWorld(World w){
		this.world=w;
	}
	
	public World getWorld(){
		return this.world;
	}
	
	public Game getGame(){
		return this.game;
	}
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}
	
	public boolean isVsync(){
		return game.isVsync();
	}
	
	public void setVsync(boolean b){
		game.setVsync(b);
	}
	
	public int getFrames(){
		return game.getFrames();
	}
	
	public KeyManager getKeyManager(){
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager(){
		return game.getMouseManager();
	}
	
	public SoundEffects getSFX(){
		return game.getSoundEffects();
	}
	
	public Player getPlayer(){
		return world.getEntityManager().getPlayer();
	}
	
	public GameState getGameState(){
		return game.getGameState();
	}
	
	public float getTime(){
		return game.getGameState().getClock().getTime();
	}
	
	public float getPlayerX(){
		return world.getEntityManager().getPlayer().getX();
	}
	public float getPlayerY(){
		return world.getEntityManager().getPlayer().getY();
	}
	
	public GameClient getClient() {
		return game.getSocketClient();
	}

	public GameServer getServer() {
		return game.getSocketServer();
	}
	
	public Clock getClock(){
		return game.getGameState().getClock();
	}
		
	public Weather getWeather(){
		return game.getGameState().getClock().getWeatherSystem();
	}
	
}
	
