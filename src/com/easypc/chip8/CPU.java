package com.easypc.chip8;

import java.util.ArrayList;

/**
 * The virtual CPU of the Chip-8 System
 * @author crazysaem
 *
 */
public class CPU {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//The Registers V0-VF for the CPU:
	private int[] V = new int[16];
	//A Pointer which can be set to an arbitrary address in the virtual RAM by the Chip 8 program
	private int I=0;
	//A timer, which counts to zero (if it is non-zero) at a rate of 60Hz
	private int delay;
	//The same as the delay timer, and will additionally output a beep-sound as long as the timer is non-zero
	private int sound;
	//Internal Registers:
		//Program Counter, points to the position in the RAM which should be executed next
		private int PC=0x200; //500d - The ROM will be loaded into the RAM with the starting address 0x200h/500d
		//The Stack will be used to save the PC when a function was called. The PC will be restored after a RET statement from the Chip 8 Program
		private ArrayList<Integer> PCstack = new ArrayList<Integer>();
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Initializes the CPU
	 */
	public void Init()
	{
		for(int i=0;i<16;i++) V[i] = 0;
	}
	
	/**
	 * Executes a Chip-8 OpCode
	 * @param c0 The 1st 4 Bit of the opCode 
	 * @param c1 The 2nd 4 Bit of the opCode
	 * @param c2 The 3rd 4 Bit of the opCode
	 * @param c3 The 4th 4 Bit of the opCode
	 */
	public void executeOpCode(int c0, int c1, int c2, int c3)
	{
		//The CPU Speed is unknown, but the timer decreasing speed is fixed at 60hz
		switch (c0)
		{
			case 0:
				if((c1==0) && (c2==0xE) && (c3==0))	//00E0
				{
					//Clear Screen;
				}
			break;
			
			default:
				System.err.println("ERROR: Unregocnised Command: " + c0 + c1 + c2 + c3);
			break;
		
		}
	}
	
	/**
	 * Sets a specific Register to a specific Value
	 * @param regNumber The Number of the Register
	 * @param value The Value of the Register
	 */
	public void setRegister(int regNumber, int value)
	{
		
	}
	
	/**
	 * Returns the Value of the specified Register
	 * @param regNumber The Number of the Register
	 * @return The Value of the Register
	 */
	public int getRegister(int regNumber)
	{
		return -1;		
	}
	
	/*----------------------------------------------------
	 * Private Method Section. Shows the Methods used internally.
	 *--------------------------------------------------*/
	
	/**
	 * Converts 2x 4Bit Numbers into an 8Bit Number
	 * @param i0 The 1st 4 Bit
	 * @param i1 The 1st 4 Bit
	 * @return the constructed 8 Bit Number
	 */
	private int get8BitValue(int i0, int i1)
	{
		//Conversion needed, as seen here: http://devernay.free.fr/hacks/chip8/C8TECH10.HTM#3.0
		return -1;		
	}
	
	/**
	 * Converts 3x 4Bit Numbers into an 12Bit Number (used for adressing)
	 * @param i0 The 1st 4 Bit
	 * @param i1 The 2nd 4 Bit
	 * @param i2 The 3rd 4 Bit
	 * @return the constructed 12 Bit Number
	 */
	private int get12BitValue(int i0, int i1, int i2)
	{
		//Conversion needed, as seen here: http://devernay.free.fr/hacks/chip8/C8TECH10.HTM#3.0
		return -1;		
	}
}
