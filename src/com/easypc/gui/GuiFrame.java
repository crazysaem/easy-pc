package com.easypc.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;


/**
 * The GuiFrame is the container which puts all Gui elements together
 * @author crazysaem
 *
 */
@SuppressWarnings("serial")
public class GuiFrame extends JFrame implements MouseMotionListener, MouseListener {
	
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/

	//The mouse* Attributes are used to store temporary information in order to move the frame with the mouse
	private boolean mousedown;
	private int mousex, mousey;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Initalises the Frame
	 */
	public GuiFrame()
	{
		//Sets the Title of the Frame to:
		super("easyPC");
		
		//Disable Window Frame
        setUndecorated(true);
        //Set the shape to the one specified in the PNG
        setShape("src/resources/shapes/all.png");        
        //Move Window to the middle of the Screen
        setLocationRelativeTo(null);
        //Choose Absolute Layout
        setLayout(null);  
        //Set the Application to exit when the Frame gets closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);   
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


	/**
	 * saves the position of the mouse and the fact that the mouse is now down
	 */
	@Override
	public void mousePressed(MouseEvent mouse) {
		mousedown=true;	
		mousex=mouse.getLocationOnScreen().x-this.getLocationOnScreen().x;
		mousey=mouse.getLocationOnScreen().y-this.getLocationOnScreen().y;
	}

	/**
	 * saves the fact, that the mouse is now up
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		mousedown=false;				
	}
	
	/**
	 * moves the window relative to the old mouse position which was stored in the mousePressed function
	 */
	@Override
	public void mouseDragged(MouseEvent mouse) {
		if(mousedown) {
			this.setLocation(mouse.getLocationOnScreen().x-mousex, mouse.getLocationOnScreen().y-mousey);		
		}		
	}
	
	/**
	 * Unused function, Implemented because of the Interface
	 */
	@Override
	public void mouseClicked(MouseEvent e) {}

	/**
	 * Unused function, Implemented because of the Interface
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 * Unused function, Implemented because of the Interface
	 */
	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	 * Unused function, Implemented because of the Interface
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {}
}
