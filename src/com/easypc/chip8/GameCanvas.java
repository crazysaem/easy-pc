package com.easypc.chip8;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import org.lwjgl.LWJGLException;

import com.easypc.backend.VideoLWJGL;

/**
 * Is responsible for showing the Chip-8 Video Output
 * @author crazysaem
 *
 */
@SuppressWarnings("serial")
public class GameCanvas extends VideoLWJGL {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//The MediaOutput Object
	private MediaOutput media;
				
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	/**
	 * Creates the GameCanvas Object
	 * @throws LWJGLException
	 */
	public GameCanvas(MediaOutput media) throws LWJGLException {
		super();
		this.media = media;
	}
	
	/**
	 * Draws stuff onto the canvas via openGL
	 */
	@Override
	public void drawOpenGl() {
		glViewport(0, 0, 64, 33); //64*32 is the exact display size of the Chip-8 System
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0.0f, (float) getWidth(), 0.0f, (float) getHeight());
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		
		for(int x=0;x<64;x++)
		{
			for(int y=0;y<32;y++)
			{
				if(media.display[x][y]==1)
					drawWhitePixel(x, y);
			}
		}

		glPopMatrix();
	}
	
	private void drawWhitePixel(int x, int y)
	{
		//TODO: Draw a white pixel with openGL
		//TIP: Pixel(0,0) would be a rectangle from like this: 0.0, 0.1, 1.1, 0.1; Use Counter-Clock-Wise representation (= Gegen den Urzeigersinn)
		//http://nehe.gamedev.net/tutorial/your_first_polygon/13002/
		//Take a look at "GL_QUADS" from the above link
	}
}