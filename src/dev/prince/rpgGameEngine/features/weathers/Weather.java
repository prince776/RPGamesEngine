package dev.prince.rpgGameEngine.features.weathers;

import java.util.HashMap;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.features.Clock;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.utils.Utils;

public class Weather {
	
	private Handler handler;
	
	private Rain rain;
	
	public HashMap<String, String> worldParams;
	private String[] excludedWeathers;
	private String permanentWeather;
	
	public Weather(Handler handler,Clock clock){
		this.handler=handler;
		this.rain = new Rain(handler);
		this.worldParams = handler.getWorld().getWorldParams();
		this.excludedWeathers = worldParams.get("ExcludedWeathers").split(",");
	}
	
	public void tick(){
		rain.tick();
	}
	
	public void render(){
		if(worldParams.get("PermanentWeather").equalsIgnoreCase("none")){//no permanent weather
			if(worldParams.get("Indoor").equals("false")){//not indoor
				
				//Doing this in case excluded weather list is being changed by creation mode(feature to be added)
				this.excludedWeathers = worldParams.get("ExcludedWeathers").split(",");
				
				if(!isExcluded("rain")){//if rain not excluded
					rain.rainSpeed = Utils.parseInt(worldParams.get("Rain"));
					rain.render();
				}
			
			}
		}
		
		permanentWeather = worldParams.get("PermanentWeather");
		
		if(!permanentWeather.equalsIgnoreCase("none")){
			switch(permanentWeather){
			case "rain":
				rain.rainSpeed = Utils.parseInt(worldParams.get("Rain"));
				rain.renderUnconditionally();
			default:
				break;
			}
		}
		
		if(worldParams.get("PermanentLight").equals("-1"))
			dayNight();
		else{
			setLight(Utils.parseInt(worldParams.get("PermanentLight")));
		}
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
	
	public void setLight(float value){
		Renderer.setColor(0, 0, 0, 1-value/100);
		Renderer.renderQuad(0, 0, handler.getWidth(),handler.getHeight());
	}
	
	public boolean isExcluded(String weatherName){
		for(int i=0;i<excludedWeathers.length;i++){
			if(excludedWeathers[i].equalsIgnoreCase(weatherName))
				return true;
		}
		return false;
	}
	
	public Rain getRain(){
		return rain;
	}
}