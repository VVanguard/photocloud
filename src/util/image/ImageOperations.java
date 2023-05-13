package util.image;

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

	
	/**
	 * Resize image by X
	 * 
	 * @param image
	 * @param pixelX
	 * @return
	 */
	public static Image ResizeImage(Image image, int pixelX, int pixelY) {
		Image newImg = image.getScaledInstance(pixelX, pixelY, Image.SCALE_SMOOTH);
		return newImg;
	}
	
	
	/**
	 * Reads a Buffered Image from the user
	 * 
	 * @param imagePath
	 * @return new buffered image
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
	 * @param extension		extension of the image
	 * 
	 * @return 				returns image path to save in user details
	 * 
	 * @throws IOException
	 */
	public static void saveImageToResources(BufferedImage img, String saveName) throws IOException {
		ImageIO.write(img, "jpg", new File("resources/pictures/" + saveName + ".jpg"));
	}
	
}
