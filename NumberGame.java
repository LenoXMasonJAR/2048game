package game1024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import game1024.Cell;
import java.lang.Math;

/**************************************************
 * This is a program that sets up the game 2048
 * 
 * @author Mason Mahoney
 * @version 10/4/2016
 **************************************************/
public class NumberGame implements NumberSlider {

	/** This is the 2D array that creates the game board */
	private int[][] gameBoard;

	/** This is the winning value */
	private int winNum;

	/** This is the variable that generates random values */
	private Random rnd;

	/** This is the array list that holds the cells */
	private ArrayList<Cell> inCell;

	/** This is the variable that holds the games status */
	private GameStatus gameStatus;

	/** This is an array list of an array list for the undo */
	private ArrayList<ArrayList<Cell>> undoArray;

	@Override
	public void resizeBoard(int height, int width, int winningValue) {
		if (height < 0 || width < 0)
			throw new ArrayIndexOutOfBoundsException();
		for (int i = 0; i != -1; i++) {
			if (winningValue == Math.pow(2, i)) {
				winNum = winningValue;
				i = -2;
			} else if (winningValue < Math.pow(2, i))
				throw new IllegalArgumentException();
		}

		winNum = winningValue;
		inCell = new ArrayList<Cell>();
		undoArray = new ArrayList<ArrayList<Cell>>();
		rnd = new Random();
		gameStatus = GameStatus.IN_PROGRESS;
		gameBoard = new int[height][width];
	}

	@Override
	public void reset() {
		for (int r = 0; r < gameBoard.length; r++)
			for (int c = 0; c < gameBoard[0].length; c++)
				gameBoard[r][c] = 0;

		placeRandomValue();
		placeRandomValue();

	}

	@Override
	public void setValues(int[][] ref) {
		for (int r = 0; r < gameBoard.length; r++) {
			for (int c = 0; c < gameBoard[0].length; c++) {
				gameBoard[r][c] = ref[r][c];
			}

		}

	}

	@Override
	public Cell placeRandomValue() {
		if (getNonEmptyTiles().size() == gameBoard.length * gameBoard[0].length)
			throw new IllegalStateException("Board has no empty cells!");
		int v = 0;
		int r = rnd.nextInt(gameBoard.length);
		int c = rnd.nextInt(gameBoard[0].length);

		while (gameBoard[r][c] != 0) {
			r = rnd.nextInt(gameBoard.length);
			c = rnd.nextInt(gameBoard[0].length);
		}
		Cell cell = new Cell();
		cell.row = r;
		cell.column = c;
		while (v != 2 && v != 4) {
			v = rnd.nextInt(5);
		}
		cell.value = v;

		setCell(cell);

		return cell;

	}

	@Override
	public boolean slide(SlideDirection dir) {
		undoVersionGetNon();

		if (!possibleMove()) {
			return false;
		}

		int[][] checkBoard = new int[gameBoard.length][gameBoard[0].length];

		for (int r = 0; r < gameBoard.length; r++) {
			for (int c = 0; c < gameBoard[0].length; c++)
				checkBoard[r][c] = gameBoard[r][c];
		}

		switch (dir) {
		case UP:
			slideUp();
			break;

		case DOWN:
			slideDown();
			break;

		case LEFT:
			slideLeft();
			break;

		case RIGHT:
			slideRight();
			break;

		default:
			return false;
		}
		if (!Arrays.deepEquals(gameBoard, checkBoard)) {
			this.placeRandomValue();
			return true;
		} else
			return false;
	}

	/************************************************************
	 * This is a method for sliding left
	 ************************************************************/
	private void slideLeft() {
		int multiplier = 2;

		// int i represents rows; int j represents columns
		for (int i = 0; i < gameBoard.length; i++) {
			ArrayList<Integer> slideTile = new ArrayList<>();

			// Copying row w/o 0s to ArrayList
			for (int j = 0; j < gameBoard[0].length; j++) {
				if (gameBoard[i][j] != 0) {
					slideTile.add(gameBoard[i][j]);
				}
			}

			// Merging tiles that are next to each other if
			// they are equal
			for (int j = 0; j < slideTile.size() - 1; j++) {
				if (slideTile.get(j).equals(slideTile.get(j + 1))) {
					slideTile.set(j, multiplier * slideTile.get(j + 1));
					slideTile.set(j + 1, 0);
				}
			}

			// Removing 0s that may have resulted from merging
			for (int j = 0; j < slideTile.size(); j++) {
				if (slideTile.get(j) == 0) {
					slideTile.remove(j);
				}
			}

			// Adding 0s to end of ArrayList to slide tiles left
			int size = slideTile.size();
			for (int j = 0; j < (gameBoard[0].length - size); j++) {
				slideTile.add(0);
			}

			// Copying ArrayList row back to game gameBoard.
			for (int j = 0; j < gameBoard[0].length; j++) {
				this.gameBoard[i][j] = slideTile.get(j);
			}
		}
	}

