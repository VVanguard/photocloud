package util.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Basic Image operations class
 * 
 * @author bkaym
 *
 */
public class ImageOperations {

	public static BufferedImage toBufferedImage(Image img) throws NullPointerException{
		// Create a buffered image with RGB values
	    BufferedImage newBuffImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the graphics of the image into the buffered image
	    Graphics2D buff_grraphics = newBuffImg.createGraphics();
	    buff_grraphics.drawImage(img, 0, 0, null);
	    buff_grraphics.dispose();

	    return newBuffImg;
	}
	
	/**
	 * Resize image by pixels
	 * 
	 * @param image		image to resize
	 * @param pixelX	resize pixel X
	 * @param pixelY	resize pixel Y
	 * 
	 * @return			returns resized image
	 */
	public static BufferedImage ResizeImage(Image image, int pixelX, int pixelY) throws IOException{
		BufferedImage newImg = toBufferedImage(image.getScaledInstance(pixelX, pixelY, Image.SCALE_SMOOTH));
		return newImg;
	}
	
	
	/**
	 * Scale an image for the display panel
	 * 
	 * @param image			image to scale
	 * 
	 * @param widthMax		max width of the screen
	 * @param heightMax		max height of the screen
	 * @return				buffered image
	 * @throws IOException 
	 */
	public static BufferedImage scaleForDisplay(Image image, int widthMax, int heightMax) throws IOException {
		double ratio = Double.valueOf(widthMax) / Double.valueOf(heightMax);
		Image scaledImg;		
		
		// Check the ratio between the current image
		// If the width of the image is larger than the height by a ratio, scale it by width
		if (image.getWidth(null) / ratio >= image.getHeight(null)) {
			double heightCalculated = Double.valueOf(widthMax) / Double.valueOf(image.getWidth(null)) * image.getHeight(null);
			scaledImg = image.getScaledInstance(widthMax, (int)heightCalculated, Image.SCALE_SMOOTH);
		} 
		
		// Else scale it by height
		else {
			double widthCalculated = Double.valueOf(heightMax) / Double.valueOf(image.getHeight(null)) * image.getWidth(null);
			scaledImg = image.getScaledInstance((int)widthCalculated, heightMax, Image.SCALE_SMOOTH);
		}
		
		return toBufferedImage(scaledImg);
	}
	
	
	/**
	 * Crops an image to center
	 * 
	 * @param image		image to crop
	 * 
	 * @return			returns center-cropped image
	 */
	public static BufferedImage cropCenterSquare(BufferedImage image) {
		
		int height = image.getHeight();
		int width = image.getWidth();
		
		if (height > width) {
			int insetY = (int)(height - width) / 2; 
			return image.getSubimage(0, insetY, width, width);
		}
		
		else if (height < width) {
			int insetX = (int)(width - height) / 2; 
			return image.getSubimage(insetX, 0, height, height);
		}
		
		else {
			return image;
		}
	}
	
	
	/**
	 * Resizes and Crops an image to center
	 * 
	 * @param image		image to resize and crop
	 * @param pixel		dimension of a centered image
	 * 
	 * @return
	 */
	public static BufferedImage resizeSquare(BufferedImage image, int pixel) throws IOException{
		return ResizeImage(cropCenterSquare(image), pixel, pixel);
	}
	
	
	
	/**
	 * Reads a Buffered Image from the user
	 * 
	 * @param imagePath
	 * 
	 * @return new buffered image
	 * 
	 * @throws IOException
	 */
	public static BufferedImage readNewImageFromUser(String imagePath) throws IOException {
		return ImageIO.read(new File(imagePath));
	}
	

	/**
	 * Saves a new image to resources folder
	 * 
	 * @param image			image to save
	 * @param saveName		image name
	 * 
	 * @throws IOException
	 */
	public static void saveImageToResources(BufferedImage img, String saveName) throws IOException {
		ImageIO.write(img, "jpg", new File("resources/pictures/" + saveName + ".jpg"));
	}
	
	
	/**
	 * Replaces the profile image of the user in resources
	 * 
	 * @param img			image that will replace
	 * @param saveName		image name
	 * 
	 * @throws IOException
	 */
	public static void updateImageInResources(BufferedImage img, String saveName) throws IOException {
		try {
			File oldProfilePicture = new File("resources/pictures/" + saveName + ".jpg");
			oldProfilePicture.delete();
		} catch (Exception e) {} finally {
			saveImageToResources(img, saveName);
		}
	}
	
}
