package com.easypc.chip8;

import java.util.ArrayList;
import java.util.Random;

import com.easypc.analysis.CPUAnalysisC;
import com.easypc.backend.Input;
import com.easypc.gui.Gui;

/**
 * The virtual CPU of the Chip-8 System
 * 
 * @author crazysaem
 * 
 */
public class CPU {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/

	// The Registers V0-VF for the CPU:
	private int[] V = new int[16];
	// A Pointer which can be set to an arbitrary address in the virtual RAM by
	// the Chip 8 program
	private int I = 0;
	// A timer, which counts to zero (if it is non-zero) at a rate of 60Hz
	private int delay;
	// The same as the delay timer, and will additionally output a beep-sound as
	// long as the timer is non-zero
	private int sound;
	// Internal Registers:
	// Program Counter, points to the position in the RAM which should be
	// executed next
	private int PC = 0x200; // 512d - The ROM will be loaded into the RAM with
							// the starting address 0x200h/500d

	// The Stack will be used to save the PC when a function was called. The PC
	// will be restored after a RET statement from the Chip 8 Program
	private ArrayList<Integer> PCstack = new ArrayList<Integer>();

	// The MediaOutput Object
	private MediaOutput media;

	private Gui gui;

	// The InputLWJGL Object
	private Input input;

	// The RAM Object
	private RAM ram;

	private Random random;
	
	private CPUAnalysisC cpuAnalysisC;

	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	/**
	 * Initializes the CPU
	 * 
	 * @param media
	 *            The MediaOutput Object
	 * @param input
	 *            The InputLWJGL Object
	 * @param ram
	 *            The RAM Object
	 */
	public CPU(MediaOutput media, Input input, RAM ram) {
		for (int i = 0; i < 16; i++)
			V[i] = 0;

		this.media = media;
		this.input = input;
		this.ram = ram;

		random = new Random(System.currentTimeMillis());
	}

	public void defineGUI(Gui gui) {
		this.gui = gui;
	}
	
	public void defineCPUAnalysisC(CPUAnalysisC cpuAnalysisC)
	{
		this.cpuAnalysisC = cpuAnalysisC;
	}

