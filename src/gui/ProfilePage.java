package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import baselogger.BaseLogger;
import image.ImageEnum;
import image.PhotocloudImage;
import user.User;
import user.UserOperations;
import util.Colors;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customcomponents.RoundedJButton;
import util.customcomponents.RoundedJTextField;
import util.customframes.FrameFactory;
import util.exceptions.InvalidFieldEntryException;
import util.image.ImageOperations;

public class ProfilePage extends FrameFactory {

	// Logger
	private BaseLogger baseLogger = new BaseLogger();
	
	// User
	User user;
	User currentUser;
	public boolean isUserSelf;
	
	// Components
	private RoundedJTextField txtSearch;
	private RoundedJButton btnSearch;
	private RoundedJButton btnDiscovery;
	private JLabel lblPPImg;
	private JLabel lblUsername;
	private JLabel lblTier;
	private RoundedJButton btnEditInfo;
	
	private JPanel scrollPanePanel;
	private JScrollPane scrollPaneUserPhotos;
	
	private RoundedJButton btnAddMore;
	private JLabel lblError;
	
	// Configurations
	private ComponentConfiguration<RoundedJTextField> txtSearchConfiguration = ComponentGenerator.generateRoundedTextField( 
			txtSearch, 15, Colors.DIM_GRAY, new Insets(5, 10, 30, 10), 3, 1);
	
	private ComponentConfiguration<RoundedJButton> btnSearchConfiguration = ComponentGenerator.generateRoundedButton(
			btnSearch, 15, "", new Font("Ariel", Font.BOLD, 12), Colors.GHOST_WHITE, Colors.DIM_GRAY, new Insets(5, 10, 30, 10), 2, 1);
	
	private ComponentConfiguration<RoundedJButton> btnDiscoveryConfiguration = ComponentGenerator.generateRoundedButton(
			btnDiscovery, 15, "Discovery", new Font("Ariel", Font.BOLD, 12), Colors.GHOST_WHITE, Colors.DIM_GRAY, new Insets(5, 10, 30, 10), 1, 1);
	
