package dev.prince.rpgGameEngine.features;

import dev.prince.rpgGameEngine.Game;
import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.net.packets.Packet03Weather;
import dev.prince.rpgGameEngine.utils.Utils;

public class Weather {
	
	private Handler handler;
	private boolean rain=false,runRainFunc=true;
	public int[] rainVars,old;
	
	public static Packet03Weather packet = null ;
	
	public Weather(Handler handler,Clock clock){
		this.handler=handler;
		rainVars=new int[2];
		old = new int[2];
		//rain();
		packet = new Packet03Weather((int)clock.hrs,(int)clock.minutes,rainVars[0],rainVars[1]);
		
	}
	
	public void tick(){
		
	}
	
	public void render(){
		
		decideRain();
		dayNight();

	}
	
	public void decideRain(){
		if(!Game.joinServer){
			if(runRainFunc){
				rain();
			}
			checkRain(rainVars[0],rainVars[1]);
			if(rain){
				handler.getGameState().getGameCreationSate().getEffects().rain();
			}
			
			if(handler.getTime() >(((rainVars[0]+rainVars[1]+120) >= Clock.maxHours*Clock.maxMinutes )? (Clock.maxHours*Clock.maxMinutes)+58 : rainVars[0]+rainVars[1]+120)){
				runRainFunc=true;
			}
		}
		if(Game.joinServer){
			if(handler.getTime() >= packet.rainStart && handler.getTime() <= packet.rainStart+packet.rainDuration){
				handler.getGameState().getGameCreationSate().getEffects().rain();
			}
		}
	}
	
	//WEATHERS
	
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
	
	/**
	 * Manipulates and decides the timing and duration of rain
	 */
	public void rain(){
		int startTime = Utils.getRandomInt(0, (int) ((Clock.maxHours*Clock.maxMinutes)));

		int duration = Utils.getRandomInt(60, 180);
		System.out.println("Duration:" +duration+ " StartTime: "+startTime);
		
		runRainFunc = !runRainFunc;
		//repeat=!repeat;
		rainVars[0]=startTime;
		rainVars[1]=duration;
		
		packet.setRainStart(rainVars[0]);
		packet.setRainDuration(rainVars[1]);
		packet.setStartHour(handler.getClock().hrs);
		packet.setStartMinute(handler.getClock().minutes);
		if(Game.isServer)
			packet.sendToAllClients(handler.getServer());
	}
	/**
	 * checks if it is the time to rain
	 * @param startTime
	 * @param duration
	 */
	public void checkRain(int startTime,int duration){
		if(handler.getTime() >= startTime && handler.getTime() <= startTime+duration){
			rain=true;
		}else{
			rain = false;
		}
	}

	public static Packet03Weather getPacket() {
		return packet;
	}

}