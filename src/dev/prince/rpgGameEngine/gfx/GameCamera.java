package dev.prince.rpgGameEngine.gfx;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.entities.Entity;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.tiles.Tile;

public class GameCamera {
	
	private Handler handler;
	
	private float xOffset , yOffset,extra=0;
	
	public GameCamera(Handler handler){
		this.handler=handler;
	}
	
	public void centerOnEntity(Entity e){
		xOffset = (int)(e.getX()-handler.getWidth()/2+e.getWidth()/2);
		yOffset =(int)( e.getY()-handler.getHeight()/2+e.getHeight()/2);
		if(handler.getWidth() < handler.getWorld().getWidth()*Tile.TILEWIDTH){
			if(handler.getHeight() < handler.getWorld().getHeight()*Tile.TILEHEIGHT){
				checkBlankSpace();
			}
		}
	}
	
	public void checkBlankSpace(){
		if(GameState.createWorld)
			extra = 0;
		else
			extra=0;
		if(xOffset<=-extra){
			xOffset = -extra;
		}
		if(yOffset <= -extra ){
			yOffset = -extra;
		}
		if(xOffset >= handler.getWorld().getWidth()*Tile.TILEWIDTH - handler.getWidth()+extra){
			xOffset = handler.getWorld().getWidth()*Tile.TILEWIDTH-handler.getWidth()+extra;
		}
		if(yOffset >= handler.getWorld().getHeight()*Tile.TILEHEIGHT - handler.getHeight()+extra){
			yOffset = handler.getWorld().getHeight()*Tile.TILEHEIGHT - handler.getHeight()+extra;
		}
		
	}

	//GETTERS
	

	public float getxOffset() {
		return xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}
	
}
