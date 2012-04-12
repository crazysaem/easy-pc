package com.easypc.gui;

import java.awt.Color;

import com.easypc.analysis.CPUAnalysisC;
import com.easypc.analysis.RAMAnalysisC;
import com.easypc.chip8.GameCanvas;
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
	public Gui(Controller controller, CPUAnalysisC cpuAnalysisC, RAMAnalysisC ramAnalysisC, GameCanvas gamecanvas)
	{
		this.controller = controller;
		
		guiFrame = new GuiFrame();
		guiFrame.getContentPane().setBackground(Color.BLACK);
		guiFrame.setVisible(true);
		
		cpuAnalysisC.setBounds(409, 7, 777-409, 185-7);
		cpuAnalysisC.addMouseListener(guiFrame);
		cpuAnalysisC.addMouseMotionListener(guiFrame);   
		guiFrame.add(cpuAnalysisC);
		
		ramAnalysisC.setBounds(5, 241, 373-5, 419-241);
		ramAnalysisC.addMouseListener(guiFrame);
		ramAnalysisC.addMouseMotionListener(guiFrame);   
		guiFrame.add(ramAnalysisC);
		
		gamecanvas.setBounds(385, 230, 416, 200);
		gamecanvas.addMouseListener(guiFrame);
		gamecanvas.addMouseMotionListener(guiFrame);   
		guiFrame.add(gamecanvas);
	}
}
