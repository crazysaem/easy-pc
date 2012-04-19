package com.easypc.gui;

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
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.easypc.analysis.CPUAnalysisC;
import com.easypc.analysis.RAMAnalysisC;
import com.easypc.backend.Input;
import com.easypc.chip8.GameCanvas;
import com.easypc.controller.Controller;
import com.easypc.controller._main;

/**
 * Gui Master - puts the entire Gui together and manages it
 * 
 * @author crazysaem
 * 
 */
public class Gui implements ImageButtonLabelCallBack {
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
	
	//The gameCanvas is used to connected it to the Gui and enable fullscreen
	private GameCanvas gameCanvas;
	private Input input;
	
	//reference to the fullscreen frame
	private JFrame full;
	
	//JList Scrollpane
	private JScrollPane scrollPane;

	private ImageButtonLabel reset, play, pause, step, fastforward,min, close;

	private boolean isFullScreen;

	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	/**
	 * Initializes the entire GUI and takes the controller Object
	 * 
	 * @param controller
	 */
	public Gui(Controller controller, CPUAnalysisC cpuAnalysisC, RAMAnalysisC ramAnalysisC, GameCanvas gamecanvas, Input input) {
		this.controller = controller;
		this.gameCanvas = gamecanvas;
		this.input = input;
		
		//runnning keylistener for the guiFrame
		this.input.checkKeys();

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

		initList();
		
		reset = new ImageButtonLabel(this, "src/resources/keys/reset.png",
				"src/resources/keys/reset_glow.png", 385, 195, 0.4f);
		guiFrame.add(reset.getLabel());

		close = new ImageButtonLabel(this, "src/resources/keys/close.png",
				"src/resources/keys/close_glow.png", 770, 190, 0.4f);
		guiFrame.add(close.getLabel());

		min = new ImageButtonLabel(this, "src/resources/keys/min.png",
				"src/resources/keys/min_glow.png", 730, 190, 0.4f);
		guiFrame.add(min.getLabel());
	}

	@Override
	public void ButtonCallBack(ImageButtonLabel pressedButton) {
		if (pressedButton == close) {
			fullscreen();
			// System.exit(0);
		} else if (pressedButton == min) {
			guiFrame.setState(Frame.ICONIFIED);
		} else if (pressedButton == reset) {
			controller.resetGame();
			showList();
		}
	}
	
	/**
	 * Initializes the list of Games which will later be displayed onto the GUI
	 */
	private void initList()
	{
		DefaultListModel<String> listmodel = controller.getRomList();

		// make gameCanvas invisible
		gameCanvas.setVisible(false);

		// create JList of games called gameList and add it to the guiFrame
		gameList = new JList<String>(listmodel);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = gameList.locationToIndex(e.getPoint());
					if(_main.DEBUG)
						System.out.println("Loading " + gameList.getModel().getElementAt(index));
					controller.loadGame(gameList.getModel().getElementAt(index).toString());
					showGameCanvas();
					try {
						controller.playGame();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		};

		KeyListener keylistener = new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(_main.DEBUG)
						System.out.println("Loading " + gameList.getModel().getElementAt(0));
					controller.loadGame(gameList.getSelectedValue().toString());
					showGameCanvas();
					try {
						controller.playGame();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
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
	private void showList() 
	{
		gameCanvas.setVisible(false);
		gameList.setVisible(true);
		scrollPane.setVisible(true);
		guiFrame.repaint();
		gameList.requestFocusInWindow();		
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
	}

	/**
	 * Creates a fullscreen JFrame with the gamecanvas on it
	 */
	private void fullscreen() {		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		for (int j = 0; j < gs.length; j++) {
			GraphicsDevice gd = gs[j];
			DisplayMode dm = gd.getDisplayMode();
			full = new JFrame(gs[j].getDefaultConfiguration());
			guiFrame.remove(gameCanvas);
			//HACK: Instead of true fullscreen, we take the size and height + 1 to avoid flickering on certain Hardware configurations
			gameCanvas.setBounds(0, 0, dm.getWidth()+1, dm.getHeight()+1);
			full.getContentPane().add(gameCanvas);
			full.setUndecorated(true);
			if (gd.isDisplayChangeSupported()) {
				gd.setDisplayMode(dm);
			}
			full.pack();
			full.setVisible(true);
			full.setAlwaysOnTop(true);
			//This will cause flickering on certain Hardware configurations
			//gd.setFullScreenWindow(full);
			gameCanvas.requestFocusInWindow();
		}
		isFullScreen = true;
	}

	/**
	 * resets the fullscreen view to normal view again
	 */
	public void reset_fullscreen() {
		if(isFullScreen)
		{
			isFullScreen=false;
			full.getContentPane().removeAll();
			full.setVisible(false);
			gameCanvas.setBounds(385, 230, 416, 200);
			guiFrame.add(gameCanvas);
			showGameCanvas();
		}
	}

}
