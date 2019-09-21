package dev.prince.rpgGameEngine.features.weathers;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.features.Clock;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.utils.Utils;

public class Rain {

	private boolean rain = false, setRainParams = true;
	public int[] rainVars, old;
	private int y = 0;
	
	public int rainSpeed = 2;
	
	private int thunderParam = 0,thunderProb = 2;
	private int thunderDuration = 0; 
	private Handler handler;

	public Rain(Handler handler) {
		this.handler = handler;
		rainVars = new int[2];
		old = new int[2];
	}

	public void tick() {
		if (setRainParams) {
			setRainParams();
		}
		checkRain(rainVars[0], rainVars[1]);//sets rain =true/false
		
		if (handler.getTime() > (((rainVars[0] + rainVars[1] + 120) >= Clock.maxHours* Clock.maxMinutes) ? (Clock.maxHours * Clock.maxMinutes) + 58
					: rainVars[0] + rainVars[1] + 120)) {//reassign rain params
			setRainParams = true;
		}
	}

	public void render() {
		if (rain) {
			
			rain(rainSpeed);
			thunderParam = Utils.getRandomInt(0, 100);
			if(thunderParam <= thunderProb)
				thunder();
			
		}
	}
	
	public void thunder(){
		//play sound
		handler.getSFX().playSoundEffect(handler.getSFX().thunder, 1, 1, 0);
		//render
		Renderer.setColor(1, 1, 1, 0.88f);
		Renderer.renderQuad(0, 0, handler.getWidth(), handler.getHeight());
	}
	public boolean thunder(long lastTime,int duration){

		long now = System.currentTimeMillis();
		thunderDuration += now - lastTime;
		
		//play sound
		handler.getSFX().playSoundEffect(handler.getSFX().thunder, 1, 1, 0);
		
		//render
		Renderer.setColor(1, 1, 1, 0.88f);
		Renderer.renderQuad(0, 0, handler.getWidth(), handler.getHeight());
		
		if(thunderDuration > duration){
			thunderDuration = 0;
			return false;
		}
		
		return true;
		
	}
	
	public void rain(int speed){
		// Play sound
		if (!handler.getSFX().walk.get(3).isPlaying())
			handler.getSFX().playSoundEffect(handler.getSFX().walk.get(3),
					1, 1, 0);

		// Render
//		if (handler.getWidth() >= 1280 || handler.getHeight() >= 720) {
//			y += 5;
//		}
		Renderer.renderImage(Assets.rain, 0, y - handler.getHeight(),handler.getWidth(), handler.getHeight(), 1f);
		Renderer.renderImage(Assets.rain, 0, y, handler.getWidth(),handler.getHeight(), 1f);
		y += speed;
		if (y > handler.getHeight()) y = 0;
	}
	
	//RAIN CALCULATION METHODS
	
	public void setRainParams() {
		int startTime = Utils.getRandomInt(0,
				(int) ((Clock.maxHours * Clock.maxMinutes)));

		int duration = Utils.getRandomInt(60, 180);

		setRainParams = !setRainParams;
		rainVars[0] = startTime;
		rainVars[1] = duration;

	}

	public void checkRain(int startTime, int duration) {
		if (handler.getTime() >= startTime
				&& handler.getTime() <= startTime + duration) {
			rain = true;
		} else {
			rain = false;
		}
	}

}
