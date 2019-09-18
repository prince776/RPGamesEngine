package dev.prince.rpgGameEngine.gfx;



import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import dev.prince.rpgGameEngine.utils.Utils;

public class Assets {
	
	public static float[] grass,floor,sand,brick,stairs,path,woodFloor,darkWoodFloor;
	
	public  float[] tree,house,pokeCenter,rock,fenceX,sign,mailBox,inscription,lab;
	public  float[] pc,bed,bookShelf,mat,table,machine,cpu,tv,pot,painting;
	public  float[] chairLeft,chairRight;
	
	public static float[] flint, arrow, bow;
	
	public static ArrayList<String> names;
	
	public static ArrayList<float[]> staticEntities;
	
	public static Texture tileSet , playerSheet , playerSwimSheet , NPCsheet,NPCswimSheet,statics,items,rain;
	public static Texture logo,credit;
	
	public static float[][] characterDown,characterUp,characterLeft,characterRight;
	
	
	public static float[][] water;

	public static float tileWidth=32,tileHeight=32;

	public  void init(){
		staticEntities = new ArrayList<float[]>();
		
		names = new ArrayList<String>();
		
		//INITIALIZE SHEETS
		
	
		
		tileSet = ImageLoader.loadImage("res/textures/tileSetNew.png", "PNG");
		playerSheet = ImageLoader.loadImage("res/textures/redPlayer.png", "PNG");
		playerSwimSheet = ImageLoader.loadImage("res/textures/playerSheetSwim.png", "PNG");
		NPCsheet = ImageLoader.loadImage("res/textures/greenPlayer.png", "PNG");
		NPCswimSheet = ImageLoader.loadImage("res/textures/playerSheetSwim.png", "PNG");
		statics = ImageLoader.loadImage("res/textures/statics.png", "PNG");
		items = ImageLoader.loadImage("res/textures/items.png", "PNG");
		rain = ImageLoader.loadImage("res/textures/rain.png", "PNG");

		//INITIALIZE PICTURES
		logo = ImageLoader.loadImage("res/textures/logo.png", "PNG");
		credit = ImageLoader.loadImage("res/textures/creditState.png", "PNG");

		//INITIALIZE TILES DATA
		floor = new float[5];
		grass = new float[5];
		sand = new float[5];
		brick = new float[5];
		stairs = new float[5];
		path = new float[5];
		woodFloor = new float[5];
		darkWoodFloor = new float[5];

		//INITIALIZE STATICS
		tree = new float[5];//tree
		house = new float[5];
		pokeCenter = new float[5];
		rock = new float[5];
		fenceX = new float[5];
		sign = new float[5];
		mailBox = new float[5];
		inscription = new float[5];
		lab = new float[5];		
		pc = new float[5];//tree
		bed = new float[5];//tree
		bookShelf = new float[5];
		mat = new float[5];
		table = new float[5];
		machine = new float[5];
		cpu = new float[5];
		tv = new float[5];
		pot = new float[5];
		painting = new float[5];
		chairLeft = new float[5];
		chairRight = new float[5];
		
		//INITIALIZE ITEMS
		flint = new float[5];
		bow=new float[5];
		arrow = new float[5];
		
		//INITIALIZE ANIMATED TILE DATA
		water = new float[2][5];

		//INITIALIZE PLAYER DATA
		characterDown = new float[4][5];//4 player images all have 5 float data
		characterUp = new float[4][5];//4 player images all have 5 float data
		characterLeft = new float[4][5];//4 player images all have 5 float data
		characterRight = new float[4][5];//4 player images all have 5 float data
		
		
		//FILL TILE FLOAT DATA
		grass = fillTileData(grass,0,0,8,8);//
		sand = fillTileData(sand , 1,0,8,8);//
		stairs = fillTileData(stairs , 2,0,8,8);//
		path = fillTileData(path,3,0,8,8);//
		woodFloor = fillTileData(woodFloor,6,0,8,8);//	
		brick = fillTileData(brick,7,0,8,8);//
		floor = fillTileData(floor,0,1,8,8);//	
		darkWoodFloor = fillTileData(darkWoodFloor,2,1,8,8);//

		
		//FILL STATICS DATA//////////////////////////////////////////////////////////////
		tree = fillTileData(tree,1,1,8,8);
		house = fillTileData(house,0,2,4,4);
		pokeCenter = fillTileData(pokeCenter,2,0,4,4);
		rock = fillTileData(rock,12,0,16,16);
		fenceX = fillTileData(fenceX,15,0,16,16);
		inscription = fillTileData(inscription,4,4,16,16);
		sign = fillTileData(sign,6,4,16,16);
		mailBox = fillTileData(mailBox,5,4,16,16);
		lab = fillTileData(lab,0,3,4,4);
		
		pc = fillTileData(pc,2,5,8,16);
		bed = fillTileData(bed,6,3,16,8);
		bookShelf = fillTileData(bookShelf,2,3,8,8);
		mat = fillTileData(mat,11,4,16,16);
		table = fillTileData(table,4.5f,4,8,16);
		machine = fillTileData(machine,3.5f,2,8,8);
		cpu = fillTileData(cpu,14,0,16,16);
		tv = fillTileData(tv,14,1,16,16);
		pot = fillTileData(pot,9,5,16,16);
		painting = fillTileData(painting,10,5,16,16);
		chairLeft = fillTileData(chairLeft,12,1,16,16);
		chairRight = fillTileData(chairRight,13,1,16,16);
		
		//FILL ITEMS DATA
		flint = fillTileData(flint,6,0,16,16);
		bow = fillTileData(bow,15,1,16,16);
		arrow = fillTileData(arrow,15,2,16,16);
		
		//ADD STATIC ENTITIES
		staticEntities.add(tree);
		staticEntities.add(house);
		staticEntities.add(pokeCenter);
		staticEntities.add(rock);
		staticEntities.add(fenceX);
		staticEntities.add(sign);
		staticEntities.add(inscription);
		staticEntities.add(mailBox);
		staticEntities.add(lab);
		staticEntities.add(pc);
		staticEntities.add(bed);
		staticEntities.add(bookShelf);
		staticEntities.add(mat);
		staticEntities.add(table);
		staticEntities.add(machine);
		staticEntities.add(cpu);
		staticEntities.add(tv);
		staticEntities.add(pot);
		staticEntities.add(painting);
		staticEntities.add(chairLeft);
		staticEntities.add(chairRight);
		
		//ADD ITEMS
		staticEntities.add(flint);
		staticEntities.add(bow);
		staticEntities.add(arrow);
		
		//ADD NAMES
		String[]  statics = Utils.loadFileAsString("res/statics.res").split("\\s+");
		for(int i=0;i<statics.length;i++){
			names.add(statics[i]);
		}	
		
		/////////////////////////////////////////////////////////////////////////////////////
		//FILL ANIMATED TILE DATA
		water = fillAnimationFramesData(water,4,0,8,8,2);

		//FILL PLAYER FLOAT DATA
		fillAnimationFramesData(characterDown,0,0,4,4,4);
		fillAnimationFramesData(characterLeft,0,1,4,4,4);
		fillAnimationFramesData(characterRight,0,3,4,4,4);
		fillAnimationFramesData(characterUp,0,2,4,4,4);
		
	}
	
