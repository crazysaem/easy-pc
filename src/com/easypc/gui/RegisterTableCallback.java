package com.easypc.gui;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.easypc.chip8.CPU;

public class RegisterTableCallback implements TableModelListener 
{
	private JTable table;
	private boolean ignoreNext = false;
	private CPU cpu;
	
	public RegisterTableCallback(JTable table, CPU cpu)
	{
		this.table = table;
		this.cpu = cpu;
	}

	@Override
	public void tableChanged(TableModelEvent arg0) 
	{		
		if(ignoreNext)
		{
			ignoreNext = false;
			return;
		}
		//System.out.println(""+arg0.getColumn()+" "+arg0.getFirstRow()+" "+arg0.getLastRow()+" "+arg0.getType());
		int col = arg0.getColumn();
		int row = arg0.getFirstRow();
		int regvalue = 0;
		try
		{
			Object val = table.getValueAt(row, col);
			if(val instanceof Integer)
			{
				regvalue = (Integer)val;
			}
			else
			{
				regvalue = Integer.parseInt((String)val);
			}
		} 
		catch (NumberFormatException e) 
		{
			this.ignoreNext();
			if(col == 0)
			{
				table.setValueAt("V" + (row*2), row, col);
				return;
			}
			if(col == 2)
			{
				table.setValueAt("V" + (row*2 + 1), row, col);
				return;
			}
			table.setValueAt("", row, col);
			return;
		}
		
		int regNumber = 0;
		if(col == 1)
			regNumber = row*2;
		if(col == 3)
			regNumber = row*2 + 1;
		cpu.setRegister(regNumber, regvalue);
	}
	
	public void ignoreNext()
	{
		ignoreNext = true;
	}
}
