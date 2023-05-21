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
			"", new Font("Ariel", Font.PLAIN, 12), new Insets(0, 0, 0, 0), 0, 0);
	
	private ComponentConfiguration<JLabel> lblUserConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Ariel", Font.PLAIN, 12), new Insets(0, 0, 0, 0), 0, 0);
	
	private ComponentConfiguration<JLabel> lblThumbnailConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Ariel", Font.PLAIN, 12), new Insets(0, 0, 0, 0), 0, 0);
	
	
	/**
	 * Initialize Image Panel
	 * @throws IOException 
	 * 
	 */
	public ImagePanel(String imgPath, String username, String thumbnail) throws IOException {
		super();
		
		GridBagLayout gbl_ImagePanel = new GridBagLayout();
		gbl_ImagePanel.columnWidths = new int[]{60, 60};
		gbl_ImagePanel.rowHeights = new int[]{120, 20};
		
		setLayout(gbl_ImagePanel);
		
		initializeComponents(this);
		
		try {
			BufferedImage bfImg = ImageOperations.resizeSquare(ImageOperations.readNewImageFromUser(imgPath), 120);
			lblImage.setIcon(new ImageIcon(bfImg));
		} catch (Exception e) {
			baseLogger.error().log("Failed to read image");
			throw new IOException();
		}
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
		addComponent(jPanel, lblUserConfiguration);
		
		// Thumbnail Label
		lblThumbnail = lblThumbnailConfiguration.getComponent();
		addComponent(jPanel, lblThumbnailConfiguration);
	}
	
	
	public static ImagePanel generateImagePanel(String imgPath) {
		ImagePanel panel;
		
		try {
			panel = new ImagePanel(imgPath);
		} catch (Exception e) {
			return null;
		}
		
		return panel;
	}
}	
