package dev.prince.rpgGameEngine.entities;


import org.lwjgl.util.Rectangle;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;

public  class Item extends Entity {
	
	private float[] imageData;
	
	
	public Item(Handler handler, float x, float y, float width, float height,String name) {
		super(handler, x, y, width, height);
		
		this.imageData = Assets.getImageData(name);
		this.bounds = new Rectangle(0,0,(int)width,(int)height);
		isItem = false;
		params.clear();
		params.add( (int)x+"");
		params.add((int)y+"");
		params.add( (int)width+"");
		params.add( (int)height+"");
		params.add( name);
		
	}
	

	@Override
	public void tick() {
		
	}

	@Override
	public void render() {
		Renderer.renderSubImage(Assets.items, x-handler.getGameCamera().getxOffset(), y-handler.getGameCamera().getyOffset(), width, height, imageData, (byte) 1f);
	}

}
