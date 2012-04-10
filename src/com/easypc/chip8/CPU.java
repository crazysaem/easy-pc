package com.easypc.chip8;

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
	 * @param c0 First Byte of the opCode
	 * @param c1 Second Byte of the opCode
	 */
	public void executeOpCode(int c0, int c1, int c2, int c3)
	{
		switch (c0)
		{
			case 0:
				if((c1==0) && (c2==0xE) && (c3==0))
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
}
