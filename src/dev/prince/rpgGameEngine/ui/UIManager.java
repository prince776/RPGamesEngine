package dev.prince.rpgGameEngine.ui;

import java.util.ArrayList;

import dev.prince.rpgGameEngine.Handler;

public class UIManager {
	
	private ArrayList<UIObject> uiObjects;
	
	public UIManager(Handler handler){
		uiObjects = new ArrayList<UIObject>();
	}
	
	public void tick(){
		for(UIObject uiobject : uiObjects){
			uiobject.tick();
		}
	}
	
	public void render(){
		for(UIObject uiobject : uiObjects){
			uiobject.render();
		}
	}
	
	//HELPER METHODS
	public void addUIObject(UIObject o){
		uiObjects.add(o);
	}
	public void removeUIObject(UIObject o){
		uiObjects.remove(o);
	}
	
}
