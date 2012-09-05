package com.easypc.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.easypc.analysis.CPUAnalysisC;
import com.easypc.analysis.RAMAnalysisC;
import com.easypc.backend.Input;
import com.easypc.chip8.CPU;
import com.easypc.chip8.GameCanvas;
import com.easypc.controller.Controller;
import com.easypc.controller._main;

/**
 * Gui Master - puts the entire Gui together and manages it
 * 
 * @author crazysaem
 * 
 */
public class Gui implements ImageButtonCallBack {
	/*----------------------------------------------------
	 * Attribute Section.
	 *--------------------------------------------------*/

	// The controller will get information about the input from the user via the
	// gui events in this class
	private Controller controller;	

	// the main gui frame
	private GuiFrame guiFrame;

	// The listview containing all Games
	private JList<String> gameList;

	// The gameCanvas is used to connected it to the Gui and enable fullscreen
	private GameCanvas gameCanvas;
	private Input input;

	// reference to the fullscreen frame
	private JFrame full;

	// JList Scrollpane
	private JScrollPane scrollPane;
	private JTable registerTable;
	private RegisterTableCallback registerTableCallback;

	// The Buttons for the GUI
	private ImageButton reset, play, pause, step, fastforward, min, close,
			maxi, hide_right, show_right, hide_top, show_top, hide_left,
			show_left, hide_bottom, show_bottom;

	// Flag which resembles the fullscreen state of the gameCanvas
	private boolean isFullScreen = false;

	// Flag which resembles whether the list or the gameCanvas is shown
	private boolean isgameCanvasShown = false;

	private RAMAnalysisC ramAnalysisC;
	private CPUAnalysisC cpuAnalysisC;
	
