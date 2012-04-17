package com.easypc.chip8;

import java.awt.Toolkit;
import java.io.File;
import java.util.Arrays;

/**
 * An Easy Wrapper for the GUI Output / Backend to Display Stuff for the Chip-8 System
 * @author crazysaem
 *
 */
public class MediaOutput {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/	
	
	//Flag which determines if a Beep-Sound is output
	private boolean isBeeping;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	//Internal, Intermediate representation of the Display
	//Will get read by the GameCanvas which will display this as the Black and White Video Output
	public byte[][] display = new byte[64][32]; //TODO: we are facing a ArrayOutOBounds Exception when running most of the programs
	
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
		//TODO: I have the feeling this function writes bullshit into display[][]
		boolean ret=false;
		byte change;
		
		for(int j=0;j<data.length;j++)
		{
			for(int i=0;i<8;i++)
			{
				change = display[getX(x+i)][getY(y+j)];
				int temp = (data[j]>>(8-i-1));
				byte bit = (byte) (temp & 1);
				display[getX(x+i)][getY(y+j)] = (byte) (display[getX(x+i)][getY(y+j)] ^ bit);
				if((change != display[getX(x+i)][getY(y+j)]) && (display[getX(x+i)][getY(y+j)]==0))
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
		// reset the display array to 0s everywhere
		for(int i=0; i<display.length; i++){
			for(int j=0; j<display[i].length; j++){
				display[i][j] = 0;
			}
		}
	}
	
	/**
	 * Starts a Beep Sound
	 */
	public void startBeep()
	{
		isBeeping = true;
		//TODO: Implementation works without a loop, you can't just loop over it like that, it will cause very bad problems
		//Will will leave it like that (if we feel like it, we can use openAL later)
		
		//while(isBeeping){
			//System.out.println((char)7);  	//generates beeps until startb ist set to false. 
											//This Method only works after projekt is build because eclipse absorbs the "beep"
		//TODO: 2nd Solution, decide one
			
		Toolkit.getDefaultToolkit().beep(); //generates the windows warning sound. Works with eclipse without building the project
		//}
		
		
	}
	
	/**
	 * Stops the Beep Sound
	 */
	public void stopBeep()
	{
		isBeeping=false;
		
	}
	
	private int getX(int x)
	{
		if(x>=64)
		{
			return x-64;
		}
		else
		{
			return x;
		}
	}
	
	private int getY(int y)
	{
		if(y>=32)
		{
			return y-32;
		}
		else
		{
			return y;
		}
	}
}