	/**
	 * Executes a Chip-8 OpCode
	 * 
	 * @param c0
	 *            The 1st 4 Bit of the opCode (Has to be between 0 and 15)
	 * @param c1
	 *            The 2nd 4 Bit of the opCode (Has to be between 0 and 15)
	 * @param c2
	 *            The 3rd 4 Bit of the opCode (Has to be between 0 and 15)
	 * @param c3
	 *            The 4th 4 Bit of the opCode (Has to be between 0 and 15)
	 */
	public void executeOpCode(int c0, int c1, int c2, int c3, boolean isTest) {
		if (c0 < 0 || c0 > 15 || c1 < 0 || c1 > 15 || c2 < 0 || c2 > 15
				|| c3 < 0 || c3 > 15) {
			System.err.println("The Parameters have to be between 0 and 15");
			return;
		}

		PC += 2;

		// The CPU Speed is unknown, but the timer decreasing speed is fixed at
		// 60hz
		// The best idea is probably to pick out the best Chip-8 Games and set a
		// speed specific for each game to run it at an optimal speed
		switch (c0) {
		case 0:
			if ((c1 == 0) && (c2 == 0xE) && (c3 == 0)) // 00E0 - CLS
			{
				media.clearScreen();
			}

			if ((c1 == 0) && (c2 == 0xE) && (c3 == 0xE)) // 00EE - RTN
			{
				// Return from func
				PC = PCstack.get(PCstack.size() - 1);
				PCstack.remove(PCstack.size() - 1);
			}
			break;

		case 1: // 1nnn - JP addr
			PC = get12BitValue(c1, c2, c3);
			break;

		case 2: // 2nnn - CALL addr
			PCstack.add(PC);
			PC = get12BitValue(c1, c2, c3);
			break;
		case 3: // 3xkk - SE Vx, byte
			if (V[c1] == get8BitValue(c2, c3))
				PC += 2;
			break;
		case 4: // 4xkk - SNE Vx, byte
			if (V[c1] != get8BitValue(c2, c3))
				PC += 2;
			break;
		case 5: // 5xy0 - SE Vx, Vy
			if (V[c1] == V[c2])
				PC += 2;
			break;
		case 6: // 6xkk - LD Vx, byte
			V[c1] = get8BitValue(c2, c3);
			break;
		case 7: // 7xkk - ADD Vx, byte
			V[c1] = V[c1] + get8BitValue(c2, c3);
			if (V[c1] > 255) {
				V[0xF] = 1;
				V[c1] = V[c1] & 0xFF;
			} else
				V[0xF] = 0;
			break;
		case 8:
			switch (c3) {
			case 0: // 8xy0 - LD Vx, Vy
				V[c1] = V[c2];
				break;
			case 1: // 8xy1 - OR Vx, Vy
				V[c1] = V[c1] | V[c2];
				break;
			case 2: // 8xy2 - AND Vx, Vy
				V[c1] = V[c1] & V[c2];
				break;
			case 3: // 8xy3 - XOR Vx, Vy
				V[c1] = V[c1] ^ V[c2];
				break;
			case 4: // 8xy4 - ADD Vx, Vy
				V[c1] = V[c1] + V[c2];
				if (V[c1] > 255) {
					V[0xF] = 1;
					V[c1] = V[c1] & 0xFF;
				} else
					V[0xF] = 0;
				break;
			case 5: // 8xy5 - SUB Vx, Vy
				V[c1] = (V[c1] - V[c2]);
				if ((V[c1] & 256) == 256)
					V[0xF] = 0;
				else
					V[0xF] = 1;
				V[c1] = V[c1] & 0xFF;
				break;
			case 6: // 8xy6 - SHR Vx {, Vy}
				V[0xF] = (V[c1] & 1);
				V[c1] = (V[c1] >> 1) & 0xFF;
				break;
			case 7: // 8xy7 - SUBN Vx, Vy
				V[c1] = (V[c2] - V[c1]);
				if ((V[c1] & 256) == 256)
					V[0xF] = 0;

				else
					V[0xF] = 1;
				V[c1] = V[c1] & 0xFF;
				break;
			case 0xE: // 8xyE - SHL Vx {, Vy}
				V[0xF] = (V[c1] & 128);
				V[c1] = (V[c1] << 1) & 0xFF;
				break;
			}
			break;
		case 9: // 9xy0 - SNE Vx, Vy
			if (V[c1] != V[c2])
				PC += 2;
			break;
		case 0xA: // Annn - LD I, addr
			I = get12BitValue(c1, c2, c3);
			break;
		case 0xB: // Bnnn - JP V0, addr
			PC = get12BitValue(c1, c2, c3) + V[0];
			break;
		case 0xC: // Cxkk - RND Vx, byte
			int r = random.nextInt(256);
			V[c1] = get8BitValue(c2, c3) & r;
			break;
		case 0xD: // Dxyn - DRW Vx, Vy, nibble
			Integer[] t = makeArray(ram.read(I, c3));
			if (media.displaySprite(V[c1], V[c2], t))
				V[0xF] = 1;
			else
				V[0xF] = 0;
			break;
		case 0xE:
			switch (c2) {
			case 9: // Ex9E - SKP Vx
				if (input.checkKey(V[c1]))
					PC += 2;
				break;
			case 0xA: // ExA1 - SKNP Vx
				if (!input.checkKey(V[c1]))
					PC += 2;
				break;
			}
			break;
		case 0xF:
			switch (get8BitValue(c2, c3)) {
			case 0x07: // Fx07 - LD Vx, DT
				V[c1] = delay;
				break;
			case 0x0A: // Fx0A - LD Vx, K
				V[c1] = input.waitforKey();
				break;
			case 0x15: // Fx15 - LD DT, Vx
				delay = V[c1];
				break;
			case 0x18: // Fx18 - LD ST, Vx
				sound = V[c1];
				media.startBeep(sound);
				break;
			case 0x1E: // Fx1E - ADD I, Vx
				I = I + V[c1];
				break;
			case 0x29: // Fx29 - LD F, Vx
				I = V[c1] * 5;
				break;
			case 0x33: // Fx33 - LD B, Vx
				ArrayList<Integer> temp = getBCDValue(V[c1]);
				ram.write(I, temp.get(0));
				ram.write(I + 1, temp.get(1));
				ram.write(I + 2, temp.get(2));
				break;
			case 0x55: // Fx55 - LD [I], Vx
				for (int i = 0; i <= c1; i++) {
					ram.write(I + i, V[i]);
				}
				break;
			case 0x65: // Fx65 - LD Vx, [I]
				ArrayList<Integer> templist = ram.read(I, c1 + 1);
				for (int i = 0; i < templist.size(); i++) {
					V[i] = templist.get(i);
				}
				break;
			}
			break;
		default:
			System.err.println("ERROR: Unregocnised Command: " + c0 + c1 + c2
					+ c3);
			break;

		}
		if (delay > 0)
			delay--;
		if (sound > 0) {
			sound--;
		} else {
			media.stopBeep();
		}

		for (int i = 0; i < RAM.MAX_LENGTH; i++) {
			ram.memory_count_read[i] *= .999;
			ram.memory_count_write[i] *= .9999;
		}
		if(!isTest)
		{
			for (int i = 0; i < 16; i++)
				gui.UpdateRegisterView(i, getRegister(i));
			cpuAnalysisC.nextInstruction();
		}
	}

