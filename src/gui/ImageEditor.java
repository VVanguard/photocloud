package gui;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import baselogger.BaseLogger;
import user.User;
import user.UserTiers;
import util.Colors;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customcomponents.RoundedJButton;
import util.customcomponents.RoundedJTextField;
import util.customframes.FrameFactory;
import util.image.ImageOperations;


public class ImageEditor extends FrameFactory {

	// User 
	private User user;
	
	// Loggers
	private BaseLogger baseLogger = new BaseLogger();
	
	// Components
	private JLabel lblImage;
	private RoundedJButton btnBlur;
	private RoundedJButton btnSharpen;
	private RoundedJButton btnBrightness;
	private RoundedJButton btnContrast;
	private RoundedJButton btnEdgeDetection;
	private RoundedJButton btnGrayscale;
	private RoundedJTextField txtFilterWeight;
	
	// Configurations
	ComponentConfiguration lblImageConfiguration = ComponentGenerator.generateCenteredLabel(
			"", null, new Insets(10, 10, 10, 10), 3, 1);
	
	ComponentConfiguration btnBlurConfiguration = ComponentGenerator.generateRoundedButton(
			btnBlur, 15, "Blur", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(5, 20, 5, 5), 1, 3);
	
	ComponentConfiguration btnSharpenConfiguration = ComponentGenerator.generateRoundedButton(
			btnSharpen, 15, "Sharpen", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(5, 20, 5, 5), 1, 4);
	
	ComponentConfiguration btnBrightnessConfiguration = ComponentGenerator.generateRoundedButton(
			btnBrightness, 15, "Brightness", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(5, 20, 5, 5), 1, 6);
	
	ComponentConfiguration btnContrastConfiguration = ComponentGenerator.generateRoundedButton(
			btnContrast, 15, "Contrast", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(5, 20, 5, 5), 1, 7);
	
	ComponentConfiguration btnEdgeDetectionConfiguration = ComponentGenerator.generateRoundedButton(
			btnEdgeDetection, 15, "Edge Detection", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(5, 20, 5, 5), 1, 9);
	
	ComponentConfiguration btnGrayscaleConfiguration = ComponentGenerator.generateRoundedButton(
			btnGrayscale, 15, "Grayscale", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(5, 20, 5, 5), 1, 10);
	
	ComponentConfiguration txtFilterWeightConfiguration = ComponentGenerator.generateRoundedTextField(
			txtFilterWeight, 10, Colors.BROKEN_WHITE, new Insets(5, 50, 0, 50), 1, 12);
	
	
	// Image
	private BufferedImage bfImg;
	private BufferedImage bfImgScaled;
	
	
	// Dummy Constructor
	public ImageEditor() {
		super(1550, 950, Type.POPUP);
		setFrameStatus(FrameStatus.HIDE);
	}
	
	
	/**
	 * Initialize Pane
	 * 
	 * @param imgPath 	path of the image to be edited
	 */
	public ImageEditor(String imgPath, User userEdit) {
		super(1440, 980, Type.POPUP);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{10, 120, 10, 1000, 100, 100, 100, 10};
		gbl_contentPane.rowHeights = new int[]{25, 260, 25, 50, 50, 25, 50, 50, 25, 50, 50, 50, 10, 200, 50};
		setBackground(Colors.GHOST_WHITE);
		
		contentPane.setLayout(gbl_contentPane);
		
		// Initialize Components
		initializeComponents(contentPane);
		
		// Create Buffered Images
		try {
			bfImg = ImageOperations.toBufferedImage(ImageOperations.readNewImageFromUser(imgPath));
			System.out.println(ImageOperations.readNewImageFromUser(imgPath).getHeight());
			System.out.println(ImageOperations.readNewImageFromUser(imgPath).getWidth());
			bfImgScaled = ImageOperations.scaleForDisplay(ImageOperations.readNewImageFromUser(imgPath), 1300, 900);
		} catch (IOException e) {
			baseLogger.error().log("Failed to read Image: " + imgPath);
		}
		
		System.out.println(bfImgScaled.getHeight());
		System.out.println(bfImgScaled.getWidth());
		
		// Set the frame visible
		lblImage.setIcon(new ImageIcon(bfImgScaled));
		setFrameStatus(FrameStatus.VISIBLE);
		
		// Declare user
		setUser(userEdit);
		
		/*
		 * Action Listeners
		 * 
		 */
		if (user.getTier() == UserTiers.PROFESSIONAL) {
			btnEdgeDetection.setBackground(Colors.BRUNSWICK_GREEN);
			btnGrayscale.setBackground(Colors.BRUNSWICK_GREEN);
		} else if (user.getTier() == UserTiers.HOBBYIST) {
			btnBrightness.setBackground(Colors.BRUNSWICK_GREEN);
			btnContrast.setBackground(Colors.BRUNSWICK_GREEN);
		}
	}


	/**
	 * Initialize Components
	 */
	@Override
	protected void initializeComponents(JPanel jPanel) {
		
		// Image Label
		lblImage = (JLabel)lblImageConfiguration.getComponent();
		lblImageConfiguration.getGridBagConstraints().gridwidth = 4;
		lblImageConfiguration.getGridBagConstraints().gridheight = 13;
		addComponent(jPanel, lblImageConfiguration);
		
		//Buttons
		btnBlur = (RoundedJButton)btnBlurConfiguration.getComponent();
		addComponent(jPanel, btnBlurConfiguration);
		
		btnSharpen = (RoundedJButton)btnSharpenConfiguration.getComponent();
		addComponent(jPanel, btnSharpenConfiguration);
		
		btnBrightness = (RoundedJButton)btnBrightnessConfiguration.getComponent();
		addComponent(jPanel, btnBrightnessConfiguration);
		
		btnContrast = (RoundedJButton)btnContrastConfiguration.getComponent();
		addComponent(jPanel, btnContrastConfiguration);
		
		btnEdgeDetection = (RoundedJButton)btnEdgeDetectionConfiguration.getComponent();
		addComponent(jPanel, btnEdgeDetectionConfiguration);
		
		btnGrayscale = (RoundedJButton)btnGrayscaleConfiguration.getComponent();
		addComponent(jPanel, btnGrayscaleConfiguration);
		
		// Weight for Edits
		txtFilterWeight = (RoundedJTextField)txtFilterWeightConfiguration.getComponent();
		addComponent(jPanel, txtFilterWeightConfiguration);
		
		// Weight Label
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Filter Weight", new Font("Arial", Font.BOLD, 12), new Insets(40, 0, 0, 0), 1, 11));
		// Tier Labels
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"FREE", new Font("Arial", Font.BOLD, 12), new Insets(20, 20, 0, 0), 1, 2));
		
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"HOBBYIST", new Font("Arial", Font.BOLD, 12), new Insets(20, 20, 0, 0), 1, 5));
		
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"PROFESSIONAL", new Font("Arial", Font.BOLD, 12), new Insets(20, 20, 0, 0), 1, 8));
	}
	
	
	/*
	 * Getters & Setters 
	 * 
	 */
	private void setUser(User user) {
		this.user = user;
	}
}