	private CPU cpu;

	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	/**
	 * Initializes the entire GUI and takes the controller Object
	 * 
	 * @param controller
	 */
	public Gui(Controller controller, CPUAnalysisC cpuAnalysisC,
			RAMAnalysisC ramAnalysisC, GameCanvas gamecanvas, Input input, CPU cpu) {
		this.controller = controller;
		this.gameCanvas = gamecanvas;
		this.input = input;
		
		this.ramAnalysisC = ramAnalysisC;
		this.cpuAnalysisC = cpuAnalysisC;

		// runnning keylistener for the guiFrame
		this.input.checkKeys();
		
		this.cpu = cpu;

		guiFrame = new GuiFrame();
		guiFrame.getContentPane().setBackground(Color.BLACK);
		guiFrame.setVisible(true);
		guiFrame.addKeyListener(input.keylistener);

		cpuAnalysisC.setBounds(409, 7, 777 - 409, 185 - 7);
		cpuAnalysisC.addMouseListener(guiFrame);
		cpuAnalysisC.addMouseMotionListener(guiFrame);
		cpuAnalysisC.addKeyListener(input.keylistener);
		guiFrame.add(cpuAnalysisC);

		ramAnalysisC.setBounds(5, 241, 373 - 5, 419 - 241);
		ramAnalysisC.addMouseListener(guiFrame);
		ramAnalysisC.addMouseMotionListener(guiFrame);
		ramAnalysisC.addKeyListener(input.keylistener);
		guiFrame.add(ramAnalysisC);

		gameCanvas.addKeyListener(input.keylistener);
		gameCanvas.setBounds(385, 230, 416, 200);
		gameCanvas.addMouseListener(guiFrame);
		gameCanvas.addMouseMotionListener(guiFrame);
		guiFrame.add(gameCanvas);

		initKeyboardView();
		initRegisterView();
		initList();

		ImageIcon img = new ImageIcon("src/resources/logo.png");
		guiFrame.setIconImage(img.getImage());

		reset = new ImageButton(this, "src/resources/keys/reset.png",
				"src/resources/keys/reset_glow.png", 385, 195, 0.4f);
		guiFrame.add(reset.getLabel());

		play = new ImageButton(this, "src/resources/keys/play.png",
				"src/resources/keys/play_glow.png", 425, 195, 0.4f);
		guiFrame.add(play.getLabel());

		pause = new ImageButton(this, "src/resources/keys/pause.png",
				"src/resources/keys/pause_glow.png", 465, 195, 0.4f);
		guiFrame.add(pause.getLabel());

		step = new ImageButton(this, "src/resources/keys/step.png",
				"src/resources/keys/step_glow.png", 505, 195, 0.4f);
		guiFrame.add(step.getLabel());

		fastforward = new ImageButton(this,
				"src/resources/keys/fastforward.png",
				"src/resources/keys/fastforward_glow.png", 545, 195, 0.4f);
		guiFrame.add(fastforward.getLabel());

		maxi = new ImageButton(this, "src/resources/keys/maxi.png",
				"src/resources/keys/maxi_glow.png", /* 690 */585, 195, 0.4f);
		guiFrame.add(maxi.getLabel());

		close = new ImageButton(this, "src/resources/keys/close.png",
				"src/resources/keys/close_glow.png", 770, 190, 0.4f);
		guiFrame.add(close.getLabel());

		min = new ImageButton(this, "src/resources/keys/min.png",
				"src/resources/keys/min_glow.png", 730, 190, 0.4f);
		guiFrame.add(min.getLabel());

		hide_right = new ImageButton(this, "src/resources/keys/min_left.png",
				"src/resources/keys/min_left_glow.png", 1006, 325, 0.4f);
		guiFrame.add(hide_right.getLabel());

		show_right = new ImageButton(this, "src/resources/keys/min_right.png",
				"src/resources/keys/min_right_glow.png", 802, 325, 0.4f);
		guiFrame.add(show_right.getLabel());
		show_right.getLabel().setVisible(false);

		hide_top = new ImageButton(this, "src/resources/keys/min_down.png",
				"src/resources/keys/min_down_glow.png", 590, 0, 0.4f);
		guiFrame.add(hide_top.getLabel());

		show_top = new ImageButton(this, "src/resources/keys/min_up.png",
				"src/resources/keys/min_up_glow.png", 590, 186, 0.4f);
		guiFrame.add(show_top.getLabel());
		show_top.getLabel().setVisible(false);

		hide_left = new ImageButton(this, "src/resources/keys/min_right.png",
				"src/resources/keys/min_right_glow.png", 0, 325, 0.4f);
		guiFrame.add(hide_left.getLabel());

		show_left = new ImageButton(this, "src/resources/keys/min_left.png",
				"src/resources/keys/min_left_glow.png", 378, 325, 0.4f);
		guiFrame.add(show_left.getLabel());
		show_left.getLabel().setVisible(false);

		hide_bottom = new ImageButton(this, "src/resources/keys/min_up.png",
				"src/resources/keys/min_up_glow.png", 590, 657, 0.4f);
		guiFrame.add(hide_bottom.getLabel());

		show_bottom = new ImageButton(this, "src/resources/keys/min_down.png",
				"src/resources/keys/min_down_glow.png", 590, 430, 0.4f);
		guiFrame.add(show_bottom.getLabel());
		show_bottom.getLabel().setVisible(false);

		guiFrame.repaint();
	}

