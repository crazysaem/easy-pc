package com.easypc.controller;

import com.easypc.backend.InputLWJGL;
import com.easypc.chip8.CPU;
import com.easypc.chip8.MediaOutput;
import com.easypc.chip8.RAM;

/**
 * Initializes the Application, e.g. creates and displays GUIs, and starts the Controller.
 * @author crazysaem
 *
 */
public class _main 
{
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	
	
	/**
	 * The Main Entry Point into the Application
	 * @param args - Not used
	 */
	public static void main(String args[])
	{
		MediaOutput media = new MediaOutput();
		InputLWJGL input = new InputLWJGL();
		RAM ram = new RAM();

		CPU cpu = new CPU(media,input,ram);
		
	}
}
