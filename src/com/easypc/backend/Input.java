package com.easypc.backend;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.easypc.controller.Controller;
import com.easypc.gui.Gui;

/**
 * Wrapper for the Input part of the LWJGL
 * 
 * @author crazysaem
 * 
 */
public class Input {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/

	// An Array for the 16 Input-Keys the Chip-8 System provides
	public boolean[] keys = new boolean[16];
	public KeyListener keylistener;
	public Controller controller;
	public Gui gui;

	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	/**
	 * Initializes the LWJGL Audio
	 */
	public void Init(Controller controller, Gui gui) {
		// TODO: http://lwjgl.org/wiki/index.php?title=LWJGL_Basics_2_(Input)
		this.controller = controller;
		this.gui = gui;
	}

	/**
	 * Maps a real Key, from the Keyboard to a virtual key, from the Chip-8
	 * System
	 * 
	 * @param vKey
	 *            the virtual key (0-15)
	 * @param rKey
	 *            a 'real' key, e.g. Keyboard.KEY_A
	 */
	public void mapKey(int vKey, int rKey) {

	}

	/**
	 * - Will be placed into the CPU Instruction Cycle - Checks whether a key is
	 * currently pressed and writes it into the internal keys array
	 */
	public int checkKeys() {
		int pressed_key = 0;
		
		//Original Keyboard Layout (Reference)
		//1	 2  3  C
		//4  5  6  D
		//7	 8  9  E
		//A	 0  B  F

		keylistener = new KeyListener() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_1:
					keys[1] = true;
				break;
				
				case KeyEvent.VK_2:
					keys[2] = true;
				break;
				
				case KeyEvent.VK_3:
					keys[3] = true;
				break;
				
				case KeyEvent.VK_4:
					keys[0xC] = true;
				break;
				
				case KeyEvent.VK_Q:
					keys[4] = true;
				break;
				
				case KeyEvent.VK_W:
					keys[5] = true;
				break;
				
				case KeyEvent.VK_E:
					keys[6] = true;
				break;
				
				case KeyEvent.VK_R:
					keys[0xD] = true;
				break;
				
				case KeyEvent.VK_A:
					keys[7] = true;
				break;
				
				case KeyEvent.VK_S:
					keys[8] = true;
				break;
				
				case KeyEvent.VK_D:
					keys[9] = true;
				break;
				
				case KeyEvent.VK_F:
					keys[0xE] = true;
				break;
				
				case KeyEvent.VK_Y:
					keys[0xA] = true;
				break;
				
				case KeyEvent.VK_X:
					keys[0xB] = true;
				break;
				
				case KeyEvent.VK_C:
					keys[0] = true;
				break;
				
				case KeyEvent.VK_V:
					keys[0xF] = true;
				break;
					
				case KeyEvent.VK_ESCAPE:

					gui.switchFullscreen();
					break;

				default:
					break;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_1:
					keys[1] = false;
				break;
				
				case KeyEvent.VK_2:
					keys[2] = false;
				break;
				
				case KeyEvent.VK_3:
					keys[3] = false;
				break;
				
				case KeyEvent.VK_4:
					keys[0xC] = false;
				break;
				
				case KeyEvent.VK_Q:
					keys[4] = false;
				break;
				
				case KeyEvent.VK_W:
					keys[5] = false;
				break;
				
				case KeyEvent.VK_E:
					keys[6] = false;
				break;
				
				case KeyEvent.VK_R:
					keys[0xD] = false;
				break;
				
				case KeyEvent.VK_A:
					keys[7] = false;
				break;
				
				case KeyEvent.VK_S:
					keys[8] = false;
				break;
				
				case KeyEvent.VK_D:
					keys[9] = false;
				break;
				
				case KeyEvent.VK_F:
					keys[0xE] = false;
				break;
				
				case KeyEvent.VK_Y:
					keys[0xA] = false;
				break;
				
				case KeyEvent.VK_X:
					keys[0xB] = false;
				break;
				
				case KeyEvent.VK_C:
					keys[0] = false;
				break;
				
				case KeyEvent.VK_V:
					keys[0xF] = false;
				break;

				default:
					break;
				}

			}

			@Override
			public void keyTyped(KeyEvent e) {}
		};
		return pressed_key;
	}

	/**
	 * Checks whether the specified key is currently pressed
	 */
	public boolean checkKey(int i) {
		if (keys[i])
			return true;
		else
			return false;
	}

	/**
	 * Waits until the specified key is pressed
	 */
	public void waitforKey(int i) {		
		//controller.pauseGame();
		//TODO: This will most likely cause the entire Application to freeze because of the infinite loop
		//		This should be tested against a game which uses this functionality.
		//		IF the application freezes, relocate the infinite loop into a Thread (have a look into the ControllerRunningThread-Class)
		for (int j = 0; j < 16; j++)
			keys[j]=false;
		while (!keys[i])
			System.out.println("waiting for key");

	}

	/**
	 * Waits until key is pressed and return the value of the key
	 */
	public int waitforKey() {
		//controller.pauseGame();
		int check = -1;
		//TODO: This will most likely cause the entire Application to freeze because of the infinite loop
		//		This should be tested against a game which uses this functionality.
		//		IF the application freezes, relocate the infinite loop into a Thread (have a look into the ControllerRunningThread-Class)
		for (int i = 0; i < 16; i++)
			keys[i]=false;
				
		while (check == -1)
			for (int i = 0; i < 16; i++)
				if (keys[i])
					check = i;

		return check;
	}
}