	/**
	 * Will be called when a ImageButton on the GUI is clicked
	 */
	@Override
	public void ButtonCallBack(ImageButton pressedButton) {
		// TODO: Maybe it would make sense to map the button to e.g. The F1 and
		// F2 etc. keys ?
		// They would then be also usable in fullscreen mode

		if ((pressedButton == reset) && (isgameCanvasShown)) {
			controller.resetGame();
			showList();
		}

		if ((pressedButton == play) && (isgameCanvasShown)) {
			try {
				controller.playGame();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		if ((pressedButton == pause) && (isgameCanvasShown)) {
			controller.pauseGame();
		}

		if ((pressedButton == step) && (isgameCanvasShown)) {
			controller.stepForwardUntilDraw();
		}

		if ((pressedButton == fastforward) && (isgameCanvasShown)) {
			// TODO: show a list which shows e.g. the entries: slow, normal, 2x
			// speed, 5x speed, 10x speed
		}

		if ((pressedButton == maxi) && (isgameCanvasShown)) {
			setFullscreen();
		}

		if (pressedButton == min) {
			guiFrame.setState(Frame.ICONIFIED);
		}

		if (pressedButton == close) {
			System.exit(0);
		}

		if (pressedButton == hide_right) {
			guiFrame.subtractShape("src/resources/shapes/right.png");
			show_right.getLabel().setVisible(true);
			guiFrame.repaint();
		}

		if (pressedButton == show_right) {
			guiFrame.addShape("src/resources/shapes/right.png");
			show_right.getLabel().setVisible(false);
			guiFrame.repaint();
		}

		if (pressedButton == hide_top) {
			guiFrame.remove(cpuAnalysisC);
			guiFrame.subtractShape("src/resources/shapes/top.png");
			show_top.getLabel().setVisible(true);
			guiFrame.repaint();
		}

		if (pressedButton == show_top) {
			guiFrame.addShape("src/resources/shapes/top.png");
			show_top.getLabel().setVisible(false);
			cpuAnalysisC.setBounds(409, 7, 777 - 409, 185 - 7);
			guiFrame.add(cpuAnalysisC);
			cpuAnalysisC.repaint();
			guiFrame.repaint();
		}

		if (pressedButton == hide_left) {
			guiFrame.remove(ramAnalysisC);
			guiFrame.subtractShape("src/resources/shapes/left.png");
			show_left.getLabel().setVisible(true);
			guiFrame.repaint();
		}

		if (pressedButton == show_left) {
			guiFrame.addShape("src/resources/shapes/left.png");
			show_left.getLabel().setVisible(false);
			ramAnalysisC.setBounds(5, 241, 373 - 5, 419 - 241);
			guiFrame.add(ramAnalysisC);
			ramAnalysisC.repaint();
			guiFrame.repaint();
		}

		if (pressedButton == hide_bottom) {
			guiFrame.subtractShape("src/resources/shapes/bottom.png");
			show_bottom.getLabel().setVisible(true);
			guiFrame.repaint();
		}

		if (pressedButton == show_bottom) {
			guiFrame.addShape("src/resources/shapes/bottom.png");
			show_bottom.getLabel().setVisible(false);
			guiFrame.repaint();
		}
	}

	/**
	 * Initializes the list of Games which will later be displayed onto the GUI
	 */
	private void initList() {
		DefaultListModel<String> listmodel = controller.getRomList();

		// make gameCanvas invisible
		gameCanvas.setVisible(false);

		// create JList of games called gameList and add it to the guiFrame
		gameList = new JList<String>(listmodel);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = gameList.locationToIndex(e.getPoint());
					if (_main.DEBUG)
						System.out.println("Loading "
								+ gameList.getModel().getElementAt(index));
					controller.loadGame(gameList.getModel().getElementAt(index)
							.toString());
					showGameCanvas();
					/*
					 * try { controller.playGame(); } catch
					 * (InterruptedException e1) { e1.printStackTrace(); }
					 */
				}
			}
		};

