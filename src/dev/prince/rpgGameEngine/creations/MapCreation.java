package dev.prince.rpgGameEngine.creations;

import java.io.File;

import org.lwjgl.input.Mouse;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.utils.Utils;
import dev.prince.rpgGameEngine.worlds.loadAndSave.WorldSave;

public class MapCreation extends Creation{

	public MapCreation(Handler handler) {
		super(handler);
	}

	@Override
	public void tick() {
		//INCREASE WORLD SIZE--------------------------------------
		/* if(GameState.prompt.getPromptText().toLowerCase().startsWith("set world width")&&Mouse.isButtonDown(1) ){
			 int Width = Utils.parseInt(GameState.prompt.getPromptText().substring(16,GameState.prompt.getPromptText().length()));
			WorldSave.Save(handler,(int)handler.getWorld().getEntityManager().getPlayer().getX(),(int) handler.getWorld().getEntityManager().getPlayer().getY(), Width, handler.getWorld().getHeight(), handler.getWorld().getTiles());
			 handler.getWorld().loadWorld(handler.getWorld().getWorldPath());
		 }
		 
		 else if(GameState.prompt.getPromptText().toLowerCase().startsWith("set world height")&&Mouse.isButtonDown(1) ){
			 int Height = Utils.parseInt(GameState.prompt.getPromptText().substring(17,GameState.prompt.getPromptText().length()));
				WorldSave.Save(handler,(int)handler.getWorld().getEntityManager().getPlayer().getX(),(int) handler.getWorld().getEntityManager().getPlayer().getY(), handler.getWorld().getWidth(), Height, handler.getWorld().getTiles());
			 handler.getWorld().loadWorld(handler.getWorld().getWorldPath());
		 }
		 
		 //MAKE FOLDER-------------------------------------
		 else if(GameState.prompt.getPromptText().toLowerCase().startsWith("create location") && Mouse.isButtonDown(1) ){
			 String[] s=GameState.prompt.getPromptText().split("\\s+");
			 if(!new File("res/worlds/"+s[s.length-1]).exists()){
				 new File("res/worlds/"+s[s.length-1]).mkdir();
			 }
			 GameState.prompt.setPromptText(s[s.length-1]+" LOCATION CREATED ");
		 }
		 //MAKE LEVEL-----------------------------------------------
		 else if(GameState.prompt.getPromptText().toLowerCase().startsWith("create level") && Mouse.isButtonDown(1) ){
			 String[] s=GameState.prompt.getPromptText().split("\\s+");
			 if(!new File("res/worlds/"+s[s.length-2]+"/"+s[s.length-1]).exists()){
				 WorldSave.createFile("res/worlds/"+s[s.length-2]+"/"+s[s.length-1]+".level");
			 }
			 GameState.prompt.setPromptText(s[s.length-1]+" LEVEL CREATED ");
		 }*/
	}

	@Override
	public void render() {
		//INCREASE WORLD SIZE--------------------------------------
		 if(GameState.prompt.getPromptText().toLowerCase().startsWith("set world width")&&Mouse.isButtonDown(1) ){
			 int Width = Utils.parseInt(GameState.prompt.getPromptText().substring(16,GameState.prompt.getPromptText().length()));
			WorldSave.Save(handler,(int)handler.getWorld().getEntityManager().getPlayer().getX(),(int) handler.getWorld().getEntityManager().getPlayer().getY(), Width, handler.getWorld().getHeight(), handler.getWorld().getTiles());
			 handler.getWorld().loadWorld(handler.getWorld().getWorldPath());
		 }
		 
		 else if(GameState.prompt.getPromptText().toLowerCase().startsWith("set world height")&&Mouse.isButtonDown(1) ){
			 int Height = Utils.parseInt(GameState.prompt.getPromptText().substring(17,GameState.prompt.getPromptText().length()));
				WorldSave.Save(handler,(int)handler.getWorld().getEntityManager().getPlayer().getX(),(int) handler.getWorld().getEntityManager().getPlayer().getY(), handler.getWorld().getWidth(), Height, handler.getWorld().getTiles());
			 handler.getWorld().loadWorld(handler.getWorld().getWorldPath());
		 }
		 
		 //MAKE FOLDER-------------------------------------
		 else if(GameState.prompt.getPromptText().toLowerCase().startsWith("create location") && Mouse.isButtonDown(1) ){
			 String[] s=GameState.prompt.getPromptText().split("\\s+");
			 if(!new File("res/worlds/"+s[s.length-1]).exists()){
				 new File("res/worlds/"+s[s.length-1]).mkdir();
			 }
			 GameState.prompt.setPromptText(s[s.length-1]+" LOCATION CREATED ");
		 }
		 //MAKE LEVEL-----------------------------------------------
		 else if(GameState.prompt.getPromptText().toLowerCase().startsWith("create level") && Mouse.isButtonDown(1) ){
			 String[] s=GameState.prompt.getPromptText().split("\\s+");
			 if(!new File("res/worlds/"+s[s.length-2]+"/"+s[s.length-1]).exists()){
				 WorldSave.createFile("res/worlds/"+s[s.length-2]+"/"+s[s.length-1]+".level");
			 }
			 GameState.prompt.setPromptText(s[s.length-1]+" LEVEL CREATED ");
		 }
	}
}
