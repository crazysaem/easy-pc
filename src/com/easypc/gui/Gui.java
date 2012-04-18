package com.easypc.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.easypc.analysis.CPUAnalysisC;
import com.easypc.analysis.RAMAnalysisC;
import com.easypc.backend.InputLWJGL;
import com.easypc.chip8.GameCanvas;
import com.easypc.controller.Controller;

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
	private JList gameList;
	private GameCanvas gameCanvas;
	private InputLWJGL input;
	private JFrame full;
	private JScrollPane scrollPane;

	private ImageButtonLabel min, close;
	
	/*----------------------------------------------------
	 * Public Method Section. Shows the Methods directly available from other Classes:
	 *--------------------------------------------------*/

	/**
	 * Initializes the entire GUI and takes the controller Object
	 * 
	 * @param controller
	 */
	public Gui(Controller controller, CPUAnalysisC cpuAnalysisC,
			RAMAnalysisC ramAnalysisC, GameCanvas gamecanvas, InputLWJGL input) {
		this.controller = controller;
		this.gameCanvas = gamecanvas;	
		this.input = input;
		input.checkKeys();

		guiFrame = new GuiFrame();
		guiFrame.getContentPane().setBackground(Color.BLACK);
		guiFrame.setVisible(true);	
		guiFrame.addKeyListener(input.keylistener);

		cpuAnalysisC.setBounds(409, 7, 777 - 409, 185 - 7);
		cpuAnalysisC.addMouseListener(guiFrame);
		cpuAnalysisC.addMouseMotionListener(guiFrame);
		guiFrame.add(cpuAnalysisC);

		ramAnalysisC.setBounds(5, 241, 373 - 5, 419 - 241);
		ramAnalysisC.addMouseListener(guiFrame);
		ramAnalysisC.addMouseMotionListener(guiFrame);
		guiFrame.add(ramAnalysisC);

		gameCanvas.addKeyListener(input.keylistener);		
		gameCanvas.setBounds(385, 230, 416, 200);
		gameCanvas.addMouseListener(guiFrame);
		gameCanvas.addMouseMotionListener(guiFrame);
		guiFrame.add(gameCanvas);		
		
		showList();

		close = new ImageButtonLabel(this, "src/resources/keys/close.png", "src/resources/keys/close_glow.png", 770, 190, 0.4f);	
		guiFrame.add(close.getLabel());
		
		min = new ImageButtonLabel(this, "src/resources/keys/min.png", "src/resources/keys/min_glow.png", 730, 190, 0.4f);	
		guiFrame.add(min.getLabel());
	}
	
	@Override
	public void ButtonCallBack(ImageButtonLabel pressedButton) {
		if(pressedButton==close)
		{	
			fullscreen();
			//System.exit(0);
		} else if(pressedButton==min)
		{
			guiFrame.setState ( Frame.ICONIFIED );
		}
	}

	/**
	 * Replaces the gamecanvas and shows a JList instead containing all games
	 * from a folder
	 * Replaces the gamecanvas and shows a JList instead containing all games from a folder
	 */
	private void showList() {

		DefaultListModel listmodel = controller.getRomList();

		// make gameCanvas invisible
		gameCanvas.setVisible(false);

		// create JList of games called gameList and add it to the guiFrame
		gameList = new JList(listmodel);

		
		
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = gameList.locationToIndex(e.getPoint());
					File game = new File("src/resources/games/"
							+ gameList.getModel().getElementAt(index)
									.toString());
					// debug code
					System.out.println("Loading "
							+ gameList.getModel().getElementAt(index));
					controller.loadGame(game);
					showGameCanvas();
					try {
						controller.playGame();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};

		KeyListener keylistener = new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					File game = new File("src/resources/games/"
							+ gameList.getSelectedValue().toString());
					// debug code
					System.out.println("Loading "
							+ gameList.getModel().getElementAt(0));
					controller.loadGame(game);
					showGameCanvas();					
					try {
						controller.playGame();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
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
	 * Removes the JList game list, and shows the gamecanvas again
	 */
	private void showGameCanvas() {
		// setting the gamelist ( see function showList() ) invisible and the
		// gameCanvas visible again
		gameList.setVisible(false);
		scrollPane.setVisible(false);
		gameCanvas.setVisible(true);		
		gameCanvas.requestFocusInWindow();
		guiFrame.repaint();
	}

	
	private void fullscreen(){
		GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (int j = 0; j < gs.length; j++) {
            GraphicsDevice gd = gs[j];
            DisplayMode dm = gd.getDisplayMode();
            full = new JFrame(gs[j].getDefaultConfiguration());
            GraphicsConfiguration[] gcs = gd.getConfigurations();           
            Canvas c = new Canvas(gd.getDefaultConfiguration());            
            guiFrame.remove(gameCanvas);
            c = gameCanvas;            
            full.getContentPane().add(c);
            full.setUndecorated(true);
            System.out.println(gd.isFullScreenSupported());
            if (gd.isDisplayChangeSupported()) {
                gd.setDisplayMode(dm);
            }
            gd.setFullScreenWindow(full); 
            c.requestFocusInWindow();
        }
       
	}

	public void reset_fullscreen(){
		full.getContentPane().removeAll();
		full.setVisible(false);
		gameCanvas.setBounds(385, 230, 416, 200);
		guiFrame.add(gameCanvas);
		showGameCanvas();
		
						
	}
	
}
