package util.filters;

import java.awt.image.BufferedImage;

import util.image.ImageMatrix;

public class Sharpen {
	
	
	/**
	 * Sharpens and image by a standard 5x5 box blur.
	 * 
	 * @param image		image to sharpen
	 * 
	 * @return			returns a buffered image
	 */
	public static BufferedImage SharpenImage(BufferedImage image, double rate) {
		
		ImageMatrix imageMatrix = new ImageMatrix(image);
		ImageMatrix blurredImage = new ImageMatrix(Blur.BlurImage(image, rate));
		
		// Sharpened = Original + (Original - Blurred)
		int[][] sharpenedImageArray = addPixelArrays(
				imageMatrix.getImgArray(), 
				substractPixelArrays(
						imageMatrix.getImgArray(), 
						blurredImage.getImgArray()
						)
				);
		
		imageMatrix.setImgArray(sharpenedImageArray);
		
		return imageMatrix.getBufferedImage();
	}
	
	
	/**
	 * Subtracts pixels from two different arrays
	 * 
	 * @param originalImageArray	original image array
	 * @param blurredImageArray		blurred image array that will be subtracted from the original
	 * 
	 * @return						returns a new image array
	 */
	private static int[][] substractPixelArrays(int[][] originalImageArray, int[][] blurredImageArray) {
		
		int[][] finalImageArray = new int[originalImageArray.length][originalImageArray[0].length];
		
		// Horizontal Iteration of the kernel
		for (int widthId = 0; widthId < originalImageArray.length; widthId++) {
			// Vertical Iteration of the kernel
			for (int heightId = 0; heightId < originalImageArray[0].length; heightId++) {
				// Do Subtraction
				finalImageArray[widthId][heightId] = ImageMatrix.convertRGB(
						Math.abs(ImageMatrix.getRed(originalImageArray, widthId, heightId) - ImageMatrix.getRed(blurredImageArray, widthId, heightId)), 
						Math.abs(ImageMatrix.getGreen(originalImageArray, widthId, heightId) - ImageMatrix.getGreen(blurredImageArray, widthId, heightId)), 
						Math.abs(ImageMatrix.getBlue(originalImageArray, widthId, heightId) - ImageMatrix.getBlue(blurredImageArray, widthId, heightId)));	
			}
		}
		
		return finalImageArray;
	}
	
	
	/**
	 * Adds pixels from two different arrays
	 * 
	 * @param originalImageArray	original image array
	 * @param detailedPixelArray	subtracted image array that will be added to the original
	 * 
	 * @return						returns a new image array
	 */
	private static int[][] addPixelArrays(int[][] originalImageArray, int[][] detailedPixelArray) {
		
		int[][] finalImageArray = new int[originalImageArray.length][originalImageArray[0].length];
		
		// Horizontal Iteration of the kernel
		for (int widthId = 0; widthId < originalImageArray.length; widthId++) {
			// Vertical Iteration of the kernel
			for (int heightId = 0; heightId < originalImageArray[0].length; heightId++) {
				// Do Subtraction
				finalImageArray[widthId][heightId] = ImageMatrix.convertRGB(
						ImageMatrix.getRed(originalImageArray, widthId, heightId) + ImageMatrix.getRed(detailedPixelArray, widthId, heightId), 
						ImageMatrix.getGreen(originalImageArray, widthId, heightId) + ImageMatrix.getGreen(detailedPixelArray, widthId, heightId), 
						ImageMatrix.getBlue(originalImageArray, widthId, heightId) + ImageMatrix.getBlue(detailedPixelArray, widthId, heightId));	
			}
		}
		
		return finalImageArray;
	}
}
