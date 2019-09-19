package dev.prince.rpgGameEngine.display;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL13;

public class DisplayClass {
	public static void initGL(String title,int width,int height){
		
		//CREATE DISPLAY
		try {
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.setTitle(title);
			//ResolutionManager.changeResolution(width, height, !Display.isFullscreen());
			Display.create();
			//DIsplay.c
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
			
		}
		
		//INIT OPENGL
		
		glEnable(GL_TEXTURE_2D);//ENABLE 2D TEXTURE
	    glShadeModel(GL_SMOOTH);        
	    glDisable(GL_DEPTH_TEST);
	    glDisable(GL_LIGHTING);   
	    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	    glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
	    glClearColor(0f,0f,0f,0f);
	    glClearDepth(1);
	    glEnable(GL13.GL_MULTISAMPLE);
		//ENABLE ALPHA BENDING MEANING ENABLE TRANSPARENCY
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
		
        glViewport(0,0,width,height);
		
		glMatrixMode(GL_PROJECTION);
		
		glLoadIdentity();
		glOrtho(0,width,height,0,1,-1);
		glMatrixMode(GL_MODELVIEW);
		
		
		
	}
}	
