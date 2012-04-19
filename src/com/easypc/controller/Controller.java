package com.easypc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.DefaultListModel;

import com.easypc.chip8.CPU;
import com.easypc.chip8.RAM;

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
	
	//CPU Cycle running flag
	private boolean isRunning;
	
	private Thread runningThread;
	private ControllerRunningThread controllerRunningThread;
	
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
		controllerRunningThread = new ControllerRunningThread(cpu, ram);		
	}
	
	/**
	 * Gets called from the View when the Player "turns the Game off", via the reset Button.
	 * The Function resets e.g. The RAM, All Registers etc. and will then tell the Main-View to display the list of available Games.
	 */
	public void resetGame() 
	{
		controllerRunningThread.isRunning=false;
		cpu.executeOpCode(0, 0, 0xE, 0);
		ram.reset();
		cpu.reset();		
	}
	
	/**
	 * This Method should load the list of files needed for the GUI and the loadGame Method
	 * @return a list thats used in the GUI TODO: perhaps another type would be better for use in the controller?
	 */
	public DefaultListModel<String> getRomList() {
				
		// Directory path here - this is where we store the games
		String path = "src/resources/games/.";
		 
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 
		DefaultListModel<String> listmodel = new DefaultListModel<String>();
		
		for (int i = 0; i < listOfFiles.length; i++) 
		{
		 if (listOfFiles[i].isFile()) 
		 {
		 files = listOfFiles[i].getName();
		 listmodel.addElement(files);		 
		    }
		}
		return listmodel;
	}
	
	/**
	 * Gets called when the Player chooses a Game from the list of available Games.
	 * The Controller will load the ROM into the RAM and tell the Main View to display a blank screen.
	 */
	public void loadGame(String game) 
	{
		
		File fgame = new File("src/resources/games/"+game);
		
		int[] rom = null;
		try {
			rom = getBytesFromFile(fgame);
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
		controllerRunningThread.isRunning=true;
		runningThread = new Thread(controllerRunningThread);
		runningThread.start();
	}

	/**
	 * Gets called when the Player pauses a Game. The Controller will pause the Emulation loop, allowing it to continue as normal later.
	 * Or, if chosen via other functions, to step back, or forward.
	 */
	public void pauseGame()
	{
		isRunning = false;
		controllerRunningThread.isRunning=false;
	}
	/**
	 * Gets called when the Player resume a Game. The Controller will resume the Emulation loop.
	 */
	public void resumeGame()
	{
		isRunning = true;
		controllerRunningThread.isRunning=true;
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
	public static int[] getBytesFromFile(File file) throws IOException {
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
	    int[] ints = new int[(int)length];

	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }
	    
	    for(int i=0;i<bytes.length;i++)
	    {
	    	if(bytes[i]<0)
	    	{
	    		ints[i] = 256 + bytes[i];
	    	} 
	    		else
	    	{
	    		ints[i] = bytes[i];
	    	}
	    }

	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    // Close the input stream and return bytes
	    is.close();
	    return ints;
	}
}
