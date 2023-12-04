package backend;

import java.util.ArrayList;

import helper.NumberGenerator;

/**
 * Game class represents the logic behind a Sliding Puzzle
 * @author Admin
 *
 */
public class Game 
{
	//Number of rows and columns for the puzzle
	private int numRows;
	private int numCols;
	
	//What a solved sliding puzzle looks like
	private int [][] solvedPuzzle;
	
	//Sliding puzzle when it is shuffled
	private int [][] shuffledPuzzle;
	
	//Flag to see when the game is over
	private boolean gameOver;
	
	//Static variables
	private static final int DEFAULT_ROWS = 3;
	private static final int DEFAULT_COLS = 3;
	
	/**
	 * Constructor
	 */
	public Game()
	{
		this.numRows = DEFAULT_ROWS;
		this.numCols = DEFAULT_COLS;
		
		createPuzzle();
		
		this.gameOver = true;
	}
	
	private void createPuzzle()
	{
		this.solvedPuzzle = new int[numRows][numCols];
		this.shuffledPuzzle = new int[numRows][numCols];
		
		int value = 1;
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				if(value == numRows * numCols)
				{
					solvedPuzzle[i][j] = 0;
					shuffledPuzzle[i][j] = 0;
				}
				else
				{
					solvedPuzzle[i][j] = value;
					shuffledPuzzle[i][j] = value;
				}
				
				++value;
			}
		}
	}
	
	public void printShuffledPuzzle()
	{
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				System.out.print(shuffledPuzzle[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Method that finds the "blank tile" in the shuffledPuzzle[][]
	 * @return (Location) the location of the "blank tile" if one exits, otherwise returns null
	 */
	private Location findBlankLocation()
	{
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				if(shuffledPuzzle[i][j] == 0)
				{
					return new Location(i, j);
				}
			}
		}
		return null;
	}
	
	/**
	 * Method that finds the possible legal locations that the "blank tile"
	 * can move to
	 * @param blankLocation (Location) the location of the "blank tile"
	 * @return an ArrayList<Location> of possible locations that the "blank tile" can move to
	 */
	private ArrayList<Location> findPossibleLocations(Location blankLocation)
	{
		ArrayList<Location> possibleLocations = new ArrayList<Location>();
		
		//Left check
		if(blankLocation.getCol() - 1 >= 0)
		{
			Location newLocation = new Location(
					blankLocation.getRow(),
					blankLocation.getCol() - 1
			);
			possibleLocations.add(newLocation);
		}
		
		//Right check
		if(blankLocation.getCol() + 1 < numCols)
		{
			Location newLocation = new Location(
					blankLocation.getRow(),
					blankLocation.getCol() + 1
			);
			possibleLocations.add(newLocation);
		}
		
		//Up check
		if(blankLocation.getRow() - 1 >= 0)
		{
			Location newLocation = new Location(
					blankLocation.getRow() - 1,
					blankLocation.getCol()
			);
			possibleLocations.add(newLocation);
		}
		
		//Down check
		if(blankLocation.getRow() + 1 < numRows)
		{
			Location newLocation = new Location(
					blankLocation.getRow() + 1,
					blankLocation.getCol()
			);
			possibleLocations.add(newLocation);
		}
		
		return possibleLocations;
	}
	
	/**
	 * Method that moves/swaps two tiles
	 * @param blankLocation (Location) location of the "blank tile"
	 * @param possibleLocations (Location) location of the tile you want "blank tile" to swap with / move to
	 */
	private void move(Location blankLocation, ArrayList<Location> possibleLocations)
	{
		int randomIndex = NumberGenerator.getRandomNumber(0, possibleLocations.size() - 1);
		int tempRow = possibleLocations.get(randomIndex).getRow();
		int tempCol = possibleLocations.get(randomIndex).getCol();
		int tempValue = shuffledPuzzle[tempRow][tempCol];
		shuffledPuzzle[tempRow][tempCol] = shuffledPuzzle[blankLocation.getRow()][blankLocation.getCol()];
		shuffledPuzzle[blankLocation.getRow()][blankLocation.getCol()] = tempValue;
	}
	
	/**
	 * Method that returns the "blank tile" to the bottom right corner of the puzzle
	 */
	private void returnToCorner()
	{
		//Find the "blank tile"
		Location blankLocation = findBlankLocation();
		
		//Move the "blank tile" until is has reached the bottom row
		while(blankLocation.getRow() != numRows - 1)
		{
			int nextRow = blankLocation.getRow() + 1;
			int nextCol = blankLocation.getCol();
			int tempValue = shuffledPuzzle[nextRow][nextCol];
			shuffledPuzzle[nextRow][nextCol] = shuffledPuzzle[blankLocation.getRow()][blankLocation.getCol()];
			shuffledPuzzle[blankLocation.getRow()][blankLocation.getCol()] = tempValue;
			
			blankLocation = findBlankLocation();
		}
		
		//Move the "blank tile" until it has reached the right most column
		while(blankLocation.getCol() != numCols - 1)
		{
			int nextRow = blankLocation.getRow();
			int nextCol = blankLocation.getCol() + 1;
			int tempValue = shuffledPuzzle[nextRow][nextCol];
			shuffledPuzzle[nextRow][nextCol] = shuffledPuzzle[blankLocation.getRow()][blankLocation.getCol()];
			shuffledPuzzle[blankLocation.getRow()][blankLocation.getCol()] = tempValue;
			
			blankLocation = findBlankLocation();
		}
	}
	
	/**
	 * Method that shuffles the puzzle
	 */
	public void shuffle()
	{
		Location blankLocation;
		ArrayList<Location> possibleLocations;
		for(int i = 0; i < 99; i++)
		{
			//Find the "blank tile" on the shuffledPuzzle
			blankLocation = findBlankLocation();
			
			//Determine the possible locations that the "blank tile" can move to
			possibleLocations = findPossibleLocations(blankLocation);
			
			//Randomly move the "blank tile" to one of those possible locations
			move(blankLocation, possibleLocations);
		}
		
		//Return the "blank tile" to the bottom right corner of the puzzle
		returnToCorner();
		
		gameOver = false;
	}
	
	/**
	 * Method that swaps two "tiles" in the shuffled puzzle
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 */
	public void swap(int row1, int col1, int row2, int col2)
	{
		int tempValue = shuffledPuzzle[row1][col1];
		shuffledPuzzle[row1][col1] = shuffledPuzzle[row2][col2];
		shuffledPuzzle[row2][col2] = tempValue;
	}
	
	/**
	 * Method that checks if win condition has been met
	 */
	public void checkForWin()
	{
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				if(shuffledPuzzle[i][j] != solvedPuzzle[i][j])
				{
					gameOver = false;
					return;
				}
			}
		}
		
		gameOver = true;
	}
	
	//Getter methods
	public int getNumRows() {return numRows;}
	public int getNumCols() {return numCols;}
	public int[][] getSolvedPuzzle() {return solvedPuzzle;}
	public int[][] getShuffledPuzzle() {return shuffledPuzzle;}
	public boolean isGameOver() {return gameOver;}
}
