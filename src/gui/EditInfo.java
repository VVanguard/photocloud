package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import baselogger.BaseLogger;
import user.User;
import user.UserOperations;
import user.UserTiers;
import util.Colors;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customframes.FrameFactory;
import util.image.ImageOperations;
import util.validators.DatabaseValidators;
import util.validators.Validators;

public class EditInfo extends FrameFactory {

	// Logger
	private BaseLogger baseLogger = new BaseLogger();
	 
	// User
	User currentUser;

	// Components
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtMail;
	private JTextField txtAge;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JLabel lblImg;
	private JLabel errorLabel;

	private JButton btnUpload;
	private JButton btnUpdate;
	private JToggleButton btnFree;
	private JToggleButton btnHobbyist;
	private JToggleButton btnProfessional;
	private ButtonGroup btnTierGroup;

	private BufferedImage ppImg = null;


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

	private ComponentConfiguration btnUpdateConfiguration = ComponentGenerator.generateRoundedButton(
			btnUpdate, 20, "Update", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(10, 20, 10, 20), 1, 15);

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

	private ComponentConfiguration errorLabelConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 14);

	
	// Dummy Constructor
	public EditInfo() {
		super(500, 580, Type.POPUP);	
	}
	
	
	/**
	 * Initialize Frame
	 * @param user
	 */
	public EditInfo(User user) {
		// Create Frame (x, y, type) and Content Pane
		super(500, 580, Type.POPUP);	
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// Title
		setTitle("Edit Info");

		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{30, 200, 50, 120, 30, 0};
		gbl_contentPane.rowHeights = new int[]{20, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 19, 60};

		contentPane.setLayout(gbl_contentPane);

		// Declare current user
		currentUser = user;
		
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

		// Update
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Create User
				try {
					errorLabel.setText("");

					// Validate Fields
					Validators.validatePassword(String.valueOf(txtPassword.getPassword()));
					Validators.validateNameSpaces(txtName.getText());
					Validators.validateNameSpaces(txtSurname.getText());
					Validators.validateAge(txtAge.getText());
					UserTiers tier = Validators.validateTierSelection(btnFree, btnHobbyist, btnProfessional);

					// Create and Save the new User
					User newUser = new User(
							txtName.getText(), 
							txtSurname.getText(), 
							txtMail.getText(), 
							Integer.valueOf(txtAge.getText()), 
							currentUser.getUsername(), 
							String.valueOf(txtPassword.getPassword()),
							tier
							);

					if (ppImg == null) {
						newUser.setPpImg(currentUser.getImgPath());
					}

					// Else replace the profile picture
					else {
						try {
							ImageOperations.updateImageInResources(ppImg, newUser.getUsername());
							newUser.setPpImg("resources/pictures/" + newUser.getUsername() + ".jpg");
						} catch (Exception error2) {
							baseLogger.error().log("Error in updating pp Image: " + error2);
						}
					}

					// Write User to database
					UserOperations.updateUserInDatabase(newUser);
					baseLogger.info().log("user updated: " + txtUsername.getText());
					
					// Adjust Frames
					GUIContainer.getEditInfo().setFrameStatus(FrameStatus.DISPOSED);
					GUIContainer.getProfilePage().setFrameStatus(FrameStatus.HIDE);
					GUIContainer.updateGUI();
					GUIContainer.updateProfilePage(currentUser.getUsername(), true);
					GUIContainer.getProfilePage().setFrameStatus(FrameStatus.VISIBLE);
					GUIContainer.updateGUI();

				} catch (Exception error) {
					errorLabel.setText(error.getMessage());
					baseLogger.error().log(error.toString());
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

		txtName = (JTextField)txtNameConfiguration.getComponent();
		txtName.setText(currentUser.getName());
		addComponent(jPanel, txtNameConfiguration);

		//Surname
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Surname", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 4));

		txtSurname = (JTextField)txtSurnameConfiguration.getComponent();
		txtSurname.setText(currentUser.getSurname());
		addComponent(jPanel, txtSurnameConfiguration);

		// E-mail
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"E-mail", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 6));

		txtMail = (JTextField)txtMailConfiguration.getComponent();
		txtMail.setText(currentUser.getEmail());
		addComponent(jPanel, txtMailConfiguration);

		// Age
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Age", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 8));

		txtAge = (JTextField)txtAgeConfiguration.getComponent();
		txtAge.setText(Integer.toString(currentUser.getAge()));
		addComponent(jPanel, txtAgeConfiguration);

		// Username
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Username", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 10));	

		txtUsername = (JTextField)txtUsernameConfiguration.getComponent();
		txtUsername.setText(currentUser.getUsername());
		txtUsername.setEditable(false);
		addComponent(jPanel, txtUsernameConfiguration);

		// Password
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Password", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 12));

		txtPassword = (JPasswordField)txtPasswordConfiguration.getComponent();
		txtPassword.setText(currentUser.getPassword());
		addComponent(jPanel, txtPasswordConfiguration);

		// Sign Up Button
		btnUpdate = (JButton)btnUpdateConfiguration.getComponent();
		addComponent(jPanel, btnUpdateConfiguration);

		// Toggle Buttons
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Select a Tier", new Font("Ariel", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 3, 2));

		btnFree = (JToggleButton)btnFreeConfiguration.getComponent();
		addComponent(jPanel, btnFreeConfiguration);

		btnHobbyist = (JToggleButton)btnHobbyistConfiguration.getComponent();
		addComponent(jPanel, btnHobbyistConfiguration);

		btnProfessional = (JToggleButton)btnProfessionalConfiguration.getComponent();
		addComponent(jPanel, btnProfessionalConfiguration);
		
		// Select the user tier
		if (currentUser.getTier() == UserTiers.FREE) {
			btnFree.setSelected(true);
		} else if (currentUser.getTier() == UserTiers.HOBBYIST) {
			btnHobbyist.setSelected(true);
		} else {
			btnProfessional.setSelected(true);
		}
		
		

		// Image Label
		lblImg = (JLabel)lblImgLabelConfiguration.getComponent();
		lblImgLabelConfiguration.getGridBagConstraints().gridheight = 5;
		// Try to read User Profile Picture
		try {
			lblImg.setIcon(new ImageIcon(ImageOperations.resizeSquare(ImageOperations.readNewImageFromUser(currentUser.getImgPath()), 180)));
		} catch (IOException e) {
			baseLogger.error().log("Failed to Upload profile picture: " + currentUser.getImgPath());
		}
		addComponent(jPanel, lblImgLabelConfiguration);

		btnUpload = (JButton)btnUploadConfiguration.getComponent();
		addComponent(jPanel, btnUploadConfiguration);

		// Error Label
		errorLabel = (JLabel)errorLabelConfiguration.getComponent();
		errorLabel.setForeground(Color.RED);
		errorLabelConfiguration.getGridBagConstraints().gridwidth = 3;
		addComponent(jPanel, errorLabelConfiguration);
	}
}
