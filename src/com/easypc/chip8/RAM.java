package com.easypc.chip8;

/**
 * The virtual RAM of the Chip-8 System
 * @author crazysaem
 *
 */
public class RAM {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//The Maximum Length of the RAM
	public static final int MAX_LENGTH = 4096;
	
	//The virtual RAM-memory
	private int[] memory = new int[4096];
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Writes an arbitrary amount of Data into the RAM at a specific address
	 * @param offset The start Address
	 * @param data An int Array of Data
	 */
	public void write(int offset, int... data)
	{
		
	}
	
	/**
	 * Reads an arbitrary amount of Data from the RAM and returns it as an int array
	 * @param offset The start Address
	 * @param count The number of bytes to read
	 * @return An int array representing the desired data
	 */
	public int[] read(int offset, int count)
	{
		return null;
	}
}
