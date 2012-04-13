package com.easypc.gui;

import java.awt.Color;

import javax.swing.JList;

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
	//The listview containing all Games
	private JList gameList;	//TODO: handle the Input from the list correctly, a.k.a: Load the chosen Game and re-attach the gameCanvas after a game was chosen
	
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
	
	/**
	 * Replaces the gamecanvas and shows a JList instead containing all entries from list	
	 * @param list the entries to be shown inside the JList
	 */
	private void showList(String[] list)
	{		
		//TODO: mage the gamecanvas invisible and show a JList http://docs.oracle.com/javase/tutorial/uiswing/components/list.html
		//	which contains the list array
		//use the gameList Attribute from this class
		//TIP: http://docs.oracle.com/javase/tutorial/uiswing/components/list.html
	}
	
	/**
	 * Removes the JList game list, and shows the gamecanvas again	
	 */
	private void showGameCanvas()
	{
		//TODO: The attached JList (gameList) from the above function should become invisible and the gamecanvas should replace it
	}
}
