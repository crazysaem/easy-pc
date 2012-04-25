package com.easypc.analysis;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import org.lwjgl.LWJGLException;

import com.easypc.backend.VideoLWJGL;
import com.easypc.chip8.RAM;

/**
 * Is responsible for Analyzing the RAM and Displaying everything onto a Canvas
 * @author crazysaem
 *
 */
@SuppressWarnings("serial")
public class RAMAnalysisC extends VideoLWJGL {
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	private RAM ram;

	/**
	 * Creates the RAMAnalysisC Object
	 * @throws LWJGLException
	 */
	public RAMAnalysisC(RAM ram) throws LWJGLException {
		super();
		this.ram = ram;
	}
	
	/**
	 * Draws stuff onto the canvas via openGL
	 */
	@Override
	public void drawOpenGl() {
		glViewport(0, 0, getWidth(), getHeight()); // 64*32 is the exact display
		// size of the Chip-8 System
		glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0.0f, 64, 0.0f, 32);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		
		//TODO: Code here
		
		glPopMatrix();
	}
}