	/************************************************************
	 * This is a method for sliding right
	 ************************************************************/
	private void slideRight() {
		int multiplier = 2;

		// int i represents rows; int j represents columns
		for (int i = 0; i < gameBoard.length; i++) {
			ArrayList<Integer> slideTile = new ArrayList<>();

			// Copying row w/o 0s to ArrayList
			for (int j = 0; j < gameBoard[0].length; j++) {
				if (gameBoard[i][j] != 0) {
					slideTile.add(gameBoard[i][j]);
				}
			}

			// Merging tiles that are next to each other if
			// they are equal
			for (int j = slideTile.size() - 1; j > 0; j--) {
				if (slideTile.get(j).equals(slideTile.get(j - 1))) {
					slideTile.set(j, multiplier * slideTile.get(j - 1));
					slideTile.set(j - 1, 0);
				}
			}

			// Removing 0s that may have resulted from merging
			for (int j = 0; j < slideTile.size(); j++) {
				if (slideTile.get(j) == 0) {
					slideTile.remove(j);
				}
			}

			// Adding 0s to beginning of ArrayList to slide tiles right
			int size = slideTile.size();
			for (int j = 0; j < (gameBoard[0].length - size); j++) {
				slideTile.add(0, 0);
			}

			// Copying ArrayList row back to game gameBoard.
			for (int j = 0; j < gameBoard[0].length; j++) {
				this.gameBoard[i][j] = slideTile.get(j);
			}
		}
	}

	/************************************************************
	 * This is a method for sliding up
	 ************************************************************/
	private void slideUp() {
		int multiplier = 2;

		// int i represents rows; int j represents columns
		for (int j = 0; j < gameBoard[0].length; j++) {
			ArrayList<Integer> slideTile = new ArrayList<>();

			// Copying row w/o 0s to ArrayList
			for (int i = 0; i < gameBoard.length; i++) {
				if (gameBoard[i][j] != 0)
					slideTile.add(gameBoard[i][j]);

			}

			// Merging tiles that are next to each other if
			// they are equal
			for (int i = 0; i < slideTile.size() - 1; i++) {
				if (slideTile.get(i).equals(slideTile.get(i + 1))) {
					slideTile.set(i, multiplier * slideTile.get(i + 1));
					System.out.println(slideTile.get(i));
					slideTile.set(i + 1, 0);
				}
			}

			// Removing 0s that may have resulted from merging
			for (int i = 0; i < slideTile.size(); i++) {
				if (slideTile.get(i) == 0) {
					slideTile.remove(i);
				}
			}

			// Adding 0s to end of ArrayList to slide tiles up
			int size = slideTile.size();
			for (int i = 0; i < (gameBoard.length - size); i++) {
				slideTile.add(0);
			}

			// Copying ArrayList row back to game gameBoard.
			for (int i = 0; i < gameBoard.length; i++) {
				this.gameBoard[i][j] = slideTile.get(i);
			}

		}
	}

