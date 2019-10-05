package dev.prince.rpgGameEngine.features;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import dev.prince.rpgGameEngine.Handler;

public class LightManager {
	
	private Handler handler;
	private ArrayList<Light> lights;
	
	public LightManager(Handler handler){
		this.handler = handler;
		lights = new ArrayList<Light>();
	}
	
	public void tick(){
		for(int i=0;i<lights.size();i++){
			lights.get(i).tick();
		}
	}
	
	public void render(){
		for(int i=0;i<lights.size();i++){
			lights.get(i).render();
		}
	}
	
	public void addLight(Light l){
		lights.add(l);
	}
	
	public void removeLight(Light l){
		lights.remove(l);
	}
	
	public void removeLight(int i){
		lights.remove(i);
	}
	
	public ArrayList<Light> getLights(){
		return lights;
	}
	
}
