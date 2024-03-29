package util.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import image.ImageEnum;
import image.PhotocloudImage;

/**
 * Basic Image operations class
 * 
 * @author bkaym
 *
 */
public class ImageOperations {

	
	/**
	 * Converts image to Buffered Image
	 * 
	 * @param img	Image
	 * 
	 * @return		new buffered image
	 * 
	 * @throws NullPointerException
	 */
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
	 * @return			returns a new buffered image
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
	
	
	/**
	 * Writes picture data to resources
	 * 
	 * @param username		username of the user
	 * @param imgPath		imgPath
	 * @param thumbnail		thumbnail of the image
	 * @param caption		caption of the image
	 * @param comments		comments HashMap<username, comment>
	 * @param imageEnum		image enum: public, private, null
	 * 
	 * @throws IOException
	 */
	public static void writePictureData(PhotocloudImage pImage, boolean isVisibilityChanged) throws IOException {

		// Picture Location
		File userPictureFile = new File("resources/users/" + pImage.getUsername() + "/picturedata/" + pImage.getImageUUID() + ".txt");
		
		// Create image data file
		try {
			userPictureFile.createNewFile();
		} catch (IOException e) {
			throw new IOException("Failed to create image data");
		}
		
		PrintWriter deleteWriter = new PrintWriter(new FileWriter(userPictureFile), false);
		deleteWriter.print("");
		deleteWriter.close();
		
		PrintWriter printWriter = new PrintWriter(userPictureFile);
		
		// Write username imgPath imgEnum
		printWriter.printf("%s %s %s\n", pImage.getUsername(), pImage.getImgPath(), pImage.getImageEnum().toString());
		// Write thumbnail
		printWriter.printf("%s\n", pImage.getThumbnail());
		// Write caption
		printWriter.printf("%s\n", pImage.getCaption());
		
		printWriter.printf("%d %d\n", pImage.getLikeCount(), pImage.getDislikeCount());
		
		//Write Comments
		for (String commentString : pImage.getComments()) {
			// Split username and comment
			String[] commentData = commentString.split(" ", 2);
			printWriter.printf("%s %s\n", commentData[0], commentData[1]);
		}
		
		if (isVisibilityChanged) {
			setImageVisibility(pImage);
		}
	
		printWriter.close();
	}
	
	
	/**
	 * Deletes picture from the user and shared pictures database
	 * 
	 * @param pImage
	 * 
	 * @throws IOException
	 */
	public static void deletePicture(PhotocloudImage pImage) throws IOException {
		
		// Update Database
		updateDatabase(pImage);
		
		// Delete the picture data file
		try {
			pImage.setImageEnum(ImageEnum.NULL);
			
			writePictureData(pImage, false);
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("File cannot be deleted!");
		}
	}
	
	
	/**
	 * Sets image visibility
	 * 
	 * @param pImage
	 * 
	 * @throws IOException
	 */
	public static void setImageVisibility(PhotocloudImage pImage) throws IOException {

		// Set Image Private
		if (pImage.getImageEnum() == ImageEnum.PRIVATE) {
			updateDatabase(pImage);
		}
		
		// Set Image Public
		else if (pImage.getImageEnum() == ImageEnum.PUBLIC) {
			
			ArrayList<String> publicImages = new ArrayList<String>();

			File sharedPictures;
			Scanner fileScanner;

			PrintWriter pw;
	
			// Try initiating writers and scanners through database file
			try {
				// Update Database
				sharedPictures = new File("resources/users/sharedpicture.txt");
				fileScanner = new Scanner(sharedPictures);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IOException("Database not located");
			}

			// Iterate through the picture database
			while (fileScanner.hasNext()) {
				String[] userLine = fileScanner.nextLine().split(" ");
				publicImages.add(String.format("%s %s", userLine[0], userLine[1]));
			}
			
			pw = new PrintWriter(new FileWriter(sharedPictures), false);
			pw.print("");
			pw.close();
			
			pw = new PrintWriter(new FileWriter(sharedPictures), true);
			
			for (String publicImage : publicImages) {
				pw.printf("%s\n", publicImage);
			}
			
			pw.printf("%s\n", pImage.getUsername() + " " + pImage.getImageUUID());
			
			pw.close();
			fileScanner.close();
		}
	}

	
	/**
	 * Updates the public database 
	 * 
	 * @param pImage		pImage to update
	 * @throws IOException 
	 */
	private static void updateDatabase(PhotocloudImage pImage) throws IOException {
		
		ArrayList<String> publicImages = new ArrayList<String>();

		File sharedPictures;
		Scanner fileScanner;

		PrintWriter pw;
		
		

		// Try initiating writers and scanners through database file
		try {
			// Update Database
			sharedPictures = new File("resources/users/sharedpicture.txt");
			fileScanner = new Scanner(sharedPictures);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Database not located");
		}

		// Iterate through the picture database
		while (fileScanner.hasNext()) {
			String[] userLine = fileScanner.nextLine().split(" ");

			// If UUID matches, don't add the line
			if (userLine[1].matches(pImage.getImageUUID())) {
				//Nothing
			} else {
				publicImages.add(String.format("%s %s", userLine[0], userLine[1]));
			}
		}
		
		pw = new PrintWriter(new FileWriter(sharedPictures), false);
		pw.print("");
		pw.close();
		
		pw = new PrintWriter(new FileWriter(sharedPictures), true);
		
		for (String publicImage : publicImages) {
			pw.printf("%s\n", publicImage);
		}
		
		pw.close();
		fileScanner.close();
	}
}