	/************************************************************
	 * This is a method for sliding down
	 ************************************************************/
	private void slideDown() {
		int multiplier = 2;

		// int i represents rows; int j represents columns
		for (int j = 0; j < gameBoard[0].length; j++) {
			ArrayList<Integer> slideTile = new ArrayList<>();

			// Copying row w/o 0s to ArrayList
			for (int i = 0; i < gameBoard.length; i++)
				if (gameBoard[i][j] != 0) {
					slideTile.add(gameBoard[i][j]);
				}

			// Merging tiles that are next to each other if
			// they are equal
			for (int i = slideTile.size() - 1; i > 0; i--) {
				if (slideTile.get(i).equals(slideTile.get(i - 1))) {
					slideTile.set(i, multiplier * slideTile.get(i - 1));
					slideTile.set(i - 1, 0);
				}
			}

			// Removing 0s that may have resulted from merging
			for (int i = 0; i < slideTile.size(); i++) {
				if (slideTile.get(i) == 0) {
					slideTile.remove(i);
				}
			}

			// Adding 0s to end of ArrayList to slide tiles up
			int size = slideTile.size();
			for (int i = 0; i < (gameBoard.length - size); i++) {
				slideTile.add(0, 0);
			}

			// Copying ArrayList row back to game gameBoard.
			for (int i = 0; i < gameBoard.length; i++) {
				this.gameBoard[i][j] = slideTile.get(i);
			}
		}
	}

	@Override
	public ArrayList<Cell> getNonEmptyTiles() {
		inCell = new ArrayList<Cell>();

		for (int r = 0; r < gameBoard.length; r++) {
			for (int c = 0; c < gameBoard[0].length; c++) {
				if (gameBoard[r][c] != 0) {
					Cell one = new Cell(r, c, gameBoard[r][c]);
					inCell.add(one);
				}
			}

		}
		return inCell;
	}

	/************************************************************
	 * This method is almost identical to the getNonEmptyTiles() except at the
	 * end of the method it adds the arrayList inCell to the arrayList that I
	 * use to do my undo method
	 * 
	 * @return array list that holds cells
	 ************************************************************/
	private ArrayList<Cell> undoVersionGetNon() {
		inCell = new ArrayList<Cell>();

		for (int r = 0; r < gameBoard.length; r++) {
			for (int c = 0; c < gameBoard[0].length; c++) {
				if (gameBoard[r][c] != 0) {
					Cell one = new Cell(r, c, gameBoard[r][c]);
					inCell.add(one);
				}
			}
		}
		undoArray.add(inCell);
		return inCell;
	}

	@Override
	public GameStatus getStatus() {
		for (int[] i : gameBoard) {
			for (int j : i)
				if (j == winNum)
					return GameStatus.USER_WON;
		}
		if (!possibleMove()) {
			return GameStatus.USER_LOST;
		}
		return GameStatus.IN_PROGRESS;
	}

	@Override
	public void undo() {
		if (undoArray.size() == 0)
			throw new IllegalStateException("Can't Undo Any Further");

		ArrayList<Cell> undo = undoArray.get(undoArray.size() - 1);

		for (int r = 0; r < gameBoard.length; r++)
			for (int c = 0; c < gameBoard[0].length; c++) {
				gameBoard[r][c] = 0;
			}
		for (int i = 0; i < undo.size(); i++) {
			gameBoard[undo.get(i).row][undo.get(i).column] = undo.get(i).value;
		}
		undoArray.remove(undoArray.size() - 1);
	}

	/************************************************************
	 * This is a method that is called by placeRandomValue() and sets the game
	 * board rows and columns and places the cells value at that location
	 * 
	 * @param c
	 *            which is a cell for the board
	 ************************************************************/
	private void setCell(Cell c) {

		this.gameBoard[c.row][c.column] = c.value;
	}

	/************************************************************
	 * Method for seeing if the board is full
	 * 
	 * @return true or false if boards full or not
	 ************************************************************/
	private boolean boardFull() {
		if (getNonEmptyTiles().size() > 0)
			return true;
		else
			return false;
	}

	/************************************************************
	 * Method for testing if a move is possible on the board
	 * 
	 * @return true or false if move is possible
	 ************************************************************/
	private boolean possibleMove() {
		if (!boardFull())
			return true;
		else if (boardFull()) {
			for (int r = 0; r < gameBoard.length - 1; r++) {
				for (int c = 0; c < gameBoard[0].length - 1; c++)
					if (gameBoard[r][c] == gameBoard[r + 1][c] || gameBoard[r][c] == gameBoard[r][c + 1]
							|| gameBoard[r + 1][c] == gameBoard[r + 1][c + 1]
							|| gameBoard[r][c + 1] == gameBoard[r + 1][c + 1])
						return true;
			}
		}
		return false;
	}
}