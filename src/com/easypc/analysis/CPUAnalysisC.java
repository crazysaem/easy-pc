package com.easypc.analysis;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.easypc.backend.VideoLWJGL;
import com.easypc.chip8.CPU;
import com.easypc.chip8.ForeshadowingCPU;
import com.easypc.chip8.RAM;

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
	private static final int ARROW = 6;
	private static final int UNKNWON = 7;
	
	ArrayList<Integer> showIcons;
	ForeshadowingCPU foreshadowingCPU;
	RAM ram;
	CPU cpu;

	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Creates the CPUAnalysisC Object
	 * @throws LWJGLException
	 * @throws IOException 
	 */
	public CPUAnalysisC(ForeshadowingCPU foreshadowingCPU, CPU cpu, RAM ram) throws LWJGLException 
	{
		super();
		
		showIcons = new ArrayList<Integer>();
		this.foreshadowingCPU = foreshadowingCPU;
		this.ram = ram;
		this.cpu = cpu;
	}
	
	public void reset()
	{
		showIcons.clear();
		foreshadowingCPU.reset();
		showIcons.add(UNKNWON);
		showIcons.add(UNKNWON);
		addThreeIcons();
	}
	
	public void clear()
	{
		showIcons.clear();
		foreshadowingCPU.reset();
	}
	
	private void addThreeIcons()
	{
		int PC = foreshadowingCPU.getRegister(19);
		ArrayList<Integer> opCode = ram.read(PC, 2);
		boolean isPredictable = foreshadowingCPU.executeOpCode((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F);
		
		int icon = getIconFromCPUInstruction((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F);
		showIcons.add(icon);
		
		if(!isPredictable)
		{
			showIcons.add(UNKNWON);
			showIcons.add(UNKNWON);
			showIcons.add(UNKNWON);
			return;
		}
		
		PC = foreshadowingCPU.getRegister(19);
		opCode = ram.read(PC, 2);
		isPredictable = foreshadowingCPU.executeOpCode((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F);
		
		icon = getIconFromCPUInstruction((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F);
		showIcons.add(icon);
		
		if(!isPredictable)
		{
			showIcons.add(UNKNWON);
			showIcons.add(UNKNWON);
			return;
		}
		
		PC = foreshadowingCPU.getRegister(19);
		opCode = ram.read(PC, 2);
		isPredictable = foreshadowingCPU.executeOpCode((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F);
		
		icon = getIconFromCPUInstruction((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F);
		showIcons.add(icon);
		
		if(!isPredictable)
		{
			showIcons.add(UNKNWON);
			return;
		}
	}
	
	@Override
	protected void initGL() {
		super.initGL();
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		textures = new Texture[8];
		
		try {
			textures[GRAPHIC] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/graphic.png"));
			textures[INPUT] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/input.png"));
			textures[MATH] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/math.png"));
			textures[MEMORY] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/memory.png"));
			textures[SOUND] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/sound.png"));
			textures[SYSTEM] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/system.png"));
			textures[ARROW] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/arrow.png"));
			textures[UNKNWON] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("src/resources/icons/unknown.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private int getIconFromCPUInstruction(int c0, int c1, int c2, int c3)
	{
		switch (c0) {
		case 0:
			if ((c1 == 0) && (c2 == 0xE) && (c3 == 0)) // 00E0 - CLS
				return GRAPHIC;

			if ((c1 == 0) && (c2 == 0xE) && (c3 == 0xE)) // 00EE - RTN
				return SYSTEM;
			break;

		case 1: // 1nnn - JP addr
			return SYSTEM;

		case 2: // 2nnn - CALL addr
			return SYSTEM;

		case 3: // 3xkk - SE Vx, byte
			return SYSTEM;

		case 4: // 4xkk - SNE Vx, byte
			return SYSTEM;

		case 5: // 5xy0 - SE Vx, Vy
			return SYSTEM;

		case 6: // 6xkk - LD Vx, byte
			return MATH;

		case 7: // 7xkk - ADD Vx, byte
			return MATH;

		case 8:
			switch (c3) {
			case 0: // 8xy0 - LD Vx, Vy
				return MATH;

			case 1: // 8xy1 - OR Vx, Vy
				return MATH;

			case 2: // 8xy2 - AND Vx, Vy
				return MATH;

			case 3: // 8xy3 - XOR Vx, Vy
				return MATH;

			case 4: // 8xy4 - ADD Vx, Vy
				return MATH;

			case 5: // 8xy5 - SUB Vx, Vy
				return MATH;

			case 6: // 8xy6 - SHR Vx {, Vy}
				return MATH;

			case 7: // 8xy7 - SUBN Vx, Vy
				return MATH;

			case 0xE: // 8xyE - SHL Vx {, Vy}
				return MATH;

			}
			break;
		case 9: // 9xy0 - SNE Vx, Vy
			return SYSTEM;

		case 0xA: // Annn - LD I, addr
			return MEMORY;

		case 0xB: // Bnnn - JP V0, addr
			return SYSTEM;

		case 0xC: // Cxkk - RND Vx, byte
			return MATH;

		case 0xD: // Dxyn - DRW Vx, Vy, nibble
			return GRAPHIC;

		case 0xE:
			switch (c2) {
			case 9: // Ex9E - SKP Vx
				return INPUT;

			case 0xA: // ExA1 - SKNP Vx
				return INPUT;

			}
			break;
		case 0xF:
			switch (CPU.get8BitValue(c2, c3)) {
			case 0x07: // Fx07 - LD Vx, DT
				return MATH;

			case 0x0A: // Fx0A - LD Vx, K
				return INPUT;

			case 0x15: // Fx15 - LD DT, Vx
				return SYSTEM;

			case 0x18: // Fx18 - LD ST, Vx
				return SOUND;

			case 0x1E: // Fx1E - ADD I, Vx
				return MEMORY;

			case 0x29: // Fx29 - LD F, Vx
				return MEMORY;

			case 0x33: // Fx33 - LD B, Vx
				return MEMORY;

			case 0x55: // Fx55 - LD [I], Vx
				return MEMORY;

			case 0x65: // Fx65 - LD Vx, [I]
				return MEMORY;

			}
			break;			
		}
		System.err.println("ERROR: Unregocnised Command: " + c0 + c1 + c2 + c3);
		return -1;
	}

	/**
	 * Gets called after a CPU Instruction is complete, to update its View
	 */
	public void nextInstruction()
	{
		showIcons.remove(0);
		
		if(showIcons.get(showIcons.size()-1)==UNKNWON)
		{
			if(showIcons.get(2) == UNKNWON)
			{
				showIcons.remove(2);
				showIcons.remove(2);
				showIcons.remove(2);

				//sync CPUs:				
				for(int i=0; i<=19; i++)
				{
					foreshadowingCPU.setRegister(i, cpu.getRegister(i));				
				}
				foreshadowingCPU.setPCStack(cpu.getPCStack());
				
				addThreeIcons();
			}
			else
			{
				showIcons.add(UNKNWON);
			}
		}
		else
		{	
			int PC = foreshadowingCPU.getRegister(19);
			ArrayList<Integer> opCode = ram.read(PC, 2);
			boolean isPredictable = foreshadowingCPU.executeOpCode((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F);
			
			int icon = getIconFromCPUInstruction((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F);
			showIcons.add(icon);
			
			if(!isPredictable)
			{
				showIcons.add(UNKNWON);
				return;
			}
		}
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
		/*
		drawIcon(GRAPHIC, 	-13, 	37, 30, 30, 0.3f);
		drawIcon(INPUT, 	37, 	37, 30, 30, 0.3f);
		drawIcon(MATH, 		87, 	37, 30, 30, 1.0f);
		drawIcon(MEMORY, 	137, 	37, 30, 30, 1.0f);
		drawIcon(SOUND, 	187, 	37, 30, 30, 1.0f);
		*/

		try	{
		drawIcon(showIcons.get(0), 	-13, 	37, 30, 30, 0.3f);
		drawIcon(showIcons.get(1), 	37, 	37, 30, 30, 0.3f);
		drawIcon(showIcons.get(2), 	87, 	37, 30, 30, 1.0f);
		drawIcon(showIcons.get(3), 	137, 	37, 30, 30, 1.0f);
		drawIcon(showIcons.get(4), 	187, 	37, 30, 30, 1.0f);
		} catch( IndexOutOfBoundsException e ) {};
		
		//if((showIcons.size()>=3) && (showIcons.size()<=5))
		drawIcon(ARROW, 87,	69, 30, 30, 1.0f);
		
		glPopMatrix();
	}
	
	private void drawIcon(int icon, int x, int y, int width, int height, float alpha)
	{
		textures[icon].bind();
		
		glColor4f(1f, 1f, 1f, alpha);
		
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
