package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import baselogger.BaseLogger;
import image.ImageEnum;
import image.PhotocloudImage;
import user.User;
import util.Colors;
import util.ComponentConfiguration;
import util.ComponentGenerator;
import util.customcomponents.RoundedJButton;
import util.customcomponents.RoundedJTextField;
import util.customframes.FrameFactory;
import util.exceptions.InvalidFieldEntryException;
import util.image.ImageOperations;

public class DiscoveryPage extends FrameFactory {
	
	// Logger
	private BaseLogger baseLogger = new BaseLogger();

	// User
	User currentUser;
	public boolean isUserSelf;

	// Components
	private RoundedJTextField txtSearch;
	private RoundedJButton btnSearch;
	private RoundedJButton btnUser;

	private JPanel scrollPanePanel;
	private JScrollPane scrollPanePhotos;

	// Configurations
	private ComponentConfiguration<RoundedJTextField> txtSearchConfiguration = ComponentGenerator.generateRoundedTextField( 
			txtSearch, 15, Colors.DIM_GRAY, new Insets(15, 10, 20, 10), 3, 2);

	private ComponentConfiguration<RoundedJButton> btnSearchConfiguration = ComponentGenerator.generateRoundedButton(
			btnSearch, 15, "", new Font("Ariel", Font.BOLD, 12), Colors.GHOST_WHITE, Colors.DIM_GRAY, new Insets(15, 10, 20, 10), 2, 2);

	private ComponentConfiguration<RoundedJButton> btnUserConfiguration = ComponentGenerator.generateRoundedButton(
			btnUser, 15, "", new Font("Ariel", Font.BOLD, 12), Colors.GHOST_WHITE, Colors.DIM_GRAY, new Insets(15, 10, 20, 10), 1, 2);
	
