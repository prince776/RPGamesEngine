package dev.prince.rpgGameEngine.fonts;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Fonts {
	public static Font awtFont,awtFontBig;
	public static TrueTypeFont font,fontBig;
	
	
	
	public static void init(){
		awtFont = new Font("Verdana",Font.BOLD,14);
		awtFontBig = new Font("Verdana",Font.BOLD,24);
		font = new TrueTypeFont(awtFont,true);
		fontBig = new TrueTypeFont(awtFontBig,true);
		
	}
	
	public static void drawString(float x, float y,String str,Color color,boolean big){
		if(big)
			fontBig.drawString(x, y, str, color);
		else
			font.drawString(x, y, str, color);
	}
	
}
