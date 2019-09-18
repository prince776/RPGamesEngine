package dev.prince.rpgGameEngine.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.entities.creatures.Player;
import dev.prince.rpgGameEngine.tiles.Tile;

public class EntityManager {
	
	private Handler handler;

	private Player player;
	public Iterator<Entity> iterator;
	private ArrayList<Entity> entities;
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){
		
		public int compare(Entity a , Entity b){
			
			if(a.zIndex < b.zIndex)
				return -1;
			if(a.zIndex > b.zIndex)
				return 1;
			if(a.getY()+a.getHeight()<b.getY()+b.getHeight()){
				return -1;
			}else{
				return 1;
			}
		}
		
	};
	
	public EntityManager(Handler handler,Player player){
		this.handler=handler;
		this.player=player;
		entities = new ArrayList<Entity>();
		entities.add(player);
		iterator = entities.iterator();

	}
	
	public void tick(int xStart,int xEnd,int yStart,int yEnd){
		iterator = entities.iterator();
		
		while(iterator.hasNext()){
			Entity e = iterator.next();
			
			if(e.getX() + e.getWidth() > xStart*Tile.TILEWIDTH && e.getX() < xEnd * Tile.TILEWIDTH &&
				e.getY() + e.getHeight() > yStart*Tile.TILEHEIGHT && e.getY() < yEnd*Tile.TILEHEIGHT	
				){
				e.isActive=true;
			}else{
				e.isActive=false;
			}
			if(e.equals(player) || e.getClass().getSimpleName().equalsIgnoreCase("PlayerMP")){
				e.tick();
				continue;
			}
			
			if(e.isActive )
				e.tick();
			
		}
		entities.sort(renderSorter);
	} 
	
	public void render(){
		iterator = entities.iterator();
		
		while(iterator.hasNext()){
			Entity e = iterator.next();	
			
		if(e.equals(player) || e.getClass().getSimpleName().equalsIgnoreCase("PlayerMP")){
				e.render();
				continue;
			}
		if(e.isActive	
				){
		e.render();
	}
			
			
		}
		
	}
	
	///HEPLER METHODS///
	public void addEntity(Entity e){
		entities.add(e);
			iterator = entities.iterator();
	}
	
	public void removeEntity(Entity e){
		entities.remove(e);
	}
	
	public Entity getEntity(float x,float y){//used in removing entities
		for(Entity e:entities){
			if(e.getX()<=x && e.getY()<=y && (e.getX()+e.getWidth())>=x && (e.getY()+e.getHeight())>=y){
				return e;
			}
		}
		return null;
	}
	
	///GETTERS AND SETTERS///
	
	public Player getPlayer() {
		return player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	
	
}
