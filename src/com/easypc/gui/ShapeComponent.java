package com.easypc.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * This Class will set a custom Shape to a JFrame
 * @author crazysaem
 *
 */
public class ShapeComponent extends ComponentAdapter 
{
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//The calculated shape area from the image
	private Area area = null;
	//A pointer to the JFrame which will be set to the shape
	private JFrame frame = null;
	//The width and height from the Shape	
	private int width, height;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Constructor, takes a frame and a path to a black/white image and will then automatically set this as the shape of the Frame
	 * You have to call "addComponentListener(shapeComponent);" inside the frame to activate this
	 * @param frame The JFrame which will be set to the shape
	 * @param path A path to a black/white image containing the shape
	 */
	public ShapeComponent(JFrame frame, String path)
	{
		this.frame = frame;
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = image.getWidth();
		height = image.getHeight();
		area = getArea(image);
	}

	@Override
    public void componentResized(ComponentEvent e) {		
		frame.setShape(area);
    }
	
	/**
	 * Gets the width of the Shape
	 * @return int width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets the height of the Shape
	 * @return int height
	 */
	public int getHeight() {
		return height;
	}
	
	public void subtractShape(String path)
	{
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Area area = getArea(image);
		this.area.subtract(area);
	}
	
	public void addShape(String path)
	{
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Area area = getArea(image);
		this.area.add(area);
	}
	
	public void refreshShape()
	{
		frame.setShape(area);
	}
	
	/*----------------------------------------------------
	 * Private Method Section. Shows the Methods used internally.
	 *--------------------------------------------------*/
	
	/**
	 * Generates a Shape from a given Black and White PNG Image
	 * White will be transparent, black will be visible
	 * @param image The Black and White Image
	 * @return a Shape based on that Image
	 */
	private Area getArea(BufferedImage image) 
	{
		//Assumes Black as Shape Color
		if(image==null) return null;
		
		Area area = new Area();
		Color color = new Color(0,0,0,255);
		Rectangle r;
		int y1,y2;
		
		for (int x=0; x<image.getWidth(); x++) 
		{
			y1=99;
			y2=-1;
            for (int y=0; y<image.getHeight(); y++) 
            {
            	Color pixel = new Color(image.getRGB(x,y));
            	if(isIncluded(color, pixel)) 
            	{
                    if(y1==99) {y1=y;y2=y;}
                    if(y>(y2+1)) 
                    {
                    	r = new Rectangle(x,y1,1,y2-y1);
                    	area.add(new Area(r)); 
                    	y1=y;y2=y;
                    }
                    y2=y;
                }            	
            }
            
            if((y2-y1)>=0) 
            {
        		r = new Rectangle(x,y1,1,y2-y1);
            	area.add(new Area(r)); 
        	}
		}
		
		return area;
	}

	/**
	 * Helper Function for getArea
	 * It checks whether to colors match
	 * @param target the target color
	 * @param pixel the pixel color from the black and white image
	 * @return whether the two colors match
	 */
    private boolean isIncluded(Color target, Color pixel) 
    {
        int rT = target.getRed();
        int gT = target.getGreen();
        int bT = target.getBlue();
        int aT = target.getAlpha();        
        int rP = pixel.getRed();
        int gP = pixel.getGreen();
        int bP = pixel.getBlue();
        int aP = pixel.getAlpha();
        return(
            (rP<=rT) && (rT<=rP) &&
            (gP<=gT) && (gT<=gP) &&
            (bP<=bT) && (bT<=bP) &&
            (aP<=aT) && (aT<=aP) );
    }

}
