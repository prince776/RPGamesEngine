package dev.prince.rpgGameEngine.features.weathers;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.features.Clock;
import dev.prince.rpgGameEngine.gfx.Renderer;

public class Weather {
	
	private Handler handler;
	
	private Rain rain;
	
	public Weather(Handler handler,Clock clock){
		this.handler=handler;
		this.rain = new Rain(handler);
	}
	
	public void tick(){
		rain.tick();
	}
	
	public void render(){
		
		rain.render();
		dayNight();

	}
	
	
	/**
	 * Manipulates Light w.r.t time;
	 */
	public void dayNight(){
		if(!handler.getGameState().getGameCreationSate().getEffects().isSetLight()){//IF manual light is not being set
			float minElapsed =(handler.getClock().hrs*Clock.maxMinutes)+handler.getClock().minutes;
			if(handler.getClock().hrs<=Clock.maxHours/2   )
				Renderer.setColor(0f, 0f, 0f,1f- (minElapsed)/780f -0.1f);
			else if(handler.getClock().hrs>Clock.maxHours/2)
				Renderer.setColor(0f, 0f, 0f,1f - (1440 - minElapsed)/780f - 0.1f);
			Renderer.renderQuad(0, 0, handler.getWidth(), handler.getHeight());	
		}
	}
	
	
	public Rain getRain(){
		return rain;
	}
}