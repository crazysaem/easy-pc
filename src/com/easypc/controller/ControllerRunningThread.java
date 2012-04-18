package com.easypc.controller;

import java.util.ArrayList;

import com.easypc.chip8.CPU;
import com.easypc.chip8.RAM;

public class ControllerRunningThread implements Runnable {
	
	private CPU cpu;
	private RAM ram;
	public boolean isRunning;
	
	public ControllerRunningThread(CPU cpu, RAM ram)
	{
		this.cpu = cpu;
		this.ram = ram;
	}

	@Override
	public void run() {
		while(isRunning){
			//TODO: fetch the opCode from the RAM not from the data byte pointer (!)
			//data=ram.read(cpu.getRegister(19), 2);
			//cpu.executeOpCode((data.get(0)&0xF0)>>4, (data.get(0)&0x0F), data.get(1)&0xF0>>4, data.get(1)&0x0F);
			int PC = cpu.getRegister(19);
			ArrayList<Integer> opCode = ram.read(PC, 2);
			int temp = opCode.get(1);
			temp = temp &0xF0;
			temp = temp>>4;
			cpu.executeOpCode((opCode.get(0)&0xF0)>>4, (opCode.get(0)&0x0F), (opCode.get(1)&0xF0)>>4, opCode.get(1)&0x0F);
			System.out.print("DEBUG -- PC:" + cpu.getRegister(19));
			System.out.print(", OP: ");
			System.out.print(Integer.toHexString(opCode.get(0))+"|"+Integer.toHexString(opCode.get(1)));
			for(int i=0;i<16;i++)
				System.out.print(", V["+i+"] = "+cpu.getRegister(i));
			System.out.println();
			//wait(10);	
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}

}
