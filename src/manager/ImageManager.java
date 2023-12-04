package manager;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * ImageManager class attempts to load image files and keeps track 
 * of them
 * 
 * @author Vachia Thoj
 *
 */
public class ImageManager 
{
	//For singleton
	private static ImageManager imageManager;
	
	//BufferedImages
	private BufferedImage[] asciiImages;
	
	/**
	 * Constructor (private)
	 */
	private ImageManager()
	{
		BufferedImage asciiSheet = loadImage("/images/AsciiSheet.png");
		
		loadAsciiImages(asciiSheet);
	}

	/**
	 * Method to be called to obtain ImageManager object (Singleton)
	 * @return ImageManager object 
	 */
	public static ImageManager instance()
	{
		if(imageManager == null)
		{
			imageManager = new ImageManager();
		}
		
		return imageManager;
	}
	
	/**
	 * Method that attempts to open/obtain an image
	 * 
	 * @param address (String) the address location of the image
	 * @return BufferedImage from the address provided, will return null if image cannot be found opened
	 */
	private BufferedImage loadImage(String address)
	{
		BufferedImage imageSheet = null;
		
		//Try to obtain an image
		try {
			//Obtain image from address
			imageSheet = ImageIO.read(getClass().getResourceAsStream(address));
			
			return imageSheet;
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading image");
			System.exit(0);
		}
		return null;
	}
	
	private void loadAsciiImages(BufferedImage sheet)
	{
		asciiImages = new BufferedImage[43];
		
		int index = 0;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 13; j++)
			{
				if(index == 0)
				{
					asciiImages[index] = sheet.getSubimage(j * 6, i * 9, 4, 9);
				}
				else
				{
					asciiImages[index] = sheet.getSubimage(j * 6, i * 9, 6, 9);
				}
				
				++index;
			}
		}
		
		for(int i = 0; i < 4; i++)
		{
			asciiImages[index] = sheet.getSubimage(i * 6, 27, 6, 9);
			++index;
		}	
	}
	
	public BufferedImage[] getAsciiImages() {return asciiImages;}
	
	public BufferedImage getAsciiImage(int index) 
	{
		if(index < 0 || index > 42)
		{
			return null;
		}
		
		return asciiImages[index];
	}
}
