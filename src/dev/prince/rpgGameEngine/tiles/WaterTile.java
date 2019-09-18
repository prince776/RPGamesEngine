package dev.prince.rpgGameEngine.tiles;

import dev.prince.rpgGameEngine.gfx.Animation;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;

public class WaterTile extends Tile{
	
	private Animation waterAnim;
	
	
	public WaterTile(int id) {
		super(Assets.water[0], id,"water");
		waterAnim = new Animation(Assets.water,200);
	}

	@Override
	public void tick() {
		waterAnim.animTick();
	}

	@Override
	public void render(float x, float y) {
		Renderer.renderSubImage( Assets.tileSet,x, y, TILEWIDTH, TILEHEIGHT, waterAnim.getCurrentFrame(), 1);
	}

}
