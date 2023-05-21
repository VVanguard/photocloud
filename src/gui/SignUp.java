package gui;

import java.awt.Color;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.filechooser.FileNameExtensionFilter;

import baselogger.BaseLogger;
import user.User;
import user.UserOperations;
import user.UserTiers;
import util.Colors;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customcomponents.RoundedJButton;
import util.customcomponents.RoundedJPasswordField;
import util.customcomponents.RoundedJTextField;
import util.customcomponents.RoundedJToggleButton;
import util.customframes.FrameFactory;
import util.image.ImageOperations;
import util.validators.DatabaseValidators;
import util.validators.Validators;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;


public class SignUp extends FrameFactory {

	// Logger
	private BaseLogger baseLogger = new BaseLogger();
	
	// Components
	private RoundedJTextField txtName;
	private RoundedJTextField txtSurname;
	private RoundedJTextField txtMail;
	private RoundedJTextField txtAge;
	private RoundedJTextField txtUsername;
	private RoundedJPasswordField txtPassword;
	private JLabel lblImg;
	private JLabel errorLabel;
	
	private RoundedJButton btnUpload;
	private RoundedJButton btnSignUp;
	private RoundedJToggleButton btnFree;
	private RoundedJToggleButton btnHobbyist;
	private RoundedJToggleButton btnProfessional;
	private ButtonGroup btnTierGroup;
	
	private BufferedImage ppImg = null;
	
	
	// Configurations
	private ComponentConfiguration<RoundedJTextField> txtNameConfiguration = ComponentGenerator.generateRoundedTextField(
			txtName, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 3);
	
	private ComponentConfiguration<RoundedJTextField> txtSurnameConfiguration = ComponentGenerator.generateRoundedTextField(
			txtSurname, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 5);
	
	private ComponentConfiguration<RoundedJTextField> txtMailConfiguration = ComponentGenerator.generateRoundedTextField(
			txtMail, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 7);
	
	private ComponentConfiguration<RoundedJTextField> txtAgeConfiguration = ComponentGenerator.generateRoundedTextField(
			txtAge, 5, Colors.BROKEN_WHITE, new Insets(10, 80, 10, 80), 1, 9);
	
	private ComponentConfiguration<RoundedJTextField> txtUsernameConfiguration = ComponentGenerator.generateRoundedTextField(
			txtUsername, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 11);
	
	private ComponentConfiguration<RoundedJPasswordField> txtPasswordConfiguration = ComponentGenerator.generateRoundedPasswordField(
			txtPassword, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 13);

	private ComponentConfiguration<RoundedJButton> btnSignUpConfiguration = ComponentGenerator.generateRoundedButton(
			btnSignUp, 20, "Sign Up", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(10, 20, 10, 20), 1, 15);
	
	private ComponentConfiguration<RoundedJToggleButton> btnFreeConfiguration = ComponentGenerator.generateRoundedToggleButton(
			btnFree, 10, "FREE", new Font("Ariel", Font.BOLD, 20), Colors.BROKEN_WHITE, Colors.CHARCOAL_GRAY, new Insets(5, 0, 5, 0), 3, 3);
	
	private ComponentConfiguration<RoundedJToggleButton> btnHobbyistConfiguration = ComponentGenerator.generateRoundedToggleButton(
			btnHobbyist, 10, "HOBBYIST", new Font("Ariel", Font.BOLD, 20), Colors.BROKEN_WHITE, Colors.CHARCOAL_GRAY, new Insets(5, 0, 5, 0), 3, 5);
	
	private ComponentConfiguration<RoundedJToggleButton> btnProfessionalConfiguration = ComponentGenerator.generateRoundedToggleButton(
			btnProfessional, 10, "PROFESSIONAL", new Font("Ariel", Font.BOLD, 20), Colors.BROKEN_WHITE, Colors.CHARCOAL_GRAY, new Insets(5, 0, 5, 0), 3, 7);
	
	private ComponentConfiguration<RoundedJButton> btnUploadConfiguration = ComponentGenerator.generateRoundedButton(
			btnUpload, 20, "Upload Picture", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(10, 20, 10, 20), 3, 15);
	
