package dev.prince.rpgGameEngine.creations;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.entities.Door;
import dev.prince.rpgGameEngine.entities.Entity;
import dev.prince.rpgGameEngine.entities.creatures.Creature;
import dev.prince.rpgGameEngine.entities.creatures.NPC;
import dev.prince.rpgGameEngine.entities.statics.StaticEntity;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.inputs.KeyManager;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.utils.Utils;
import dev.prince.rpgGameEngine.worlds.loadAndSave.WorldSave;

public class EntityCreation extends Creation{
	
	private boolean makeDoor=false,makeStatic=false,makeNPC=false,place=false,hold=false;
	private int mouseX,mouseY,sWidth=50,sHeight=50,staticEntityX,staticEntityY;
	private int doorLength=50;
	private Entity e=null;
	public EntityCreation(Handler handler) {
		super(handler);
	}

	public void tick() {
		
	}

	public void render() {
		 mouseX=(int)((handler.getGameCamera().getxOffset()+Mouse.getX()));
		 mouseY=(int)(((handler.getHeight()-Mouse.getY()) + handler.getGameCamera().getyOffset()));
		//ADD ENTITIES
		
				//Move Entities
				boolean move=false;
				if(GameState.prompt.getPromptText().toLowerCase().startsWith("move entity")){
					move=true;
				}
				
				//ADD DOOR
				boolean makeDoor=false,makeStatic=false;
				int length=50;
				
				//PRE_STAGE
				if(GameState.prompt.getPromptText().toLowerCase().startsWith("use door")){//DOOR
					if((GameState.prompt.getPromptText().length()>10))
						length = Utils.parseInt(GameState.prompt.getPromptText().substring(9).split("\\s+")[0]);
					makeDoor=true;
				}
				if(makeDoor){//DOOR
					Renderer.setColor(0.5f, 0.5f, 0.5f, 0.5f);
					Renderer.renderQuad(mouseX-handler.getGameCamera().getxOffset(), mouseY-handler.getGameCamera().getyOffset(), length,20);
				}		
				if(GameState.prompt.getPromptText().equalsIgnoreCase("USE NPC")){//NPC
					Renderer.setColor(1f, 1f, 1f, 0.5f);
					Renderer.renderSubImage(Assets.NPCsheet, mouseX-handler.getGameCamera().getxOffset(), mouseY-handler.getGameCamera().getyOffset(), Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT, Assets.characterDown[0], 0.4f);
				}
				if(GameState.prompt.getPromptText().toLowerCase().startsWith("use static entity")){//STATIC ENTITY
					
					if(GameState.prompt.getPromptText().length() > 18){
						for(int i=0;i<Assets.names.size();i++){
							if(GameState.prompt.getPromptText().substring(18, GameState.prompt.getPromptText().length()).equalsIgnoreCase(Assets.names.get(i))){//IF NAME MATCHES
								
								
								Renderer.setColor(1f, 1f, 1f, 0.5f);
								Renderer.renderSubImage(Assets.statics, mouseX-handler.getGameCamera().getxOffset(), mouseY-handler.getGameCamera().getyOffset(), sWidth,sHeight,Assets.staticEntities.get(i), 0.4f);
								makeStatic = true;
							}
						}
					}
				}
				if(makeStatic){
					if(KeyManager.value == Keyboard.KEY_W){
						sHeight+=5*2;

					}
					if(KeyManager.value == Keyboard.KEY_S)
						sHeight-=5*2;
					if(KeyManager.value == Keyboard.KEY_D)
						sWidth+=5*2;
					if(KeyManager.value == Keyboard.KEY_A)
						sWidth-=5*2;
					KeyManager.value=0;
				}
				while(Mouse.next()){//MOUSE.NEXT()
					if(Mouse.getEventButtonState()){
						
						//MAKING ENTITIES
						//NPC
						if(Mouse.getEventButton() == 1 && GameState.prompt.getPromptText().equalsIgnoreCase("USE NPC")){
							handler.getWorld().getEntityManager().addEntity(new NPC(handler,mouseX,mouseY));
							
						}
						
						//DOOR
						if(makeDoor && Mouse.getEventButton() == 1){
							String[] doorData=null;
							if(GameState.prompt.getPromptText().length()>=10)
							doorData = GameState.prompt.getPromptText().substring(9).split("\\s+"); 
							if(doorData.length>5){
								Door d=((doorData[5].equalsIgnoreCase("true"))?
										 new Door(handler,mouseX,mouseY,Utils.parseInt(doorData[1]),Utils.parseInt(doorData[2]),Utils.parseInt(doorData[0]),20,
													GameState.currentLocation,GameState.currentLevel.substring(0,GameState.currentLevel.length()-6),doorData[3],doorData[4],handler.getWorld().getEntityManager().getPlayer(),true)
										:
											new Door(handler,mouseX,mouseY,Utils.parseInt(doorData[1]),Utils.parseInt(doorData[2]),20,Utils.parseInt(doorData[0]),
													GameState.currentLocation,GameState.currentLevel.substring(0,GameState.currentLevel.length()-6),doorData[3],doorData[4],handler.getWorld().getEntityManager().getPlayer(),false)					);
								handler.getWorld().getEntityManager().addEntity(
									d
								);
								makeDoor=false;
								WorldSave.saveDoor(d);
								
							}
						}
						
						if(makeStatic && Mouse.getEventButton() == 1){
							String tokens[] = GameState.prompt.getPromptText().split("\\s+");
							handler.getWorld().getEntityManager().addEntity(new StaticEntity(handler,mouseX,mouseY,sWidth,sHeight,tokens[tokens.length-1]));
							makeStatic=false;
						}
						
						/////
						//MOVE
						if(!hold || !place){
							if(move){
								if(Mouse.getEventButton()==1){
									if(!(handler.getWorld().getEntityManager().getEntity(mouseX, mouseY)==null)){
										e =handler.getWorld().getEntityManager().getEntity(mouseX, mouseY);
										System.out.println("GOT THE ENTITY");
										this.hold = true;
										move=false;
										if(e instanceof StaticEntity ){
											staticEntityX=mouseX;
											staticEntityY=mouseY;
										}
									}
								}
								
							}		
						}
						
						//PLACE
						if(place){
							
							
							if(e.getClass().getSimpleName().equalsIgnoreCase("DOOR")){
								Door d=(Door)e;
								// PREVIOUS DATA
								float tx=handler.getWorld().getEntityManager().getPlayer().getX();
								float ty=handler.getWorld().getEntityManager().getPlayer().getY();
								String oldPath = handler.getWorld().getWorldPath();

								if(handler.getWorld().getWorldPath().equalsIgnoreCase(d.worlds[0])){//BRING CHANGE IN CURRENT FILE
									d.x1=mouseX;
									d.y1=mouseY;
									d.params.set(0, mouseX+"");
									d.params.set(1, mouseY+"");
									System.out.println("YEPPPPPPPP");
									//THEN SAVE IT
								}
								
								if(handler.getWorld().getWorldPath().equalsIgnoreCase(d.worlds[1])){//BRING CHANGE IN CURRENT FILE
									d.x2=mouseX;
									d.y2=mouseY;
									d.params.set(2, mouseX+"");
									d.params.set(3, mouseY+"");
									//THEN SAVE IT
								}
								WorldSave.entitySave();
								
								//DEAL WITH THE NEXT FILE
								
								for(int i=0;i<d.worlds.length;i++){//GET THE FILE TO DEAL WITH
									if(d.worlds[i].equalsIgnoreCase(handler.getWorld().getWorldPath())){
										handler.getWorld().setWorldPath(d.worlds[((i==0)?1:0)]);
										handler.getWorld().loadWorld(d.worlds[((i==0)?1:0)]);//new entity loaded
										break;
									}
								}
								
								
								for(Entity entity:handler.getWorld().getEntityManager().getEntities()){
									if( 	(entity.params.get(2).equalsIgnoreCase(e.params.get(2)) && entity.params.get(3).equalsIgnoreCase(e.params.get(3)))
										||	(entity.params.get(0).equalsIgnoreCase(e.params.get(0)) && entity.params.get(1).equalsIgnoreCase(e.params.get(1)))
										&&(entity.params.get(4).equals(e.params.get(4)) && entity.params.get(5).equals(e.params.get(5))&&
												entity.params.get(7).equals(e.params.get(7)) && entity.params.get(6).equals(e.params.get(6)) 
												)
										){
										entity.params=e.params;
									}
								}
								WorldSave.entitySave();
								handler.getWorld().loadWorld(oldPath);
								handler.getWorld().getEntityManager().getPlayer().setX(tx);
								handler.getWorld().getEntityManager().getPlayer().setY(ty);
								place=false;
								hold=false;
							}
							
							else{//OTHER ENTITIES
								e.params.set(0, mouseX+"");
								e.params.set(1, mouseY+"");
								e.setX(mouseX);
								e.setY(mouseY);
								place=false;
								hold=false;
							}
							
						}
						
						
						
						//DELETE
						if(Mouse.getEventButton() == 1 && GameState.prompt.getPromptText().equalsIgnoreCase("DELETE entity")){
							Entity e1 =(handler.getWorld().getEntityManager().getEntity(mouseX, mouseY));	
							if(e1==null){
								return;
							}
							if(e1.equals(handler.getWorld().getEntityManager().getPlayer())){
								return;
							}
							float tx=handler.getWorld().getEntityManager().getPlayer().getX();
							float ty=handler.getWorld().getEntityManager().getPlayer().getY();
							String oldPath = handler.getWorld().getWorldPath();

							handler.getWorld().getEntityManager().getEntities().remove(e1);//DOOR IN PRESENT FILE  REMOVED

						
							//REMOVE ENTITY DATA FROM OTHER FILE
							if(e1.getClass().getSimpleName().equalsIgnoreCase("Door")){//REMOVE DOOR IN THE LINKED FILE
								Door d =(Door) e1;
								WorldSave.entitySave();

								//LOAD ENTITIES OF THAT FILE
								for(int i=0;i<d.worlds.length;i++){
									if(d.worlds[i].equalsIgnoreCase(handler.getWorld().getWorldPath())){
										handler.getWorld().setWorldPath(d.worlds[((i==0)?1:0)]);
										handler.getWorld().loadWorld(d.worlds[((i==0)?1:0)]);
										break;
									}
								}//MAIN STUFF
								for(Entity e11:handler.getWorld().getEntityManager().getEntities()){
									if(e11.params.equals(e1.params)){
										handler.getWorld().getEntityManager().getEntities().remove(e11);
										break;
									}
								}
								//SAVE FILE
								WorldSave.entitySave();
								handler.getWorld().loadWorld(oldPath);
								handler.getWorld().getEntityManager().getPlayer().setX(tx);
								handler.getWorld().getEntityManager().getPlayer().setY(ty);
							}
						}
					}	
				}//MOUSE.NEXT() OVER
				
				//HOLD
				if(this.hold){
					
					if(e.getClass().getSimpleName().equalsIgnoreCase("NPC")){
						Renderer.renderSubImage(Assets.NPCsheet, mouseX-handler.getGameCamera().getxOffset(), mouseY-handler.getGameCamera().getyOffset(), Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT, Assets.characterDown[0],  0.6f);
						place=true;
						return;
					}
					
					if(e.getClass().getSimpleName().equalsIgnoreCase("DOOR")){
						Renderer.setColor(0.8f, 0.8f, 0.8f, 0.5f);
						Renderer.renderQuad(mouseX-handler.getGameCamera().getxOffset(), mouseY-handler.getGameCamera().getyOffset(), (int)e.getWidth(), (int)e.getHeight());				
						place=true;
						return;

					}
					if(e.getClass().getSimpleName().equalsIgnoreCase("StaticEntity")){
						Entity e11 = handler.getWorld().getEntityManager().getEntity(staticEntityX, staticEntityY);
						StaticEntity s=null;
						if(e11 instanceof StaticEntity)
							s = (StaticEntity) e11;
						sWidth = (int) s.getWidth();
						sHeight=(int) s.getHeight();
						Renderer.renderSubImage(Assets.statics, mouseX-handler.getGameCamera().getxOffset(), mouseY-handler.getGameCamera().getyOffset(), sWidth, sHeight,s.imageData, 0.6f);
						place=true;
						return;

					}
				}
				
	}

}
