package dev.prince.rpgGameEngine.gfx;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class ImageLoader {
	
	public ImageLoader(){
		
	}
	
	
	
	public static Texture loadImage(String path,String format) {
			try {
				
				Texture tex= TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(path));
				glBindTexture(GL_TEXTURE_2D,tex.getTextureID());
				glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
				glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
//				glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,tex.getImageWidth(),tex.getImageHeight(),0,GL_RGBA,GL_UNSIGNED_BYTE,BufferCreator.createByteBuffer(tex.getTextureData()));
				glBindTexture(GL_TEXTURE_2D,0);
				return tex;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		
			
		
	}
	
}
