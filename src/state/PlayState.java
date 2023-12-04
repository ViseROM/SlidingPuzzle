package state;

import java.awt.Color;
import java.awt.Graphics2D;

import button.*;
import context.Context;
import main.GamePanel;
import text.Text;

/**
 * PlayState class represents the "play area" Sliding Puzzle 
 * 
 * @author Vachia Thoj
 *
 */
public class PlayState extends State
{
	//Context for the frontend to be able to talk to the backend
	private Context context;
	
	//2D array; Boxes/tiles for the sliding puzzle
	private IntegerBox[][] boxes;
	
	//Number of rows and columns for the 2D array
	private int numRows;
	private int numCols;
	
	//Buttons
	private NameButton newGameButton;
	
	//Texts
	private Text solvedText;
	
	/**
	 * Constructor
	 */
	public PlayState()
	{
		createContext();
		
		this.numRows = this.context.getNumRows();
		this.numCols = this.context.getNumCols();
		
		createBoxes();
		createButtons();
		createTexts();
	}
	
////////////////////////////////////////////// CREATE METHODS //////////////////////////////////////////////
	
	private void createContext()
	{
		this.context = new Context();
	}
	
	private void createButtons()
	{
		this.newGameButton = new NameButton(
				((GamePanel.WIDTH / 2) - 96),
				16,
				192,
				32,
				"NEW GAME"
		);
	}
	
	private void createBoxes()
	{
		this.boxes = new IntegerBox[numRows][numCols];
		
		int startX = 120;
		int startY = 120;
		
		int width = 160;
		int height = 160;
		
		int[][] board = this.context.getShuffledPuzzle();
		
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				this.boxes[i][j] = new IntegerBox(
						startX + (width * j),
						startY + (height * i),
						width,
						height,
						board[i][j]
				);
			}
		}
	}
	
	private void createTexts()
	{
		this.solvedText = new Text("SOLVED");
		this.solvedText.changeColor(Color.RED);
		this.solvedText.changeScale(3);
		this.solvedText.setX((GamePanel.WIDTH / 2) - (solvedText.getWidth() / 2));
		this.solvedText.setY(64);
	}
	
////////////////////////////////////////////// UPDATE METHODS //////////////////////////////////////////////
	
	/**
	 * Method that updates the Buttons
	 */
	private void updateButtons()
	{
		newGameButton.update();
		
		performButtonAction();
	}
	
	/**
	 * Method that performs a button action if that Button has been clicked on
	 */
	private void performButtonAction()
	{
		if(newGameButton.isMouseClickingButton())
		{
			newGameButton.setMouseClickingButton(false);
			context.shuffle();
			createBoxes();
		}
	}
	
	/**
	 * Method that updates the boxes
	 */
	private void updateBoxes()
	{
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				boxes[i][j].update();
				
				if(boxes[i][j].isMouseClickingButton())
				{
					boxes[i][j].setMouseClickingButton(false);
					
					//Left check
					if(j - 1 >= 0 && boxes[i][j - 1].getNumber() == 0)
					{
						int tempValue = boxes[i][j].getNumber();
						boxes[i][j].setNumber(boxes[i][j - 1].getNumber());
						boxes[i][j - 1].setNumber(tempValue);
						context.swap(i, j, i, j - 1);
						context.checkForWin();
						return;
					}
					
					//Right check
					if(j + 1 < numCols && boxes[i][j + 1].getNumber() == 0)
					{
						int tempValue = boxes[i][j].getNumber();
						boxes[i][j].setNumber(boxes[i][j + 1].getNumber());
						boxes[i][j + 1].setNumber(tempValue);
						context.swap(i, j, i, j + 1);
						context.checkForWin();
						return;
					}
					
					//Up check
					if(i - 1 >= 0 && boxes[i - 1][j].getNumber() == 0)
					{
						int tempValue = boxes[i][j].getNumber();
						boxes[i][j].setNumber(boxes[i - 1][j].getNumber());
						boxes[i - 1][j].setNumber(tempValue);
						context.swap(i, j, i - 1, j);
						context.checkForWin();
						return;
					}
					
					//Down check
					if(i + 1 < numRows && boxes[i + 1][j].getNumber() == 0)
					{
						int tempValue = boxes[i][j].getNumber();
						boxes[i][j].setNumber(boxes[i + 1][j].getNumber());
						boxes[i + 1][j].setNumber(tempValue);
						context.swap(i, j, i + 1, j);
						context.checkForWin();
						return;
					}
					
					return;
				}
			}
		}
	}
	
	/**
	 * Method that updates the PlayState
	 */
	public void update()
	{
		updateButtons();
		if(context.isGameOver())
		{
			return;
		}
		updateBoxes();
	}
	
////////////////////////////////////////////// DRAW METHODS //////////////////////////////////////////////
	
	/**
	 * Method that draws the background
	 * @param g (Graphics2D g) The Graphics2D object to be drawn on
	 */
	private void drawBackground(Graphics2D g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	}
	
	/**
	 * Method that draws the buttons
	 * @param g (Graphics2D g) The Graphics2D object to be drawn on
	 */
	private void drawButtons(Graphics2D g)
	{
		newGameButton.draw(g);
	}
	
	/**
	 * Method that draws the texts
	 * @param g (Graphics2D g) The Graphics2D object to be drawn on
	 */
	private void drawTexts(Graphics2D g)
	{
		if(context.isGameOver())
		{
			solvedText.draw(g);
		}
	}
	
	/**
	 * Method that draws the boxes of slidng puzzle
	 * @param g (Graphics2D g) The Graphics2D object to be drawn on
	 */
	private void drawBoxes(Graphics2D g)
	{
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				boxes[i][j].draw(g);
			}
		}
	}
	
	/**
	 * Method that draws the PlayState
	 * @param g (Graphics2D g) The Graphics2D object to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		drawBackground(g);
		drawButtons(g);
		drawTexts(g);
		drawBoxes(g);
	}
}
