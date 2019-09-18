package dev.prince.rpgGameEngine.tiles;

import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;

public  class Tile {
	
	protected float[] textureData;
	protected int id;
	protected String name;
	
	
	public int zIndex=0;
	
	public static float TILEWIDTH =16 , TILEHEIGHT = 16; 
	
	public boolean isActive=false;
	
	public static Tile[] tiles = new Tile[256];
	//THEY GETS EMBEDDED IN tiles[] ARRAY AS WE'VE MADE THEIR OBJECTS AND DUE TO
	//tiles[id]=this;//WHILE INSTANTIATION
	public static Tile grassTile = new Tile(Assets.grass,0,"grass");
	public static Tile waterTile = new WaterTile(1);
	public static Tile stoneTile = new Tile(Assets.brick,2,"stone");
	public static Tile stairsTile =new Tile(Assets.stairs,3,"stairs");
	public static Tile sandTile = new Tile(Assets.sand,4,"sand");
	public static Tile pathtile = new Tile(Assets.path,5,"path");
	public static Tile voidtile = new VoidTile(6);
	public static Tile woodFloorTile = new Tile(Assets.woodFloor,7,"wood");
	public static Tile FloorTile = new Tile(Assets.floor,8,"floor");
	public static Tile darkFloorTile = new Tile(Assets.darkWoodFloor,9,"darkWood");

	
	public Tile(float[] textureData  , int id,String name){
		this.textureData = textureData;
		this.id = id;
		this.name=name;
		tiles[id] = this;
	}
	
	public boolean isSolid(){
		return false;
	}

	public String getName(){
		return name;
	}
	public int getID(){
		return id;
	}
	
	
	
	public float[] getTextureData() {
		return textureData;
	}

	public  void tick(){
		
	};
	public  void render(float x , float y){
		Renderer.renderSubImage(Assets.tileSet, x, y, TILEWIDTH, TILEHEIGHT, textureData,  1f);
	};
	
}
