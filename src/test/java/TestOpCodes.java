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
    public void testOpCode_6xkk()
    {
    	//Set Vx = kk.
    	cpu.executeOpCode(6, 2, 4, 2);
    	int check = 4<<4 & 2;
    	assertEquals(check, cpu.getRegister(2));
    	
    	System.out.println("@Test - testOpCode_6xkk");
    }
}
