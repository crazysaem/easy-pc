package com.easypc.controller;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.easypc.analysis.CPUAnalysisC;
import com.easypc.analysis.RAMAnalysisC;
import com.easypc.backend.Input;
import com.easypc.chip8.CPU;
import com.easypc.chip8.GameCanvas;
import com.easypc.chip8.MediaOutput;
import com.easypc.chip8.RAM;
import com.easypc.gui.Gui;

/**
 * Initializes the Application, e.g. creates and displays GUIs, and starts the Controller.
 * @author crazysaem
 *
 */
public class _main 
{
	/*----------------------------------------------------
	 * Global Constants:
	 *--------------------------------------------------*/
	
	public static final boolean DEBUG = false;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * The Main Entry Point into the Application
	 * It will initialize all needed Components
	 * @param args - Not used
	 */
	public static void main(String args[])
	{
		MediaOutput media = new MediaOutput();
		Input input = new Input();
		
		RAM ram = new RAM();

		CPU cpu = new CPU(media,input,ram);
		
		Controller controller = new Controller(cpu,ram);
		
		CPUAnalysisC cpuAnalysisC = null;
		RAMAnalysisC ramAnalysisC = null;
		GameCanvas gamecanvas = null;
		try {
			cpuAnalysisC = new CPUAnalysisC();
			ramAnalysisC = new RAMAnalysisC(ram, cpu);
			gamecanvas = new GameCanvas(media);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}		
		
		Gui gui = new Gui(controller, cpuAnalysisC, ramAnalysisC, gamecanvas, input, cpu);
		input.Init(controller,gui);		
		cpu.defineGUI(gui);
		
		//FPS for every LWJGL Display:
		Display.sync(30);
	}
}
