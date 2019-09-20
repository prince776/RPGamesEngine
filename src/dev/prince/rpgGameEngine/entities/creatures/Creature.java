package dev.prince.rpgGameEngine.entities.creatures;



import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.entities.Entity;
import dev.prince.rpgGameEngine.gfx.Animation;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.tiles.Tile;

public abstract class Creature extends Entity {
	
	public static float  DEFAULT_SPEED=1;
	public static final int DEFAULT_HEALTH=10;
	public static final float DEFAULT_WIDTH=62,DEFAULT_HEIGHT=67;
	public static final int DEFAULT_ANIMSPEED=110;
	
	protected float speed,runSpeed;
	protected int health;
	
	protected int animSpeed;
	
	protected boolean isRunning=false , isSwimming=false;
	protected int direction=2;//0->North 1->East 2->South 3->West
	
	protected Animation animDown,animUp,animRight,animLeft;
	protected int soundWait=0;

	
	public Creature(Handler handler, float x, float y, float width, float height) {
		super(handler, x, y, width, height);
		this.speed=DEFAULT_SPEED;
		this.health = DEFAULT_HEALTH;
		animSpeed= DEFAULT_ANIMSPEED;
		runSpeed=speed+(4*speed)/4;
		animDown = new Animation(Assets.characterDown,animSpeed);
		animLeft = new Animation(Assets.characterLeft,animSpeed);
		animRight = new Animation(Assets.characterRight,animSpeed);
		animUp = new Animation(Assets.characterUp,animSpeed);
	}
	
	//SOUND CODE//
	public void playSound(){	
		
		int PX =(int) ((this.x+this.width/2)/Tile.TILEWIDTH);
		int PY =(int) ((this.y+this.bounds.getY()+this.bounds.getHeight())/Tile.TILEHEIGHT);
		String tileName = handler.getWorld().getTiles(PX, PY).getName();
		soundWait+=4;

		for(int j=0;j<handler.getSFX().names.size();j++){

				 if(tileName.toLowerCase().contains(handler.getSFX().names.get(j))){//IF NAME MATCHES
					 if(soundWait>=(animSpeed) &&(xMove!=0 || yMove!=0)){
						
						handler.getSFX().playSoundEffect(handler.getSFX().walk.get(j), x, y, 0);
						soundWait=0;
					 }	
				 }
		 }
		
	 }
	
	///MOVE CODE/////
	
	public void move(){
		
		
		if(!getEntityCollision(xMove,0f))
			moveX();
		if(!getEntityCollision(0f,yMove))
			moveY();
	}
	
	public void moveX(){
		if(xMove>0){
			float tx = (int)((x+xMove+bounds.getX()+bounds.getWidth())/Tile.TILEWIDTH);		
			if(!checkTileCollision((int)tx, (int)((y+bounds.getY())/Tile.TILEHEIGHT )) 
				&& !checkTileCollision((int)tx, (int)((y+bounds.getY()+bounds.getHeight())/Tile.TILEHEIGHT )) 	
				&& !checkTileCollision((int)tx,(int) ((int)(y+bounds.getY()+(bounds.getHeight())/2)/Tile.TILEHEIGHT))	){	
				x+=xMove;
			}
		}
		if(xMove<0){
			float tx = (int)((x+xMove+bounds.getX())/Tile.TILEWIDTH);		
			if(!checkTileCollision((int)tx, (int)((y+bounds.getY())/Tile.TILEHEIGHT )) 
				&& !checkTileCollision((int)tx, (int)((y+bounds.getY()+bounds.getHeight())/Tile.TILEHEIGHT )) 	
				&& !checkTileCollision((int)tx,(int) ((int)(y+bounds.getY()+(bounds.getHeight())/2)/Tile.TILEHEIGHT))		){	
				x+=xMove;
			}
			
		}
		
	}
	
