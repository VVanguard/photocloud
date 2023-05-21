package util.filters;

import java.awt.image.BufferedImage;

import util.image.ImageMatrix;

public class Grayscale {

	
	/**
	 * Grayscales an image
	 * 
	 * @param image		image to grayscale
	 * @param ratio		weigth of the filter
	 * 
	 * @return			returns a new buffered image
	 */
	public static BufferedImage GrayscaleImage(BufferedImage image, double ratio) {
		
		ImageMatrix imageMatrix = new ImageMatrix(image);
		int initialImageArray[][] = imageMatrix.getImgArray(); 
		int finalImageArray[][] = initialImageArray.clone();

		// Horizontal Iteration of the kernel
		for (int widthId = 0; widthId < imageMatrix.getWidth(); widthId++) {
			// Vertical Iteration of the kernel
			for (int heightId = 0; heightId < imageMatrix.getHeight(); heightId++) {

				double red = ImageMatrix.getRed(initialImageArray, widthId, heightId);
				double green = ImageMatrix.getBlue(initialImageArray, widthId, heightId);
				double blue = ImageMatrix.getGreen(initialImageArray, widthId, heightId);
				
				// Grayscale pixel and weighted value
				int grascalePixel = (int) ((red + green + blue) / 3 * (1 + (101 - ratio) / 200));
				
				// Calculate and insert the RGB value of the kernel's center
				finalImageArray[widthId][heightId] = ImageMatrix.convertRGB(grascalePixel, grascalePixel, grascalePixel);
			}
		}
		
		imageMatrix.setImgArray(finalImageArray);
		return imageMatrix.getBufferedImage();
	}
}
