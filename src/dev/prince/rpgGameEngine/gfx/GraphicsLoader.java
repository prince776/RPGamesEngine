package dev.prince.rpgGameEngine.gfx;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;

public class GraphicsLoader {
	
	public  int width,height,tex;
	public  int[] data;
	
	public GraphicsLoader(String path){
		loadImage(path);
	}
	
	public  int loadImage(String path){
		int[] pixels=null;
		
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height=image.getHeight();
			pixels = new int[width*height];
			image.getRGB(0, 0,width,height,pixels,0,width);
		} catch ( IOException e) {
			e.printStackTrace();
		}
		
		data = new int[width*height];
		for(int i=0;i<width*height;i++){
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a<<24|b<<16|g<<8|r;
			
		}
		
		int tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D,tex);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,BufferCreator.createIntByteBuffer(data));
		glBindTexture(GL_TEXTURE_2D,0);
		return tex;
	}
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
	public int getTex() {
		return tex;
	}
	
	
}
