package util.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class EdgeDetection {

	
	/**
	 * Creates an edge-detected image
	 * 
	 * @param image		image to detect edges
	 * 
	 * @return			returns a new Buffered Image
	 */
	public static BufferedImage detectEdges(BufferedImage image) {
		BufferedImage bfBlurredImage = Blur.BlurImage(image, 40);
		BufferedImage bfGrayscaledImage = Grayscale.GrayscaleImage(bfBlurredImage, 40);
		return Blur.BlurImage(applySobelKernels(bfGrayscaledImage), 0);
	}
	
	
	/**
	 * Applies Sobel Kernel to the image
	 * 
	 * @param image		image to apply Sobel Kernel
	 * 
	 * @return			returns a new Buffered Image
	 */
	private static BufferedImage applySobelKernels(BufferedImage image) {
		
		int width = image.getWidth();
        int height = image.getHeight();
        
        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Sobel Kernels
        int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        // Run the Sobel kernel to generate resolved direction vectors 
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
            	
            	// Calculate Gradient X 
                int G_x = (
                    (image.getRGB(x - 1, y - 1) & 0xFF) * sobelX[0][0] +
                    (image.getRGB(x, y - 1) & 0xFF) * sobelX[0][1] +
                    (image.getRGB(x + 1, y - 1) & 0xFF) * sobelX[0][2] +
                    (image.getRGB(x - 1, y + 1) & 0xFF) * sobelX[2][0] +
                    (image.getRGB(x, y + 1) & 0xFF) * sobelX[2][1] +
                    (image.getRGB(x + 1, y + 1) & 0xFF) * sobelX[2][2]
                );

                // Calculate Gradient Y
                int G_y = (
                    (image.getRGB(x - 1, y - 1) & 0xFF) * sobelY[0][0] +
                    (image.getRGB(x - 1, y) & 0xFF) * sobelY[1][0] +
                    (image.getRGB(x - 1, y + 1) & 0xFF) * sobelY[2][0] +
                    (image.getRGB(x + 1, y - 1) & 0xFF) * sobelY[0][2] +
                    (image.getRGB(x + 1, y) & 0xFF) * sobelY[1][2] +
                    (image.getRGB(x + 1, y + 1) & 0xFF) * sobelY[2][2]
                );

                // Calculate effective directional vector - limit it between 0-255
                int directionVector = ((int) Math.sqrt(G_x * G_x + G_y * G_y) > 255) ? 255 : ((int) Math.sqrt(G_x * G_x + G_y * G_y) < 0) ? 0 : (int) Math.sqrt(G_x * G_x + G_y * G_y);
                
                
                // Set Magnitude as the Color
                finalImage.setRGB(x, y, new Color(directionVector, directionVector, directionVector).getRGB());
            }
        }

        return finalImage;
	}
}
