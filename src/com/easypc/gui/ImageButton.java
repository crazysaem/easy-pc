package com.easypc.gui;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This Function simulates a Button through an Icon placed on a Label
 * @author Team5-Listener
 *
 */
public class ImageButton implements MouseListener, MouseMotionListener {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//The Label which will simulate the Button
	private JLabel label;
	//2 Images which will store different states of the Button
	private ImageIcon icon, iconOver;
	private BufferedImage image, imageOver;
	//The position of the button
	private int x, y;
	//The parent Object which will recive a callback when the button has been clicked
	private ImageButtonCallBack parent=null;
	//Flag Variables to determine the state the button is currently in
	private boolean mouseDown=false;
	private boolean inPressEffect=false, inPressed=false, reset=false;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Initializes an ImageButton
	 * @param parent ImageButtonCallBack Object which shall receive a callback with this Object when this Button has been clicked
	 * @param imagePath the Path to the Image which resembles the default button state
	 * @param imageMouseOverPath the Path to the Image which resembles the MouseOver button state
	 * @param x vertical Position of the Button
	 * @param y horizontal Position of the Button
	 * @param scale the scale factor of the Images provided via the imagepaths (1.0f is standard)
	 */
	public ImageButton(ImageButtonCallBack parent, String imagePath, String imageMouseOverPath, int x, int y, float scale) {
		try {
			image = ImageIO.read(new File(imagePath));
	        icon = new ImageIcon(image.getScaledInstance((int)(image.getWidth()*scale), (int)(image.getHeight()*scale), Image.SCALE_SMOOTH));
	        label = new JLabel(icon);
	        
	        imageOver = ImageIO.read(new File(imageMouseOverPath));
	        iconOver = new ImageIcon(imageOver.getScaledInstance((int)(imageOver.getWidth()*scale), (int)(imageOver.getHeight()*scale), Image.SCALE_SMOOTH));
	        
	        //imagePressed = ImageIO.read(new File(imageMousePressedPath));
	        //iconPressed = new ImageIcon(imagePressed);
	        
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }	   
		this.x = x;
		this.y = y;
		label.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
		
		label.addMouseListener(this);
        label.addMouseMotionListener(this);   
        this.parent = parent;
	}
	
	/**
	 * Resets the position of the button to a new position
	 * @param x the vertical position
	 * @param y the horizontal position
	 */
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
		label.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
	}
	
	/**
	 * Getter for width
	 * @return width of the Button
	 */
	public int getWidth() {
		return icon.getIconWidth();
	}
	
	/**
	 * Getter for height
	 * @return height of the Button
	 */
	public int getHeight() {
		return icon.getIconHeight();
	}
	
	/**
	 * Getter for x
	 * @return the current vertical position
	 */
	public int getXPos() {
		return x;
	}
	
	/**
	 * Getter for y
	 * @return the current horizontal position
	 */
	public int getYPos() {
		return y;
	}
	
	/**
	 * Return the JLabel in which the Button is actually represented with
	 * @return JLabel
	 */
	public JLabel getLabel() {
		return label;
	}
	
	/**
	 * Checks whether the Pixel from the Image sample is transparent or not
	 * @param img	The BufferedImage from which you want to sample
	 * @param x		The X Position of the sample pixel
	 * @param y		The Y Position of the sample pixel
	 * @return
	 */
	private boolean transparencyCheck(BufferedImage img, int x, int y) {
		if((x<0) || (y<0) || (x>=img.getWidth()) || (y>=img.getHeight())) return false;
		int color = img.getRGB(x, y);
		int alpha = (color>>24)&255;
		if (alpha>0) return true; 
			else 
		return false;
	}

	/*----------------------------------------------------
	 * The next mouse* methods will handle the different states of the button when the user interacts with it via the mouse
	 *--------------------------------------------------*/
	
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!mouseDown) {
			if(!transparencyCheck(image, e.getX(), e.getY())) return;
			//if(!inPressed) {label.setIcon(iconOver);}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(!transparencyCheck(image, e.getX(), e.getY())) {
			return;
		}
		//TODO: Overlay effect deactivated until it is implemented correctly. In the current state it is bugged
		//if(!inPressed) {if(!mouseDown) label.setIcon(iconOver);	else label.setIcon(iconPressed);}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(transparencyCheck(image, e.getX(), e.getY())) {
			return;
		}
		if(!inPressed) {label.setIcon(icon);}		
	}

	/**
	 * Will also call the CallBack function of the parent object which was provided in the constructor
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown=true;
		if(!transparencyCheck(image, e.getX(), e.getY())) {
			//parent.mousePressed(e);
			return;
		}
		//if(!inPressed) {label.setIcon(iconPressed);}	
		parent.ButtonCallBack(this);
		if((inPressEffect) && (!reset)) {label.setIcon(iconOver);inPressed=true;}
		reset=false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {}	
}
