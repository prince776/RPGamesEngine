package dev.prince.rpgGameEngine.features;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.fonts.Fonts;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.inputs.KeyManager;
import dev.prince.rpgGameEngine.ui.TextArea;

public class Inventory {
	
	private Handler handler;
	private String title="Inventory";
	private String[] items,quantities;
	private int selected =0,xOffset = 15,yOffset=10 , width,height,maxQuantity=25,displayQuantity=12,moveMagnitude=0,visibleLength=0;
	private int x,y;
	
	private float[] selectedColor;
	
	public Inventory(Handler handler,String[] items,String[] quantities){
		
		this.handler=handler;
		this.items = new String[maxQuantity];
		this.quantities =  new String[maxQuantity];
	
		//ADD ITEMS
		for(int i=0;i<items.length;i++){
			this.items[i] = items[i];
			this.quantities[i] = quantities[i];
		}
		
		//DEFINE WIDTH AND HEIGHT
		width = handler.getWidth() - 2*(10*xOffset);
		height = yOffset + Fonts.font.getHeight("ANYTHING")*displayQuantity + displayQuantity*yOffset + yOffset;
		x = handler.getWidth()/2 - width/2;
		y = handler.getHeight()/2-height/2;
		
		//DEFINE COLOR FLOAT VALUES
		selectedColor = new float[]{1,0,0,0.8f};
		
		
		
	}
	
	public void tick(){
		
		//Set Values
		visibleLength=0;
		
		for(int i=0;i<maxQuantity;i++){
			if(items[i] == null)
				break;
			visibleLength++;
		}
		
		if(selected>displayQuantity/2){
			moveMagnitude=selected-displayQuantity/2-1;
		}
		//System.out.println("Visible length is: "+visibleLength);
		//NAVIGATION
		if(KeyManager.value == Keyboard.KEY_DOWN){
			if(selected<visibleLength-1)
				selected++;
			KeyManager.value=0;
		}else if(KeyManager.value == Keyboard.KEY_UP){
			if(selected >= 1)
				selected--;
			KeyManager.value=0;
		}
		//System.out.println("SELECETED VALUE IS: "+selected);
		
		handler.getWorld().getEntityManager().getPlayer().zIndex=1;

	
	}
	
	public void render(){
		
		TextArea.renderTextArea(handler.getWidth()/2-Fonts.fontBig.getWidth(title)/2, y-TextArea.getyOffset()*2-Fonts.fontBig.getHeight("I"), title, Color.white, new float[]{0,0,0,0}, new float[]{0,0,0,0},true);
		
		Renderer.setColor(0.6f, 0.7f, 0.8f, 0.7f);
		Renderer.renderQuad(x,y, width, height);
		Renderer.setColor(0.6f, 0.7f, 0.8f, 1f);
		Renderer.renderOutlineOfQuad(x,y, width, height);
		
		//NORMAL RENDER
		for(int i=
				((moveMagnitude<=selected)?moveMagnitude:selected)
				;i<
				((displayQuantity+moveMagnitude<=maxQuantity)?displayQuantity+moveMagnitude:maxQuantity)
				;i++){
			if(items[i]!=null){					
				if(i==selected){
					TextArea.renderTextArea(x+xOffset, y+yOffset+(i-moveMagnitude)*Fonts.font.getHeight("A")+(i-moveMagnitude)*yOffset, items[i] + " x " + quantities[i], Color.black,selectedColor,new float[]{0,0,0,1},false);
					continue;
				}
				TextArea.renderTextArea(x + xOffset, y + yOffset + (i-moveMagnitude)*Fonts.font.getHeight("A") + (i-moveMagnitude)*yOffset, items[i] + " x " + quantities[i], Color.black);
			}
		}
		
	}
	
	public  void addItem(String item , String quantity){
		for(int i=0;i<items.length;i++){
			if(items[i]==null){
				items[i] = item;
				quantities[i]=quantity;
				return;
			}
		}
	}
	
}
