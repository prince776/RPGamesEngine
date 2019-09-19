package dev.prince.rpgGameEngine.tiles;


public class VoidTile extends Tile{
	
	static float[] data ={0,0,0,0};
	
	public VoidTile(int id) {
		super(data, id, "void");
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(float x, float y) {
	//	if(GameState.createWorld){
			//zIndex=1;
			//Renderer.setColor(0f, 0f, 0f, 0.5f);
			//Renderer.renderQuad(x, y, (int)TILEWIDTH, (int)TILEHEIGHT);
		//}else{
		//	zIndex=0;
			//Renderer.renderSubImage(Assets.tileSet, x, y, TILEWIDTH, TILEHEIGHT, Assets.grass,1);
	//	}
	
	}
	
	public boolean isSolid(){
		return true;
	}

}
