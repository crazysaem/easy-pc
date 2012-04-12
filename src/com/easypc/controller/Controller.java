package com.easypc.controller;

import com.easypc.chip8.CPU;

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
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * The Controller Constructor
	 * @param cpu
	 */
	public Controller(CPU cpu)
	{
		this.cpu = cpu;
	}
	
	/**
	 * Gets called from the View when the Player "turns the Game off", via the reset Button.
	 * The Function the resets e.g. The RAM, All Registers etc. and will then tell the Main-View to display the list of available Games.
	 */
	public void resetGame() 
	{
		//TODO: Add logic.
	}
	
	/**
	 * Gets called when the Player chooses a Game from the list of available Games.
	 * The Controller will load the ROM into the RAM and tell the Main View to display a blank screen.
	 */
	public void loadGame() 
	{
		//TODO: Add logic.
	}
	
	/**
	 * Gets called when the Player presses the "Play" button. The Controller will then loop at the normal speed of a Chip-8 Emulator through
	 * the opCodes to allow the player to play the Game as normal.
	 */
	public void playGame() 
	{
		//TODO: Add logic.		
	}

	/**
	 * Gets called when the Player pauses a Game. The Controller will pause the Emulation loop, allowing it to continue as normal later.
	 * Or, if chosen via other functions, to step back, or forward.
	 */
	public void pauseGame()
	{
		//TODO: Add logic.
	}

	/**
	 * Gets called when the Player presses the Step Forward Button. The Controller will then Step one, or multiple opCodes foward in an instant,
	 * depending on the level of abstraction displayed in the upper view (The CPU-Instruction View)
	 */
	public void stepForward() 
	{
		//TODO: Add logic.
	}
		
	/**
	 * Gets called when the Player presses the Step Backward Button. The Controller will then Step one, or multiple opCodes backward in an instant,
	 * depending on the level of abstraction displayed in the upper view (The CPU-Instruction View)
	 */
	public void stepBackward() 
	{
		//TODO: Add logic.		
	}
}
