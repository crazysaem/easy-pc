package test.java;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.easypc.backend.InputLWJGL;
import com.easypc.chip8.CPU;
import com.easypc.chip8.MediaOutput;
import com.easypc.chip8.RAM;

/**
 * This test class will validate all the opCodes of our CPU against the specification 
 * @author crazysaem
 *
 */
public class TestOpCodes {
	
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/
	
	private static CPU cpu;
	private static RAM ram;
	private static MediaOutput media;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/
	
	/**
	 * Gets executed once for this entire Test class
	 */
	@BeforeClass
    public static void oneTimeSetUp() {
		media = new MediaOutput();
		InputLWJGL input = new InputLWJGL();
		ram = new RAM();

		cpu = new CPU(media,input,ram);
		
    	System.out.println("@BeforeClass - Setting up the CPU and all depencies for it");
    }
 
	/**
	 * Gets executed once for this entire Test class
	 * Will reset every component to make it ready for the next test
	 */
	@AfterClass
    public static void oneTimeTearDown() {		
		cpu.reset();
		media.clearScreen();
		ram.reset();
    	System.out.println("@AfterClass - Resetting the CPU, RAM and MediaOutput");
    }
 
	/**
	 * Gets executed for every test Method
	 */
    /*@Before
    public void setUp() {
        System.out.println("@Before - setUp");
    }*/
 
	/**
	 * Gets executed for every test Method
	 */
	/*
    @After
    public void tearDown() {
        System.out.println("@After - tearDown");
    }*/
 
    @Test
    public void testOpCode_1nnn() 
    {
    	//The interpreter sets the program counter to nnn.    	
    	cpu.executeOpCode(1, 1, 2, 3);     	
    	int check = 1<<8 & 2<<4 & 3;    	
    	assertEquals(check, cpu.getRegister(19));
    	
        System.out.println("@Test - testOpCode_1nnn");
    }
    
    @Test
    public void testOpCode_2nnn() 
    {
    	//The interpreter increments the stack pointer, 
    	//then puts the current PC on the top of the stack. 
    	//The PC is then set to nnn.    	
    	cpu.executeOpCode(2, 1, 2, 3); 
		//TODO: can/must I check this: cpu.PCstack.get(cpu.PCstack.size()) ???
    	int check = 1<<8 & 2<<4 & 3;    	
    	assertEquals(check, cpu.getRegister(19));
    	
        System.out.println("@Test - testOpCode_2nnn");
    }
    @Test
    public void testOpCode_3xkk()
    {
    	//Skip next instruction if Vx = kk.
    	int PC =cpu.getRegister(19);
    	cpu.executeOpCode(3, 2, 4, 2);
    	int check = 4<<4 & 2;
    	if(check==cpu.getRegister(2))
    		assertEquals(PC+4, cpu.getRegister(19));
    	else
    		assertEquals(PC+2, cpu.getRegister(19));
    	
    	System.out.println("@Test - testOpCode_3xkk");
    }
    
    @Test
    public void testOpCode_4xkk()
    {
    	//Skip next instruction if Vx != kk.
    	int PC =cpu.getRegister(19);
    	cpu.executeOpCode(4, 2, 4, 2);
    	int check = 4<<4 & 2;
    	if(check!=cpu.getRegister(2))
    		assertEquals(PC+4, cpu.getRegister(19));
    	else
    		assertEquals(PC+2, cpu.getRegister(19));
    	
    	System.out.println("@Test - testOpCode_4xkk");
    }
    
    @Test
    public void testOpCode_5xy0()
    {
    	//Skip next instruction if Vx = Vy.
    	int PC =cpu.getRegister(19);
    	cpu.executeOpCode(5, 2, 4, 0);
    	if(cpu.getRegister(2)==cpu.getRegister(4))
    		assertEquals(PC+4, cpu.getRegister(19));
    	else
    		assertEquals(PC+2, cpu.getRegister(19));
    	
    	System.out.println("@Test - testOpCode_5xy0");
    }
    
    @Test
    public void testOpCode_6xkk()
    {
    	//Set Vx = kk.
    	cpu.executeOpCode(6, 2, 4, 2);
    	int check = 4<<4 & 2;
    	assertEquals(check, cpu.getRegister(2));
    	
    	System.out.println("@Test - testOpCode_6xkk");
    }
    
    @Test
    public void testOpCode_7xkk()
    {
        //Set Vx = Vx + kk.
    	cpu.executeOpCode(7, 2, 4, 2);
    	int check = 4<<4 & 2;
    	check=check+cpu.getRegister(2);
    	assertEquals(check, cpu.getRegister(2));
    	
    	System.out.println("@Test - testOpCode_6xkk");
    }
    
}
