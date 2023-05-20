package util.filters;

import java.awt.image.BufferedImage;

import util.image.ImageMatrix;

public class Blur {

	
	/**
	 * Blurs the image
	 * 
	 * @param image		image to blur
	 * @param rate		filter weight in double
	 * 
	 * @return			returns a new buffered image
	 */
	public static BufferedImage BlurImage(BufferedImage image, double rate) {
		
		ImageMatrix imageMatrix = new ImageMatrix(image);
		int initialImageArray[][] = imageMatrix.getImgArray(); 
		int finalImageArray[][] = initialImageArray.clone();

		System.out.println(finalImageArray);

		int kernelSize = 2 * (int)Math.ceil(rate / 20) + 1;

		// Horizontal Iteration of the kernel
		for (int widthId = 0; widthId < imageMatrix.getWidth(); widthId++) {
			// Vertical Iteration of the kernel
			for (int heightId = 0; heightId < imageMatrix.getHeight(); heightId++) {

				// Calculate and insert the RGB value of the kernel's center
				finalImageArray[widthId][heightId] = calculateKernelAverage(initialImageArray, widthId, heightId, kernelSize);
			}
		}

		// Return the buffered image
		imageMatrix.setImgArray(finalImageArray);
		return imageMatrix.getBufferedImage();
	}
	
	
	/**
	 * Calculates the kernel average inside an image array
	 * 
	 * @param imageArray	original image array 
	 * @param x				start pixel x
	 * @param y				start pixel y
	 * @param kernelSize	size of the kernel
	 * 
	 * @return				returns the average of the kernel
	 */
	private static int calculateKernelAverage(int[][] imageArray, int x, int y, int kernelSize) {
		
		int totalR = 0;
		int totalG = 0;
		int totalB = 0;
		int count = 0;

		// Horizontal iteration in the kernel
		for (int widthId = 0; widthId < kernelSize; widthId++) {
			// Vertical iteration in the kernel
			for (int heightId = 0; heightId < kernelSize; heightId++) {
				try {
					totalR += ImageMatrix.getRed(imageArray, x - (kernelSize-1)/2 + widthId, y - (kernelSize-1)/2 + heightId);
					totalG += ImageMatrix.getGreen(imageArray, x - (kernelSize-1)/2 + widthId, y - (kernelSize-1)/2 + heightId);
					totalB += ImageMatrix.getBlue(imageArray, x - (kernelSize-1)/2 + widthId, y - (kernelSize-1)/2 + heightId);
					count++;
				} catch (Exception e) {
					// No Accessible Pixel: Do Nothing
				}
			}
		}

		return ImageMatrix.convertRGB(totalR / count, totalG / count, totalB / count);
	}
}