	/**
	 * Sets a specific Register to a specific Value
	 * 
	 * @param regNumber
	 *            The Number of the Register
	 * @param value
	 *            The Value of the Register
	 */
	public void setRegister(int regNumber, int value) {
		if (regNumber < 16)
			V[regNumber] = value;
		else if (regNumber == 16)
			I = value;
		else if (regNumber == 17)
			delay = value;
		else if (regNumber == 18)
			sound = value;
		else if (regNumber == 19)
			PC = value;
		else
			System.err
					.println("ERROR: Can not set Register (Undefined Register Number: "
							+ regNumber + ")");

	}

	/**
	 * Returns the Value of the specified Register
	 * 
	 * @param regNumber
	 *            The Number of the Register
	 * @return The Value of the Register
	 */
	public int getRegister(int regNumber) {
		if (regNumber < 16)
			return V[regNumber];
		else if (regNumber == 16)
			return I;
		else if (regNumber == 17)
			return delay;
		else if (regNumber == 18)
			return sound;
		else if (regNumber == 19)
			return PC;
		else {
			System.err
					.println("ERROR: Can not get Register (Undefined Register Number: "
							+ regNumber + ")");
			return -1;
		}

	}
	
	public ArrayList<Integer> getPCStack ()
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for(int i=0; i<PCstack.size(); i++)
		{
			int value = PCstack.get(i);
			result.add(value);
		}
		
		return result;
	}

	/*----------------------------------------------------
	 * Private Method Section. Shows the Methods used internally.
	 *--------------------------------------------------*/

	/**
	 * Converts 2x 4Bit Numbers into an 8Bit Number
	 * 
	 * @param i0
	 *            The 1st 4 Bit
	 * @param i1
	 *            The 2nd 4 Bit
	 * @return the constructed 8 Bit Number
	 */
	public static int get8BitValue(int i0, int i1) {
		i0 = i0 << 4;
		return (i0 | i1);
	}

	/**
	 * Converts 3x 4Bit Numbers into an 12Bit Number (used for addressing)
	 * 
	 * @param i0
	 *            The 1st 4 Bit
	 * @param i1
	 *            The 2nd 4 Bit
	 * @param i2
	 *            The 3rd 4 Bit
	 * @return the constructed 12 Bit Number
	 */
	private int get12BitValue(int i0, int i1, int i2) {
		i0 = i0 << 8;
		i1 = i1 << 4;
		return (i0 | i1 | i2);
	}

	/**
	 * Converts a 3 digit number into its BCD form
	 * 
	 * @param i
	 *            the decimal value
	 * @return (temp) BCD Value in ArrayList
	 */
	private ArrayList<Integer> getBCDValue(int i) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(i / 100);
		temp.add(i / 10 - temp.get(0) * 10);
		temp.add(i - temp.get(0) * 100 - temp.get(1) * 10);
		return temp;
	}

	/**
	 * Converts Integer ArrayList to Array
	 * 
	 * @param list
	 *            = Array List
	 * @return (temp) Integer Array
	 */
	private Integer[] makeArray(ArrayList<Integer> list) {
		Integer[] temp = new Integer[list.size()];
		for (int i = 0; i < list.size(); i++)
			temp[i] = list.get(i);
		return temp;
	}

	/**
	 * Reset all CPU Register
	 */
	public void reset() {
		for (int i = 0; i < 16; i++)
			V[i] = 0;
		I = 0;
		delay = 0;
		sound = 0;
		PC = 0x200;
	}
}
