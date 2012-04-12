package com.easypc.gui;

import javax.swing.JFrame;


/**
 * The GuiFrame is the container which puts all Gui elements together
 * @author crazysaem
 *
 */
@SuppressWarnings("serial")
public class GuiFrame extends JFrame {

	/**
	 * Initalises the Frame
	 */
	public GuiFrame()
	{
		//Sets the Title of the Frame to:
		super("easyPC");
		
		//Disable Window Frame
        setUndecorated(true);
        
        //Move Window to the middle of the Screen
        setLocationRelativeTo(null);
        //Choose Absolute Layout
        setLayout(null);  
        
        setShape("src/resources/shapes/all.png"); //TODO: Add the path to the right PNG file        
	}
	
	/**
	 * Sets the custom shape of this frame
	 * @param path The path to the black/while PNG file containing the shape
	 */
	private void setShape(String path)
	{
		ShapeComponent shapeComponent = new ShapeComponent(this, path);
		addComponentListener(shapeComponent);
		setSize(shapeComponent.getWidth(), shapeComponent.getHeight());
	}
}
