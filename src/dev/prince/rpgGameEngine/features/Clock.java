package dev.prince.rpgGameEngine.features;

import org.newdawn.slick.Color;

import dev.prince.rpgGameEngine.Game;
import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.fonts.Fonts;
import dev.prince.rpgGameEngine.ui.TextArea;

public class Clock {
	public static int maxMinutes=60,maxHours=24;
	
	public float interval =Game.UPS,count=0;//Game.FPS;
	
	public int startH,startM;
	public int hrs = 0,minutes = 0;
	
	private Handler handler;
	private Weather weatherSystem;
	
	
	public Clock(Handler handler,int hr,int min){
		this.handler=handler;
		
		startH=hr;
		startM=min;
		
		if(!Game.joinServer){//IN THIS CASE SERVER WILL PROVIDE TIME
			hrs=startH;
			minutes=startM;
		}
	}
	
	public void init(){
		
		weatherSystem = new Weather(handler,this);
	}
	
	public void tick(){
		count++;
		if(count>=interval){
			minutes+=1;
			if(minutes>maxMinutes){
				minutes=0;
				hrs+=1;
				if(hrs>=maxHours)
					hrs=0;
			}
			count=0;
		}
		
		weatherSystem.tick();
		
	}
	
	public void render(){
		String time = (int)hrs + " : " + (int)minutes;
		TextArea.renderTextArea(handler.getWidth()-(70 + Fonts.font.getWidth(time)), handler.getHeight()-(10 + Fonts.font.getHeight(time)), time, Color.white, new float[]{0,0,0,0}, new float[]{0,0,0,0},false);
		
		weatherSystem.render();
		
		//RENDER EFFECT
		/*float minElapsed =(hrs*60)+minutes;
		if(hrs<=12   )
			Renderer.setColor(0f, 0f, 0f,1f- (minElapsed)/780f -0.1f);
		else if(hrs>12)
			Renderer.setColor(0f, 0f, 0f,1f - (1440 - minElapsed)/780f - 0.1f);
		Renderer.renderQuad(0, 0, handler.getWidth(), handler.getHeight());	*/
	}
	
	public float getTime(){
		return ((hrs*60)+minutes);
	}

	public Weather getWeatherSystem() {
		return weatherSystem;
	}

	
}
