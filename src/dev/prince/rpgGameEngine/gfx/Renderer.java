package dev.prince.rpgGameEngine.gfx;


import static org.lwjgl.opengl.ARBBufferObject.GL_STATIC_DRAW_ARB;
import static org.lwjgl.opengl.ARBBufferObject.glBindBufferARB;
import static org.lwjgl.opengl.ARBBufferObject.glBufferDataARB;
import static org.lwjgl.opengl.ARBBufferObject.glGenBuffersARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;





import com.sun.corba.se.impl.ior.ByteBuffer;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.fonts.Fonts;
import static org.lwjgl.opengl.EXTFramebufferObject.*;

public class Renderer {
	
	public static int displayListHandler,vertexCount,vertexSize,colorSize;
	
	public static int vboID,normalVBO;
	
	public static FloatBuffer totalData,tData;
	public static float r=0f,g=0f,b=0f,a=0f;
	
	public static int texture ;
	
	//FBO
	public static int framebufferID,colorTextureID,depthRenderBufferID;
	
	
	public static Handler handler;
	
	public Renderer(){
		
	}
	
	public static void init(Handler handler1){
		handler=handler1;
		displayListHandler = glGenLists(1);
		vertexCount = 4;
		vertexSize=2;
		colorSize=4;
		totalData = BufferUtils.createFloatBuffer(vertexCount*vertexSize+vertexCount*colorSize+vertexCount*vertexSize);
		tData = BufferUtils.createFloatBuffer(vertexCount*vertexSize+vertexCount*colorSize);
		
		vboID = generateVBOID();
		normalVBO = generateVBOID();
		
		
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER,vboID);
		
