package com.easypc.chip8;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/**
 * An Easy Wrapper for the GUI Output / Backend to Display Stuff for the Chip-8
 * System
 * 
 * @author crazysaem
 * 
 */
public class MediaOutput {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/

	// Flag which determines if a Beep-Sound is output
	private boolean isBeeping;
	private PlayBeep playx = new PlayBeep("src/resources/sound/beep-kurz.wav");
//	private Thread	playThread = new Thread(playx);

	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	// Internal, Intermediate representation of the Display
	// Will get read by the GameCanvas which will display this as the Black and
	// White Video Output
	public byte[][] display = new byte[64][32]; // TODO: we are facing a
												// ArrayOutOBounds Exception
												// when running most of the
												// programs

	/**
	 * Displays a n-long Sprite which is read from the virtual memory via the
	 * I-Pointer and XORed to the Screen This Information is entirely supplied
	 * by the opCode
	 * "If this causes any pixels to be erased, VF is set to 1, otherwise it is set to 0"
	 * 
	 * @param x
	 *            The x Coordinate for the Sprite
	 * @param y
	 *            The y Coordinate for the Sprite
	 * @param n
	 *            The Read Count for the Pointer
	 */
	public boolean displaySprite(int x, int y, Integer... data) {
		// TODO: I have the feeling this function writes bullshit into
		// display[][]
		boolean ret = false;
		byte change;
		for (int j = 0; j < data.length; j++) {
			for (int i = 0; i < 8; i++) {
				change = display[(x + i) % 64][(y + j) % 32];
				int temp = (data[j] >> (8 - i - 1));
				byte bit = (byte) (temp & 1);
				display[(x + i) % 64][(y + j) % 32] = (byte) (display[(x + i) % 64][(y + j) % 32] ^ bit);
				if ((change != display[(x + i) % 64][(y + j) % 32])
						&& (display[(x + i) % 64][(y + j) % 32] == 0)) {
					ret = true;
				}
			}
		}

		return ret;
	}

	/**
	 * Clears the GameCanvas and shows only Black
	 */
	public void clearScreen() {
		// reset the display array to 0s everywhere
		for (int i = 0; i < display.length; i++) {
			for (int j = 0; j < display[i].length; j++) {
				display[i][j] = 0;
			}
		}
	}

	/**
	 * Starts a Beep Sound
	 */
	public void startBeep(int length) {
		playx.playBeep();
		// try {
		// Synthesizer synth = MidiSystem.getSynthesizer();
		// synth.open();
		//
		// final MidiChannel[] mc = synth.getChannels();
		// Instrument[] instr = synth.getAvailableInstruments();
		//
		// System.out.println(instr[38].getName());
		//
		// // instrument loading is irrelevant, it is
		// // the program change that matters..
		// mc[4].programChange(38);
		// mc[4].noteOn(95, 300);
		// mc[1].programChange(31);
		// mc[4].noteOn(55, 300);
		//
		// try {
		// Thread.sleep(length);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// mc[4].noteOff(100);
		// mc[1].noteOff(55);
		//
		// } catch (MidiUnavailableException e) {
		// e.printStackTrace();
		// }

		isBeeping = false;
	}

	/**
	 * Stops the Beep Sound
	 */
	public void stopBeep() {
		isBeeping = false;

	}
}
