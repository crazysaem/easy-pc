package com.easypc.chip8;

import java.io.File;
import java.util.Arrays;

/**
 * An Easy Wrapper for the GUI Output / Backend to Display Stuff for the Chip-8 System
 * @author crazysaem
 *
 */
public class MediaOutput {	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	//Internal, Intermediate representation of the Display
	//Will get read by the GameCanvas which will display this as the Black and White Video Output
	public byte[][] display = new byte[64][32];
	
	/**
	 * Displays a n-long Sprite which is read from the virtual memory via the I-Pointer and XORed to the Screen
	 * This Information is entirely supplied by the opCode
	 * "If this causes any pixels to be erased, VF is set to 1, otherwise it is set to 0"
	 * @param x The x Coordinate for the Sprite
	 * @param y The y Coordinate for the Sprite
	 * @param n The Read Count for the Pointer
	 */
	public boolean displaySprite(int x, int y, Integer... data)
	{
		boolean ret=false;
		byte change;
		
		for(int j=0;j<data.length;j++)
		{		
			for(int i=0;i<8;i++)
			{
				change = display[x+i][y+j];
				display[x+i][y+j] = (byte) (display[x+i][y+j] ^ ((data[j]>>i) & 1));
				if((change != display[x+i][y+j]) && (display[x+i][y+j]==0))
				{
					ret = true;
				}
			}
		}
		
		return ret;
	}
	
	/**
	 * Clears the GameCanvas and shows only Black
	 */
	public void clearScreen()
	{
		
	}
	
	/**
	 * Shows the list of available ROMs inside the GameCanvas
	 * TODO: Maybe use a SWING overlay to make it easier ? Or use a LWJGL GUI Library: http://twl.l33tlabs.org/ or http://nifty-gui.lessvoid.com/
	 */
	public void showGames()
	{
		String[] entries = new File( "." ).list(); 
		System.out.println( Arrays.toString(entries) );
		
		
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
