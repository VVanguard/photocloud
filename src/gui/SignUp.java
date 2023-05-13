package gui;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import baselogger.BaseLogger;
import user.User;
import util.Colors;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customframes.FrameFactory;
import util.image.ImageOperations;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;


public class SignUp extends FrameFactory {

	// Logger
	private BaseLogger baseLogger = new BaseLogger();
	
	// Components
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtMail;
	private JTextField txtAge;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JLabel lblImg;
	
	private JButton btnUpload;
	private JButton btnSignUp;
	private JToggleButton btnFree;
	private JToggleButton btnHobbyist;
	private JToggleButton btnProfessional;
	private ButtonGroup btnTierGroup;
	
	private Image ppImg = null;
	
	
	// Configurations
	private ComponentConfiguration txtNameConfiguration = ComponentGenerator.generateRoundedTextField(
			txtName, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 3);
	
	private ComponentConfiguration txtSurnameConfiguration = ComponentGenerator.generateRoundedTextField(
			txtSurname, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 5);
	
	private ComponentConfiguration txtMailConfiguration = ComponentGenerator.generateRoundedTextField(
			txtMail, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 7);
	
	private ComponentConfiguration txtAgeConfiguration = ComponentGenerator.generateRoundedTextField(
			txtAge, 5, Colors.BROKEN_WHITE, new Insets(10, 80, 10, 80), 1, 9);
	
	private ComponentConfiguration txtUsernameConfiguration = ComponentGenerator.generateRoundedTextField(
			txtUsername, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 11);
	
	private ComponentConfiguration txtPasswordConfiguration = ComponentGenerator.generateRoundedPasswordField(
			txtPassword, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 13);

	private ComponentConfiguration btnSignUpConfiguration = ComponentGenerator.generateRoundedButton(
			btnSignUp, 20, "Sign Up", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(10, 20, 10, 20), 1, 15);
	
	private ComponentConfiguration btnFreeConfiguration = ComponentGenerator.generateRoundedToggleButton(
			btnFree, 10, "FREE", new Font("Ariel", Font.BOLD, 20), Colors.BROKEN_WHITE, Colors.CHARCOAL_GRAY, new Insets(5, 0, 5, 0), 3, 3);
	
	private ComponentConfiguration btnHobbyistConfiguration = ComponentGenerator.generateRoundedToggleButton(
			btnHobbyist, 10, "HOBBYIST", new Font("Ariel", Font.BOLD, 20), Colors.BROKEN_WHITE, Colors.CHARCOAL_GRAY, new Insets(5, 0, 5, 0), 3, 5);
	
	private ComponentConfiguration btnProfessionalConfiguration = ComponentGenerator.generateRoundedToggleButton(
			btnProfessional, 10, "PROFESSIONAL", new Font("Ariel", Font.BOLD, 20), Colors.BROKEN_WHITE, Colors.CHARCOAL_GRAY, new Insets(5, 0, 5, 0), 3, 7);
	
	private ComponentConfiguration btnUploadConfiguration = ComponentGenerator.generateRoundedButton(
			btnUpload, 20, "Upload Picture", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(10, 20, 10, 20), 3, 15);
	
