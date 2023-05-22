package gui;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import baselogger.BaseLogger;
import image.PhotocloudImage;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customframes.PanelFactory;
import util.image.ImageOperations;

public class ImagePanel extends PanelFactory {

	// Base Logger
	BaseLogger baseLogger = new BaseLogger();
	
	// Components
	private JLabel lblImage;
	private JLabel lblUser;
	private JLabel lblThumbnail;
	
	// Configurations
	private ComponentConfiguration<JLabel> lblImageConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Ariel", Font.PLAIN, 12), new Insets(5, 0, 5, 0), 0, 0);
	
	private ComponentConfiguration<JLabel> lblUserConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Ariel", Font.PLAIN, 12), new Insets(0, 15, 0, 0), 0, 1);
	
	private ComponentConfiguration<JLabel> lblThumbnailConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Ariel", Font.PLAIN, 12), new Insets(0, 0, 0, 5), 1, 1);
	
	
	/**
	 * Initialize Image Panel
	 * @throws IOException 
	 * 
	 */
	public ImagePanel(PhotocloudImage pImage) throws IOException {
		super(120, 140);
		
		GridBagLayout gbl_ImagePanel = new GridBagLayout();
		gbl_ImagePanel.columnWidths = new int[]{60, 60};
		gbl_ImagePanel.rowHeights = new int[]{120, 20};
		
		setLayout(gbl_ImagePanel);
		
		initializeComponents(this);
		
		System.out.println(pImage.getImgPath());
		
		try {
			BufferedImage bfImg = ImageOperations.resizeSquare(ImageOperations.readNewImageFromUser(pImage.getImgPath()), 120);
			lblImage.setIcon(new ImageIcon(bfImg));
		} catch (Exception e) {
			baseLogger.error().log("Failed to read image");
			e.printStackTrace();
			throw new IOException();
		}
		
		lblUser.setText(pImage.getUsername());
		lblThumbnail.setText(pImage.getThumbnail());
	}


	/**
	 * Initialize Components
	 * 
	 */
	@Override
	public void initializeComponents(JPanel jPanel) {
		
		/*
		 * Components
		 * 
		 * Image Label
		 * User Label
		 * Thumbnail Label
		 */
		
		// Image Label
		lblImage = lblImageConfiguration.getComponent();
		lblImageConfiguration.getGridBagConstraints().gridwidth = 2;
		addComponent(jPanel, lblImageConfiguration);
		
		// User Label
		lblUser = lblUserConfiguration.getComponent();
		lblUser.setHorizontalAlignment(SwingConstants.LEFT);
		addComponent(jPanel, lblUserConfiguration);
		
		// Thumbnail Label
		lblThumbnail = lblThumbnailConfiguration.getComponent();
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		addComponent(jPanel, lblThumbnailConfiguration);
	}
}	
