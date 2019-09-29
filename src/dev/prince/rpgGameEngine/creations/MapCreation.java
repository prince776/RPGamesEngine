package dev.prince.rpgGameEngine.creations;

import java.io.File;

import org.lwjgl.input.Keyboard;

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
		
	}

	@Override
	public void render() {
		
		String command = GameState.prompt.getPromptText().toLowerCase();
		
		//INCREASE WORLD SIZE--------------------------------------
		 if(command.startsWith("set world width")&& Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			 int Width = Utils.parseInt(GameState.prompt.getPromptText().substring(16,GameState.prompt.getPromptText().length()));
			 WorldSave.Save(handler,(int)handler.getWorld().getEntityManager().getPlayer().getX(),(int) handler.getWorld().getEntityManager().getPlayer().getY(), Width, handler.getWorld().getHeight(), handler.getWorld().getTiles());
			 handler.getWorld().loadWorld(handler.getWorld().getWorldPath());
		 }
		 
		 else if(command.startsWith("set world height")&& Keyboard.isKeyDown(Keyboard.KEY_RETURN) ){
			 int Height = Utils.parseInt(GameState.prompt.getPromptText().substring(17,GameState.prompt.getPromptText().length()));
				WorldSave.Save(handler,(int)handler.getWorld().getEntityManager().getPlayer().getX(),(int) handler.getWorld().getEntityManager().getPlayer().getY(), handler.getWorld().getWidth(), Height, handler.getWorld().getTiles());
			 handler.getWorld().loadWorld(handler.getWorld().getWorldPath());
		 }
		 
		 //MAKE FOLDER-------------------------------------
		 else if(command.startsWith("create location") && Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			 String[] s=GameState.prompt.getPromptText().split("\\s+");
			 if(!new File("res/worlds/"+s[s.length-1]).exists()){
				 new File("res/worlds/"+s[s.length-1]).mkdir();
			 }
			 //TODO:when chat box is added push this on that
			 GameState.prompt.setPromptText(s[s.length-1]+" LOCATION CREATED ");
		 }
		 //MAKE LEVEL-----------------------------------------------
		 else if(command.startsWith("create level") && Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			 String[] s=GameState.prompt.getPromptText().split("\\s+");
			 if(!new File("res/worlds/"+s[s.length-2]+"/"+s[s.length-1]).exists()){
				 WorldSave.createFile("res/worlds/"+s[s.length-2]+"/"+s[s.length-1]+".level");
			 }
			 //TODO:when chat box is added push this on that
			 GameState.prompt.setPromptText(s[s.length-1]+" LEVEL CREATED ");
		 }
	}
}
