package dev.prince.rpgGameEngine.entities.creatures;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.entities.Entity;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.inputs.KeyManager;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.utils.Utils;

public class NPC extends Creature {
	
	
	private Texture sheet;
	
	//PATTERN VARIABLES
	private long patternTime = 800,lastTime,timer;
	private boolean up,down,right,left,isStaticNPC=false;
	private int chaseOrEscape=0;
	
	//CHARACTERISITICS
	private String name = "Anonymous" , dialogue="...";
	private boolean isMerchant=false,interact=false;
	
	public NPC(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_WIDTH,DEFAULT_HEIGHT);
		bounds.setX(18);
		bounds.setY(38 );
		bounds.setWidth(17);
		bounds.setHeight(12);
		sheet = Assets.NPCsheet;
		lastTime=System.currentTimeMillis();
	}
	
	public NPC(Handler handler,float x,float y,String name,String dialogue,boolean isMerchant,boolean isStatic){
		this(handler,x,y);
		this.name=name;
		this.dialogue = dialogue;
		this.isMerchant=isMerchant;
		this.isStaticNPC=isStatic;
	}	
	
	@Override
	public void tick() {
		
		interact();
		if(!interact){
			checkInput();
	
			checkSwim();
			if(isSwimming){
				sheet = Assets.NPCswimSheet;
	
			}else{
				isSwimming = false;
				sheet = Assets.NPCsheet;
			}
			
			if(yMove<0)
				animUp.animTick();
			if(yMove>0)
				animDown.animTick();
			if(xMove<0)
				animLeft.animTick();
			if(xMove>0)
				animRight.animTick();
			
			move();
			checkOutOfBounds();
			if(!getEntityCollision(xMove,yMove)){
				moveX();
				moveY();
			}
		
		}
		
		//playSound();

	}
	
	//CHECK INPUT
	public void checkInput(){
		if(GameState.prompt.getPromptText().equalsIgnoreCase("NPC CHASE") && Keyboard.isKeyDown(Keyboard.KEY_RETURN))
			chaseOrEscape=1;
		if(GameState.prompt.getPromptText().equalsIgnoreCase("NPC ESCAPE") && Keyboard.isKeyDown(Keyboard.KEY_RETURN))
			chaseOrEscape=2;
		if(GameState.prompt.getPromptText().equalsIgnoreCase("NPC NORMAL") && Keyboard.isKeyDown(Keyboard.KEY_RETURN))
			chaseOrEscape=0;
	}

	@Override
	public void render() {
		//zIndex=0;
		Renderer.renderSubImage(sheet, (x-handler.getGameCamera().getxOffset()), (y-handler.getGameCamera().getyOffset()), width, height, getCurrentAnimationFrame(),1f);
		//if(interact){
			//zIndex=1;
		//	TextArea.renderTextArea(handler.getWidth()/10, handler.getHeight()-50,name+": "+dialogue, Color.black);
		//}

	}
	
	
	
	
	//PATTERN ALGORITHM
	
	public void move(){
		if(chaseOrEscape == 0 ){
		timer+=System.currentTimeMillis()-lastTime;
		lastTime=System.currentTimeMillis();
			if(timer>patternTime){
				patternFollow();
				timer=0;
			}
		}
		if(chaseOrEscape != 0 )
			chaseOrEscape(handler.getWorld().getEntityManager().getPlayer(),chaseOrEscape);
	}
	
	public void patternFollow(){
		//NORTH
		if(direction == 0){
			yMove= -speed;
			xMove=0;
			direction = Utils.getRandomInt(0, 3);
		}
		//EAST
		if(direction == 1){
			xMove=speed;
			yMove=0;
			direction = Utils.getRandomInt(0, 3);
		}
		//SOUTH
		if(direction == 2){
			yMove=speed;
			xMove=0;
			direction = Utils.getRandomInt(0, 3);
		}
		//WEST
		if(direction == 3){
			xMove=-speed;
			yMove=0;
			direction = Utils.getRandomInt(0, 3);
		}
		if(!isStaticNPC){
			if(Utils.getRandomInt(0, 2)<2){
				xMove=0;
				yMove=0;
			}
		}
		if(isStaticNPC){
			if(Utils.getRandomInt(0, 2)<=2){
				xMove=0;
				yMove=0;
			}
		}
		
	}
	

	
	public void chaseOrEscape(Entity e,int chaseOrEscape){//CHASE IS 1 ESCAPE IS 2
			xMove=0;
			yMove=0;
			
			float pX = e.getX();
			float pY = e.getY();
			float dx=0,dy=0;
			if(chaseOrEscape==1){
				dx=-(this.x-pX);//distance X
				dy = -(this.y-pY);//distance Y
			}
			if(chaseOrEscape==2){
				dx=(this.x-pX);//distance X
				dy = (this.y-pY);//distance Y
			}
			if(dx>1){
				right=true;
				direction=1;
			}
			
			if(dx<-1){
				left=true;
				direction=3;

			}
			
			if(dy>1){
				down=true;
				direction=2;

			}
			
			if(dy<-1){
				up=true;
				direction=0;

			}
		
			if(up)
				yMove= (-this.runSpeed);
			if(down)
				yMove= (runSpeed);
			if(left)
				xMove=(-runSpeed);
			if(right)
				xMove= (runSpeed);
			
			up=false;
			down=false;
			left=false;
			right=false;			
	}

	public void interact(){
		if(checkIntersect()){
			if(KeyManager.value==Keyboard.KEY_RETURN){
				interact=true;
				GameState.dialogue = name+": "+dialogue;
				GameState.shouldDialogue = !GameState.shouldDialogue;
				
				if(handler.getPlayer().direction==0)
					direction=2;
				if(handler.getPlayer().direction==1)
					direction=3;
				if(handler.getPlayer().direction==2)
					direction=0;
				if(handler.getPlayer().direction==3)
					direction=1;
				}
		}
		else{
			interact=false;
		}
	}
	
	boolean checkIntersect(){
		Rectangle rect1 = new Rectangle((int)x+bounds.getX()-3,(int)y+bounds.getY()-3,(int)bounds.getWidth()+6,(int)bounds.getHeight()+6);
		Rectangle rect2 = new Rectangle((int)handler.getPlayerX()+handler.getPlayer().getBounds().getX()-3,(int)handler.getPlayerY()+handler.getPlayer().getBounds().getY()-3,(int)handler.getPlayer().getWidth()+handler.getPlayer().getBounds().getWidth()+6,(int)handler.getPlayer().getHeight()+handler.getPlayer().getBounds().getHeight()+6);

		if(rect1.intersects(rect2)){	
			return true;
		}
		else
			return false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDialogue() {
		return dialogue;
	}

	public void setDialogue(String dialogue) {
		this.dialogue = dialogue;
	}

	public boolean isMerchant() {
		return isMerchant;
	}

	public void setMerchant(boolean isMerchant) {
		this.isMerchant = isMerchant;
	}
	
	
}
