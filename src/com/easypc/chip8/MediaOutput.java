package com.easypc.chip8;

/**
 * An Easy Wrapper for the GUI Output / Backend to Display Stuff for the Chip-8 System
 * @author crazysaem
 *
 */
public class MediaOutput {	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Displays a n-long Sprite which is read from the virtual memory via the I-Pointer and XORed to the Screen
	 * This Information is entirely suplied by the opCode
	 * "If this causes any pixels to be erased, VF is set to 1, otherwise it is set to 0"
	 * @param x The x Coordinate for the Sprite
	 * @param y The y Coordinate for the Sprite
	 * @param n The Read Count for the Pointer
	 */
	public void displaySprite(int x, int y, int n)
	{
		
	}
	
	/**
	 * Starts a Beep Sound
	 */
	public void startBeep()
	{
		
	}
	
	/**
	 * Stops the Beep Sound
	 */
	public void stopBeep()
	{
		
	}
}
