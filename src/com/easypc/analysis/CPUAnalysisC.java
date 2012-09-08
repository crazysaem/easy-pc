package com.easypc.analysis;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.easypc.backend.VideoLWJGL;

/**
 * Is responsible for Analyzing the opCodes and Displaying everything onto a Canvas
 * @author crazysaem
 *
 */
@SuppressWarnings("serial")
public class CPUAnalysisC extends VideoLWJGL 
{
	private Texture textures[];
	
	private static final int GRAPHIC = 0;
	private static final int INPUT = 1;
	private static final int MATH = 2;
	private static final int MEMORY = 3;
	private static final int SOUND = 4;
	private static final int SYSTEM = 5;

	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Creates the CPUAnalysisC Object
	 * @throws LWJGLException
	 * @throws IOException 
	 */
	public CPUAnalysisC() throws LWJGLException 
	{
		super();
	}
	
	@Override
	protected void initGL() {
		super.initGL();
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		textures = new Texture[6];
		
		try {
			textures[GRAPHIC] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/graphic.png"));
			textures[INPUT] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/input.png"));
			textures[MATH] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/math.png"));
			textures[MEMORY] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/memory.png"));
			textures[SOUND] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/sound.png"));
			textures[SYSTEM] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/system.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Gets called after a CPU Instruction is complete, to update its View
	 */
	public void nextInstruction()
	{
		
	}
	
	/**
	 * Draws stuff onto the canvas via openGL
	 */
	@Override
	public void drawOpenGl() 
	{		
		glViewport(0, 0, getWidth(), getHeight());		
		glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		//gluOrtho2D(0.0f, (float) getWidth(), 0.0f, (float) getHeight());
		glOrtho(0, 200, 100, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		
		//TODO: actually show the CPU instructions here
		drawIcon(GRAPHIC, 	-13, 	37, 30, 30);
		drawIcon(INPUT, 	37, 	37, 30, 30);
		drawIcon(MATH, 		87, 	37, 30, 30);
		drawIcon(MEMORY, 	137, 	37, 30, 30);
		drawIcon(SOUND, 	187, 	37, 30, 30);
		
		glPopMatrix();
	}
	
	private void drawIcon(int icon, int x, int y, int width, int height)
	{
		textures[icon].bind();
		
		glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f(x, y);
			glTexCoord2f(1,0);
			glVertex2f(x + width, y);
			glTexCoord2f(1,1);
			glVertex2f(x + width, y + height);
			glTexCoord2f(0,1);
			glVertex2f(x, y + height);
		glEnd();

	}
}
