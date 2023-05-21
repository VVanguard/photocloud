package util.filters;

import java.awt.image.BufferedImage;

import util.image.ImageMatrix;

public class Contrast {

	
	/**
	 * Increases the contrast of an image
	 * 
	 * @param image		image to increase the contrast
	 * @param rate		weight of the operation
	 * 
	 * @return			returns a new Buffered Image
	 */
	public static BufferedImage changeContrast(BufferedImage image, double rate) {
		
		ImageMatrix imageMatrix = new ImageMatrix(image);
		
		int initialImageArray[][] = imageMatrix.getImgArray(); 
		int finalImageArray[][] = initialImageArray.clone();
		
		double contrastWeight;
		
		
		
		// Horizontal Iteration of the kernel
		for (int widthId = 0; widthId < imageMatrix.getWidth(); widthId++) {
			// Vertical Iteration of the kernel
			for (int heightId = 0; heightId < imageMatrix.getHeight(); heightId++) {
				
				int red = ImageMatrix.getRed(initialImageArray, widthId, heightId);
				int green = ImageMatrix.getGreen(initialImageArray, widthId, heightId);
				int blue = ImageMatrix.getBlue(initialImageArray, widthId, heightId);
				
				// Weights for the luminance of a pixel
				double luminance = (0.2126*red + 0.7152*green + 0.0722*blue);
				
				// Adjust the weight to make light lighter, dark darker
				if (luminance >= 127) {
					contrastWeight = (1 + rate / 600);
				} else {
					contrastWeight  = (1 - rate / 600);
				}

				// Calculate and insert the RGB value with brightness weight
				finalImageArray[widthId][heightId] = ImageMatrix.convertRGB(
						(int)(ImageMatrix.getRed(initialImageArray, widthId, heightId) * contrastWeight), 
						(int)(ImageMatrix.getGreen(initialImageArray, widthId, heightId) * contrastWeight), 
						(int)(ImageMatrix.getBlue(initialImageArray, widthId, heightId) * contrastWeight)
						);
			}
		}
		
		imageMatrix.setImgArray(finalImageArray);
		return imageMatrix.getBufferedImage();
	}
}
