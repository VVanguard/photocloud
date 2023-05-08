package gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import util.Colors;
import util.customfields.ComponentConfiguration;
import util.customfields.RoundedJButton;
import util.customfields.RoundedJPasswordField;
import util.customfields.RoundedJTextField;
import util.customframes.FrameFactory;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Label;


public class Login extends FrameFactory {

	
	// Components
	private Label headLabel;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnLogIn;
	private JLabel lblSignUp;
	private JButton btnSignUp;

	
	/**
	 * Create the frame.
	 */
	public Login() {
		
		// Create Frame (x, y, type) and Content Pane
		super(350, 500, Type.NORMAL);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Title
		setTitle("");

		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{65, 0, 65, 0};
		gbl_contentPane.rowHeights = new int[]{20, 100, 0, 0, 50, 0, 0, 50, 0, 50, 75, 0, 0, 0};
		
		contentPane.setLayout(gbl_contentPane);
		
		// Initialize Components
		initializeComponents(contentPane);	
		
		// Action Listeners
		// Log In
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("login");
			}
		});

		// Sign Up
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				SignUp signUp = new SignUp();
				signUp.setVisible(true);
			}
		});
	}

	
	/**
	 * Initialize Components on the Panel
	 * 
	 */
	public void initializeComponents(JPanel jPanel) {
		
		// Heading
		addComponent(jPanel, generateHeadingLabel());
		
		// Username
		addComponent(jPanel, generateUsernameLabel());
		addComponent(jPanel, generateUsernameTextBox());
		
		// Password
		addComponent(jPanel, generatePasswordLabel());
		addComponent(jPanel, generatePasswordTextBox());
		
		// LogIn
		addComponent(jPanel, generateLogInButton());
		
		// SignUp
		addComponent(jPanel, generateSignUpLabel());
		addComponent(jPanel, generateSignUpButton());
	}
	
		
	/*
	 * Components
	 * 
	 * 
	 * Heading Label
	 * Username Label
	 * Username TxtBox
	 * Password Label
	 * Password TxtBox 
	 * LogIn Button
	 * SignUp Label
	 * SignUp Button
	 */


	/**
	 * Generate Heading Label configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateHeadingLabel() {
		
		// Head Label
		headLabel = new Label("PhotoCloud Editor");
		headLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		
		// Head Label Configuration
		GridBagConstraints gbc_HeadLabel = new GridBagConstraints();
		gbc_HeadLabel.insets = new Insets(0, 0, 10, 0);
		gbc_HeadLabel.gridwidth = 3;
		gbc_HeadLabel.gridx = 0;
		gbc_HeadLabel.gridy = 1;
		
		return new ComponentConfiguration(headLabel, gbc_HeadLabel);
	}
	
	
	/**
	 * Generate Username Label configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateUsernameLabel() {
		
		// Username JLabel 
		lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 10));
		
		// Username JLabel Placement
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.anchor = GridBagConstraints.BASELINE;
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 3;
		
		return new ComponentConfiguration(lblUsername, gbc_lblUsername);
	}
	
	
	/**
	 * Generate Username Text Box configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateUsernameTextBox() {
		
		// Username Rounded Text Box
		txtUsername = new RoundedJTextField(5);
		txtUsername.setBackground(Colors.BROKEN_WHITE);
				
		// Username Text Box Placement
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.fill = GridBagConstraints.BOTH;
		gbc_txtUsername.gridx = 1;
		gbc_txtUsername.gridy = 4;
		
		return new ComponentConfiguration(txtUsername, gbc_txtUsername);	
	}
	
	
	/**
	 * Generate Password Label configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generatePasswordLabel() {
		
		// Password JLabel
		lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 10));
		
		// Password JLabel Placement
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 6;
		
		return new ComponentConfiguration(lblPassword, gbc_lblPassword);
	}
	
	
	/**
	 * Generate Password Text Box configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generatePasswordTextBox() {
		
		// Password Rounded Text Box
		txtPassword = new RoundedJPasswordField(5);
		txtPassword.setBackground(new Color(242, 242, 242));
		
		// Paassword Text Box Placement
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.fill = GridBagConstraints.BOTH;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.gridx = 1;
		gbc_txtPassword.gridy = 7;
		
		return new ComponentConfiguration(txtPassword, gbc_txtPassword);
	}
	
	
	/**
	 * Generate LogIn Button configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateLogInButton() {
		
		// LogIn Rounded JButton
		btnLogIn = new RoundedJButton(5);
		btnLogIn.setText("Log In");
		btnLogIn.setFont(new Font("Ariel", Font.BOLD, 12));
		btnLogIn.setForeground(Colors.BROKEN_WHITE);
		btnLogIn.setBackground(Colors.BRUNSWICK_GREEN);
		
		// LogIn Rounded JButton Placement
		GridBagConstraints gbc_btnLogIn = new GridBagConstraints();
		gbc_btnLogIn.insets = new Insets(10, 30, 10, 30);
		gbc_btnLogIn.fill = GridBagConstraints.BOTH;
		gbc_btnLogIn.gridx = 1;
		gbc_btnLogIn.gridy = 9;
		
		return new ComponentConfiguration(btnLogIn, gbc_btnLogIn);	
	}
	
	
	/**
	 * Generate SingUp Label configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateSignUpLabel() {
		
		// SignUp JLabel
		lblSignUp = new JLabel("Come join us!");
		lblSignUp.setFont(new Font("Arial", Font.PLAIN, 10));
		
		// SignUp Label Placement
		GridBagConstraints gbc_lblSignUp = new GridBagConstraints();
		gbc_lblSignUp.insets = new Insets(0, 0, 5, 5);
		gbc_lblSignUp.gridx = 1;
		gbc_lblSignUp.gridy = 11;

		return new ComponentConfiguration(lblSignUp, gbc_lblSignUp);	
	}
	
	
	/**
	 * Generate Sing Up Button configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateSignUpButton() {

		// SignUp Button
		btnSignUp = new RoundedJButton(5);
		btnSignUp.setText("Sign Up");
		btnSignUp.setFont(new Font("Ariel", Font.BOLD, 12));
		btnSignUp.setForeground(Colors.BROKEN_WHITE);
		btnSignUp.setBackground(Colors.BRUNSWICK_GREEN);
		
		// SignUp Button Placement
		GridBagConstraints gbc_btnSignUp = new GridBagConstraints();
		gbc_btnSignUp.fill = GridBagConstraints.BOTH;
		gbc_btnSignUp.insets = new Insets(0, 60, 20, 60);
		gbc_btnSignUp.gridx = 1;
		gbc_btnSignUp.gridy = 12;
		
		return new ComponentConfiguration(btnSignUp, gbc_btnSignUp);
	}
	
}
