package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

import util.customcomponents.RoundedJButton;
import util.customcomponents.RoundedJPasswordField;
import util.customcomponents.RoundedJTextField;
import util.customcomponents.RoundedJToggleButton;


/**
 * Field Generator Class for GUI 
 * @author bkaym
 *
 */
public class ComponentGenerator {
	
	
	/**
	 * Generates a new Jlabel with grid bag constraints
	 * 
	 * @param txt		Text to use in the label
	 * @param font		new Font
	 * @param gridX		X placement in grid bag constraints
	 * @param gridY		Y placement in grid bag constraints
	 * @param insets	new Insets 
	 * 
	 * @return 			returns new Component Configuration
	 */
	public static ComponentConfiguration<JLabel> generateCenteredLabel(
			String txt, Font font, Insets insets, int gridX, int gridY) {
		
		// Centered Label
		JLabel newLabel = new JLabel(txt);
		newLabel.setFont(font);
		
		// Centered Label Configuration
		GridBagConstraints gbc_newLabel = new GridBagConstraints();
		gbc_newLabel.insets = insets;
		gbc_newLabel.gridx = gridX;
		gbc_newLabel.gridy = gridY;
		
		return new ComponentConfiguration<JLabel>(newLabel, gbc_newLabel);
	}

	
	/**
	 * Generates a new Rounded JText Field with grid bag constraints
	 * 
	 * @param jTextField	text field to generate
	 * @param arcL			arc length of the rounded box
	 * @param color			background color 
	 * @param insets		new Insets
	 * @param gridX			X placement in grid bag constraints
	 * @param gridY			Y placement in grid bag constraints
	 * 
	 * @return				returns new Component Configuration
	 */
	public static ComponentConfiguration<RoundedJTextField> generateRoundedTextField(
			RoundedJTextField jTextField, int arcL, Color color, Insets insets, int gridX, int gridY) {
		
		// New Rounded Text Box
		jTextField = new RoundedJTextField(arcL);
		jTextField.setBackground(color);

		// New Rounded Text Box Placement
		GridBagConstraints gbc_newTxtField = new GridBagConstraints();
		gbc_newTxtField.insets = insets;
		gbc_newTxtField.fill = GridBagConstraints.BOTH;
		gbc_newTxtField.gridx = gridX;
		gbc_newTxtField.gridy = gridY;

		return new ComponentConfiguration<RoundedJTextField>(jTextField, gbc_newTxtField);	
	}
	
	
	/**
	 * Generates a new Rounded JPassword Field with grid bag constraints
	 * 
	 * @param jPasswordField	text field to generate
	 * @param arcL				arc length of the rounded box
	 * @param color				background color 
	 * @param insets			new Insets
	 * @param gridX				X placement in grid bag constraints
	 * @param gridY				Y placement in grid bag constraints
	 * 
	 * @return					returns new Component Configuration
	 */
	public static ComponentConfiguration<RoundedJPasswordField> generateRoundedPasswordField(
			RoundedJPasswordField jPasswordField, int arcL, Color color, Insets insets, int gridX, int gridY) {
		
		// Rounded Password Field
		jPasswordField = new RoundedJPasswordField(arcL);
		jPasswordField.setBackground(color);
		
		// Rounded Password Field Placement
		GridBagConstraints gbc_newPasswordField = new GridBagConstraints();
		gbc_newPasswordField.fill = GridBagConstraints.BOTH;
		gbc_newPasswordField.insets = insets;
		gbc_newPasswordField.gridx = gridX;
		gbc_newPasswordField.gridy = gridY;
		
		return new ComponentConfiguration<RoundedJPasswordField>(jPasswordField, gbc_newPasswordField);
	}
	
	
	/**
	 * Generates a new Rounded JButton with grid bag constraints
	 * 
	 * @param jButton			button to be generated
	 * @param arcL				arc length of the rounded box
	 * @param txt				Text to use in the label
	 * @param font				new Font	
	 * @param foregroundColor	foreground color 
	 * @param backgorundColor	background color 
	 * @param insets			new Insets
	 * @param gridX				X placement in grid bag constraints
	 * @param gridY				Y placement in grid bag constraints
	 * 
	 * @return					returns new Component Configuration
	 */
	public static ComponentConfiguration<RoundedJButton> generateRoundedButton(
			RoundedJButton jButton, int arcL, String txt, Font font, Color foregroundColor, Color backgorundColor, Insets insets, int gridX, int gridY) {

		// SignUp Button
		jButton = new RoundedJButton(arcL);
		jButton.setText(txt);
		jButton.setFont(font);
		jButton.setForeground(foregroundColor);
		jButton.setBackground(backgorundColor);
		
		// SignUp Button Placement
		GridBagConstraints gbc_jButton = new GridBagConstraints();
		gbc_jButton.fill = GridBagConstraints.BOTH;
		gbc_jButton.insets = insets;
		gbc_jButton.gridx = gridX;
		gbc_jButton.gridy = gridY;
		
		return new ComponentConfiguration<RoundedJButton>(jButton, gbc_jButton);
	}
	
	
	/**
	 * Generates a new Rounded JToggleButton with grid bag constraints
	 * 
	 * @param jToggleButton			button to be generated
	 * @param arcL				arc length of the rounded box
	 * @param txt				Text to use in the label
	 * @param font				new Font	
	 * @param foregroundColor	foreground color 
	 * @param backgorundColor	background color 
	 * @param insets			new Insets
	 * @param gridX				X placement in grid bag constraints
	 * @param gridY				Y placement in grid bag constraints
	 * 
	 * @return					returns new Component Configuration
	 */
	public static ComponentConfiguration<RoundedJToggleButton> generateRoundedToggleButton(
		RoundedJToggleButton jToggleButton, int arcL, String txt, Font font, Color foregroundColor, Color backgorundColor, Insets insets, int gridX, int gridY) {

		// SignUp Button
		jToggleButton = new RoundedJToggleButton(arcL);
		jToggleButton.setText(txt);
		jToggleButton.setFont(font);
		jToggleButton.setForeground(foregroundColor);
		jToggleButton.setBackground(backgorundColor);
		
		// SignUp Button Placement
		GridBagConstraints gbc_jToggleButton = new GridBagConstraints();
		gbc_jToggleButton.fill = GridBagConstraints.BOTH;
		gbc_jToggleButton.insets = insets;
		gbc_jToggleButton.gridx = gridX;
		gbc_jToggleButton.gridy = gridY;
		
		return new ComponentConfiguration<RoundedJToggleButton>(jToggleButton, gbc_jToggleButton);
	}
}
