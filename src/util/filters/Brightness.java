package util.filters;

import java.awt.image.BufferedImage;

import util.image.ImageMatrix;

public class Brightness {

	
	/**
	 * Brightens an image
	 * 
	 * @param image		image to brighten
	 * @param rate		weight of the operation
	 * 
	 * @return			returns a new Buffered Image
	 */
	public static BufferedImage BrightenImage(BufferedImage image, double rate) {
		
		ImageMatrix imageMatrix = new ImageMatrix(image);
	
		int initialImageArray[][] = imageMatrix.getImgArray(); 
		int finalImageArray[][] = initialImageArray.clone();
		
		int brightnessWeigth = (int) rate / 2;
		
		
		
		// Horizontal Iteration of the kernel
		for (int widthId = 0; widthId < imageMatrix.getWidth(); widthId++) {
			// Vertical Iteration of the kernel
			for (int heightId = 0; heightId < imageMatrix.getHeight(); heightId++) {

				// Calculate and insert the RGB value with brightness weight
				finalImageArray[widthId][heightId] = ImageMatrix.convertRGB(
						ImageMatrix.getRed(initialImageArray, widthId, heightId) + brightnessWeigth, 
						ImageMatrix.getGreen(initialImageArray, widthId, heightId) + brightnessWeigth, 
						ImageMatrix.getBlue(initialImageArray, widthId, heightId) + brightnessWeigth
						);
			}
		}
		
		imageMatrix.setImgArray(finalImageArray);
		return imageMatrix.getBufferedImage();
	
	}
}
