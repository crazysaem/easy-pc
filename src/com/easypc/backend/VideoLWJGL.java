package com.easypc.backend;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;

/**
 * The Superclass of all Canvas classes which want to use openGL
 * @author crazysaem
 *
 */
@SuppressWarnings("serial")
public class VideoLWJGL extends AWTGLCanvas {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//The width and height of the AWTGLCanvas
	private int width, height;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	/**
	 * Constructor, which just calls the superConstructor
	 * @throws LWJGLException
	 */
	public VideoLWJGL() throws LWJGLException
	{
		super();
	}	
	
	@Override
	protected void initGL() {
		super.initGL();
	}

	/**
	 * Gets called all the time (srsly). So we use a trigger that we can better handle the calling
	 */
	@Override
	public void paintGL() {
    	try {
            if (getWidth() != width || getHeight() != height) {
            	width = getWidth();
            	height = getHeight();
            }			
         
            drawOpenGl();

            swapBuffers();
            repaint();
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
    }
	
	/**
	 * Override this to draw openGl stuff onto the canvas
	 * Please Override me.
	 */
	public void drawOpenGl()
	{
		
	}
}
