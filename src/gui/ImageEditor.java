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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import baselogger.BaseLogger;
import user.User;
import user.UserTiers;
import util.Colors;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customcomponents.RoundedJButton;
import util.customcomponents.RoundedJTextField;
import util.customframes.FrameFactory;
import util.filters.Blur;
import util.filters.FilterType;
import util.image.ImageOperations;


public class ImageEditor extends FrameFactory {

	// User 
	private User user;
	
	// Filter Enumerator
	private FilterType filterType = FilterType.NULL;
	
	// Filter List
	private ArrayList<String> filtersApplied = new ArrayList<String>();
	
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
	
	private RoundedJTextField txtCaption;
	private RoundedJButton btnSaveToDrafts;
	private RoundedJButton btnShare;

	
	// Configurations
	ComponentConfiguration lblImageConfiguration = ComponentGenerator.generateCenteredLabel(
			"", null, new Insets(0, 0, 0, 0), 2, 1);
	
	ComponentConfiguration lblFilterConfiguration = ComponentGenerator.generateCenteredLabel(
			"Select Filter" , new Font("Ariel", Font.BOLD, 14), new Insets(100, 40, 0, 0), 1, 1);
	
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
	
	ComponentConfiguration btnApplyFilterConfiguration = ComponentGenerator.generateRoundedButton(
			btnApplyFilter, 15, "Apply", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(150, 20, 15, 5), 1, 13);
	
	ComponentConfiguration txtCaptionConfiguration = ComponentGenerator.generateRoundedTextField(
			txtCaption, 25, Colors.BROKEN_WHITE, new Insets(0, 20, 40, 0), 2, 14);
	
	ComponentConfiguration btnSaveToDraftsConfiguration = ComponentGenerator.generateRoundedButton(
			btnSaveToDrafts, 10, "Save To Drafts", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(0, 5, 40, 5), 3, 14);
	
	ComponentConfiguration btnShareConfiguration = ComponentGenerator.generateRoundedButton(
			btnShare, 10, "Share", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(0, 5, 40, 75), 4, 14);
	
	// Images
	private BufferedImage bfImg;
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
		gbl_contentPane.columnWidths = new int[]{10, 120, 1000, 100, 100, 100, 20};
		gbl_contentPane.rowHeights = new int[]{25, 260, 25, 50, 50, 25, 50, 50, 25, 50, 50, 50, 10, 200, 60};
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
		
		// Set the frame visible
		lblImage.setIcon(new ImageIcon(bfImgScaled));
		setFrameStatus(FrameStatus.VISIBLE);
		
		// Declare user
		setUser(userEdit);
		
		// Tier Based Coloring
		if (user.getTier() == UserTiers.PROFESSIONAL) {
			btnEdgeDetection.setBackground(Colors.BRUNSWICK_GREEN);
			btnGrayscale.setBackground(Colors.BRUNSWICK_GREEN);
			btnBrightness.setBackground(Colors.BRUNSWICK_GREEN);
			btnContrast.setBackground(Colors.BRUNSWICK_GREEN);
		} else if (user.getTier() == UserTiers.HOBBYIST) {
			btnBrightness.setBackground(Colors.BRUNSWICK_GREEN);
			btnContrast.setBackground(Colors.BRUNSWICK_GREEN);
		}
		
		/*
		 * Action Listeners
		 * 
		 * Filters
		 * 
		 */
		
		btnBlur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { 
				filterType = FilterType.BLUR;	
				slider.setValue(1);
				lblFilter.setText(filterType.toString());
			}
		});
		
		btnSharpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterType = FilterType.SHARPEN;	
				slider.setValue(1);
				lblFilter.setText(filterType.toString());
			}
		});
		
		btnBrightness.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterType = FilterType.BRIGHTNESS;	
				slider.setValue(50);
				lblFilter.setText(filterType.toString());
			}
		});
		
		btnContrast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterType = FilterType.CONTRAST;	
				slider.setValue(50);
				lblFilter.setText(filterType.toString());
			}
		});
		
		btnEdgeDetection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterType = FilterType.EDGE_DETECTION;	
				slider.setValue(1);
				lblFilter.setText(filterType.toString());
			}
		});

		btnGrayscale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterType = FilterType.GRAYSCALE;	
				slider.setValue(1);
				lblFilter.setText(filterType.toString());
			}
		});
		
		
		/*
		 * Action Listeners
		 * 
		 * Apply Filter
		 * 
		 */
		
		btnApplyFilter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Try applying the selected filter
				try {
					// Blur
					if (filterType == FilterType.BLUR) {
						bfImg = Blur.BlurImage(bfImg, slider.getValue());
						bfImgScaled = Blur.BlurImage(bfImgScaled, slider.getValue());
					}
					
					else if (false) {
						
					} 
					
					else if (false) {

					} 

					else if (false) {

					} 

					else if (false) {

					} 

					else if (false) {

					} 
					
					lblImage.setIcon(new ImageIcon(bfImgScaled));
					
				} catch (Exception e2) {
					baseLogger.error().log("Failed to apply filter: " + filterType.toString());
				}
				
			}
		});
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
		
		// Filter Label
		lblFilter = (JLabel)lblFilterConfiguration.getComponent();
		addComponent(jPanel, lblFilterConfiguration);
		
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
		
		btnApplyFilter = (RoundedJButton)btnApplyFilterConfiguration.getComponent();
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
		
		// Caption
		txtCaption = (RoundedJTextField)txtCaptionConfiguration.getComponent();
		addComponent(jPanel, txtCaptionConfiguration);
		
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Add Caption", new Font("Ariel", Font.BOLD, 12), new Insets(0, 120, 40, 0), 1, 14));
		
		// Share/Save Buttons
		btnSaveToDrafts = (RoundedJButton)btnSaveToDraftsConfiguration.getComponent();
		addComponent(jPanel, btnSaveToDraftsConfiguration);
		
		btnShare = (RoundedJButton)btnShareConfiguration.getComponent();
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
