package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import baselogger.BaseLogger;
import image.ImageEnum;
import image.PhotocloudImage;
import user.User;
import user.UserTiers;
import util.Colors;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customcomponents.RoundedJButton;
import util.customcomponents.RoundedJTextField;
import util.customframes.FrameFactory;
import util.filters.Blur;
import util.filters.Brightness;
import util.filters.Contrast;
import util.filters.EdgeDetection;
import util.filters.FilterType;
import util.filters.Grayscale;
import util.filters.Sharpen;
import util.image.ImageOperations;


public class ImageEditor extends FrameFactory {

	// User 
	private User user;
	
	// Filter Enumerator
	private FilterType filterType = FilterType.NULL;
	
	// Loggers
	private BaseLogger baseLogger = new BaseLogger();
	
	// Components
	private JLabel lblImage;
	private JLabel lblFilter;
	private RoundedJButton btnBlur;
	private RoundedJButton btnSharpen;
	private RoundedJButton btnBrightness;
	private RoundedJButton btnContrast;
	private RoundedJButton btnEdgeDetection;
	private RoundedJButton btnGrayscale;
	private JSlider slider;
	private RoundedJButton btnApplyFilter;
	
	private RoundedJTextField txtThumbnail;
	private RoundedJTextField txtCaption;
	private RoundedJButton btnSaveToDrafts;
	private RoundedJButton btnShare;

	
	// Configurations
	ComponentConfiguration<JLabel> lblImageConfiguration = ComponentGenerator.generateCenteredLabel(
			"", null, new Insets(0, 0, 0, 0), 2, 1);
	
	ComponentConfiguration<JLabel> lblFilterConfiguration = ComponentGenerator.generateCenteredLabel(
			"Select Filter" , new Font("Ariel", Font.BOLD, 14), new Insets(100, 40, 0, 0), 1, 1);
	
	ComponentConfiguration<RoundedJButton> btnBlurConfiguration = ComponentGenerator.generateRoundedButton(
			btnBlur, 15, "Blur", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(5, 20, 5, 5), 1, 3);
	
	ComponentConfiguration<RoundedJButton> btnSharpenConfiguration = ComponentGenerator.generateRoundedButton(
			btnSharpen, 15, "Sharpen", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(5, 20, 5, 5), 1, 4);
	
	ComponentConfiguration<RoundedJButton> btnBrightnessConfiguration = ComponentGenerator.generateRoundedButton(
			btnBrightness, 15, "Brightness", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(5, 20, 5, 5), 1, 6);
	
	ComponentConfiguration<RoundedJButton> btnContrastConfiguration = ComponentGenerator.generateRoundedButton(
			btnContrast, 15, "Contrast", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(5, 20, 5, 5), 1, 7);
	
	ComponentConfiguration<RoundedJButton> btnEdgeDetectionConfiguration = ComponentGenerator.generateRoundedButton(
			btnEdgeDetection, 15, "Edge Detection", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(5, 20, 5, 5), 1, 9);
	
	ComponentConfiguration<RoundedJButton> btnGrayscaleConfiguration = ComponentGenerator.generateRoundedButton(
			btnGrayscale, 15, "Grayscale", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(5, 20, 5, 5), 1, 10);
	
	ComponentConfiguration<RoundedJButton> btnApplyFilterConfiguration = ComponentGenerator.generateRoundedButton(
			btnApplyFilter, 15, "Apply", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(150, 20, 15, 5), 1, 13);
	
	ComponentConfiguration<RoundedJTextField> txtThumbnailConfiguration = ComponentGenerator.generateRoundedTextField(
			txtThumbnail, 25, Colors.BROKEN_WHITE, new Insets(0, 20, 40, 0), 3, 14);
	
	ComponentConfiguration<RoundedJTextField> txtCaptionConfiguration = ComponentGenerator.generateRoundedTextField(
			txtCaption, 25, Colors.BROKEN_WHITE, new Insets(0, 0, 40, 0), 5, 14);
	
