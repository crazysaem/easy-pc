package com.easypc.backend;

import org.lwjgl.input.Keyboard;

/**
 * Wrapper for the Input part of the LWJGL
 * @author crazysaem
 *
 */
public class InputLWJGL {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//An Array for the 16 Input-Keys the Chip-8 System provides
	public boolean[] keys = new boolean[16];
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Initializes the LWJGL Audio
	 */
	public void Init()
	{
		//TODO: http://lwjgl.org/wiki/index.php?title=LWJGL_Basics_2_(Input)
	}
	
	/**
	 * Maps a real Key, from the Keyboard to a virtual key, from the Chip-8 System
	 * @param vKey the virtual key (0-15)
	 * @param rKey a 'real' key, e.g. Keyboard.KEY_A
	 */
	public void mapKey(int vKey, int rKey)
	{

	}

	/**
	 * - Will be placed into the CPU Instruction Cycle -
	 * Checks whether a key is currently pressed and writes it into the internal keys array 
	 */
	public void checkKeys()
	{
		//Test for maven, pls ignore
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) 
		{
			System.out.println("SPACE KEY IS DOWN");
		}
	}
	
	/**
	 * Checks whether the specified key is currently pressed 
	 */
	public boolean checkKey(int i)
	{
		return true;
	}
	
	/**
	 * Waits until the specified key is pressed 
	 */
	public void waitforKey(int i)
	{
		
	}
	/**
	 * Waits until key is pressed and return the value of the key 
	 */
	public int waitforKey()
	{
		return 1;
	}
}
