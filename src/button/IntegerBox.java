package button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import text.Text;

/**
 * IntegerBox class represents a box/tile that contains an integer
 * @author Vachia Thoj
 *
 */
public class IntegerBox extends Button
{	
	//Possible colors of IntegerBox
	private Color color1;
	private Color color2;
	
	//The current color of IntegerBox
	private Color currentColor;
	
	//The border color of IntegerBox
	private Color borderColor;
	
	//Flag to see if IntegerBox has a border
	private boolean bordered;
	
	//The number within IntegerBox
	private int number;
	
	//Number represented as an image
	private Text numberImage;
	
	//Static variables
	private static final Color DEFAULT_COLOR1 = Color.WHITE;
	private static final Color DEFAULT_COLOR2 = new Color(235, 235, 235);
	private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
	
	/**
	 * Constructor
	 * @param x (int) x-coordinate of IntegerBox
	 * @param y (int) y-coordinate of IntegerBox
	 * @param width (int) width of the IntegerBox
	 * @param height (int) height of the the IntegerBox
	 * @param number (int) A number value for the IntegerBox
	 */
	public IntegerBox(int x, int y, int width, int height, int number)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.number = number;
		
		this.color1 = DEFAULT_COLOR1;
		this.color2 = DEFAULT_COLOR2;
		this.currentColor = color1;
		
		this.borderColor = DEFAULT_BORDER_COLOR;
		this.bordered = true;
		
		this.visible = true;
		this.disabled = false;
		
		createNumberImage();
	}
	
	private void createNumberImage()
	{
		this.numberImage = new Text(String.valueOf(number));
		this.numberImage.changeScale(5);
		this.numberImage.setX(((x + x + width) / 2) - (numberImage.getWidth() / 2));
		this.numberImage.setY(((y + y + height) / 2) - (numberImage.getHeight() / 2));
	}
	
	//Getter methods
	public Color getColor1() {return color1;}
	public Color getColor2() {return color2;}
	public Color getCurrentColor() {return currentColor;}
	public Color getBorderColor() {return borderColor;}
	public boolean isBordered() {return bordered;}
	public int getNumber() {return number;}
	
	//Setter methods
	public void setColor1(Color color1) {this.color1 = color1;}
	public void setColor2(Color color2) {this.color2 = color2;}
	public void setCurrentColor(Color currentColor) {this.currentColor = currentColor;}
	public void setBorderColor(Color borderColor) {this.borderColor = borderColor;}
	public void setBordered(boolean b) {this.bordered = b;}
	
	public void setNumber(int number)
	{
		this.number = number;
		createNumberImage();
	}

	/**
	 * Method that updates IntegerBox
	 */
	public void update()
	{
		if(disabled == true || visible == false)
		{
			return;
		}
		
		//Check if mouse has touched IntegerBox
		checkIfMouseTouchingButton();
		
		//Check if mouse has clicked on IntegerBox
		checkIfMouseClickingButton();
		
		//Change color of IntegerBox if mouse is touching IntegerBox
		if(mouseTouchingButton)
		{
			currentColor = color2;
		}
		else
		{
			currentColor = color1;
		}
	}
	
	/**
	 * Method that draws the box
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawBox(Graphics2D g)
	{
		g.setColor(currentColor);
		g.fillRect(x, y, width, height);
	}
	
	/**
	 * Method that draws the border
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawBorder(Graphics2D g)
	{
		if(bordered)
		{
			g.setColor(borderColor);
			g.setStroke(new BasicStroke(2));
			g.drawRect(x, y, width, height);
			
			g.setStroke(new BasicStroke(1));
		}
	}
	/**
	 * Method that draws numberImage
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawNumberImage(Graphics2D g)
	{
		if(numberImage != null && number != 0)
		{
			numberImage.draw(g);
		}
	}
	
	/**
	 * Method that draws IntegerBox
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		if(visible)
		{
			drawBox(g);
			drawBorder(g);
			drawNumberImage(g);
		}
	}
}