	ComponentConfiguration<RoundedJButton> btnSaveToDraftsConfiguration = ComponentGenerator.generateRoundedButton(
			btnSaveToDrafts, 10, "Save To Drafts", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(0, 5, 40, 5), 6, 14);
	
	ComponentConfiguration<RoundedJButton> btnShareConfiguration = ComponentGenerator.generateRoundedButton(
			btnShare, 10, "Share", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(0, 5, 40, 75), 7, 14);
	
	// Images
	private BufferedImage bfImgScaled;

	
	// Dummy Constructor
	public ImageEditor() {
		super(1440, 1000, Type.POPUP);
		setFrameStatus(FrameStatus.HIDE);
	}
	
	
	/**
	 * Initialize Pane
	 * 
	 * @param imgPath 	path of the image to be edited
	 */
	public ImageEditor(String imgPath, User userEdit) {
		super(1450, 1000, Type.POPUP);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{10, 200, 100, 250, 100, 470, 100, 100, 100, 20};
		gbl_contentPane.rowHeights = new int[]{25, 260, 25, 50, 50, 25, 50, 50, 25, 50, 50, 50, 10, 200, 60};
		setBackground(Colors.GHOST_WHITE);
		
		contentPane.setLayout(gbl_contentPane);
		
		// Declare user
		setUser(userEdit);
		
		// Initialize Components
		initializeComponents(contentPane);
		
		// Create Buffered Images
		try {
			GUIContainer.getProfilePage().displayFileError("");
			bfImgScaled = ImageOperations.scaleForDisplay(ImageOperations.readNewImageFromUser(imgPath), 1300, 900);
		} catch (NullPointerException e) {
			baseLogger.error().log("Failed to read Image: " + imgPath);
			GUIContainer.getProfilePage().displayFileError("Cose a jpg!");
		} catch (IOException e) {
			baseLogger.error().log("Failed to read Image: " + imgPath);
			GUIContainer.getProfilePage().displayFileError("Cose a jpg!");
		}
		
		// Set the frame visible
		lblImage.setIcon(new ImageIcon(bfImgScaled));
		setFrameStatus(FrameStatus.VISIBLE);

		
		/*
		 * Action Listeners
		 * 
		 * Filters
		 * 
		 */
		
		// Blur
		btnBlur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { 
				slider.setEnabled(true);
				slider.setValue(1);
				filterType = FilterType.BLUR;	
				lblFilter.setText(filterType.toString());
			}
		});
		
		// Sharpen
		btnSharpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slider.setEnabled(true);
				slider.setValue(1);
				filterType = FilterType.SHARPEN;	
				lblFilter.setText(filterType.toString());
			}
		});
		
		// Tier Based Configuration
		if (user.getTier() == UserTiers.PROFESSIONAL) {
			// Edge Detection
			btnEdgeDetection.setBackground(Colors.BRUNSWICK_GREEN);
			btnEdgeDetection.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					slider.setValue(100);
					slider.setEnabled(false);
					filterType = FilterType.EDGE_DETECTION;	
					lblFilter.setText(filterType.toString());
				}
			});
			// Grayscale
			btnGrayscale.setBackground(Colors.BRUNSWICK_GREEN);
			btnGrayscale.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					slider.setEnabled(true);
					slider.setValue(1);
					filterType = FilterType.GRAYSCALE;	
					lblFilter.setText(filterType.toString());
				}
			});
		} 
		
		if (user.getTier() == UserTiers.HOBBYIST || user.getTier() == UserTiers.PROFESSIONAL) {
			// Brightness
			btnBrightness.setBackground(Colors.BRUNSWICK_GREEN);
			btnBrightness.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					slider.setEnabled(true);
					slider.setValue(1);
					filterType = FilterType.BRIGHTNESS;	
					lblFilter.setText(filterType.toString());
				}
			});
			
			// Contrast
			btnContrast.setBackground(Colors.BRUNSWICK_GREEN);
			btnContrast.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					slider.setEnabled(true);
					slider.setValue(1);
					filterType = FilterType.CONTRAST;	
					lblFilter.setText(filterType.toString());
				}
			});
		}
		
		
		/*
		 * Action Listeners
		 * 
		 * Apply Filter
		 * 
		 */
		
		// Apply
		btnApplyFilter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				long startTime = System.nanoTime();
				// Try applying the selected filter
				try {
					// Blur
					if (filterType == FilterType.BLUR) {
						bfImgScaled = Blur.BlurImage(bfImgScaled, slider.getValue());
					}
					// Sharpen
					else if (filterType == FilterType.SHARPEN) {
						bfImgScaled = Sharpen.SharpenImage(bfImgScaled, slider.getValue());
					} 
					// Grayscale
					else if (filterType == FilterType.GRAYSCALE) {
						bfImgScaled = Grayscale.GrayscaleImage(bfImgScaled, slider.getValue());
					} 
					// Edge Detection
					else if (filterType == FilterType.EDGE_DETECTION) {
						bfImgScaled = EdgeDetection.detectEdges(bfImgScaled);
					} 
					// Brightness
					else if (filterType == FilterType.BRIGHTNESS) {
						bfImgScaled = Brightness.BrightenImage(bfImgScaled, slider.getValue());
					} 
					// Contrast
					else if (filterType == FilterType.CONTRAST) {
						bfImgScaled = Contrast.changeContrast(bfImgScaled, slider.getValue());
					} 
					// Set Edited image as displayed
					lblImage.setIcon(new ImageIcon(bfImgScaled));
					
					long endTime = System.nanoTime(); 
					baseLogger.info().log(user.getUsername() + " applied " + filterType.toString() + " to image at " + imgPath + ". Duration: " + (endTime - startTime) / 1000000 + "ms.");
					
				} catch (Exception e2) {
					baseLogger.error().log("Failed to apply filter: " + filterType.toString());
				}
			}
		});
		
		// Save to Drafts
		btnSaveToDrafts.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				UUID imageUUID = UUID.randomUUID();
				
				// Create an image object
				PhotocloudImage pImage = new PhotocloudImage(
						user.getUsername(), 
						"resources/pictures/picturedatabase/" + imageUUID.toString() + ".jpg", 
						txtThumbnail.getText(), 
						txtCaption.getText(),
						0, 0,
						new ArrayList<String>(), 
						ImageEnum.PRIVATE,
						imageUUID.toString()
					);
				
				// Save image
				try {
					ImageOperations.saveImageToResources(bfImgScaled, "/picturedatabase/" + pImage.getImageUUID());
				} catch (IOException e1) {
					baseLogger.error().log("Failed to save image");
				}
				
				// Write Picture Details
				try {
					ImageOperations.writePictureData(pImage, true);
				} catch (IOException e1) {
					baseLogger.error().log("Failed to write picture data");
				}
				
				// Update Frames
				GUIContainer.getImageEditor().setFrameStatus(FrameStatus.DISPOSED);
				GUIContainer.getProfilePage().setFrameStatus(FrameStatus.DISPOSED);
				GUIContainer.updateGUI();
				GUIContainer.updateProfilePage(user.getUsername(), true);
				GUIContainer.getProfilePage().setFrameStatus(FrameStatus.VISIBLE);
				GUIContainer.updateGUI();
			}
		});
		
		// Share 
		btnShare.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				UUID imageUUID = UUID.randomUUID();
				
				// Create an image object
				PhotocloudImage pImage = new PhotocloudImage(
						user.getUsername(), 
						"resources/pictures/picturedatabase/" + imageUUID.toString() + ".jpg", 
						txtThumbnail.getText(), 
						txtCaption.getText(), 
						0,0,
						new ArrayList<String>(), 
						ImageEnum.PUBLIC,
						imageUUID.toString()
					);
				
				// Save image
				try {
					ImageOperations.saveImageToResources(bfImgScaled, "/picturedatabase/" + pImage.getImageUUID());
				} catch (IOException e1) {
					baseLogger.error().log("Failed to save image");
				}
				
				// Write Picture Details
				try {
					ImageOperations.writePictureData(pImage, true);
				} catch (IOException e1) {
					baseLogger.error().log("Failed to write picture data");
				}
				
				// Update Frames
				GUIContainer.getImageEditor().setFrameStatus(FrameStatus.DISPOSED);
				GUIContainer.getProfilePage().setFrameStatus(FrameStatus.DISPOSED);
				GUIContainer.updateGUI();
				GUIContainer.updateProfilePage(user.getUsername(), true);
				GUIContainer.getProfilePage().setFrameStatus(FrameStatus.VISIBLE);
				GUIContainer.updateGUI();
				
			}
		});
	}


	/**
	 * Initialize Components
	 */
	@Override
	public void initializeComponents(JPanel jPanel) {
		
		/*
		 * Components 
		 *
		 * 
		 * 
		 */
		
		// Image Label
		lblImage = lblImageConfiguration.getComponent();
		lblImageConfiguration.getGridBagConstraints().gridwidth = 7;
		lblImageConfiguration.getGridBagConstraints().gridheight = 13;
		addComponent(jPanel, lblImageConfiguration);
		
		// Filter Label
		lblFilter = lblFilterConfiguration.getComponent();
		addComponent(jPanel, lblFilterConfiguration);
		
		//Buttons
		btnBlur = btnBlurConfiguration.getComponent();
		addComponent(jPanel, btnBlurConfiguration);
		
		btnSharpen = btnSharpenConfiguration.getComponent();
		addComponent(jPanel, btnSharpenConfiguration);
		
		btnBrightness = btnBrightnessConfiguration.getComponent();
		addComponent(jPanel, btnBrightnessConfiguration);
		
		btnContrast = btnContrastConfiguration.getComponent();
		addComponent(jPanel, btnContrastConfiguration);
		
		btnEdgeDetection = btnEdgeDetectionConfiguration.getComponent();
		addComponent(jPanel, btnEdgeDetectionConfiguration);
		
		btnGrayscale = btnGrayscaleConfiguration.getComponent();
		addComponent(jPanel, btnGrayscaleConfiguration);
		
		btnApplyFilter = btnApplyFilterConfiguration.getComponent();
		addComponent(jPanel, btnApplyFilterConfiguration);
		
		// Weight for Edits
		slider = new JSlider(1, 100);
		slider.setOrientation(SwingConstants.HORIZONTAL);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = MAXIMIZED_HORIZ;
		gbc_slider.gridx = 1;
		gbc_slider.gridy = 12;
		getContentPane().add(slider, gbc_slider);
		
		// Weight Label
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Filter Weight", new Font("Arial", Font.BOLD, 12), new Insets(40, 40, 0, 0), 1, 11));
		
		// Tier Labels
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"FREE", new Font("Arial", Font.BOLD, 12), new Insets(20, 40, 0, 0), 1, 2));
		
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"HOBBYIST", new Font("Arial", Font.BOLD, 12), new Insets(20, 40, 0, 0), 1, 5));
		
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"PROFESSIONAL", new Font("Arial", Font.BOLD, 12), new Insets(20, 40, 0, 0), 1, 8));
		
		// Thumbnail
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Add Thumbnail", new Font("Ariel", Font.BOLD, 12), new Insets(0, 80, 40, 0), 2, 14));
		
		txtThumbnail = txtThumbnailConfiguration.getComponent();
		addComponent(jPanel, txtThumbnailConfiguration);
		
		// Caption
		txtCaption = txtCaptionConfiguration.getComponent();
		addComponent(jPanel, txtCaptionConfiguration);
		
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Add Caption", new Font("Ariel", Font.BOLD, 12), new Insets(0, 50, 40, 0), 4, 14));
		
		// Share/Save Buttons
		btnSaveToDrafts = btnSaveToDraftsConfiguration.getComponent();
		addComponent(jPanel, btnSaveToDraftsConfiguration);
		
		btnShare = btnShareConfiguration.getComponent();
		btnShareConfiguration.getGridBagConstraints().gridwidth = 2;
		addComponent(jPanel, btnShareConfiguration);
	}
	
	
	/*
	 * Getters & Setters 
	 * 
	 */
	private void setUser(User user) {
		this.user = user;
	}
}
