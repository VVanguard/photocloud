package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import util.Colors;
import util.customfields.ComponentConfiguration;
import util.customfields.RoundedJButton;
import util.customfields.RoundedJPasswordField;
import util.customfields.RoundedJTextField;
import util.customfields.RoundedJToggleButton;
import util.customframes.FrameFactory;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JButton;


public class SignUp extends FrameFactory {

	
	// Components
	private JLabel headLabel;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JLabel lblUsername;
	private JLabel lblPassword;
	
	private JToggleButton btnFree;
	private JToggleButton btnHobbyist;
	private JToggleButton btnProfessional;
	private JButton btnSignUp;

	private ButtonGroup btnTierGroup;
	

	/**
	 * Create the frame.
	 */
	public SignUp() {

		// Create Frame (x, y, type) and Content Pane
		super(500, 350, Type.POPUP);	
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Title
		setTitle("Sign Up");
		
		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{30, 200, 50, 120, 30, 0};
		gbl_contentPane.rowHeights = new int[]{50, 60, 0, 60, 0, 60, 19, 60, 60, 0};

		contentPane.setLayout(gbl_contentPane);
		
		// Initialize Components
		initializeComponents(contentPane);		
		
		// Add Button Group
		btnTierGroup = new ButtonGroup();
		btnTierGroup.add(btnFree);
		btnTierGroup.add(btnHobbyist);
		btnTierGroup.add(btnProfessional);
	}

	
	/**
	 * Initialize Components on the Panel
	 * 
	 */
	@Override
	public void initializeComponents(JPanel jPanel) {
		
		addComponent(jPanel, generateHeadingLabel());
		
		addComponent(jPanel, generateUsernameLabel());		
		addComponent(jPanel, generateUsernameTextBox());
		
		addComponent(jPanel, generatePasswordLabel());
		addComponent(jPanel, generatePasswordTextBox());
		
		addComponent(jPanel, generateSignUpButton());
		
		addComponent(jPanel, generateFreeTierButton());
		addComponent(jPanel, generateHobbyistTierButton());
		addComponent(jPanel, generateProfessionalTierButton());
	}
	
	
	/*
	 * Components
	 * 
	 * Heding Label
	 * Username Label
	 * Userame TxtBox
	 * Password Label
	 * Password TxtBox
	 * SignUp Button
	 * Free Tier Button
	 * Hobbyist Button
	 * Professional Button
	 * 
	 */
	
	
	/**
	 * Generate Heading Label configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateHeadingLabel() {
		
		// Head Label
		headLabel = new JLabel("PhotoCloud Editor");
		headLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		
		// Head Label Configuration
		GridBagConstraints gbc_HeadLabel = new GridBagConstraints();
		gbc_HeadLabel.insets = new Insets(0, 0, 20, 0);
		gbc_HeadLabel.gridx = 1;
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
		gbc_lblUsername.insets = new Insets(0, 0, 0, 0);
		gbc_lblUsername.anchor = GridBagConstraints.BASELINE;
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 2;
		
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
		gbc_txtUsername.insets = new Insets(10, 5, 10, 5);
		gbc_txtUsername.fill = GridBagConstraints.BOTH;
		gbc_txtUsername.gridx = 1;
		gbc_txtUsername.gridy = 3;
		
		return new ComponentConfiguration(txtUsername, gbc_txtUsername);	
	}

	
	/**
	 * Generate Password Label configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generatePasswordLabel() {
		
		lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		//lblPassword.setVerticalAlignment(SwingConstants.SOUTH);
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 10));
		
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 0, 0);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 4;
		
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
		
		// Password Text Box Placement
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.fill = GridBagConstraints.BOTH;
		gbc_txtPassword.insets = new Insets(10, 5, 10, 5);
		gbc_txtPassword.gridx = 1;
		gbc_txtPassword.gridy = 5;
		
		return new ComponentConfiguration(txtPassword, gbc_txtPassword);
	}
	
	
	/**
	 * Generate Sing Up Button configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateSignUpButton() {

		// SignUp Button
		btnSignUp = new RoundedJButton(20);
		btnSignUp.setText("Sign Up");
		btnSignUp.setFont(new Font("Ariel", Font.BOLD, 12));
		btnSignUp.setForeground(Colors.BROKEN_WHITE);
		btnSignUp.setBackground(Colors.BRUNSWICK_GREEN);
		
		// SignUp Button Placement
		GridBagConstraints gbc_btnSignUp = new GridBagConstraints();
		gbc_btnSignUp.fill = GridBagConstraints.BOTH;
		gbc_btnSignUp.insets = new Insets(10, 20, 10, 20);
		gbc_btnSignUp.gridx = 1;
		gbc_btnSignUp.gridy = 7;
		
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("signup");
			}
		});
		
		return new ComponentConfiguration(btnSignUp, gbc_btnSignUp);
	}
	
	
	/**
	 * Generate Free Tier Button configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateFreeTierButton() {
		
		// Free Tier Button
		btnFree = new RoundedJToggleButton(10);
		btnFree.setText("FREE");
		btnFree.setFont(new Font("Ariel", Font.BOLD, 20));
		btnFree.setForeground(Colors.BROKEN_WHITE);
		btnFree.setBackground(Colors.CHARCOAL_GRAY);
		
		// Free Tier Button Placement
		GridBagConstraints gbc_btnFree = new GridBagConstraints();
		gbc_btnFree.fill = GridBagConstraints.BOTH;
		gbc_btnFree.insets = new Insets(5, 0, 5, 0);
		gbc_btnFree.gridx = 3;
		gbc_btnFree.gridy = 1;
		
		return new ComponentConfiguration(btnFree, gbc_btnFree);
	}
	
	
	/**
	 * Generate Free Tier Button configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateHobbyistTierButton() {
		
		// Hobbyist Tier Button
		btnHobbyist = new RoundedJToggleButton(10);
		btnHobbyist.setText("HOBBYIST");
		btnHobbyist.setFont(new Font("Ariel", Font.BOLD, 20));
		btnHobbyist.setForeground(Colors.BROKEN_WHITE);
		btnHobbyist.setBackground(Colors.CHARCOAL_GRAY);
		
		// Hobbyist Tier Button Placement
		GridBagConstraints gbc_btnHobbyist = new GridBagConstraints();
		gbc_btnHobbyist.fill = GridBagConstraints.BOTH;
		gbc_btnHobbyist.insets = new Insets(5, 0, 5, 0);
		gbc_btnHobbyist.gridx = 3;
		gbc_btnHobbyist.gridy = 3;
		
		return new ComponentConfiguration(btnHobbyist, gbc_btnHobbyist);
	}
	
	
	/**
	 * Generate Professional Tier Button configuration
	 * @return ComponentConfiguration
	 */
	private ComponentConfiguration generateProfessionalTierButton() {
		
		// Hobbyist Tier Button
		btnProfessional = new RoundedJToggleButton(10);
		btnProfessional.setText("PROFESSIONAL");
		btnProfessional.setFont(new Font("Ariel", Font.BOLD, 20));
		btnProfessional.setForeground(Colors.BROKEN_WHITE);
		btnProfessional.setBackground(Colors.CHARCOAL_GRAY);
		
		// Hobbyist Tier Button Placement
		GridBagConstraints gbc_btnProfessional = new GridBagConstraints();
		gbc_btnProfessional.fill = GridBagConstraints.BOTH;
		gbc_btnProfessional.insets = new Insets(5, 0, 5, 0);
		gbc_btnProfessional.gridx = 3;
		gbc_btnProfessional.gridy = 5;
		
		return new ComponentConfiguration(btnProfessional, gbc_btnProfessional);
	}
}