	private ComponentConfiguration<JLabel> lblImgLabelConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 3, 9);
	
	private ComponentConfiguration<JLabel> errorLabelConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 14);
	
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
				
				// Create User
				try {
					
					// Validate Fields
					Validators.validateUsername(txtUsername.getText());
					Validators.validatePassword(String.valueOf(txtPassword.getPassword()));
					Validators.validateNameSpaces(txtName.getText());
					Validators.validateNameSpaces(txtSurname.getText());
					Validators.validateAge(txtAge.getText());
					UserTiers tier = Validators.validateTierSelection(btnFree, btnHobbyist, btnProfessional);
					
					DatabaseValidators.validateUniqueness(txtUsername.getText(), txtMail.getText());
					
					// Create and Save the new User
					User newUser = new User(
							txtName.getText(), 
							txtSurname.getText(), 
							txtMail.getText(), 
							Integer.valueOf(txtAge.getText()), 
							txtUsername.getText(), 
							String.valueOf(txtPassword.getPassword()),
							tier
						);
					
					
					// Use the default profile picture if there is no preference
					if (ppImg == null) {
						newUser.setPpImg("resources/pictures/Default_Profile_Picture.png");
					}
					
					// Else use the selected profile picture
					else {
						try {
							ImageOperations.saveImageToResources(ppImg, newUser.getUsername());
							newUser.setPpImg("resources/pictures/" + newUser.getUsername() + ".jpg");
						} catch (Exception error2) {
							baseLogger.error().log("Error in saving pp Image: " + error2);
						}
					}
					
					// Write User to database
					UserOperations.writeUser(newUser);
					
					// Arrange Other Pages
					GUIContainer.getLogIn().setFrameStatus(FrameStatus.HIDE);
					GUIContainer.getSignUp().setFrameStatus(FrameStatus.HIDE);
					//TODO: Show Profile or Discovery
					
					baseLogger.info().log("New User Created: " + txtUsername.getText());
					
					// Update Frame Status and navigate to the new user's profile page
					GUIContainer.updateProfilePage(newUser.getUsername(), true);
					GUIContainer.getProfilePage().setFrameStatus(FrameStatus.VISIBLE);
					GUIContainer.getLogIn().setFrameStatus(FrameStatus.HIDE);
					GUIContainer.getSignUp().setFrameStatus(FrameStatus.HIDE);
					GUIContainer.updateGUI();
					
					
				} catch (Exception error) {
					errorLabel.setText(error.getMessage());
					baseLogger.error().log(error.getMessage());
				}
				
				
						
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
						ppImg = ImageIO.read(new File(imagePath));
						
						baseLogger.info().log("Photo Upload Successful");
					} catch (IOException error) {
						baseLogger.error().log("Photo Upload Failed: " + error);
						lblImg.setText("Upload Failed, Try Again Later");
					}
				} 
			}
		});
		
		
		/**
		 * Window Listener for closing
		 */
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent e) {
				setFrameStatus(FrameStatus.HIDE);
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
		
		txtName = txtNameConfiguration.getComponent();
		addComponent(jPanel, txtNameConfiguration);
		
		//Surname
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Surname", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 4));
		
		txtSurname = txtSurnameConfiguration.getComponent();
		addComponent(jPanel, txtSurnameConfiguration);

		// E-mail
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"E-mail", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 6));

		txtMail = txtMailConfiguration.getComponent();
		addComponent(jPanel, txtMailConfiguration);

		// Age
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Age", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 8));
		
		txtAge = txtAgeConfiguration.getComponent();
		addComponent(jPanel, txtAgeConfiguration);
		
		// Username
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Username", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 10));	
		
		txtUsername = txtUsernameConfiguration.getComponent();
		addComponent(jPanel, txtUsernameConfiguration);
				
		// Password
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Password", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 12));
		
		txtPassword = txtPasswordConfiguration.getComponent();
		addComponent(jPanel, txtPasswordConfiguration);
		
		// Sign Up Button
		btnSignUp = btnSignUpConfiguration.getComponent();
		addComponent(jPanel, btnSignUpConfiguration);
		
		// Toggle Buttons
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Select a Tier", new Font("Ariel", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 3, 2));
		
		btnFree = btnFreeConfiguration.getComponent();
		addComponent(jPanel, btnFreeConfiguration);

		btnHobbyist = btnHobbyistConfiguration.getComponent();
		addComponent(jPanel, btnHobbyistConfiguration);
		
		btnProfessional = btnProfessionalConfiguration.getComponent();
		addComponent(jPanel, btnProfessionalConfiguration);
		
		// Image Label
		lblImg = lblImgLabelConfiguration.getComponent();
		lblImgLabelConfiguration.getGridBagConstraints().gridheight = 5;
		addComponent(jPanel, lblImgLabelConfiguration);
		
		btnUpload = btnUploadConfiguration.getComponent();
		addComponent(jPanel, btnUploadConfiguration);
		
		// Error Label
		errorLabel = errorLabelConfiguration.getComponent();
		errorLabel.setForeground(Color.RED);
		errorLabelConfiguration.getGridBagConstraints().gridwidth = 3;
		addComponent(jPanel, errorLabelConfiguration);
	}
}
