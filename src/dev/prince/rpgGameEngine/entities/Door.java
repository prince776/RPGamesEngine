package dev.prince.rpgGameEngine.entities;

import java.io.File;

import org.lwjgl.util.Rectangle;

import dev.prince.rpgGameEngine.Game;
import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.net.packets.Packet09Locale;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.worlds.loadAndSave.WorldSave;

public class Door extends Entity{
	
	public String[] worlds;
	private Entity allowedEntity;
	public float x2,x1,y1,y2; 
	private boolean xAxis,change=false;
	
	private Packet09Locale localePacket;
	
	public Door(Handler handler, float x1, float y1,float x2,float y2, float width, float height,String folder1,String world1,String folder2,String world2,Entity entityAllowed,boolean xAxis) {//xAxis is the orientation
		super(handler, x1, y1, width, height);
		
		localePacket = new Packet09Locale(GameState.currentLocation,GameState.currentLevel);
		
		bounds.setX(1);
		bounds.setY(1);
		bounds.setWidth((int)width-2);
		bounds.setHeight((int)height-2);
		this.x1=x1;
		this.y1=y1;
		
		this.x2=x2;
		this.y2=y2;
		
		zIndex=1;
		
		worlds=new String[2];
		this.worlds[0]="res/worlds/"+folder1+"/"+world1+".level";
		this.worlds[1]="res/worlds/"+folder2+"/"+world2+".level";
		
		if(!new File("res/worlds/"+folder1).exists()){
			new File("res/worlds/"+folder1).mkdir();
		}
		
		if(!new File("res/worlds/"+folder2).exists()){
			new File("res/worlds/"+folder2).mkdir();
		}
		
		for(int i=0;i<worlds.length;i++){
			
			if(!new File(worlds[i]).exists()){
				WorldSave.createFile(worlds[i]);
			}
			
		}
		
		if((GameState.path+GameState.currentLevel).equalsIgnoreCase(worlds[0])){
			this.x= x1;
			this.y=y1;
		}
		if((GameState.path+GameState.currentLevel).equalsIgnoreCase(worlds[1])){
			this.x= x2;
			this.y=y2;
		}
		
		this.xAxis=xAxis;
		
		this.allowedEntity=entityAllowed;
		
		
		params.removeAll(params);//REMOVE 
		
		params.add(String.valueOf((int)x1));
		params.add(String.valueOf((int)y1));
		params.add(String.valueOf((int)x2));
		params.add(String.valueOf((int)y2));
		params.add(String.valueOf((int)width));
		params.add(String.valueOf((int)height));
		params.add(folder1);
		params.add(world1);
		params.add(folder2);
		params.add(world2);
		params.add(entityAllowed.getClass().getSimpleName());
		params.add(String.valueOf(xAxis));

		
	}

	@Override
	public void tick() {
		
				if(handler.getWorld().getWorldPath().equalsIgnoreCase(worlds[0])){
					x = x1;
					y = y1;
				}
				
				if(handler.getWorld().getWorldPath().equalsIgnoreCase(worlds[1])){
					x = x2;
					y = y2;
				}
				
				if(xAxis){
					if(allowedEntity.getCollisionBounds(0f, 0f).intersects(new Rectangle((int)x+bounds.getX(),(int)y,(int)bounds.getWidth(),(int)height))){
						float xN=0;
						
						boolean goingDown=false,goingUp=false;
						//IF GOING DOWN
						if(allowedEntity.getY()+allowedEntity.bounds.getY()+allowedEntity.bounds.getHeight() <= y+height/2){
							goingDown=true;
						}
						//IF GOING UP
						if(allowedEntity.getY()+allowedEntity.bounds.getY()>=y+height/2){
							goingUp=true;
						}	
							
								changeWorld();
							
						
						if(handler.getWorld().getWorldPath()==worlds[0]){
							x = x1;
							y = y1;
						}
						
						if(handler.getWorld().getWorldPath()==worlds[1]){
							x = x2;
							y = y2;
						}
						
						
						xN=x+width/2-allowedEntity.width/2;
						allowedEntity.setX(xN);
					
						if(goingDown){//IF GOING DOWN
							allowedEntity.setY(y+height-allowedEntity.bounds.getY());
							goingDown=false;
						}
						else if(goingUp){//IF GOING UP
							allowedEntity.setY(y-allowedEntity.bounds.getY()-allowedEntity.bounds.getHeight());
							goingUp=false;
						}
							
					
						
					}
				}
				else{
					if(allowedEntity.getCollisionBounds(0f, 0f).intersects(new Rectangle((int)x,(int)y+bounds.getY(),(int)width,(int)bounds.getHeight()))){
						float yN=0;
						
						boolean goingRight=false,goingLeft=false;
						//IF GOING RIGHT
						if(allowedEntity.getX()+allowedEntity.bounds.getX()+allowedEntity.bounds.getWidth() <= x+width/2){
							goingRight=true;
						}
						//IF GOING LEFT
						if(allowedEntity.getX()+allowedEntity.bounds.getX()>=x+width/2){
							goingLeft=true;
						}	
							
						changeWorld();
						
						
						if(handler.getWorld().getWorldPath()==worlds[0]){
							x = x1;
							y = y1;
						}
						
						if(handler.getWorld().getWorldPath()==worlds[1]){
							x = x2;
							y = y2;
						}
						
						
							yN=y+height/2-allowedEntity.height/2;
							allowedEntity.setY(yN);
							
							if(goingRight){//IF GOING RIGHT
								allowedEntity.setX(x+width-allowedEntity.bounds.getX());
								goingRight=false;
							}
							else if(goingLeft){//IF GOING LEFT
								allowedEntity.setX(x-allowedEntity.bounds.getX()-allowedEntity.bounds.getWidth());
								goingLeft=false;
							}
		
					}
				}
				if(change){
					handler.getSFX().playSoundEffect(handler.getSFX().door, x, y, 0);
					change=false;
				}
	}
	@Override
	public void render() {
		if(GameState.createWorld){
			Renderer.setColor(0.421f, 0.289f, 0.238f,0.7f);
			Renderer.renderQuad(x-handler.getGameCamera().getxOffset(), y-handler.getGameCamera().getyOffset(), (int)width, (int)height);
		}
	}
	
	public void changeWorld(){
		for(int i=0;i<worlds.length;i++){
			if(worlds[i].equalsIgnoreCase(handler.getWorld().getWorldPath())){
				String a=worlds[((i == 0 )?1:0)];
				change=true;
				handler.getWorld().loadWorld(a);
				localePacket.setLocation(GameState.currentLocation);
				localePacket.setLevel(GameState.currentLevel);
				if(Game.isServer){
					localePacket.sendToAllClients(handler.getServer());
				}
				if(Game.joinServer){
					localePacket.sendToServer(handler.getClient());
				}
				
				return;
			}
		}
			
	}
	
	public void changeEntity(){
		
	}
	
}
