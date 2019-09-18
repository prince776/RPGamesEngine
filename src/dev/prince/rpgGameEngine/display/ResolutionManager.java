package dev.prince.rpgGameEngine.display;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class ResolutionManager {
	
	public static void setResoulution(int width,int height){
		try {
			Display.setDisplayMode(new DisplayMode(width,height));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void changeResolution(int width , int height , boolean shouldDoFullScreen){
		
		if(Display.getWidth() == width &&
		   Display.getHeight() == height &&
		   Display.isFullscreen() == shouldDoFullScreen)
			return;
			
		DisplayMode target=null;	
		
		try {
			
			if(shouldDoFullScreen){
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				
				for(int i=0;i<modes.length;i++){
					DisplayMode current = modes[i];
					int freq=0;
					
					if(current.getWidth() == width && current.getHeight() == height){
						if(target == null || current.getFrequency() >= freq){
							if(target == null || current.getBitsPerPixel() > target.getBitsPerPixel()){
								target = current;
								freq = target.getFrequency();
							}
						}
						
						if(current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()){
							if(current.getFrequency() == Display.getDesktopDisplayMode().getFrequency()){
								target = current;
								break;
							}
						}
						
					}
					
				}
				
			}
			else{
				target = new DisplayMode(width,height);
			}
			
			if(target == null){
				 System.out.println("Failed to find value mode: "+width+"x"+height+" FullScreen="+shouldDoFullScreen);
	             return;
			}
			
			Display.setDisplayMode(target);
			Display.setFullscreen(shouldDoFullScreen);
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
			
		
		
	}
	
}
