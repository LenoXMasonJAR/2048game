package game1024;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/******************************************************************
 * GUI for the game 2048
 * 
 * @author Mason Mahoney
 * @version 10/4/2016
 ******************************************************************/
public class gui1024Panel extends JPanel {
	/** createa new NumberGame engine */
	NumberGame game;

	/** 2D array Jlabel called board */
	JLabel[][] board;

	/** integer for board width */
	private int boardW;

	/** integer for board height */
	private int boardH;

	/** integer for games played */
	private int gamesPlayed;

	/** integer for wins */
	private int gWins;

	/** integer for moves */
	private int gMoves;

	/** JLabel for error messages */
	JLabel errorL;

	/** JLabel for winning value */
	JLabel winVal;

	/** JLabel for games */
	JLabel games;

	/** JLabel for moves */
	JLabel moves;

	/** JLabel for wins */
	JLabel wins;

	/** Jpanel for the panel */
	JPanel panel;

	/** JPanel for the main panel */
	JPanel main;

	/** JPanel for the stats panel */
	JPanel statsPan;

	/** JButton for the Up slide */
	JButton upButton;

	/** JButton for the Down slide */
	JButton downButton;

	/** JButton for the Left slide */
	JButton leftButton;

	/** JButton for the Right slide */
	JButton rightButton;

	/** JField for the board height */
	JTextField bHeight;

	/** JField for the board width */
	JTextField bWidth;

	/** JField for winning Value */
	JTextField winValue;

	/*****************************************************************
	 * Constructor for GUI that sets up the view for the NumberGame model.
	 *****************************************************************/
	public gui1024Panel() {

		gamesPlayed = 0;
		errorL = new JLabel("");
		winVal = new JLabel("Winning Number: 1024");
		wins = new JLabel("Wins: " + gWins);
		games = new JLabel("Games: " + gamesPlayed);
		moves = new JLabel("Moves: " + gMoves);
		Listener listener = new Listener();

		bHeight = new JTextField(4);
		bWidth = new JTextField(4);
		winValue = new JTextField(6);
		JPanel labelPanel = new JPanel(new FlowLayout());
		labelPanel.add(errorL);
		labelPanel.add(winVal);

		labelPanel.add(new JLabel("Width:"));
		labelPanel.add(bWidth);
		labelPanel.add(new JLabel("Height:"));
		labelPanel.add(bHeight);
		labelPanel.add(new JLabel("Winning Value:"));
		labelPanel.add(winValue);

		game = new NumberGame();
		game.resizeBoard(4, 4, 1024);
		boardW = 4;
		boardH = 4;
		this.setLayout(new BorderLayout());


		board = new JLabel[boardW][boardH];
		game.placeRandomValue();
		game.placeRandomValue();
		main = new JPanel((new GridLayout(boardW, boardH)));
		reset();
		setBoardVals();

		statsPan = new JPanel(new GridLayout(2, 2));
		panel = new JPanel(new GridLayout(2, 2));
		upButton = new JButton("UP");
		upButton.addActionListener(listener);
		downButton = new JButton("DOWN");
		downButton.addActionListener(listener);
		leftButton = new JButton("LEFT");
		leftButton.addActionListener(listener);
		rightButton = new JButton("RIGHT");
		rightButton.addActionListener(listener);
		panel.add(upButton);
		panel.add(downButton);
		panel.add(leftButton);
		panel.add(rightButton);
		
		statsPan.add(wins);
		statsPan.add(games);
		statsPan.add(moves);
		
		add(statsPan, BorderLayout.WEST);
		add(main, BorderLayout.CENTER);
		add(labelPanel, BorderLayout.NORTH);

		add(panel, BorderLayout.EAST);
	}

