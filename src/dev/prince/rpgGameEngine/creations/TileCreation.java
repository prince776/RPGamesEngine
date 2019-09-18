package dev.prince.rpgGameEngine.creations;

import org.lwjgl.input.Mouse;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.tiles.Tile;
import dev.prince.rpgGameEngine.utils.Utils;

public class TileCreation extends Creation{
	private int MX,MY;
	
	private Tile tile=null;
	
	private boolean setSolid=false;
	
	public TileCreation(Handler handler) {
		super(handler);
	}

	@Override
	public void tick() {

		
		
	}

	@Override
	public void render() {
		
		//MX AND MY
		 MX =(int) ((Mouse.getX() + handler.getGameCamera().getxOffset())/Tile.TILEWIDTH);
		 MY =(int) (((handler.getHeight()-Mouse.getY()) + handler.getGameCamera().getyOffset())/Tile.TILEHEIGHT);
		 
		 if(tile!=null){
				Renderer.renderSubImage(Assets.tileSet, MX*Tile.TILEWIDTH - handler.getGameCamera().getxOffset(), MY*Tile.TILEHEIGHT -  handler.getGameCamera().getyOffset(), Tile.TILEWIDTH, Tile.TILEHEIGHT, tile.getTextureData(), 0.5f);
			}
			if(setSolid){
				Renderer.setColor(1f, 0f, 0f, 0.4f);
				Renderer.renderQuad(MX*Tile.TILEWIDTH-handler.getGameCamera().getxOffset(), MY*Tile.TILEHEIGHT-handler.getGameCamera().getyOffset(), (int)Tile.TILEWIDTH, (int)Tile.TILEHEIGHT);
			}
		
		//RETURN TILE ID----------------------
		 if(GameState.prompt.getPromptText().toLowerCase().startsWith("give id of")&&Mouse.isButtonDown(1) ){

			 for(int i=0;i<Tile.tiles.length;i++){
				 if(Tile.tiles[i] == null){
					 return;
				 }
					if(GameState.prompt.getPromptText().endsWith(Tile.tiles[i].getName())){
					 	GameState.prompt.appendText(": "+String.valueOf(Tile.tiles[i].getID()));
				 }
			 }
		 }
		 
		 //ADD TILES------------------------------------------------------
		 else if(GameState.prompt.getPromptText().toLowerCase().startsWith("use") ){
			 for(int i=0;i<Tile.tiles.length;i++){
				 if(Tile.tiles[i] == null)
					 return;
				 if(GameState.prompt.getPromptText().equalsIgnoreCase("use "+Tile.tiles[i].getName())){
					 tile = Tile.tiles[i];
					 if(Mouse.isButtonDown(1)){
						 handler.getWorld().setTilesValue(MX, MY, i);
				 }

			 }
		
			 }
		 }
		
		//REPLACING TILES FEATURE------------------------------
		 else if(GameState.prompt.getPromptText().toLowerCase().startsWith("tile to tile") && Mouse.isButtonDown(1)){
			replaceTiles(Utils.parseInt(String.valueOf(GameState.prompt.getPromptText().charAt(13))),Utils.parseInt(String.valueOf(GameState.prompt.getPromptText().charAt(15))));
			}
		
		//SET SOLID
		 else if(GameState.prompt.getPromptText().equalsIgnoreCase("set solid") ){
			 setSolid=true;
			 if(Mouse.isButtonDown(1)){
				 handler.getWorld().addSolidTile(MX, MY);
				 return;
			 }
		 }else{
			 setSolid=false;
		 }
		 
		 if(GameState.prompt.getPromptText().equalsIgnoreCase("remove solid") ){
			 setSolid=false;
			 if(Mouse.isButtonDown(1)){
				 handler.getWorld().removeSolidTile(MX, MY);
			 }
		 }
		
		
			
	
	}
	
	//HELPER METHOD

	public void replaceTiles(int a,int b){
		for(int y=0;y<handler.getWorld().getHeight();y++){
			for(int x=0;x<handler.getWorld().getWidth();x++){
				if(handler.getWorld().getTilesValue(x, y) == a){
					handler.getWorld().setTilesValue(x, y,b);
				}
			}
		}
	}
	
}
