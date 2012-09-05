package com.easypc.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.easypc.backend.Input;

/**
 * Catches mouse events from the buttons in the bottom window, to control the game
 * @author samuelsc
 *
 */
public class ButtonCallback implements MouseListener
{
	private int button=-1;
	private Input input;
	
	public ButtonCallback(int button, Input input)
	{
		this.button = button;
		this.input = input;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		input.setKeyState(button, true);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{		
		input.setKeyState(button, false);
	}

}
