package dev.prince.rpgGameEngine.entities.statics;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.entities.Entity;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;

public  class StaticEntity extends Entity {
	
	public float[] imageData;
	
	//public Texture texture;
	
	public StaticEntity(Handler handler, float x, float y, float width,float height,String name) {
		super(handler, x, y, width, height);
		//this.texture=Assets.statics;
		this.imageData=Assets.getImageData(name);
		this.bounds=new Rectangle(0,0,0,0);
		isStatic=true;
		params.clear();
		params.add( (int)x+"");
		params.add((int)y+"");
		params.add( (int)width+"");
		params.add( (int)height+"");
		params.add( name);
		luminous=true;
		if(params.get(4).equalsIgnoreCase("mat")){
			zIndex=-1;
		}
		
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render() {
		Renderer.renderSubImage(Assets.statics, x-handler.getGameCamera().getxOffset(), y-handler.getGameCamera().getyOffset(), width, height, imageData,  1f);
		
	}

}
