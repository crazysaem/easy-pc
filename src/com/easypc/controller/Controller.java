package com.easypc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.easypc.chip8.CPU;
import com.easypc.chip8.RAM;
import com.easypc.gui.Gui;

/**
 * The following Class represents the Controller of the Program, which - for the most part - will control the Application
 * @author crazysaem
 *
 */
public class Controller 
{
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//The CPU which executes the CHIP-8 opCodes
	private CPU cpu;
	private RAM ram;
	//private Gui gui;
	
	//CPU Cycle running flag
	private boolean isRunning;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * The Controller Constructor
	 * @param cpu
	 */
	public Controller(CPU cpu, RAM ram)
	{
		this.cpu = cpu;
		this.ram = ram;
		//this.gui = gui;
	}

	
	/**
	 * Gets called from the View when the Player "turns the Game off", via the reset Button.
	 * The Function resets e.g. The RAM, All Registers etc. and will then tell the Main-View to display the list of available Games.
	 */
	public void resetGame() 
	{
		ram.reset();
		cpu.reset();
		
		//TODO: display list of Games/Roms as function call - we need a reference to the gui for calling showList()
	}
	
	/**
	 * Gets called when the Player chooses a Game from the list of available Games.
	 * The Controller will load the ROM into the RAM and tell the Main View to display a blank screen.
	 */
	public void loadGame() 
	{
		byte[] rom = null;
		try {
			rom = getBytesFromFile(null /*TODO: Load the right rom which was choosen from the user*/);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ram.write(512, rom);
	}
	
	/**
	 * Gets called when the Player presses the "Play" button. The Controller will then loop at the normal speed of a Chip-8 Emulator through
	 * the opCodes to allow the player to play the Game as normal.
	 * @throws InterruptedException 
	 */
	public void playGame() throws InterruptedException 
	{
		while(isRunning){
			ram.read(cpu.getRegister(19), 2);
			wait(10);				
		}
	}

	/**
	 * Gets called when the Player pauses a Game. The Controller will pause the Emulation loop, allowing it to continue as normal later.
	 * Or, if chosen via other functions, to step back, or forward.
	 */
	public void pauseGame()
	{
		isRunning = false;
	}
	/**
	 * Gets called when the Player resume a Game. The Controller will resume the Emulation loop.
	 */
	public void resumeGame()
	{
		isRunning = true;
	}
	/**
	 * Gets called when the Player presses the Step Forward Button. The Controller will then Step one, or multiple opCodes foward in an instant,
	 * depending on the level of abstraction displayed in the upper view (The CPU-Instruction View)
	 */
	public void stepForward() 
	{
		ram.read(cpu.getRegister(19), 2);
	}
		
	/**
	 * Gets called when the Player presses the Step Backward Button. The Controller will then Step one, or multiple opCodes backward in an instant,
	 * depending on the level of abstraction displayed in the upper view (The CPU-Instruction View)
	 */
	public void stepBackward() 
	{
		ram.read(cpu.getRegister(19)-2, 2);
	}
	
	/**
	 * Reads a file into a byte array
	 * @param file the file you want to read
	 * @return the byte array from the given input file
	 * @throws IOException if something goes wrong
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    // Get the size of the file
	    long length = file.length();

	    // You cannot create an array using a long type.
	    // It needs to be an int type.
	    // Before converting to an int type, check
	    // to ensure that file is not larger than Integer.MAX_VALUE.
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    	return null;
	    }

	    // Create the byte array to hold the data
	    byte[] bytes = new byte[(int)length];

	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    // Close the input stream and return bytes
	    is.close();
	    return bytes;
	}
}
