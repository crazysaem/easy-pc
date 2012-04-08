package com.easypc.chip8;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;

/**
 * Is responsible for showing the Chip-8 Video Output
 * @author crazysaem
 *
 */
@SuppressWarnings("serial")
public class GameCanvas extends AWTGLCanvas {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//The width and height of the AWTGLCanvas
	private int width, height;
	//A trigger which will tell the AWTGLCanvas to repaint itself once, and set the trigger to false immediatly afterwards
	boolean paint=true;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	/**
	 * Constructor, which just calls the superConstructor
	 * @throws LWJGLException
	 */
	public GameCanvas() throws LWJGLException {super();}
	
	/**
	 * Gets called after a CPU Instruction is complete, to update its View
	 */
	public void nextInstruction()
	{
		
	}
	
	/**
	 * Gets called all the time (srsly). So we use a trigger that we can better handle the calling
	 */
	@Override
	public void paintGL() {
    	if(paint)
    	try {
    		paint=false;
            if (getWidth() != width || getHeight() != height) {
            	width = getWidth();
            	height = getHeight();
                //glViewport(0, 0, current_width, current_height);
            }			            
            glViewport(0, 0, getWidth(), getHeight());
            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            gluOrtho2D(0.0f, (float) getWidth(), 0.0f, (float) getHeight());
            glMatrixMode(GL_MODELVIEW);
            glPushMatrix();
            
            //TODO: Insert CPU Analysis specific Code
            
            glPopMatrix();
            swapBuffers();
            repaint();
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
    }
}