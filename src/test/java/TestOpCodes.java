package test.java;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.easypc.backend.Input;
import com.easypc.chip8.CPU;
import com.easypc.chip8.MediaOutput;
import com.easypc.chip8.RAM;
import com.easypc.gui.Gui;

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
		Input input = new Input();
		ram = new RAM();

		cpu = new CPU(media,input,ram);
		//cpu.defineGUI(gui);		
		/*
		Controller controller = new Controller(cpu,ram);
		
		CPUAnalysisC cpuAnalysisC = null;
		RAMAnalysisC ramAnalysisC = null;
		GameCanvas gamecanvas = null;

			cpuAnalysisC = new CPUAnalysisC();
			ramAnalysisC = new RAMAnalysisC(ram, cpu);
			gamecanvas = new GameCanvas(media);	
		
		Gui gui = new Gui(controller, cpuAnalysisC, ramAnalysisC, gamecanvas, input);
		input.Init(controller,gui);		
		cpu.defineGUI(gui);*/
		
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
    @After
    public void tearDown() {
    	cpu.reset();
		media.clearScreen();
		ram.reset();
        System.out.println("@After - tearDown");
    }
    
    @Test
    public void testOpCode_1nnn() 
    {
    	//The interpreter sets the program counter to nnn.    	
    	cpu.executeOpCode(1, 1, 2, 3, true);     	
    	int check = 1<<8 | 2<<4 | 3;    	
    	assertEquals(check, cpu.getRegister(19));
    	
        System.out.println("@Test - testOpCode_1nnn");
    }
    
    @Test
    public void testOpCode_2nnn() 
    {
    	//The interpreter increments the stack pointer, 
    	//then puts the current PC on the top of the stack. 
    	//The PC is then set to nnn.
    	cpu.executeOpCode(2, 1, 2, 3, true); 
		//TODO: can/must I check this: cpu.PCstack.get(cpu.PCstack.size()) ???
    	int check = 1<<8 | 2<<4 | 3;    	
    	assertEquals(check, cpu.getRegister(19));
    	
        System.out.println("@Test - testOpCode_2nnn");
    }
    @Test
    public void testOpCode_3xkk()
    {
    	//Skip next instruction if Vx = kk.
    	int PC =cpu.getRegister(19);
    	cpu.executeOpCode(3, 2, 4, 2, true);
    	int check = 4<<4 | 2;
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
    	cpu.executeOpCode(4, 2, 4, 2, true);
    	int check = 4<<4 | 2;
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
    	cpu.executeOpCode(5, 2, 4, 0, true);
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
    	cpu.executeOpCode(6, 2, 4, 2, true);
    	int check = 4<<4 | 2;
    	assertEquals(check, cpu.getRegister(2));
    	
    	System.out.println("@Test - testOpCode_6xkk");
    }
    
    @Test
    public void testOpCode_7xkk()
    {
        //Set Vx = Vx + kk.
    	int tempVX = cpu.getRegister(2);
    	cpu.executeOpCode(7, 2, 4, 2, true);    	
    	int check = 4<<4 | 2;
    	check=check+tempVX;
    	assertEquals(check, cpu.getRegister(2));
    	
    	System.out.println("@Test - testOpCode_7xkk");
    }
    
    @Test
    public void testOpCode_8xy0()
    {
        //Set Vx = Vy.
    	cpu.executeOpCode(8, 2, 4, 0, true);
    	assertEquals(cpu.getRegister(4), cpu.getRegister(2));
    	
    	System.out.println("@Test - testOpCode_8xy0");
    }
    
    @Test
    public void testOpCode_8xy1()
    {
        //Set Vx = Vx OR Vy.
    	cpu.executeOpCode(8, 2, 4, 1, true);
    	int check=cpu.getRegister(4) | cpu.getRegister(2);
    	assertEquals(check, cpu.getRegister(2));
    	
    	System.out.println("@Test - testOpCode_8xy1");
    }
    
    @Test
    public void testOpCode_8xy2()
    {
        //Set Vx = Vx AND Vy.
    	cpu.executeOpCode(8, 2, 4, 2, true);
    	int check=cpu.getRegister(4) & cpu.getRegister(2);
    	assertEquals(check, cpu.getRegister(2));
    	
    	System.out.println("@Test - testOpCode_8xy2");
    }
    
    @Test
    public void testOpCode_8xy3()
    {
        //Set Vx = Vx XOR Vy.
    	cpu.executeOpCode(8, 2, 4, 3, true);
    	int check=cpu.getRegister(4) ^ cpu.getRegister(2);
    	assertEquals(check, cpu.getRegister(2));
    	
    	System.out.println("@Test - testOpCode_8xy3");
    }
    
    @Test
    public void testOpCode_8xy4()
    {
        //Set Vx = Vx + Vy, set VF = carry.
    	cpu.executeOpCode(8, 2, 4, 4, true);
    	int check=cpu.getRegister(4) + cpu.getRegister(2);
    	if(check>255){
    		assertEquals(1, cpu.getRegister(15));
    		assertEquals(255, cpu.getRegister(2));
    		check=255;
    	}
    	else 
    		assertEquals(0, cpu.getRegister(15));
		assertEquals(check, cpu.getRegister(2));

    	System.out.println("@Test - testOpCode_8xy4");
    }
    
    @Test
    public void testOpCode_8xy5()
    {
        //Set Vx = Vx - Vy, set VF = NOT borrow.
    	cpu.executeOpCode(8, 2, 4, 5, true);
    	//TODO: This test is incomplete
    	int check=cpu.getRegister(2) - cpu.getRegister(4);
		assertEquals(check, cpu.getRegister(2));

    	System.out.println("@Test - testOpCode_8xy5");
    }    
    
    @Test
    public void testOpCode_8xy6()
    {
        //Set Vx = Vx SHR 1.
    	cpu.executeOpCode(8, 2, 4, 6, true);
    	//TODO: This test is incomplete
    	int check=cpu.getRegister(2)>>1;
		assertEquals(check, cpu.getRegister(2));

    	System.out.println("@Test - testOpCode_8xy6");
    }
    
    @Test
    public void testOpCode_8xy7()
    {
        //Set Vx = Vy - Vx, set VF = NOT borrow.
    	cpu.executeOpCode(8, 2, 4, 7, true);
    	//TODO: This test is incomplete
    	int check=cpu.getRegister(4) - cpu.getRegister(2);
		assertEquals(check, cpu.getRegister(2));

    	System.out.println("@Test - testOpCode_8xy7");
    }  
    
    @Test
    public void testOpCode_8xyE()
    {
        //Set Vx = Vx SHL 1.
    	cpu.executeOpCode(8, 2, 4, 0xE, true);
    	if((cpu.getRegister(2)&128)==128)
    		assertEquals(1, cpu.getRegister(15));
    	else
    		assertEquals(0, cpu.getRegister(15));
    	int check=cpu.getRegister(2)<<1;
		assertEquals(check, cpu.getRegister(2));
	
    	System.out.println("@Test - testOpCode_8xyE");
    }
    
    @Test
    public void testOpCode_9xy0()
    {
        //Skip next instruction if Vx != Vy.
    	int PC =cpu.getRegister(19);
    	cpu.executeOpCode(9, 2, 4, 0, true);
    	if(cpu.getRegister(2)!=cpu.getRegister(4))
    		assertEquals(PC+4, cpu.getRegister(19));
    	else
    		assertEquals(PC+2, cpu.getRegister(19));
    	
    	System.out.println("@Test - testOpCode_9xy0");
    }
    
    @Test
    public void testOpCode_Annn()
    {
        //Set I = nnn.
    	cpu.executeOpCode(0xA, 1, 2, 3, true);     	
    	int check = 1<<8 | 2<<4 | 3;    	
    	assertEquals(check, cpu.getRegister(16));
    	
    	System.out.println("@Test - testOpCode_Annn");
    }
    
    @Test
    public void testOpCode_Bnnn()
    {
        //Jump to location nnn + V0.
    	cpu.executeOpCode(0xB, 1, 2, 3, true);     	
    	int check = (1<<8 | 2<<4 | 3)+cpu.getRegister(0);    	
    	assertEquals(check, cpu.getRegister(19));
    	
    	System.out.println("@Test - testOpCode_Bnnn");
    }
    
    @Test
    public void testOpCode_Cxkk()
    {
        //Set Vx = random byte AND kk.
    	cpu.executeOpCode(0xC, 2, 15, 15, true);     	
    	@SuppressWarnings("unused")
		int check = (4<<4 & 2)& (int)(Math.random()*255);  
    	//TODO: Do not think that it will be the same random number...
    	//Perfectly right, therefore we cant really check this for correctness
    	//assertEquals(check, cpu.getRegister(2));

    	System.out.println("@Test - testOpCode_Cxkk");
    }
}
