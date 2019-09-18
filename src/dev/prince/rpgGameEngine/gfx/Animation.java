package dev.prince.rpgGameEngine.gfx;

public class Animation {
	
	float[][] frames;
	int animSpeed;
	int index =0;
	
	long lastTime,now,timer=0;
	
	public Animation(float[][] images,int animSpeed){
		this.frames=images;
		this.animSpeed=animSpeed;
		
		lastTime = System.currentTimeMillis();
	}
	
	public void animTick(){
		now = System.currentTimeMillis();
		timer+=now-lastTime;
		lastTime=now;
		if(timer>=animSpeed){
			timer=0;
			index++;
		}
		if(index>=frames.length){
			index=0;
		}
	}
	
	public float[] getCurrentFrame(){
		return frames[index];
	}
	
	public void setAnimSpeed(int animSpeed) {
		this.animSpeed = animSpeed;
	}
	
	
	
}	
