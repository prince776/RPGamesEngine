package dev.prince.rpgGameEngine.worlds;

import java.util.Random;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.tiles.Tile;

public class RandomWorldGenerator {//////STOP MAKING DISTORTION IN TREES IF IT INTERSECTS ANOTHER TREE CROUD
	
	private Random rand;
	
	private Handler handler;
	
	private int pondNo,treeCroudNo;
	
	
	public RandomWorldGenerator(Handler handler){
		this.handler=handler;
		rand = new Random();
		handler.getWorld().setWidth(rand.nextInt(100)+50);
		handler.getWorld().setHeight(rand.nextInt(100)+50);
		pondNo =rand.nextInt(4)+1;
		treeCroudNo = rand.nextInt((int)handler.getWorld().getWidth()/3)+8;
	}
	
	
	public void generateWorld(){//ADD GRASS
		for(int y=0;y<handler.getWorld().getHeight();y++){
			for(int x=0;x<handler.getWorld().getWidth();x++){
				handler.getWorld().setTilesValue(x, y, 0);					
			}
		}
		
		
		
		for(int i=0 ;i <pondNo;i++){
			//WATER PONDS VALUES
			int waterWidth = rand.nextInt(22)+12,waterHeight = rand.nextInt(11)+8;
			int waterX = rand.nextInt(handler.getWorld().getWidth()-waterWidth),waterY = rand.nextInt(handler.getWorld().getHeight()-waterHeight);
			
		
			//ADD SAND NEAR PONDS
			int sandX = waterX-3, sandY= waterY-3;
			int sandWidth =  waterWidth + 3+3, sandHeight =  waterHeight + 3+ 3;
			if(sandX < 0)
				sandX=0;
			if(sandX+sandWidth>handler.getWorld().getWidth())
				sandWidth=handler.getWorld().getWidth()-sandX;
			if(sandY+sandHeight>handler.getWorld().getHeight())
				sandHeight = handler.getWorld().getHeight()-sandY;
			if(sandY<0)
				sandY=0;
			
			//ADD SAND IN REGULAR MANNER
			for(int y = sandY ; y <sandY+sandHeight; y++){
				for(int x =sandX ;x <sandX+sandWidth; x++){
					handler.getWorld().setTilesValue(x, y, 4);					
				}
			}
			
			generateDistortion(sandX,sandY,sandWidth,sandHeight,4,6);//RIGHT AND BOTTOM
	
			//ADD WATER POND
			for(int y=waterY;y<waterY + waterHeight;y++ ){
				for(int x=waterX;x<waterX + waterWidth;x++ ){
					handler.getWorld().setTilesValue(x, y, 1);					
				}
			}
			generateDistortion(waterX,waterY,waterWidth,waterHeight,1,2);
		}
		
		for(int i=0;i<treeCroudNo;i++){
			//ADD TREES
			int treeWidth = rand.nextInt(7)+1,treeHeight = rand.nextInt(6)+2;
			int treeX = rand.nextInt(handler.getWorld().getWidth()-treeWidth),treeY = rand.nextInt(handler.getWorld().getHeight()-treeHeight);
			
			if(treeX < 0)
				treeX=0;
			if(treeX+treeWidth>handler.getWorld().getWidth())
				treeWidth=handler.getWorld().getWidth()-treeX;
			if(treeY+treeHeight>handler.getWorld().getHeight())
				treeHeight = handler.getWorld().getHeight()-treeY;
			if(treeY<0)
				treeY=0;
			
			for(int y=treeY;y<treeY+treeHeight;y+=2){
				for(int x=treeX;x<treeX+treeWidth;x++){
					handler.getWorld().setTilesValue(x, y, 6);
					handler.getWorld().setTilesValue(x, y+1, 7);
				}
			}
			
			generateDistortion2(treeX,treeY,treeWidth,treeHeight,6,4,3,2,2);
		}	
		
	}
	
	public void generateDistortion(int x,int y,int width,int height,int value,int Const){
		int distortion = rand.nextInt(Const);
		//IN Y AXIS
		for(int y1 = y;y1<y+height;y1++){
			for(int x1=x;x1<=x+width+distortion;x1++){
				distortion=rand.nextInt(Const);
				if(x+width+distortion>=handler.getWorld().getWidth())
					distortion=handler.getWorld().getWidth()-width-x-1;
					handler.getWorld().setTilesValue(x1, y1, value);
			}
		}
		//IN X AXIS
		for(int x1 = x;x1<x+height;x1++){
			for(int y1=y;y1<=y+height+distortion;y1++){
				distortion=rand.nextInt(Const);
				if(y+height+distortion>=handler.getWorld().getHeight())
					distortion=handler.getWorld().getHeight()-height-y-1;
				handler.getWorld().setTilesValue(x1, y1, value);			
				}
		}
		//IN X AXIS
		for(int x1 = x; x1<x+width;x1++){
					for(int y1=y;y1>=y-distortion;y1--){
						distortion=rand.nextInt(Const);
						if(y-distortion<=0)
							distortion=y-1;
						handler.getWorld().setTilesValue(x1, y1, value);					
						}
				}
		//IN Y AXIS
		for(int y1 = y; y1<y+height;y1++){
					for(int x1=x;x1>=x-distortion;x1--){
						distortion=rand.nextInt(Const);
						if(x-distortion<=0)
							distortion=x-1;
						handler.getWorld().setTilesValue(x1, y1, value);					
						}
				}
	}
	public void generateDistortion2(int x,int y,int width,int height,int value,int Const,int incrementY,int incrementX,int heightO){
		int distortion = rand.nextInt(Const);
		//IN Y AXIS
		for(int y1 = y;y1<y+height;y1+=incrementY){
			for(int x1=x;x1<=x+width+distortion;x1+=incrementX){
				distortion=rand.nextInt(Const);
				if(x+width+distortion>=handler.getWorld().getWidth())
					distortion=handler.getWorld().getWidth()-width-x-1;
				for(int i=0;i<incrementY;i++){
					handler.getWorld().setTilesValue(x1, y1+i, value+i);
				}
			}
		}
		//IN X AXIS
		for(int x1 = x;x1<x+height;x1+=incrementX){
			for(int y1=y;y1<=y+height+distortion;y1+=incrementY){
				distortion=rand.nextInt(Const);
				if(y+height+distortion>=handler.getWorld().getHeight())
					distortion=handler.getWorld().getHeight()-height-y-1;
				for(int i=0;i<heightO;i++){
					handler.getWorld().setTilesValue(x1, y1+i, value+i);
				}			
			}
		}
		//IN X AXIS
		for(int x1 = x; x1<x+width;x1+=incrementX){
			for(int y1=y;y1>=y-distortion;y1-=incrementY){
				distortion=rand.nextInt(Const);
				if(y-distortion<=0)
					distortion=y-1;
				for(int i=0;i<heightO;i++){
					handler.getWorld().setTilesValue(x1, y1+i, value+i);
				}				}
		}
		//IN Y AXIS
		for(int y1 = y; y1<y+height;y1+=incrementY){
				for(int x1=x;x1>=x-distortion;x1-=incrementX){
					distortion=rand.nextInt(Const);
					if(x-distortion<=0)
						distortion=x-1;
					for(int i=0;i<heightO;i++){
						handler.getWorld().setTilesValue(x1, y1+i, value+i);
						}				
					}
			}
			
		
	}
}
