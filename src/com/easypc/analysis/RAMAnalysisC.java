package com.easypc.analysis;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import org.lwjgl.LWJGLException;

import com.easypc.backend.VideoLWJGL;
import com.easypc.chip8.RAM;
import com.easypc.controller._main;

/**
 * Is responsible for Analyzing the RAM and Displaying everything onto a Canvas
 * 
 * @author crazysaem
 * 
 */
@SuppressWarnings("serial")
public class RAMAnalysisC extends VideoLWJGL {
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	private RAM ram;
	private int width;
	private int height;
	int counter = width*height;
	int max_read = 1;
	int max_write = 1;

	/**
	 * Creates the RAMAnalysisC Object
	 * 
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
				
		this.height = (int) Math.sqrt(ram.MAX_LENGTH / 2);
		this.width = 2 * this.height;
		for(int i=512; i<ram.MAX_LENGTH;i++)
			if(ram.memory_count_read[i]>max_read) max_read = ram.memory_count_read[i];
		
		for(int i=512; i<ram.MAX_LENGTH;i++)
			if(ram.memory_count_write[i]>max_read) max_write = ram.memory_count_write[i];
			 

		glViewport(0, 0, getWidth(), getHeight()); // 64*32 is the exact display
		// size of the Chip-8 System
		glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0.0f, width, 0.0f, height);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();		

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				drawWhitePixel(x, y);

		//drawGrid();
 

		glPopMatrix();
		counter=width*height;
	}

	private void drawWhitePixel(int x, int y) {

		counter--;
		//System.out.println(max_read + "," + max_write + " | " + counter);
		if(counter==0)
			for(int i=0; i<ram.MAX_LENGTH;i++)
				ram.memory_count_read[i]*=0.2;
		glBegin(GL_QUADS);
		
		glColor3f((float)(ram.memory_count_read[(x+1)*(y+1)]/max_read), 0,(float)(ram.memory_count_write[(x+1)*(y+1)]/max_write));
		
		glVertex2f(x, y);
		glVertex2f(x + 1f, y);
		glVertex2f(x + 1f, y + 1f);
		glVertex2f(x, y + 1f);
		glEnd();
						
		// TIP: Pixel(0,0) would be a rectangle from like this: 0.0, 0.1, 1.1,
		// 0.1; Use Counter-Clock-Wise representation (= Gegen den Urzeigersinn)
		// http://nehe.gamedev.net/tutorial/your_first_polygon/13002/
		// Take a look at "GL_QUADS" from the above link
	}
	
	private void drawGrid(){
		glBegin(GL_LINES);
		
		for(int i = 1; i<width; i++){
			glColor3f(1, 1, 1);
		glVertex2f(i, 0);
		glVertex2f(i, height);
		}		
	
		for(int hor = 1; hor < height; hor++){
			glVertex2f(0,hor);
			glVertex2f(width, hor);	
		}
		
		glEnd();		
	}
}