	/************************************************************
	 * Method that sets the boards values and color of cells based on what value
	 * is in the cell.
	 ************************************************************/
	private void setBoardVals() {

		for (int row = 0; row < boardW; row++)
			for (int col = 0; col < boardH; col++) {
				board[row][col].setText("");
				board[row][col].setBackground(Color.WHITE);
			}

		for (Cell c : game.getNonEmptyTiles()) {
			switch (c.value) {
			case 2:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.TWO.col);
				break;
			case 4:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.FOUR.col);
				break;
			case 8:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.EIGHT.col);
				break;
			case 16:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.SIXT.col);
				break;
			case 32:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.THRTW.col);
				break;
			case 64:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.SIXF.col);
				break;
			case 128:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.ONETWO.col);
				break;
			case 256:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.TWOFIF.col);
				break;
			case 512:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.FIVETWEL.col);
				break;
			case 1024:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.ONETHOU.col);
				break;
			case 2048:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.TWENTYF.col);
				break;
			case 4096:
				board[c.row][c.column].setText("" + c.value);
				board[c.row][c.column].setBackground(ColorL.FOURNIN.col);
				break;
			default:
				board[c.row][c.column].setText("" + c.value);
				Random rand = new Random();
				board[c.row][c.column]
						.setBackground(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));

			}

		}

	}

	/************************************************************
	 * Method that resets the board to a new game
	 ************************************************************/
	private void reset() {
		for (int row = 0; row < boardW; row++){
			for (int col = 0; col < boardH; col++) {
				JLabel jl = new JLabel("" + 0, SwingConstants.CENTER);
				jl.setOpaque(true);
				jl.setBackground(new Color(0, 0, 0));
				board[row][col] = jl;
				main.add(board[row][col]);
				add(main, BorderLayout.CENTER);

			}
		
		}
	}

	/************************************************************
	 * method that sets up the stats panel
	 ************************************************************/
	private void statsUpdate() {
		wins = new JLabel("Wins: " + gWins);
		moves = new JLabel("Moves: " + gMoves);
		games = new JLabel("Games: " + gamesPlayed);
		statsPan.removeAll();
		statsPan.add(wins);
		statsPan.add(moves);
		statsPan.add(games);
	}

	/************************************************************
	 * main method that sets up the JFrame
	 * 
	 * @param args
	 ************************************************************/
	public static void main(String[] args) {
		gui1024Panel gui = new gui1024Panel();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Listener listener = gui.new Listener();

		JMenuBar menuBar = new JMenuBar();
		JMenuItem quit = new JMenuItem("Quit");

		quit.addActionListener(listener);
		quit.setActionCommand("Quit");
		JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(listener);
		reset.setActionCommand("Reset");
		JMenuItem undo = new JMenuItem("Undo");
		undo.addActionListener(listener);
		undo.setActionCommand("Undo");
		JMenuItem resize = new JMenuItem("Resize");
		resize.addActionListener(listener);
		resize.setActionCommand("Resize");
		// JMenuItem stats = new JMenuItem("Stats");
		// resize.addActionListener(listener);
		// resize.setActionCommand("Stats");

		menuBar.add(undo);
		menuBar.add(reset);
		menuBar.add(quit);
		menuBar.add(resize);
		// menuBar.add(stats);
		frame.setJMenuBar(menuBar);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(gui, BorderLayout.CENTER);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	private class Listener implements ActionListener {

		/***********************************************************
		 * method for actionPerformed when buttons are hit and things are
		 * clicked on within the GUI
		 * 
		 * @param ActionEvent
		 *            e which is what is being chosen
		 ***********************************************************/
		public void actionPerformed(ActionEvent e) {

			if (upButton == e.getSource()) {
				game.slide(SlideDirection.UP);
				gMoves++;
				statsUpdate();
			}

			if (downButton == e.getSource()) {
				game.slide(SlideDirection.DOWN);
				gMoves++;
				statsUpdate();
			}

			if (leftButton == e.getSource()) {
				game.slide(SlideDirection.LEFT);
				gMoves++;
				statsUpdate();
			}

			if (rightButton == e.getSource()) {
				game.slide(SlideDirection.RIGHT);
				gMoves++;
				statsUpdate();
			}

			if (game.getStatus() == GameStatus.USER_WON) {	
				gMoves = 0;		
				gWins += 1;
				statsUpdate();
				JOptionPane.showMessageDialog(null, "You WON!");
			}

			if (game.getStatus() == GameStatus.USER_LOST) {
				gMoves = 0;
				statsUpdate();
				JOptionPane.showMessageDialog(null, "You LOST!");
			}
			
			if ("Quit" == e.getActionCommand())
				System.exit(0);

			if ("Reset" == e.getActionCommand()) {
				gMoves = 0;
				gamesPlayed += 1;
				statsUpdate();
				game.reset();
			}

			if ("Undo" == e.getActionCommand())
				try {
					game.undo();
				} catch (IllegalStateException exp) {
					errorL.setText("Can't Undo any further!");
				}
			if ("Resize" == e.getActionCommand()) {
				try {
					game.resizeBoard(Integer.parseInt(bWidth.getText()), Integer.parseInt(bHeight.getText()),
							Integer.parseInt(winValue.getText()));
					boardW = Integer.parseInt(bWidth.getText());
					boardH = Integer.parseInt(bHeight.getText());
					winVal.setText("Number to Win: " + winValue.getText());
					board = new JLabel[boardW][boardH];
					main=new JPanel((new GridLayout(boardW, boardH)));
					reset();
					game.placeRandomValue();
					game.placeRandomValue();
					setBoardVals();
				} catch (Exception exep) {
					errorL.setText("Not a valid winning value Height or Width is negative");
				}
			}
			setBoardVals();
		}

	}
}