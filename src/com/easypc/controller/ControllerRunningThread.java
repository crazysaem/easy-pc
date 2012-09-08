package com.easypc.controller;

import java.util.ArrayList;

import com.easypc.chip8.CPU;
import com.easypc.chip8.RAM;

/**
 * A thread which is used to construct the 'infinite' execution loop which would otherwise easily interfeare with e.g. the GUI
 * @author crazysaem
 *
 */
public class ControllerRunningThread implements Runnable {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	//The virtual CHIP-8 CPU
	private CPU cpu;
	//The virtual CHIP-8 RAM
	private RAM ram;
	//A flag which determines whether the execution loop is running or stopped
	private boolean isRunning;
	
	private int sleepTime = 2;

	/**
	 * Generates a new ControllerRunningThread
	 * @param cpu the virtual CHIP-8 CPU
	 * @param ram the virtual CHIP-8 RAM
	 */
	public ControllerRunningThread(CPU cpu, RAM ram) {
		this.cpu = cpu;
		this.ram = ram;
	}

	/**
	 * Will start the 'infinite' execution loop
	 * Can be stopped by calling setRunning(false)
	 */
	@Override
	public void run() 
	{
		while (isRunning) 
		{
			int PC = cpu.getRegister(19);
			ArrayList<Integer> opCode = ram.read(PC, 2);
			cpu.executeOpCode((opCode.get(0) & 0xF0) >> 4, (opCode.get(0) & 0x0F), (opCode.get(1) & 0xF0) >> 4, opCode.get(1) & 0x0F, false);
			if (_main.DEBUG) {
				System.out.print("DEBUG -- PC:" + cpu.getRegister(19));
				System.out.print(", OP: ");
				System.out.print(Integer.toHexString(opCode.get(0)) + "|"
						+ Integer.toHexString(opCode.get(1)));
				for (int i = 0; i < 16; i++)
					System.out.print(", V[" + i + "] = " + cpu.getRegister(i));
				System.out.println();
				// wait(10);
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isRunning = false;
	}
	
	/**
	 * Returns the state of the isRunning Flag
	 * @return determines whether the execution loop is running
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Sets the state of the isRunning Flag
	 * @param isRunning determines whether the execution loop is running
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void setSleepTime(int sleepTime)
	{
		this.sleepTime = sleepTime;
	}	
}
