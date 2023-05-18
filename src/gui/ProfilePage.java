package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import baselogger.BaseLogger;
import user.User;
import user.UserOperations;
import util.Colors;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customcomponents.RoundedJButton;
import util.customcomponents.RoundedJTextField;
import util.customframes.FrameFactory;
import util.image.ImageOperations;


public class ProfilePage extends FrameFactory {

	// Logger
	private BaseLogger baseLogger = new BaseLogger();
	
	// User
	User user;
	
	// Components
	private RoundedJTextField txtSearch;
	private JLabel lblSearch;
	private RoundedJButton btnDiscovery;
	private JLabel lblPPImg;
	private JLabel lblUsername;
	private JLabel lblTier;
	private RoundedJButton btnEditInfo;
	
	private JScrollPane scrollPaneUserPhotos;
	
	private JPanel panelAddMore;
	
	// Configurations
	private ComponentConfiguration txtSearchConfiguration = ComponentGenerator.generateRoundedTextField( 
			txtSearch, 15, Colors.DIM_GRAY, new Insets(5, 10, 30, 10), 3, 1);
	
	private ComponentConfiguration lblSearchConfiguration = ComponentGenerator.generateCenteredLabel(
			"", null, new Insets(0, 20, 25, 0), 2, 1);
	
	private ComponentConfiguration btnDiscoveryConfiguration = ComponentGenerator.generateRoundedButton(
			btnDiscovery, 15, "Discovery", new Font("Ariel", Font.BOLD, 12), Colors.GHOST_WHITE, Colors.DIM_GRAY, new Insets(5, 10, 30, 10), 1, 1);
	
	private ComponentConfiguration lblPPImgConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 10), new Insets(50, 0, 0, 0), 1, 1);
	
	private ComponentConfiguration lblUsernameConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 20), new Insets(25, 0, 0, 0), 3, 2);
	
	private ComponentConfiguration lblTierConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.BOLD, 14), new Insets(0, 0, 5, 0), 3, 3);
	
	private ComponentConfiguration btnEditInfoConfiguration = ComponentGenerator.generateRoundedButton(
			btnEditInfo, 5, "Edit Info", new Font("Ariel", Font.BOLD, 10), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(0, 50, 0, 50), 3, 5);
	
	
	/**
	 * Create Pane
	 * 
	 * @param username	username of the profile
	 */
	public ProfilePage(String username) {
		super(475, 775, Type.POPUP);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{25, 200, 25, 200, 25};
		gbl_contentPane.rowHeights = new int[]{50, 25, 25, 25, 75, 25, 25, 500, 25};
		
		contentPane.setLayout(gbl_contentPane);
		
		// Initialize Components
		initializeComponents(contentPane);
		
		// Get User from username
		try {
			user = UserOperations.getUserFromDatabase(username);
		} catch (Exception e) {
			baseLogger.error().log("Failed to get User from username: " + username);
		}
		
		//Read User Profile Picture
		try {
			lblPPImg.setIcon(new ImageIcon(ImageOperations.resizeSquare(ImageOperations.readNewImageFromUser(user.getImgPath()), 180)));
		} catch (IOException e) {
			baseLogger.error().log("Failed to upload profile picture!");
		}
		
		// Import Search Icon
		try {
			lblSearch.setIcon(new ImageIcon(ImageOperations.resizeSquare(ImageOperations.readNewImageFromUser("resources/pictures/search_icon.png"), 20)));
		} catch (IOException e) {
			baseLogger.error().log("Failed Import: search_icon.png at pictures");
		}
		
		// Print User Details to Screen
		lblUsername.setText(user.getUsername());
		lblTier.setText(user.getTier().toString().toUpperCase());
		
		// Set Frame Status
		setFrameStatus(FrameStatus.HIDE);
		baseLogger.info().log("Entered to User Profile: " + username);
		
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
	protected void initializeComponents(JPanel jPanel) {
		
		// Search TextBox
		txtSearch = (RoundedJTextField)txtSearchConfiguration.getComponent();
		addComponent(jPanel, txtSearchConfiguration);
		
		lblSearch = (JLabel)lblSearchConfiguration.getComponent();
		addComponent(jPanel, lblSearchConfiguration);
		
		// Discovery Button
		btnDiscovery = (RoundedJButton)btnDiscoveryConfiguration.getComponent();
		addComponent(jPanel, btnDiscoveryConfiguration);
		
		// Profile Picture Label
		lblPPImg = (JLabel)lblPPImgConfiguration.getComponent();
		lblPPImgConfiguration.getGridBagConstraints().gridheight = 5;
		addComponent(jPanel, lblPPImgConfiguration);
		
		// Username Label
		lblUsername = (JLabel)lblUsernameConfiguration.getComponent();
		addComponent(jPanel, lblUsernameConfiguration);
		
		// Tier Label
		lblTier = (JLabel)lblTierConfiguration.getComponent();
		lblTier.setForeground(Colors.BRUNSWICK_GREEN);
		addComponent(jPanel, lblTierConfiguration);
		
		// Edit Info Button
		btnEditInfo = (RoundedJButton)btnEditInfoConfiguration.getComponent();
		addComponent(jPanel, btnEditInfoConfiguration);
		
		// Scroll Pane
		scrollPaneUserPhotos = new JScrollPane();
		scrollPaneUserPhotos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneUserPhotos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridy = 7;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 35, 0);
		getContentPane().add(scrollPaneUserPhotos, gbc_scrollPane);
	}

}