	public void moveY(){
		if(yMove>0){
			float ty = (int)((y+yMove+bounds.getY()+bounds.getHeight())/Tile.TILEHEIGHT);		
			if(!checkTileCollision((int)((x+bounds.getX())/Tile.TILEWIDTH ),(int)ty) 
				&& !checkTileCollision((int)((x+bounds.getX()+bounds.getWidth())/Tile.TILEWIDTH ),(int)ty) 	
				&& !checkTileCollision((int)((x+bounds.getX()+(bounds.getWidth())/2)/Tile.TILEWIDTH ),(int)ty) 	
	){	
				y+=yMove;
			}
		}
		if(yMove<0){
			float ty = (int)((y+yMove+bounds.getY())/Tile.TILEHEIGHT);		
			if(!checkTileCollision((int)((x+bounds.getX())/Tile.TILEWIDTH ),(int)ty) 
				&& !checkTileCollision((int)((x+bounds.getX()+bounds.getWidth())/Tile.TILEWIDTH ),(int)ty) 	
				&& !checkTileCollision((int)((x+bounds.getX()+(bounds.getWidth())/2)/Tile.TILEWIDTH ),(int)ty) 		){	
				y+=yMove;
			}
		}
	}
	
	public boolean checkTileCollision(int x , int y){	
		
		if(handler.getWorld().getSolidTile(x, y)){
			return true;
		}else{
			return false;
		}
	}
	
	public void run(){
		if(xMove>0)
			xMove=runSpeed;
		if(xMove<0)
			xMove=-runSpeed;
		if(yMove>0)
			yMove=runSpeed;// (speed+(4*speed)/4);
		if(yMove<0)
			yMove=-runSpeed;//(-speed-(4*speed)/4);
		isRunning=true;
	}
	
	//EXTRA METHODS//
	
	public void checkOutOfBounds(){
		if(x<0)
			x=0;
		if(y<0)
			y=0;
		if(x+width>=handler.getWorld().getWidth()*Tile.TILEWIDTH)
			x=handler.getWorld().getWidth()*Tile.TILEWIDTH-width;
		if(y+height>=handler.getWorld().getHeight()*Tile.TILEHEIGHT)
			y=handler.getWorld().getHeight()*Tile.TILEHEIGHT-height;
	}
	

	public void checkSwim(){		
		for(int y=0;y<(handler.getWorld().getHeight());y++){
			for(int x=0;x<(handler.getWorld().getWidth());x++){
				
				if(handler.getWorld().getTiles(x, y).isActive){
					if((this.x+this.bounds.getX()+(this.bounds.getWidth()/2)+1)>x*Tile.TILEWIDTH && ( this.x+this.bounds.getX()+(this.bounds.getWidth()/2)-1) <= (x+1)*Tile.TILEWIDTH && ((this.y+this.bounds.getY()+(bounds.getHeight()/2)+1)>y*Tile.TILEHEIGHT) && (this.y+this.bounds.getY()+(bounds.getHeight()/2)-1 ) <=(y+1)*Tile.TILEHEIGHT){
						if(handler.getWorld().getTiles(x, y)==Tile.tiles[1]){
							this.isSwimming=true;
							return;
						}else{
							this.isSwimming=false;
						}
					}
				}
			}
		}
	}
	//ANIMATION FRAME
	public float[] getCurrentAnimationFrame(){
		//WHILE MOVING
		if(xMove>0){
			return animRight.getCurrentFrame();
		}
		if(xMove<0){
			return animLeft.getCurrentFrame();
		}
		if(yMove>0){
			return animDown.getCurrentFrame();
		}
		if(yMove<0){
			return animUp.getCurrentFrame();
		}
		//WHILE STANDING STILL
		if(direction==0){
			return Assets.characterUp[0];
		}
		if(direction==1){
			return Assets.characterRight[0];
		}
		if(direction==2){
			return Assets.characterDown[0];
		}
		if(direction==3){
			return Assets.characterLeft[0];
		}
		return Assets.characterDown[0];
	}
	
	
	
}