	public static float[] getImageData(String name){
		for(int i=0;i<names.size();i++){
			if(names.get(i).equalsIgnoreCase(name)){
				return staticEntities.get(i);
			}
		}
		return new float[]{0,0,0,0,0};
	}
	
	/**
	 * 
	 * @param a
	 * @param x
	 * @param y
	 * @param dividingConstX
	 * @param dividingConstY
	 * @return array with new values embedded
	 */
	
	public static float[] fillTileData(float[] a,float x,float y,float dividingConstX,float dividingConstY){
		a[0] = x/dividingConstX;
		a[1]=y/dividingConstY;
		a[2]=x/dividingConstX + 1/dividingConstX ;
		a[3]=y/dividingConstY + 1/dividingConstY;
		a[4]=1;
		return a;
	}
	
	/**
	 * 
	 * @param a
	 * @param x
	 * @param y
	 * @param dividingConstX
	 * @param dividingConstY
	 * @param length
	 * @return
	 */
	
	public static float[][] fillAnimationFramesData(float[][] a, float x,float y,float dividingConstX,float dividingConstY,int length){
		
		for(int i=0;i<length;i++){//LOOP THROUGH EVERY PLAYER IMAGE AND FILL FLOAT DATA INTO IT... 
			fillTileData(a[i],x,y,dividingConstX,dividingConstY);
			a[i][0] = x/dividingConstX + i/dividingConstX;
			a[i][2] = x/dividingConstX + i/dividingConstX+1/dividingConstX;

		}
		return a;
	}
	
	
	
}
