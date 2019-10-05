package dev.prince.rpgGameEngine.features;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;

public class Light {
	
	private Handler handler;
	
	private int x,y;
	private int radius;
	private float[] color;
	
	private float minIntensity;
	private float maxIntensity;
	private int LOD;
	private float dIntensity;
	
	/**
	 * 
	 * @param x = center position of light source in x axis
	 * @param y	= center position of light source in y axis
	 * @param radius
	 * @param color = new float[]{r,g,b}, don't include alpha values
	 * @param minIntensity
	 * @param maxIntensity
	 * @param LOD = no of circles. More the no of circles for fluent lighting is (<= radius)
	 */
	public Light(Handler handler,int x, int y, int radius, float[] color, float minIntensity, float maxIntensity, int LOD){
		
		this.handler = handler;
		
		if(minIntensity == -1)	minIntensity = 0.0025f;
		if(maxIntensity == -1)	maxIntensity = 0.8f;
		if(LOD == -1)			LOD = radius/2;
		
		this.x=x;
		this.y=y;
		this.radius=radius;
		this.color=color;
		this.minIntensity = minIntensity;
		this.maxIntensity = maxIntensity;
		this.LOD = LOD;
		
		if(this.LOD > this.radius)	this.LOD = this.radius;
		if(this.maxIntensity > 1)	this.maxIntensity =1;
		if(this.minIntensity <0)	this.minIntensity = 0;
		
		this.dIntensity = (maxIntensity - minIntensity)/LOD;
		
	}
	
	
	public void tick(){
		
	}
	
	public void render(){
		float r = radius/LOD;
		for(int i = LOD ;i >= 1; i--){
			Renderer.renderImage(Assets.light, x-i*r - handler.getGameCamera().getxOffset(), y-i*r - handler.getGameCamera().getyOffset(), 2*r*i, 2*r*i, new float[]{color[0],color[1],color[2],minIntensity + dIntensity});
		}
	}

	//getters

	public Handler getHandler() {
		return handler;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRadius() {
		return radius;
	}

	public float[] getColor() {
		return color;
	}

	public float getMinIntensity() {
		return minIntensity;
	}

	public float getMaxIntensity() {
		return maxIntensity;
	}

	public int getLOD() {
		return LOD;
	}

	public float getdIntensity() {
		return dIntensity;
	}
	
}