	private ComponentConfiguration<JLabel> lblPPImgConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 10), new Insets(50, 0, 0, 0), 1, 1);
	
	private ComponentConfiguration<JLabel> lblUsernameConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 20), new Insets(25, 0, 0, 0), 3, 2);
	
	private ComponentConfiguration<JLabel> lblTierConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.BOLD, 14), new Insets(0, 0, 5, 0), 3, 3);
	
	private ComponentConfiguration<RoundedJButton> btnEditInfoConfiguration = ComponentGenerator.generateRoundedButton(
			btnEditInfo, 5, "Edit Info", new Font("Ariel", Font.BOLD, 10), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(0, 50, 0, 50), 3, 5);
	
	private ComponentConfiguration<RoundedJButton> btnAddMoreConfiguration = ComponentGenerator.generateRoundedButton(
			btnAddMore, 20, "+", new Font("Ariel", Font.BOLD, 24), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(0, 0, 25, 0), 2, 8);
	
	private ComponentConfiguration<JLabel> lblErrorConfiguraion = ComponentGenerator.generateCenteredLabel(
			"", new Font("Arial", Font.PLAIN, 12), new Insets(25, 0, 0, 0), 2, 9);
	
	
	/**
	 * Create Pane
	 * 
	 * @param username	username of the profile
	 */
	public ProfilePage(String username, boolean isUserSelf, User currentUser) {
		super(475, 825, Type.POPUP);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{25, 200, 20, 200, 25};
		gbl_contentPane.rowHeights = new int[]{50, 25, 25, 25, 75, 25, 25, 475, 50, 25};
		
		contentPane.setLayout(gbl_contentPane);
		
		// Initialize Components
		this.isUserSelf = isUserSelf;
		this.currentUser = currentUser;
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
			btnSearch.setIcon(new ImageIcon(ImageOperations.resizeSquare(ImageOperations.readNewImageFromUser("resources/pictures/search_icon.png"), 20)));
		} catch (IOException e) {
			baseLogger.error().log("Failed Import: search_icon.png at pictures");
		}
		
		// Print User Details to Screen
		lblUsername.setText(user.getUsername());
		lblTier.setText(user.getTier().toString().toUpperCase());
		
		// Set Frame Status
		setFrameStatus(FrameStatus.HIDE);
		
		if (username != "dummyuser") {
			baseLogger.info().log("Entered to User Profile: " + username);
		}
		
		//Display images on the database
		displayImages();
		
		
		/**
		 * 
		 * Window Listener for closing
		 *
		 */
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent e) {
				setFrameStatus(FrameStatus.HIDE);
			}
		});
		
		
		/**
		 * 
		 * Action Listeners
		 * 
		 */
		
		// Add More Photos
		btnAddMore.addActionListener(new ActionListener() {
			
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
					
					// Create new Edit Window
					// Update Frame Status and navigate to the image editor
					GUIContainer.updateImageEditor(imagePath, user);
					GUIContainer.updateGUI();
				} 
			}
		});
		
		//Edit Info
		btnEditInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIContainer.updateEditInfo(user);
				GUIContainer.getEditInfo().setFrameStatus(FrameStatus.VISIBLE);
				GUIContainer.updateGUI();
			}
		});
		 
		// Search
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				String usernameString = txtSearch.getText();
				try {
					// Create new Profile Page and navigate
					if (usernameString.matches("admin")) {
						throw new InvalidFieldEntryException("");
					}
					
					GUIContainer.updateProfilePage(usernameString, 
							usernameString.matches(GUIContainer.getCurrentUser().getUsername()) || GUIContainer.getCurrentUser().getUsername().matches("admin"));
					GUIContainer.getProfilePage().setFrameStatus(FrameStatus.VISIBLE);
					GUIContainer.updateGUI();

					baseLogger.info().log("Entered to profile: " + txtSearch.getText());

				} catch (Exception error) {
					txtSearch.setText("No User Found!!");
					baseLogger.error().log("No User such: " + txtSearch.getText());
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
		 * Search TxtBox
		 * Search Label
		 * Discovery Button
		 * Profile Picture Label
		 * Username Label
		 * Tier Label
		 * Edit Info Button
		 * Scroll Pane for image display
		 * 
		 */
		
		
		// Search TextBox
		txtSearch = txtSearchConfiguration.getComponent();
		addComponent(jPanel, txtSearchConfiguration);
		
		btnSearch = btnSearchConfiguration.getComponent();
		addComponent(jPanel, btnSearchConfiguration);
		
		// Discovery Button
		btnDiscovery = btnDiscoveryConfiguration.getComponent();
		addComponent(jPanel, btnDiscoveryConfiguration);
		
		// Profile Picture Label
		lblPPImg = lblPPImgConfiguration.getComponent();
		lblPPImgConfiguration.getGridBagConstraints().gridheight = 5;
		addComponent(jPanel, lblPPImgConfiguration);
		
		// Username Label
		lblUsername = lblUsernameConfiguration.getComponent();
		addComponent(jPanel, lblUsernameConfiguration);
		
		// Tier Label
		lblTier = lblTierConfiguration.getComponent();
		lblTier.setForeground(Colors.BRUNSWICK_GREEN);
		addComponent(jPanel, lblTierConfiguration);
		
		// Edit Info Button
		btnEditInfo = btnEditInfoConfiguration.getComponent();
		
		if (isUserSelf) {
			addComponent(jPanel, btnEditInfoConfiguration);
		}
		
		// Scroll Pane Grid - 18 photos max per user
		GridBagLayout gbc_scrollPanePanel = new GridBagLayout();
		gbc_scrollPanePanel.columnWidths = new int[] {140,140,140};
		gbc_scrollPanePanel.rowHeights = new int[] {140, 140, 140, 140, 140, 140};
		scrollPanePanel = new JPanel(gbc_scrollPanePanel);

		// Scroll Pane
		scrollPaneUserPhotos = new JScrollPane(scrollPanePanel);
		scrollPaneUserPhotos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneUserPhotos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridy = 7;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 10, 0);
		getContentPane().add(scrollPaneUserPhotos, gbc_scrollPane);
		
		
		// Add More Button
		btnAddMore = btnAddMoreConfiguration.getComponent();
		
		if (isUserSelf) {
			addComponent(jPanel, btnAddMoreConfiguration);
		}
		
		lblError = lblErrorConfiguraion.getComponent();
		addComponent(jPanel, lblErrorConfiguraion);
	
	}
	
	
	/**
	 * Displays the image error on the profile page
	 * 
	 * @param str	error string
	 */
	public void displayFileError(String str) {
		lblError.setText(str);
	}
	
	
	/**
	 * Displays images that are read from user database
	 * 
	 */
	private void displayImages() {
		
		String DATA_PATH = "resources/users/" + user.getUsername() + "/picturedata/";
		File[] imageDataFileList = new File(DATA_PATH).listFiles();
		
		Scanner fileScanner;
		ImagePanel imagePanel;
		
		boolean isDeleted = false;
		
		// Image Panel Count
		int count = 0;
		
		// Iterate through files
		for (File imageDataFile : imageDataFileList) {
			
			// Initiate file specific scanner
			try {
				fileScanner = new Scanner(imageDataFile);
				isDeleted = false;
				
				// [username][imgPath][Visibility]
				String[] firstLineData = fileScanner.nextLine().split(" ");
				ImageEnum imageEnum;
				
				if (firstLineData[0].matches("deleted")) {
					System.out.println("deleted found");
					isDeleted = true;
				}
				
				String thumbnail = fileScanner.nextLine();
				String caption = fileScanner.nextLine();
				
				String[] likeDislikeCounts = fileScanner.nextLine().split(" ");
				
				// Comments
				ArrayList<String> comments = new ArrayList<String>();
				
				// Save Comments
				while (fileScanner.hasNextLine()) {
					String commentLine = fileScanner.nextLine();
					comments.add(commentLine);
				}
				
				fileScanner.close();
				
				// Convert String to Enumerator
				if (firstLineData[2].matches("PRIVATE")) {
					imageEnum = ImageEnum.PRIVATE;
				} else if (firstLineData[2].matches("PUBLIC")) {
					imageEnum = ImageEnum.PUBLIC;
				} else {
					imageEnum = ImageEnum.NULL;
				}
				
				System.out.println(imageDataFile.getName().replace(".txt", ""));
				
				// Create new Image
				PhotocloudImage pImage = new PhotocloudImage(
						firstLineData[0], 
						firstLineData[1], 
						thumbnail, 
						caption, 
						Integer.valueOf(likeDislikeCounts[0]), Integer.valueOf(likeDislikeCounts[1]), 
						comments, 
						imageEnum,
						imageDataFile.getName().replace(".txt", "")
					);
				
				// Create Panel that contains image
				imagePanel = new ImagePanel(pImage);
				
				
				/*
				 * Add Mouse Listeners to Image Panel
				 * 
				 */
				imagePanel.addMouseListener(new MouseAdapter() {
		
					@Override
					public void mouseClicked(MouseEvent e) {
						ImageDisplay imageDisplay = new ImageDisplay(pImage, currentUser);
						imageDisplay.setVisible(true);
					}
				});
				
				
				// If user is self add every panel
				if (isUserSelf) {
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.gridx = count % 3;
					gbc.gridy = count / 3;
					scrollPanePanel.add(imagePanel, gbc);
					//scrollPanePanel.add(imagePanel);
				} 
				
				// If user is not self add only public panels
				else {
					if (imageEnum == ImageEnum.PUBLIC) {
						GridBagConstraints gbc = new GridBagConstraints();
						gbc.gridx = count % 3;
						gbc.gridy = count / 3;
						scrollPanePanel.add(imagePanel, gbc);
					}
				}
				
				// Increase Count
				count++;
			} catch (Exception e) {
				if (!isDeleted) {
					baseLogger.error().log("Failed to read image data file: " + imageDataFile.getName());
				}
			}
		}
	
	
	}

}