	private ComponentConfiguration<JLabel> lblAppConfiguration = ComponentGenerator.generateCenteredLabel(
			"PhotoCloud", new Font("Arial", Font.PLAIN, 40), new Insets(30, 0, 20, 0), 1, 1);

	
	/**
	 * Initialize Pane
	 * 
	 */
	public DiscoveryPage(User currentUser) {
		super(475, 825, Type.POPUP);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{25, 200, 20, 200, 25};
		gbl_contentPane.rowHeights = new int[]{50, 50, 0, 0, 75, 25, 25, 475, 50, 25};
		
		contentPane.setLayout(gbl_contentPane);
		
		// Initialize Components
		this.currentUser = currentUser;
		initializeComponents(contentPane);
		
		// Import Search Icon
		try {
			btnSearch.setIcon(new ImageIcon(ImageOperations.resizeSquare(ImageOperations.readNewImageFromUser("resources/pictures/search_icon.png"), 20)));
		} catch (IOException e) {
			baseLogger.error().log("Failed Import: search_icon.png at pictures");
		}
		
		if (currentUser != null) {
			btnUser.setText(currentUser.getUsername());
		}
		
		// Set Frame Status
		setFrameStatus(FrameStatus.HIDE);
		
		//Display images on the database
		displayImages();
		
		
		/*
		 * Action Listeners
		 *  
		 */
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
					GUIContainer.updateInOrderComment();

					baseLogger.info().log("Entered to profile: " + txtSearch.getText());

				} catch (Exception error) {
					txtSearch.setText("No User Found!!");
					baseLogger.error().log("No User such: " + txtSearch.getText());
				}

			}
		});
		
		// Profile Page
		btnUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIContainer.getProfilePage().dispose();
				GUIContainer.updateProfilePage(currentUser.getUsername(), true);
				GUIContainer.getProfilePage().setFrameStatus(FrameStatus.VISIBLE);
				GUIContainer.getDiscoveryPage().setFrameStatus(FrameStatus.HIDE);
				GUIContainer.updateGUI();
			}
		});
	}

	
	/**
	 * Initializes Components
	 * 
	 */
	@Override
	public void initializeComponents(JPanel jPanel) {
		
		lblAppConfiguration.getGridBagConstraints().gridwidth = 3;
		addComponent(jPanel, lblAppConfiguration);
		
		txtSearch = txtSearchConfiguration.getComponent();
		addComponent(jPanel, txtSearchConfiguration);
		
		btnSearch = btnSearchConfiguration.getComponent();
		addComponent(jPanel, btnSearchConfiguration);
		
		// User Button
		btnUser = btnUserConfiguration.getComponent();
		addComponent(jPanel, btnUserConfiguration);
		
		// Scroll Pane Grid - 45 photos max per application
		GridBagLayout gbc_scrollPanePanel = new GridBagLayout();
		gbc_scrollPanePanel.columnWidths = new int[] {140,140,140};
		gbc_scrollPanePanel.rowHeights = new int[] {140, 140, 140, 140, 140, 140, 140, 140, 140, 140, 140, 140, 140, 140, 140};
		scrollPanePanel = new JPanel(gbc_scrollPanePanel);
		scrollPanePanel.setBackground(Colors.BROKEN_WHITE);

		// Scroll Pane
		scrollPanePhotos = new JScrollPane(scrollPanePanel);
		scrollPanePhotos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanePhotos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridy = 4;
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 10, 0);
		getContentPane().add(scrollPanePhotos, gbc_scrollPane);
	}
	
	
	/**
	 * Displays images that are read from user database
	 * 
	 */
	private void displayImages() {
		
		String DATA_PATH = "resources/users/sharedpicture.txt";
		File publicImagesFile = new File(DATA_PATH);
		
		Scanner fileScanner;
		
		ImagePanel imagePanel;
		boolean isDeleted = false;
		ImageEnum imageEnum;
		
		// Image Panel Count
		int count = 0;
		
		// Try reading shared pictures
		try {
			
			fileScanner = new Scanner(publicImagesFile);
			
			// WHile there is images shared
			while (fileScanner.hasNext()) {
				
				// Try creating an image panel
				try {
					// Public Image Data
					String[] publicImageData = fileScanner.nextLine().split(" ");

					String username = publicImageData[0];
					String imageDataPath = publicImageData[1];

					// Image Data File
					File imageDataFile = new File("resources/users/" + username + "/picturedata/" + imageDataPath + ".txt");
					
					Scanner dataScanner = new Scanner(imageDataFile);

					// [username][imgPath][Visibility]
					String[] firstLineData = dataScanner.nextLine().split(" ");

					// If deleted
					if (firstLineData[0].matches("deleted")) {
						System.out.println("deleted found");
						isDeleted = true;
					}

					String thumbnail = dataScanner.nextLine();
					String caption = dataScanner.nextLine();

					String[] likeDislikeCounts = dataScanner.nextLine().split(" ");

					// Comments
					ArrayList<String> comments = new ArrayList<String>();

					// Save Comments
					while (dataScanner.hasNextLine()) {
						String commentLine = dataScanner.nextLine();
						comments.add(commentLine);
					}

					dataScanner.close();

					// Convert String to Enumerator
					if (firstLineData[2].matches("PRIVATE")) {
						imageEnum = ImageEnum.PRIVATE;
					} else if (firstLineData[2].matches("PUBLIC")) {
						imageEnum = ImageEnum.PUBLIC;
					} else {
						imageEnum = ImageEnum.NULL;
					}

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

					// Add listener to enlarge the image
					imagePanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							ImageDisplay imageDisplay = new ImageDisplay(pImage, currentUser);
							imageDisplay.setVisible(true);	
						}
					});
					
					if (pImage.getImageEnum() != ImageEnum.NULL) {
						// Place the panel
						GridBagConstraints gbc = new GridBagConstraints();
						gbc.gridx = count % 3;
						gbc.gridy = count / 3;
						scrollPanePanel.add(imagePanel, gbc);
						count++;
					}
				} catch (Exception e) {
					if (!isDeleted) {
						e.printStackTrace();
						baseLogger.error().log("Failed to display image at line " + count + " in sharedpicture!"  );
					}
				}	
			}
		} catch (FileNotFoundException e1) {
			baseLogger.error().log("Failed to access: " + DATA_PATH);
		}
	}
}
