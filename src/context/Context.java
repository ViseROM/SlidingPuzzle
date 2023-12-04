package context;

import backend.Game;

/**
 * Context connects the front end to back end 
 * 
 * @author Vachia Thoj
 *
 */
public class Context
{
	//Game object
	private Game game;
	
	/**
	 * Constructor
	 */
	public Context()
	{
		this.game = new Game();
	}
	
	//Getter methods
	public int getNumRows() {return this.game.getNumRows();}
	public int getNumCols() {return this.game.getNumCols();}
	public int[][] getSolvedPuzzle() {return this.game.getSolvedPuzzle();}
	public int[][] getShuffledPuzzle() {return this.game.getShuffledPuzzle();}
	public boolean isGameOver() {return this.game.isGameOver();}
	
	public void shuffle() {this.game.shuffle();}
	public void swap(int row1, int col1, int row2, int col2) {this.game.swap(row1, col1, row2, col2);}
	public void checkForWin() {this.game.checkForWin();}
}
