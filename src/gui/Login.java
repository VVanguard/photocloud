package gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import util.Colors;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customframes.FrameFactory;
import util.validators.DatabaseValidators;
import util.validators.Validators;

import java.awt.GridBagLayout;
import javax.swing.JTextField;

import baselogger.BaseLogger;

import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Login extends FrameFactory {
	
	// Logger
	private BaseLogger baseLogger = new BaseLogger();
	
	// Components
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogIn;
	private JButton btnSignUp;
	private JLabel lblError;
	
	//Configurations
	private ComponentConfiguration txtUsernameConfiguration = ComponentGenerator.generateRoundedTextField(
			txtUsername, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 4);
	
	private ComponentConfiguration txtPasswordConfiguration = ComponentGenerator.generateRoundedPasswordField(
			txtPassword, 5, Colors.BROKEN_WHITE, new Insets(10, 5, 10, 5), 1, 7);
	
	private ComponentConfiguration btnLogInConfiguration = ComponentGenerator.generateRoundedButton(
			btnLogIn, 5, "Log In", new Font("Arial", Font.PLAIN, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(10, 30, 10, 30), 1, 9);
	
	private ComponentConfiguration btnSignUpConfiguration = ComponentGenerator.generateRoundedButton(
			btnSignUp, 5, "Sıgn Up", new Font("Arial", Font.PLAIN, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(0, 60, 20, 60), 1, 12);
	
	private ComponentConfiguration lblErrorConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 50, 0), 1, 10);
	
	
	/**
	 * Create the frame.
	 */
	public Login() {
		
		// Create Frame (x, y, type) and Content Pane
		super(350, 500, Type.NORMAL);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{65, 0, 65, 0};
		gbl_contentPane.rowHeights = new int[]{20, 100, 0, 0, 50, 0, 0, 50, 0, 50, 75, 0, 0, 0};
		
		contentPane.setLayout(gbl_contentPane);
		
		// Initialize Components
		initializeComponents(contentPane);	
		
		/*
		 * Action Listeners
		 */
		// Log In
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Validate Credentials
					String usernameToLogIn = DatabaseValidators.ValideLogInCredentials(
							txtUsername.getText(), String.valueOf(txtPassword.getPassword()));
					
					baseLogger.info().log("New LogIn: " + usernameToLogIn);
					
				} catch (Exception error) {
					lblError.setText(error.getMessage());
					baseLogger.error().log("Username or Password is not correct");
				}
			}
		});

		// Sign Up
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				// New SignUP Request
				GUIContainer.getSignUp().setFrameStatus(FrameStatus.VISIBLE);
				GUIContainer.updateGUI();
				baseLogger.info().log("New SignUp Request");
			}
		});
		
		setFrameStatus(FrameStatus.VISIBLE);
	}

	
	/**
	 * Initialize Components on the Panel
	 * 
	 */
	public void initializeComponents(JPanel jPanel) {
		
		// Heading
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"PhotoCloud Editor", new Font("Arial", Font.PLAIN, 24), new Insets(0, 0, 10, 0), 1, 1));
		
		// Username
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Username", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 3));
		
		txtUsername = (JTextField)txtUsernameConfiguration.getComponent();
		addComponent(jPanel, txtUsernameConfiguration);
		
		// Password
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Password", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 1, 6));
		
		txtPassword = (JPasswordField)txtPasswordConfiguration.getComponent();
		addComponent(jPanel, txtPasswordConfiguration);
		
		// LogIn
		btnLogIn = (JButton)btnLogInConfiguration.getComponent();
		addComponent(jPanel, btnLogInConfiguration);
		
		// SignUp
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Sign Up Now!", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 5, 0), 1, 11));
		
		btnSignUp = (JButton) btnSignUpConfiguration.getComponent();
		addComponent(jPanel, btnSignUpConfiguration);
		
		// Error Label
		lblError = (JLabel)lblErrorConfiguration.getComponent();
		lblError.setForeground(Color.RED);
		addComponent(jPanel, lblErrorConfiguration);
	}
}