		KeyListener keylistener = new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (_main.DEBUG)
						System.out.println("Loading "
								+ gameList.getModel().getElementAt(0));
					controller.loadGame(gameList.getSelectedValue().toString());
					showGameCanvas();
					/*
					 * try { controller.playGame(); } catch
					 * (InterruptedException e1) { e1.printStackTrace(); }
					 */
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		};

		gameList.addKeyListener(keylistener);
		gameList.addMouseListener(mouseListener);
		gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameList.setSelectedIndex(0);
		gameList.setVisibleRowCount(10);
		gameList.setBounds(385, 230, 416, 200);
		gameList.addMouseListener(guiFrame);
		gameList.addMouseMotionListener(guiFrame);
		gameList.setVisible(true);

		scrollPane = new JScrollPane(gameList);
		scrollPane.setBounds(385, 230, 416, 200);
		scrollPane.setVisible(true);

		guiFrame.add(scrollPane);
		guiFrame.repaint();
		gameList.requestFocusInWindow();
	}

	/**
	 * Replaces the gamecanvas and shows a JList instead containing all games
	 * from a folder Replaces the gamecanvas and shows a JList instead
	 * containing all games from a folder
	 */
	private void showList() {
		gameCanvas.setVisible(false);
		gameList.setVisible(true);
		scrollPane.setVisible(true);
		guiFrame.repaint();
		gameList.requestFocusInWindow();
		isgameCanvasShown = false;
	}

	/**
	 * Removes the JList game list, and shows the gamecanvas again
	 */
	private void showGameCanvas() {
		gameList.setVisible(false);
		scrollPane.setVisible(false);
		gameCanvas.setVisible(true);
		gameCanvas.requestFocusInWindow();
		guiFrame.repaint();
		isgameCanvasShown = true;
	}

	/**
	 * Creates a fullscreen JFrame with the gamecanvas on it
	 */
	private void setFullscreen() {
		if (!isFullScreen) {
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsDevice gd = gs;
			DisplayMode dm = gd.getDisplayMode();
			full = new JFrame(gs.getDefaultConfiguration());
			guiFrame.remove(gameCanvas);
			// HACK: Instead of true fullscreen, we take the size and height + 1
			// to avoid flickering on certain Hardware configurations
			gameCanvas.setBounds(0, 0, dm.getWidth(), dm.getHeight() + 1);
			full.getContentPane().add(gameCanvas);
			full.setUndecorated(true);
			if (gd.isDisplayChangeSupported()) {
				gd.setDisplayMode(dm);
			}
			full.pack();
			full.setVisible(true);
			full.setAlwaysOnTop(true);
			// This will cause flickering on certain Hardware configurations
			// gd.setFullScreenWindow(full);
			gameCanvas.requestFocusInWindow();
			isFullScreen = true;
		}
	}

	/**
	 * resets the fullscreen view to normal view again
	 */
	public void resetFullscreen() {
		if (isFullScreen) {
			isFullScreen = false;
			full.getContentPane().removeAll();
			full.setVisible(false);
			gameCanvas.setBounds(385, 230, 416, 200);
			guiFrame.add(gameCanvas);
			showGameCanvas();
		}
	}

	/**
	 * Switches the Fullscreen mode from the current state to the opposite state
	 */
	public void switchFullscreen() {
		if (isFullScreen) {
			resetFullscreen();
		} else {
			setFullscreen();
		}
	}

	public void initKeyboardView(){
		Button b1 = new Button("1");
		b1.setBounds(491,438,52,54);
		b1.setBackground(Color.WHITE);
		b1.addMouseListener(new ButtonCallback(0x01, input));
		guiFrame.add(b1);
		Button b2 = new Button("2");
		b2.setBounds(543,438,52,54);
		b2.setBackground(Color.WHITE);
		b2.addMouseListener(new ButtonCallback(0x02, input));
		guiFrame.add(b2);
		Button b3 = new Button("3");
		b3.setBounds(595,438,52,54);
		b3.setBackground(Color.WHITE);
		b3.addMouseListener(new ButtonCallback(0x03, input));
		guiFrame.add(b3);
		Button bC = new Button("C");
		bC.setBounds(647,438,52,54);
		bC.setBackground(Color.WHITE);
		bC.addMouseListener(new ButtonCallback(0x0C, input));
		guiFrame.add(bC);
		
		Button b4 = new Button("4");
		b4.setBounds(491,492,52,54);
		b4.setBackground(Color.WHITE);
		b4.addMouseListener(new ButtonCallback(0x04, input));
		guiFrame.add(b4);
		Button b5 = new Button("5");
		b5.setBounds(543,492,52,54);
		b5.setBackground(Color.WHITE);
		b5.addMouseListener(new ButtonCallback(0x05, input));
		guiFrame.add(b5);
		Button b6 = new Button("6");
		b6.setBounds(595,492,52,54);
		b6.setBackground(Color.WHITE);
		b6.addMouseListener(new ButtonCallback(0x06, input));
		guiFrame.add(b6);
		Button bD = new Button("D");
		bD.setBounds(647,492,52,54);
		bD.setBackground(Color.WHITE);
		bD.addMouseListener(new ButtonCallback(0x0D, input));
		guiFrame.add(bD);
		
		Button b7 = new Button("7");
		b7.setBounds(491,546,52,54);
		b7.setBackground(Color.WHITE);
		b7.addMouseListener(new ButtonCallback(0x07, input));
		guiFrame.add(b7);
		Button b8 = new Button("8");
		b8.setBounds(543,546,52,54);
		b8.setBackground(Color.WHITE);
		b8.addMouseListener(new ButtonCallback(0x08, input));
		guiFrame.add(b8);
		Button b9 = new Button("9");
		b9.setBounds(595,546,52,54);
		b9.setBackground(Color.WHITE);
		b9.addMouseListener(new ButtonCallback(0x09, input));
		guiFrame.add(b9);
		Button bE = new Button("E");
		bE.setBounds(647,546,52,54);
		bE.setBackground(Color.WHITE);
		bE.addMouseListener(new ButtonCallback(0x0E, input));
		guiFrame.add(bE);
		
		Button bA = new Button("A");
		bA.setBounds(491,600,52,54);
		bA.setBackground(Color.WHITE);
		bA.addMouseListener(new ButtonCallback(0x0A, input));
		guiFrame.add(bA);
		Button b0 = new Button("0");
		b0.setBounds(543,600,52,54);
		b0.setBackground(Color.WHITE);
		b0.addMouseListener(new ButtonCallback(0x00, input));
		guiFrame.add(b0);
		Button bB = new Button("B");
		bB.setBounds(595,600,52,54);
		bB.setBackground(Color.WHITE);
		bB.addMouseListener(new ButtonCallback(0x0B, input));
		guiFrame.add(bB);
		Button bF = new Button("F");
		bF.setBounds(647,600,52,54);
		bF.setBackground(Color.WHITE);
		bF.addMouseListener(new ButtonCallback(0x0F, input));
		guiFrame.add(bF);
		
	}
	
	private void initRegisterView()
	{
		String[] _header = new String[] { "Reg", "Val", "Reg", "Val" };
		String[][] _data = new String[8][4];
		for(int i = 0; i < 16; i++)
		{
			if(i%2==0) _data[i/2][0] = "V"+i;			
			else _data[i/2][2] = "V"+i;
		}
		DefaultTableModel model = new DefaultTableModel(_data,_header);		
		registerTable = new JTable(model);
		registerTableCallback = new RegisterTableCallback(registerTable, cpu);
		model.addTableModelListener(registerTableCallback);
		registerTable.setRowHeight(19);
		registerTable.setBackground(Color.BLACK);
		registerTable.getTableHeader().setBackground(Color.BLACK);
		registerTable.setForeground(Color.WHITE);
		registerTable.getTableHeader().setForeground(Color.WHITE);
		registerTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(registerTable);
		scrollPane.setBounds(809, 241, 190, 175);
		scrollPane.setVisible(true);
		guiFrame.add(scrollPane);
		guiFrame.repaint();
	}
	
	public void UpdateRegisterView(int reg, int val)
	{
		TableModel model =	registerTable.getModel();
		if(reg%2==0)
		{
			registerTableCallback.ignoreNext();
			model.setValueAt(val, reg/2, 1);
		}
		else
		{
			registerTableCallback.ignoreNext();
			model.setValueAt(val, reg/2, 3);
		}
	}
}
