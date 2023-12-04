package button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import text.Text;

/**
 * NameButton represents a Button with a name
 * @author Vachia Thoj
 *
 */
public class NameButton extends Button
{	
	//Possible colors of NameButton
	private Color color1;
	private Color color2;
	
	//The current Color of NameButton 
	private Color currentColor;
	
	//The border color of NameButton
	private Color borderColor;
	
	//Flag to see if NameButton has a border
	private boolean bordered;
	
	//The name of NameButton
	private String name;
	
	//The name of NameButton has an image
	private Text nameImage;
	
	//Static variables
	private static final Color DEFAULT_COLOR1 = Color.WHITE;
	private static final Color DEFAULT_COLOR2 = new Color(235, 235, 235);
	private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
	
	/**
	 * Constructor
	 * @param x (int) x-coordinate of NameButton
	 * @param y (int) y-coordinate of NameButton
	 * @param width (int) width of the NameButton
	 * @param height (int) height of the the NameButton
	 * @param number (int) a name for the NameButton
	 */
	public NameButton(int x, int y, int width, int height, String name)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
		
		this.color1 = DEFAULT_COLOR1;
		this.color2 = DEFAULT_COLOR2;
		this.currentColor = color1;
		
		this.borderColor = DEFAULT_BORDER_COLOR;
		this.bordered = true;
		
		this.visible = true;
		this.disabled = false;
		
		createNameImage();
	}
	
	private void createNameImage()
	{
		this.nameImage = new Text(name);
		this.nameImage.changeScale(3);
		this.nameImage.setX(((x + x + width) / 2) - (nameImage.getWidth() / 2));
		this.nameImage.setY(((y + y + height) / 2) - (nameImage.getHeight() / 2));
	}
	
	//Getter methods
	public Color getColor1() {return color1;}
	public Color getColor2() {return color2;}
	public Color getCurrentColor() {return currentColor;}
	public Color getBorderColor() {return borderColor;}
	public boolean isBordered() {return bordered;}
	public String getName() {return name;}
	
	//Setter methods
	public void setColor1(Color color1) {this.color1 = color1;}
	public void setColor2(Color color2) {this.color2 = color2;}
	public void setCurrentColor(Color currentColor) {this.currentColor = currentColor;}
	public void setBorderColor(Color borderColor) {this.borderColor = borderColor;}
	public void setBordered(boolean b) {this.bordered = b;}
	
	public void setName(String name)
	{
		this.name = name;
		createNameImage();
	}
	
	/**
	 * Method that updates NameButton
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
	 * Method that draws the button
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawButton(Graphics2D g)
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
	 * Method that draws the nameImage
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawNameImage(Graphics2D g)
	{
		if(nameImage != null)
		{
			nameImage.draw(g);
		}
	}
	
	/**
	 * Method that draws NameButton
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		if(visible)
		{
			drawButton(g);
			drawBorder(g);
			drawNameImage(g);
		}
	}
}