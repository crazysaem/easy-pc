package com.easypc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
	//The RAM which contains the data
	private RAM ram;
	
	//The Thread Object and the RunningThread together will form the executionThread
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
		controllerRunningThread.setRunning(false);
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
		if(!controllerRunningThread.isRunning())
		{
			controllerRunningThread.setRunning(true);
			runningThread = new Thread(controllerRunningThread);
			runningThread.start();
		}
	}

	/**
	 * Gets called when the Player pauses a Game. The Controller will pause the Emulation loop, allowing it to continue as normal later.
	 * Or, if chosen via other functions, to step back, or forward.
	 */
	public void pauseGame()
	{
		controllerRunningThread.setRunning(false);
	}

	/**
	 * Gets called when the Player presses the Step Forward Button. The Controller will then Step one, or multiple opCodes foward in an instant,
	 * depending on the level of abstraction displayed in the upper view (The CPU-Instruction View)
	 */
	public void stepForward() 
	{
		if(!controllerRunningThread.isRunning())
		{
			int PC = cpu.getRegister(19);
			ArrayList<Integer> opCode = ram.read(PC, 2);
			int temp = opCode.get(1);
			temp = temp & 0xF0;
			temp = temp >> 4;
			cpu.executeOpCode((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F);
		}
	}
	
	/**
	 * Executes opCodes until a Draw command was recognized.
	 * This provides better feedback to the end-user.
	 */
	public void stepForwardUntilDraw()
	{
		if(!controllerRunningThread.isRunning())
		{
			ArrayList<Integer> opCode;
			do
			{
				int PC = cpu.getRegister(19);
				opCode = ram.read(PC, 2);
				int temp = opCode.get(1);
				temp = temp & 0xF0;
				temp = temp >> 4;
				cpu.executeOpCode((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F);
			} while(((opCode.get(0) & 0xF0) >> 4)!=0xD);
		}
	}
		
	/**
	 * Gets called when the Player presses the Step Backward Button. The Controller will then Step one, or multiple opCodes backward in an instant,
	 * depending on the level of abstraction displayed in the upper view (The CPU-Instruction View)
	 */
	public void stepBackward() 
	{
		//TODO: IF(!) we want to support backwards stepping we will have to save the state of the CPU for every instruction (meaning all registers and whatnot) and revert that
		//		It won't function correctly with just the code below
		//ram.read(cpu.getRegister(19)-2, 2);
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