	private ComponentConfiguration lblImgLabelConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 3, 9);
	
	/**
	 * Create the frame.
	 */
	public SignUp() {

		// Create Frame (x, y, type) and Content Pane
		super(500, 580, Type.POPUP);	
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Title
		setTitle("Sign Up");
		
		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{30, 200, 50, 120, 30, 0};
		gbl_contentPane.rowHeights = new int[]{20, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 19, 60};

		contentPane.setLayout(gbl_contentPane);
		
		// Initialize Components
		initializeComponents(contentPane);		
		
		// Add Button Group
		btnTierGroup = new ButtonGroup();
		btnTierGroup.add(btnFree);
		btnTierGroup.add(btnHobbyist);
		btnTierGroup.add(btnProfessional);
		
		
		/**
		 * 
		 * Action Listeners	
		 * 
		 */
		
		// SignUp
		btnSignUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Use the default profile picture if there is no preference
				if (ppImg == null) {
					try {
						ppImg = ImageOperations.readNewImageFromUser("resources\\profilepictures\\Default_Profile_Picture.png");
					} catch (IOException error) {
						baseLogger.error().log("Failed to set Default Profile Picture: " + error);
					}
				}
				
				User newUser = new User(
						getName(), 
						getName(), 
						getTitle(), 
						ABORT, 
						getWarningString(), 
						getName(), 
						ppImg, 
						null
					);
						
			}
		});
		
		// Upload Image 
		btnUpload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				// Set Current Directory
				chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				
				// Create image filter
				FileNameExtensionFilter filter = new FileNameExtensionFilter("images", "jpg", "gif", "png");
				chooser.addChoosableFileFilter(filter);
				
				// Create result variable
				int result = chooser.showSaveDialog(null);
				
				// If File is selected in the chooser
				if (result == JFileChooser.APPROVE_OPTION) {
					
					File selectedFile = chooser.getSelectedFile();
					String imagePath = selectedFile.getAbsolutePath();
					
					// Insert Image to the JLabel
					try {
						lblImg.setIcon(new ImageIcon(ImageOperations.ResizeImage(ImageOperations.readNewImageFromUser(imagePath), 150, 150)));
						baseLogger.info().log("Photo Upload Successful");
					} catch (IOException error) {
						baseLogger.error().log("Photo Upload Failed: " + error);
						lblImg.setText("Upload Failed, Try Again Later");
					}
				} 
			}
		});
	}

	
	/**
	 * Initialize Components on the Panel
	 * 
	 */
	@Override
	public void initializeComponents(JPanel jPanel) {
		
		/*
		 * Components
		 * 
		 * Heading Label
		 * Username Label
		 * Username TxtBox
		 * Password Label
		 * Password TxtBox
		 * SignUp Button
		 * Free Tier Button
		 * Hobbyist Button
		 * Professional Button
		 * 
		 */
		
		
		// Head Label
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"PhotoCloud Editor", new Font("Arial", Font.PLAIN, 24), new Insets(0, 0, 20, 0), 1, 1));
		
		// Name
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Name", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 2));
		
		txtName = (JTextField)txtNameConfiguration.getComponent();
		addComponent(jPanel, txtNameConfiguration);
		
		//Surname
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Surname", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 4));
		
		txtSurname = (JTextField)txtSurnameConfiguration.getComponent();
		addComponent(jPanel, txtSurnameConfiguration);

		// E-mail
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"E-mail", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 6));

		txtMail = (JTextField)txtMailConfiguration.getComponent();
		addComponent(jPanel, txtMailConfiguration);

		// Age
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Age", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 8));
		
		txtAge = (JTextField)txtAgeConfiguration.getComponent();
		addComponent(jPanel, txtAgeConfiguration);
		
		// Username
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Username", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 10));	
		
		txtUsername = (JTextField)txtUsernameConfiguration.getComponent();
		addComponent(jPanel, txtUsernameConfiguration);
				
		// Password
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Password", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 12));
		
		txtPassword = (JPasswordField)txtPasswordConfiguration.getComponent();
		addComponent(jPanel, txtPasswordConfiguration);
		
		// Sign Up Button
		btnSignUp = (JButton)btnSignUpConfiguration.getComponent();
		addComponent(jPanel, btnSignUpConfiguration);
		
		// Toggle Buttons
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Select a Tier", new Font("Ariel", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 3, 2));
		
		btnFree = (JToggleButton)btnFreeConfiguration.getComponent();
		addComponent(jPanel, btnFreeConfiguration);

		btnHobbyist = (JToggleButton)btnHobbyistConfiguration.getComponent();
		addComponent(jPanel, btnHobbyistConfiguration);
		
		btnProfessional = (JToggleButton)btnProfessionalConfiguration.getComponent();
		addComponent(jPanel, btnProfessionalConfiguration);
		
		//Image Label
		lblImg = (JLabel)lblImgLabelConfiguration.getComponent();
		lblImgLabelConfiguration.getGridBagConstraints().gridheight = 5;
		addComponent(jPanel, lblImgLabelConfiguration);
		
		btnUpload = (JButton)btnUploadConfiguration.getComponent();
		addComponent(jPanel, btnUploadConfiguration);
	}
}
