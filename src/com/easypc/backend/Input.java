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

		keylistener = new KeyListener() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_1:

					keys[0] = true;
					break;
				case KeyEvent.VK_2:

					keys[1] = true;
					break;
				case KeyEvent.VK_3:

					keys[2] = true;
					break;
				case KeyEvent.VK_4:

					keys[3] = true;
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

					keys[7] = true;
					break;
				case KeyEvent.VK_A:

					keys[8] = true;
					break;
				case KeyEvent.VK_S:

					keys[9] = true;
					break;
				case KeyEvent.VK_D:

					keys[10] = true;
					break;
				case KeyEvent.VK_F:

					keys[11] = true;
					break;
				case KeyEvent.VK_Y:

					keys[12] = true;
					break;
				case KeyEvent.VK_X:

					keys[13] = true;
					break;
				case KeyEvent.VK_C:

					keys[14] = true;
					break;
				case KeyEvent.VK_V:

					keys[15] = true;
					break;
				case KeyEvent.VK_ESCAPE:

					gui.reset_fullscreen();
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
					// System.out.println("released "+ e.getKeyChar() );
					keys[0] = false;
					break;
				case KeyEvent.VK_2:
					// System.out.println("released "+ e.getKeyChar() );
					keys[1] = false;
					break;
				case KeyEvent.VK_3:
					// System.out.println("released "+ e.getKeyChar() );
					keys[2] = false;
					break;
				case KeyEvent.VK_4:
					// System.out.println("released "+ e.getKeyChar() );
					keys[3] = false;
					break;
				case KeyEvent.VK_Q:
					// System.out.println("released "+ e.getKeyChar() );
					keys[4] = false;
					break;
				case KeyEvent.VK_W:
					// System.out.println("released "+ e.getKeyChar() );
					keys[5] = false;
					break;
				case KeyEvent.VK_E:
					// System.out.println("released "+ e.getKeyChar() );
					keys[6] = false;
					break;
				case KeyEvent.VK_R:
					// System.out.println("released "+ e.getKeyChar() );
					keys[7] = false;
					break;
				case KeyEvent.VK_A:
					// System.out.println("released "+ e.getKeyChar() );
					keys[8] = false;
					break;
				case KeyEvent.VK_S:
					// System.out.println("released "+ e.getKeyChar() );
					keys[9] = false;
					break;
				case KeyEvent.VK_D:
					// System.out.println("released "+ e.getKeyChar() );
					keys[10] = false;
					break;
				case KeyEvent.VK_F:
					// System.out.println("released "+ e.getKeyChar() );
					keys[11] = false;
					break;
				case KeyEvent.VK_Y:
					// System.out.println("released "+ e.getKeyChar() );
					keys[12] = false;
					break;
				case KeyEvent.VK_X:
					// System.out.println("released "+ e.getKeyChar() );
					keys[13] = false;
					break;
				case KeyEvent.VK_C:
					// System.out.println("released "+ e.getKeyChar() );
					keys[14] = false;
					break;
				case KeyEvent.VK_V:
					// System.out.println("released "+ e.getKeyChar() );
					keys[15] = false;
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
		controller.pauseGame();
		while (!keys[i])
			System.out.println("waiting for key");
		controller.resumeGame();
	}

	/**
	 * Waits until key is pressed and return the value of the key
	 */
	public int waitforKey() {
		controller.pauseGame();
		int check = -1;
		while (check == -1)
			for (int i = 0; i < 16; i++)
				if (keys[i])
					check = i;
		controller.resumeGame();
		return check;
	}
}
