package gui;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customframes.PanelFactory;


public class CommentPanel extends PanelFactory {

	// Components
	private JLabel username;
	private JLabel comment;
	
	// Configurations
	private ComponentConfiguration<JLabel> usernameConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.BOLD, 10), new Insets(0, 0, 0, 0), 0, 0);
	
	private ComponentConfiguration<JLabel> commentConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 10), new Insets(0, 0, 0, 0), 0, 1);
	
	
	public CommentPanel(String usernameText, String commentText) {
		super(140, 30);

		GridBagLayout gbl_ImagePanel = new GridBagLayout();
		gbl_ImagePanel.columnWidths = new int[]{275};
		gbl_ImagePanel.rowHeights = new int[]{15, 15};

		setLayout(gbl_ImagePanel);

		initializeComponents(this);
		
		username.setText(usernameText);
		comment.setText(commentText);
	}

	
	/**
	 * Initializes Components
	 * 
	 */
	@Override
	public void initializeComponents(JPanel jPanel) {
		
		username = usernameConfiguration.getComponent();
		addComponent(jPanel, usernameConfiguration);
		
		comment = commentConfiguration.getComponent();
		addComponent(jPanel, commentConfiguration);	
	}
	
	
	//
	// Getters
	//
	
	
	public String getUsername() {
		return username.getText();
	}
	
	
	public String getComment() {
		return comment.getText();
	}
}