		initFBO();
		
	}
	
	public static void initFBO(){
		  // init our fbo
	     
        framebufferID = glGenFramebuffersEXT();                                         // create a new framebuffer
        colorTextureID = GL11.glGenTextures();                                               // and a new texture used as a color buffer
        depthRenderBufferID = glGenRenderbuffersEXT();                                  // And finally a new depthbuffer
 
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebufferID);                        // switch to the new framebuffer
 
        // initialize color texture
        GL11.glBindTexture(GL_TEXTURE_2D, colorTextureID);                                   // Bind the colorbuffer texture
        GL11.glTexParameterf(GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);               // make it linear filterd
        GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL11.GL_RGBA8, handler.getWidth(), handler.getHeight(), 0,GL11.GL_RGBA, GL11.GL_INT, (java.nio.ByteBuffer) null);  // Create the texture data
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D, colorTextureID, 0); // attach it to the framebuffer
 
 
        // initialize depth renderbuffer
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);                // bind the depth renderbuffer
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, 512, 512); // get the data space for it
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, depthRenderBufferID); // bind it to the renderbuffer
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0); 
	}
	
		
	
	//VBO STUFF
	
	//ID
	public static int generateVBOIDARB(){
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
		    IntBuffer buffer = BufferUtils.createIntBuffer(1);
		    glGenBuffersARB(buffer);
		    return buffer.get(0);
		}
		return 0;
	}
	///
	public static int generateVBOID(){
		return glGenBuffers();
	}
	//
	//INITIALIZATION/STORING VBO ID
	public static void bufferData(int id,FloatBuffer buffer){//INITIALIZATION OF
		glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
	}
	//
	public static void indexBufferData(int id,IntBuffer buffer){//INITIALIZATION OF
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
	}
	//ARB EXTENSION//
	public static void arbBufferData(int id,FloatBuffer buffer){
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, id);
		glBufferDataARB(GL_ARRAY_BUFFER_ARB, buffer,GL_STATIC_DRAW_ARB);
	}
	//
	
	//RENDERING STUFF
	public static void setColor(float R,float G , float B,float A){
		glColor4f(R,G,B,A);
		r=R;
		g=G;
		b=B;
		a=A;
	}
	//
	public static void renderString(float x , float y ,String str,Color color,boolean big){
		if(!GL11.glIsEnabled(GL_TEXTURE_2D))
		glEnable(GL_TEXTURE_2D);
		Fonts.drawString(x, y, str, color,big);
	}
	
	public static void renderLine(float x1 , float y1,float x2,float y2){
		glNewList(displayListHandler,GL_COMPILE);
		glBegin(GL_LINES);
			glVertex2f(x1,y1);
			glVertex2f(x2,y2);
		glEnd();
		glEndList();
		glCallList(displayListHandler);
		
	}
	
	//
	public static void renderQuad(float x , float y , int width,int height){
		if(GL11.glIsEnabled(GL_TEXTURE_2D))

			glDisable(GL_TEXTURE_2D);
		//glEnable(GL_BLEND);
	
		tData.put(new float[]{
				x,y,				r,g,b,a,
				x+width,y,			r,g,b,a,
				x+width,y+height,	r,g,b,a,
				x,y+height,			r,g,b,a
		});
		tData.flip();
		//MAKE VBOs		
		bufferData(vboID,tData);//vbo binded and data buffered
		//COLOR
		glVertexPointer(vertexSize,GL_FLOAT,6*4,0L);
		glColorPointer(colorSize,GL_FLOAT,6*4,2*4);		
		//ENABLEs
		
		glDrawArrays(GL_QUADS,0,vertexCount);
		//
		//glEnable(GL_TEXTURE_2D);
	
	}
	//	
	public static void renderOutlineOfQuad(float x , float y , int width,int height){
		if(GL11.glIsEnabled(GL_TEXTURE_2D))

			glDisable(GL_TEXTURE_2D);
		//glEnable(GL_BLEND);
		
		glNewList(displayListHandler,GL_COMPILE);
		glBegin(GL_LINE_STRIP);
			glVertex2f(x,y);
			glVertex2f(x+width,y);
			glVertex2f(x+width,y+height);
			glVertex2f(x,y+height);
		glEnd();
		glBegin(GL_LINE_STRIP);
			glVertex2f(x,y);
			glVertex2f(x,y+height);
		glEnd();
		glEndList();

		glCallList(displayListHandler);
		
		//glEnable(GL_TEXTURE_2D);

	}
	//
	public static void renderRotatableQuad(float x , float y , int width,int height,float rotation){
		glDisable(GL_BLEND);
		//glEnable(GL_TEXTURE_2D);
		glNewList(displayListHandler,GL_COMPILE);

		glPushMatrix();
			
			glTranslatef(x+width/2,y+height/2,0);
			glRotatef(rotation,0,0,1);
			glTranslatef(-x-width/2,-y-height/2,0);
			
			glBegin(GL_QUADS);
				glVertex2f(x,y);
				glVertex2f(x+width,y);
				glVertex2f(x+width,y+height);
				glVertex2f(x,y+height);
			glEnd();
		glPopMatrix();
		glEndList();

		glCallList(displayListHandler);
		glEnable(GL_BLEND);

	}
	//	
	/**
	 * Draws an Image with specefic x , y width , height , and float alpha value
	 * @param texture
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param alpha
	 */
	
	public static void renderImage(Texture texture,float x , float y , float width,float height,float alpha){
	
		texture.bind();
		
		totalData.put(new float[]{
				x,y,				1,1,1,alpha, 	0,0,	
				x+width,y,			1,1,1,alpha,	1,0,	
				x+width,y+height,	1,1,1,alpha,	1,1,	
				x,y+height,			1,1,1,alpha,	0,1
		});
		
		totalData.flip();
		
		bufferData(vboID,totalData);
		
		//BIND VBOs
		//VERTEX
		glVertexPointer(vertexSize,GL_FLOAT,8*4,0L);
				
		//COLOR
		glColorPointer(colorSize,GL_FLOAT,8*4,2*4);
				
		//TEXTURE
		GL11.glTexCoordPointer(vertexSize,GL_FLOAT,8*4,6*4);
		
		//NORMALS
		
		FloatBuffer normals = BufferUtils.createFloatBuffer(12);
		normals.put(new float[]{
			0,0,1,
			0,0,-1,
			0,0,1,
			0,0,-1
		});
		normals.flip();
		
		GL11.glNormalPointer(GL_FLOAT,4*4,0L);
		
		
		glDrawArrays(GL_QUADS,0,vertexCount);

		
	}
	
	/*
	 * renders image upside down
	 */
	public static void renderImage(int texture,float x , float y , float width,float height,float alpha){
		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		
		totalData.put(new float[]{
				x,y,				1,1,1,alpha,	0,1,//delta1
				x+width,y,			1,1,1,alpha,	1,1,//delta2
				x+width,y+height,	1,1,1,alpha,	1,0,//delta3
				x,y+height,			1,1,1,alpha,	0,0
		});
		
		totalData.flip();
		
		bufferData(vboID,totalData);
		
		//BIND VBOs
		//VERTEX
		glVertexPointer(vertexSize,GL_FLOAT,8*4,0L);
				
		//COLOR
		glColorPointer(colorSize,GL_FLOAT,8*4,2*4);
				
		//TEXTURE
		GL11.glTexCoordPointer(vertexSize,GL_FLOAT,8*4,6*4);		
		
		glDrawArrays(GL_QUADS,0,vertexCount);

		
	}
	
	
	public static void renderImage(Texture texture,float x , float y , float width,float height,float[] rgba){
		
		texture.bind();
		
		totalData.put(new float[]{
				x,y,				rgba[0],rgba[1],rgba[2],rgba[3],	0,0,//delta1
				x+width,y,			rgba[0],rgba[1],rgba[2],rgba[3],	1,0,//delta2
				x+width,y+height,	rgba[0],rgba[1],rgba[2],rgba[3],	1,//delta3
																		1,
				x,y+height,			rgba[0],rgba[1],rgba[2],rgba[3],	0,1
		});
		
		totalData.flip();
		
		bufferData(vboID,totalData);
		
		//BIND VBOs
		//VERTEX
		glVertexPointer(vertexSize,GL_FLOAT,8*4,0L);
				
		//COLOR
		glColorPointer(colorSize,GL_FLOAT,8*4,2*4);
				
		//TEXTURE
		GL11.glTexCoordPointer(vertexSize,GL_FLOAT,8*4,6*4);		
		
		glDrawArrays(GL_QUADS,0,vertexCount);

		
	}
	
	public static void renderSubImage(Texture texture,float x , float y,float width,float height,float[] data,float alpha ){//,float x1,float y1, int width,int height,float alpha){
	
		texture.bind();
		totalData.put(new float[]{
		x,y,				1,1,1,alpha,	data[0],data[1],//delta1
		x+width,y,			1,1,1,alpha,	data[2],data[1],//delta2
		x+width,y+height,	1,1,1,alpha,	data[2],data[3],
											
		x,y+height,			1,1,1,alpha,	data[0],data[3]
		});
		
		totalData.flip();
		bufferData(vboID,totalData);
		//BIND VBOs
		//VERTEX
		glVertexPointer(vertexSize,GL_FLOAT,8*4,0L);
		
		//COLOR
		glColorPointer(colorSize,GL_FLOAT,8*4,2*4);
		
		//TEXTURE
		GL11.glTexCoordPointer(vertexSize,GL_FLOAT,8*4,6*4);
		
		glDrawArrays(GL_QUADS,0,vertexCount);	
			
		
	}
	
	



	
	public static void destroy(){
		glDeleteLists(displayListHandler,1);
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER_ARB,0);
		glDeleteBuffers(vboID);

	}
	
}
