package com.easypc.controller;

import com.easypc.chip8.CPU;

public class ControllerRunningThread implements Runnable {
	
	private CPU cpu;
	public boolean isRunning;
	
	public ControllerRunningThread(CPU cpu)
	{
		this.cpu = cpu;
	}

	@Override
	public void run() {
		while(isRunning){
			//TODO: fetch the opCode from the RAM not from the data byte pointer (!)
			//data=ram.read(cpu.getRegister(19), 2);
			//cpu.executeOpCode((data.get(0)&0xF0)>>4, (data.get(0)&0x0F), data.get(1)&0xF0>>4, data.get(1)&0x0F);
			System.out.print("DEBUG -- PC:" + cpu.getRegister(19));
			for(int i=0;i<16;i++)
				System.out.print(", V["+i+"] = "+cpu.getRegister(i));
			System.out.println();
			//wait(10);			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}

}
