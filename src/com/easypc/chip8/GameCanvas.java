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
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	/**
	 * Creates the GameCanvas Object
	 * @throws LWJGLException
	 */
	public GameCanvas() throws LWJGLException {
		super();
	}
	
	/**
	 * Draws stuff onto the canvas via openGL
	 */
	@Override
	public void drawOpenGl() {
		glViewport(0, 0, 64, 32); //64*32 is the exact display size of the Chip-8 System
		glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluOrtho2D(0.0f, (float) getWidth(), 0.0f, (float) getHeight());
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        
      //TODO: Add openGL specific drawing code here
                        
        glPopMatrix();
	}
}