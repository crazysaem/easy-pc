package com.easypc.gui;

import java.awt.Color;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

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
	private GameCanvas gameCanvas;
	
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
		this.gameCanvas = gamecanvas;
		
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
		
		gameCanvas.setBounds(385, 230, 416, 200);
		gameCanvas.addMouseListener(guiFrame);
		gameCanvas.addMouseMotionListener(guiFrame);   
		guiFrame.add(gameCanvas);
		
		showList();
	}
	
	/**
	 * Replaces the gamecanvas and shows a JList instead containing all games from a folder
	 */
	private void showList()
	{
		//TODO: Check showList() Implementation
		// Directory path here
		String path = "."; //TODO: where do we store the game files?
		 
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 
		DefaultListModel listmodel = new DefaultListModel();
		
		for (int i = 0; i < listOfFiles.length; i++) 
		{
		 if (listOfFiles[i].isFile()) 
		 {
		 files = listOfFiles[i].getName();
		 listmodel.addElement(files);
		 //System.out.println(files); //remove comment for debug output of the filelist
		    }
		}
		
		//make gameCanvas invisible (TODO: Pls check this - I created a private reference to the gamecanvas, is that ok?)
		gameCanvas.setVisible(false);
		
		//create JList of games called gameList and add it to the guiFrame
		gameList = new JList(listmodel);
		gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameList.setSelectedIndex(0);
		gameList.setVisibleRowCount(5);
		gameList.setBounds(385, 230, 416, 200);
		gameList.addMouseListener(guiFrame);
		gameList.addMouseMotionListener(guiFrame);
		gameList.setVisible(true);
		guiFrame.add(gameList);
		
		//this one is necessary, dont know why...
		guiFrame.repaint();
		
		
		// mage the gamecanvas invisible and show a JList http://docs.oracle.com/javase/tutorial/uiswing/components/list.html
		//	which contains the list array
		//use the gameList Attribute from this class
		//TIP: http://docs.oracle.com/javase/tutorial/uiswing/components/list.html
		
		// Use the code-snippet below from Fritz to display all files from a given folder
		//The dot "." will get you all the files in a folder, e.g. "bin/." should get you all files within the bin folder when used like below
		//String[] entries = new File( "." ).list(); 
		//System.out.println( Arrays.toString(entries) );
	}
	
	/**
	 * Removes the JList game list, and shows the gamecanvas again	
	 */
	private void showGameCanvas()
	{
		//TODO: The attached JList (gameList) from the above function should become invisible and the gamecanvas should replace it
	}
	
}
