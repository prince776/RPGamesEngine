package dev.prince.rpgGameEngine.worlds.loadAndSave;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.entities.Entity;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.utils.Utils;

public class WorldSave {
	
	private static Formatter formatter;
	private static Handler handler;
	
	public static void init(Handler handler1){
		handler = handler1;
	}
	
	public static void Save(final Handler handler1,final int x,final int y,final int width,final int height,int[][]tiles){
		Thread t=new Thread(){
			public void run(){
				handler = handler1;
				try {
					formatter = new Formatter(new File(handler.getWorld().getWorldPath()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				//ADD WIDTH AND HEIGHT
				formatter.format(width+" ");
				formatter.format(height+" \n");
				
				//ADD spawnX AND spawnY
				formatter.format(x+" ");
				formatter.format(y+" \n");
				
				//ADD TILES ARRAY TO THE FILE
				
				for(int h=0;h<height;h++){
					for(int w=0;w<width;w++){
						formatter.format(handler.getWorld().getTilesValue(w,h)+" ");
					}
					formatter.format("\n");
				}
				formatter.format(GameState.currentLevel);
				//CLOSE THE FILE
				formatter.close();
				solidTileSave();
				entitySave();
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void mainSave(){
		try {
			Formatter f = new Formatter("res/worlds/saveFile.sav");
			
			f.format(GameState.currentLocation+"\n");
			f.format(GameState.currentLevel+"\n");
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void solidTileSave(){
		if(!new File(handler.getWorld().getWorldPath().substring(0, handler.getWorld().getWorldPath().length()-5)+"solids").exists()){
			new File(handler.getWorld().getWorldPath().substring(0, handler.getWorld().getWorldPath().length()-5)+"solids");
		}
		try {
			Formatter f = new Formatter(handler.getWorld().getWorldPath().substring(0, handler.getWorld().getWorldPath().length()-5)+"solids");
		
			for(int i=0;i<handler.getWorld().getSolidTiles().size();i++){
				f.format(handler.getWorld().getSolidTiles().get(i)[0]+ " "+handler.getWorld().getSolidTiles().get(i)[1]+"\n");
			}
		f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void entitySave(){
		try {
			FileWriter fw = new FileWriter(new File(handler.getWorld().getWorldPath().substring(0, handler.getWorld().getWorldPath().length()-5)+"entity"));
			
			for(Entity e:handler.getWorld().getEntityManager().getEntities()){		
				if(!e.equals(handler.getWorld().getEntityManager().getPlayer())){
					fw.append(e.getClass().getSimpleName()+" ");
	
						for(int i=0;i<e.params.size();i++){
							fw.append(e.params.get(i)+" ");
						}
					fw.append("\n");
				}
			}
			
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * will save door in both files
	 * @param e
	 */
	public static void saveDoor(Entity e){
			try {
				String text = Utils.loadFileAsString(GameState.root+GameState.currentLocation+"/"+GameState.currentLevel.substring(0, GameState.currentLevel.length()-5)+"entity");
				FileWriter fw = new FileWriter(new File(GameState.root+GameState.currentLocation+"/"+GameState.currentLevel.substring(0, GameState.currentLevel.length()-5)+"entity"));
				fw.append(text);
				
				fw.append("Door ");
				for(int i=0;i<e.params.size();i++){
					fw.append(e.params.get(i)+" ");
				}
				fw.close();
				
				//NEXT FILE
				String text1 = Utils.loadFileAsString(GameState.root+e.params.get(8)+"/"+e.params.get(9)+".entity");
				FileWriter fw1 = new FileWriter(new File(GameState.root+e.params.get(8)+"/"+e.params.get(9)+".entity"));
				fw1.append(text1);
				fw1.append("Door ");
				for(int i=0;i<e.params.size();i++){
					fw1.append(e.params.get(i)+" ");
				}
				fw1.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
		
			}

	}
	
	public static void createFile(final String path){
		Formatter f = null;
		try {
			 f = new Formatter(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("FAILED");
			return;
		}
		f.format("15 15\n");
		f.format("10 10\n");
		for(int a=0;a<15;a++){
			f.format("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n");
		}
			f.format(path.substring(GameState.root.length()+new File(path).getParentFile().getName().length()+1));
			
		f.close();
		try {
			new File(path.substring(0,path.length()-5)+"entity").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			new File(path.substring(0,path.length()-5)+"solids").createNewFile();
			Formatter f1 = new Formatter(path.substring(0,path.length()-5)+"solids");
			f1.format("15 15\n");
			f1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	
}
}
