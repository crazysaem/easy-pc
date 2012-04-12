package com.easypc.chip8;

import java.util.ArrayList;

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
	//Counts every read memory access. Is used by the Analysis
	public int[] memory_count_read = new int[4096];
	//Counts every write memory access. Is used by the Analysis
	public int[] memory_count_write = new int[4096];
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Constructor of the Class - Initializes necessary start values
	 */
	public RAM()
	
	//Fonts 0-9 and A-F into the first 0x200h/500d Bytes of the RAM as seen here: http://devernay.free.fr/hacks/chip8/C8TECH10.HTM#2.4

	{
		write(0,0xF0,0x90,0x90,0x90,0xF0, 		//0
				0x20,0x60,0x20,0x20,0x70,     	//1
				0xF0,0x10,0xF0,0x80,0xF0,		//2
				0xF0,0x10,0xF0,0x10,0xF0,		//3
				0x90,0x90,0xF0,0x10,0x10,		//4
				0xF0,0x80,0xF0,0x10,0xF0,		//5
				0xF0,0x80,0xF0,0x90,0xF0,		//6
				0xF0,0x10,0x20,0x40,0x40,		//7
				0xF0,0x90,0xF0,0x90,0xF0,		//8
				0xF0,0x90,0xF0,0x10,0xF0,		//9
				0xF0,0x90,0xF0,0x90,0x90,		//A
				0xE0,0x90,0xE0,0x90,0xE0,		//B
				0xF0,0x80,0x80,0x80,0xF0,		//C
				0xE0,0x90,0x90,0x90,0xE0,		//D
				0xF0,0x80,0xF0,0x80,0xF0,		//E
				0xF0,0x80,0xF0,0x80,0x80		//F
		);
		//TODO: Put the Fonts 0-9 and A-F into the first 0x200h/500d Bytes of the RAM as seen here: http://devernay.free.fr/hacks/chip8/C8TECH10.HTM#2.4
	}
	
	/**
	 * Writes an arbitrary amount of Data into the RAM at a specific address
	 * @param offset The start Address
	 * @param data An int Array of Data
	 */
	public void write(int offset, int... data)
	{
		for(int i=0;i<data.length; i++){
			memory[offset+i]=data[i];
			memory_count_write[offset+i]++;
			
		}
		//Also add every memory access to the count array
	}
	
	/**
	 * Writes an arbitrary amount of Data into the RAM at a specific address
	 * @param offset The start Address
	 * @param data An byte Array of Data
	 */
	public void write(int offset, byte... data)
	{
		for(int i=0;i<data.length; i++){
			memory[offset+i]=data[i];
			memory_count_write[offset+i]++;
			
		}
		//Also add every memory access to the count array
	}
	
	/**
	 * Reads an arbitrary amount of Data from the RAM and returns it as an int array
	 * @param offset The start Address
	 * @param count The number of bytes to read
	 * @return An int array representing the desired data
	 */
	public ArrayList<Integer> read(int offset, int count)
	{
		ArrayList<Integer> data = new ArrayList<Integer>();
		//Also add every memory access to the count array
		for(int f=0;f<count;f++){
		data.add(memory[offset+f]);
		memory_count_read[offset+f]++;
		}
		return data;
		
	}
}
