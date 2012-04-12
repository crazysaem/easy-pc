package com.easypc.gui;

import java.awt.Color;

import com.easypc.controller.Controller;

/**
 * Gui Master - puts the entire Gui together and manages it
 * @author crazysaem
 *
 */
public class Gui {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//The controller will get information about the input from the user via the gui events in this class
	private Controller controller;	
	//the main gui frame
	private GuiFrame guiFrame;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Initializes the entire GUI and takes the controller Object
	 * @param controller
	 */
	public Gui(Controller controller)
	{
		this.controller = controller;
		
		guiFrame = new GuiFrame();
		guiFrame.getContentPane().setBackground(Color.BLACK);
		guiFrame.setVisible(true);		
	}
}
