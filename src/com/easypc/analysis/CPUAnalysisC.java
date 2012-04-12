package com.easypc.analysis;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import org.lwjgl.LWJGLException;

import com.easypc.backend.VideoLWJGL;

/**
 * Is responsible for Analyzing the opCodes and Displaying everything onto a Canvas
 * @author crazysaem
 *
 */
@SuppressWarnings("serial")
public class CPUAnalysisC extends VideoLWJGL {
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Creates the CPUAnalysisC Object
	 * @throws LWJGLException
	 */
	public CPUAnalysisC() throws LWJGLException {
		super();
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
	public void drawOpenGl() {
		glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
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
