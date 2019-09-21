package dev.prince.rpgGameEngine.states;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.creations.Creation;
import dev.prince.rpgGameEngine.creations.EffectsCreation;
import dev.prince.rpgGameEngine.creations.EntityCreation;
import dev.prince.rpgGameEngine.creations.MapCreation;
import dev.prince.rpgGameEngine.creations.TileCreation;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.inputs.KeyManager;
import dev.prince.rpgGameEngine.tiles.Tile;
import dev.prince.rpgGameEngine.ui.TextArea;
import dev.prince.rpgGameEngine.worlds.loadAndSave.WorldSave;

public class WorldCreationState extends State{
	
	
	private Color c=Color.darkGray.darker();
	private float fadeValue=1.0f,fadeMagnitude=0f;	
	private boolean save=false;
	
	private Creation[] modes;
	
	public WorldCreationState(Handler handler) {
		super(handler);
		
		modes = new Creation[6];
		modes[0] = new TileCreation(handler);
		modes[1] = new MapCreation(handler);
		modes[2] = new EntityCreation(handler);
		modes[3] = new EffectsCreation(handler);
	}
	
	@Override
	public void tick() {
		
		if(((GameState.prompt.getPromptText().equalsIgnoreCase("debug end")) && Keyboard.isKeyDown(Keyboard.KEY_RETURN))){
			GameState.createWorld=false;
			System.out.println("EXITING WORLD CREATION STATE");
		}
		
		//SET CREATIONS ACCORDINGLY
		 if(GameState.prompt.getPromptText().toLowerCase().startsWith("enter") ){
			 for(int i=0;i<modes.length;i++){
				 if(modes[i] == null)
					 return;
				 if(GameState.prompt.getPromptText().equalsIgnoreCase("enter "+modes[i].getClass().getSimpleName().substring(0, modes[i].getClass().getSimpleName().length()-8))){
					 if(KeyManager.value == Keyboard.KEY_RETURN ){
							Creation.setCreation(modes[i]);
					 }
				 }
			 }
		
		 }
		if(Creation.getCreation()!=null){
			Creation.getCreation().tick();
		}
	}
	
	
	
	@Override
	public void render() {
		
		 //RENDER INFO----------------------------
		 TextArea.renderTextArea(7, 5, "DEBUG: TRUE", Color.black);
		 TextArea.renderTextArea(7, 30, "X: "+ (int)handler.getWorld().getEntityManager().getPlayer().getX(), Color.black);
		 TextArea.renderTextArea(7, 55, "Y: "+ (int)handler.getWorld().getEntityManager().getPlayer().getY(), Color.black);
		 TextArea.renderTextArea(7, 80, "Tile X: "+ (int)((handler.getWorld().getEntityManager().getPlayer().getX()+handler.getWorld().getEntityManager().getPlayer().getWidth())/Tile.TILEWIDTH), Color.black);
		 TextArea.renderTextArea(7, 105, "Tile Y: "+ (int)((handler.getWorld().getEntityManager().getPlayer().getY()+handler.getWorld().getEntityManager().getPlayer().getHeight())/Tile.TILEHEIGHT), Color.black);
		 TextArea.renderTextArea(7,  130, "Level: "+GameState.currentLevel, Color.black);

		 
		 if(Creation.getCreation()!=null){
			 TextArea.renderTextArea(7,  155, "MODE: "+Creation.getCreation().getClass().getSimpleName(), Color.black);
				Creation.getCreation().render();
			}
			
		

		
	}
	
	
	//OTHER FATURES
	
	public void replaceTiles(int a,int b){
		for(int y=0;y<handler.getWorld().getHeight();y++){
			for(int x=0;x<handler.getWorld().getWidth();x++){
				if(handler.getWorld().getTilesValue(x, y) == a){
					handler.getWorld().setTilesValue(x, y,b);
				}
			}
		}
	}
	
	//SAVING CODE..
	
	public void saveWorld(){
		WorldSave.Save(handler,(int)handler.getWorld().getEntityManager().getPlayer().getX(),(int) handler.getWorld().getEntityManager().getPlayer().getY(), handler.getWorld().getWidth(), handler.getWorld().getHeight(), handler.getWorld().getTiles());
	}
	
	public void saveGame(){
		saveWorld();
		WorldSave.mainSave();
	}
	
	public void giveMessage(Color c,String message){
		Renderer.renderString(handler.getWidth()-(message.length()*25), 25f, message, c, true);
	
	}
	
	//GETTERS
	public EffectsCreation getEffects(){
		
		return (EffectsCreation) modes[3];
		
	}
	
}
