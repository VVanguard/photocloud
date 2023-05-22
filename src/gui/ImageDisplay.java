package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

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
import util.image.ImageOperations;

public class ImageDisplay extends FrameFactory {
	
	// User 
	private User user;

	// Loggers
	private BaseLogger baseLogger = new BaseLogger();
	
	// Logic Variables
	private boolean isLikedOrDisliked = false;

	// Components
	private JLabel lblImage;
	
	private RoundedJTextField txtThumbnail;
	private RoundedJTextField txtCaption;
	private RoundedJButton btnChangeVisibility;
	private RoundedJButton btnDeleteImage;

	private JLabel lblProfilePicture;
	private JLabel lblUsername;
	private RoundedJButton btnLike;
	private RoundedJButton btnDislike;
	
	private JPanel scrollPanePanel;
	private JScrollPane scrollPaneComments;
	private RoundedJTextField txtComment;
	private RoundedJButton btnComments;
	
	// Configurations
	ComponentConfiguration<JLabel> lblImageConfiguration = ComponentGenerator.generateCenteredLabel(
			"", null, new Insets(0, 0, 0, 0), 2, 1);
	
	ComponentConfiguration<RoundedJTextField> txtThumbnailConfiguration = ComponentGenerator.generateRoundedTextField(
			txtThumbnail, 25, Colors.BROKEN_WHITE, new Insets(10, 20, 30, 0), 3, 14);

	ComponentConfiguration<RoundedJTextField> txtCaptionConfiguration = ComponentGenerator.generateRoundedTextField(
			txtCaption, 25, Colors.BROKEN_WHITE, new Insets(10, 0, 30, 0), 5, 14);

	ComponentConfiguration<RoundedJButton> btnChangeVisibilityConfiguration = ComponentGenerator.generateRoundedButton(
			btnChangeVisibility, 10, "", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(10, 5, 30, 5), 6, 14);

	ComponentConfiguration<RoundedJButton> btnDeleteConfiguration = ComponentGenerator.generateRoundedButton(
			btnDeleteImage, 10, "Delete", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Color.red, new Insets(10, 5, 30, 75), 7, 14);
	
	ComponentConfiguration<JLabel> lblProfilePictureConfiguration = ComponentGenerator.generateCenteredLabel(
			"", null, new Insets(0, 0, 20, 0), 10, 1);
	
	ComponentConfiguration<JLabel> lblUsernameConfiguration = ComponentGenerator.generateCenteredLabel(
			"", new Font("Ariel", Font.BOLD, 16), new Insets(0, 0, 20, 0), 10, 2);
	
	ComponentConfiguration<RoundedJButton> btnLikeConfiguration = ComponentGenerator.generateRoundedButton(
			btnLike, 10, "", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.BRUNSWICK_GREEN, new Insets(0, 60, 20, 10), 10, 3);
	
	ComponentConfiguration<RoundedJButton> btnDislikeConfiguration = ComponentGenerator.generateRoundedButton(
			btnDislike, 10, "", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Color.red, new Insets(0, 0, 20, 60), 11, 3);
	
	ComponentConfiguration<RoundedJTextField> txtCommentConfiguration = ComponentGenerator.generateRoundedTextField(
			txtCaption, 25, Colors.BROKEN_WHITE, new Insets(10, 30, 10, 30), 10, 14);
	
	ComponentConfiguration<RoundedJButton> btnCommentsConfiguration = ComponentGenerator.generateRoundedButton(
			btnComments, 10, "Comment", new Font("Ariel", Font.BOLD, 12), Colors.BROKEN_WHITE, Colors.DIM_GRAY, new Insets(0, 60, 50, 60), 10, 15);

	// Images
	private BufferedImage bfImgScaled;
	private BufferedImage bfProfilePicture;
	private PhotocloudImage pImage;
	
	/**
	 * Initializes image display panel
	 * 
	 * @param image 	image to be displayed
	 */
	public ImageDisplay(PhotocloudImage image, User currentUser) {
		super(1700, 1000, Type.POPUP);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Content Pane Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{10, 0, 100, 250, 100, 470, 100, 100, 100, 20, 100, 100};
		gbl_contentPane.rowHeights = new int[]{25, 260, 25, 50, 50, 25, 50, 50, 25, 50, 50, 50, 10, 160, 50, 50};
		setBackground(Colors.GHOST_WHITE);
		
		contentPane.setLayout(gbl_contentPane);
		
		// Declare user
		setUser(currentUser);
		pImage = image;
		
		// Initialize Components
		initializeComponents(contentPane);
		
		// Display Comments in the database
		displayComments();
		
		// Create Buffered Images
		try {
			GUIContainer.getProfilePage().displayFileError("");
			bfImgScaled = ImageOperations.scaleForDisplay(ImageOperations.readNewImageFromUser(image.getImgPath()), 1300, 880);
			User imageUser = UserOperations.getUserFromDatabase(image.getUsername());
			bfProfilePicture = ImageOperations.resizeSquare(ImageOperations.readNewImageFromUser(imageUser.getImgPath()), 200);
		} catch (NullPointerException e) {
			baseLogger.error().log("Failed to read Image: " + image.getImgPath());
			GUIContainer.getProfilePage().displayFileError("Cose a jpg!");
		} catch (IOException e) {
			baseLogger.error().log("Failed to read Image: " + image.getImgPath());
			GUIContainer.getProfilePage().displayFileError("Cose a jpg!");
		}
		
		// Set Icons
		lblImage.setIcon(new ImageIcon(bfImgScaled));
		lblProfilePicture.setIcon(new ImageIcon(bfProfilePicture));
		
		// Set Username
		lblUsername.setText(image.getUsername());

		// Set Like & Dislike Texts
		btnLike.setText("  Likes: " + image.getLikeCount() + "  ");
		btnDislike.setText("Dislikes: " + image.getDislikeCount());
		
		//Set Thumbnail and Caption
		txtThumbnail.setText("  " + image.getThumbnail());
		txtCaption.setText("  " + image.getCaption());
		
		// Set the frame visible
		setFrameStatus(FrameStatus.VISIBLE);
		
		/**
		 * Action Listeners
		 * 
		 */
		

		// Comment
		btnComments.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		
				// Create new Comment Panel
				CommentPanel commentPanel = new CommentPanel(user.getUsername(), txtComment.getText());
				
				/*
				 * Add Mouse Listeners to Comment Panel
				 */
				commentPanel.addMouseListener(new MouseAdapter() {
		
					@Override
					public void mouseClicked(MouseEvent e) {
	
						// Generate new profile page
						GUIContainer.getProfilePage().dispose();
						dispose();
						GUIContainer.updateProfilePage(commentPanel.getUsername(), commentPanel.getUsername().matches(user.getUsername()));
						GUIContainer.getProfilePage().setFrameStatus(FrameStatus.VISIBLE);
						GUIContainer.updateGUI();
					}
				});
				
				// Add comment to scroll pane
				if (!commentPanel.getComment().matches("")) {
					scrollPanePanel.add(commentPanel);
					baseLogger.info().log(user.getUsername() + " commented on: " + image.getThumbnail() + " of user " + image.getUsername());
				}
			}
		});
		
		// Like
		btnLike.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isLikedOrDisliked) {
					image.likeImage();
					btnLike.setText("  Likes: " + image.getLikeCount() + "  ");
					isLikedOrDisliked = true;
				}
				
			}
		});
		
		// Dislike
		btnDislike.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isLikedOrDisliked) {
					image.dislikeImage();
					btnDislike.setText("Dislikes: " + image.getDislikeCount());
					isLikedOrDisliked = true;
				}
			}
		});
		
		
		/**
		 * Window Listener
		 * 
		 * Update Image Data
		 *
		 */
		
		this.addWindowListener(new WindowAdapter() {		
			@Override
			public void windowClosed(WindowEvent e) {
				
				// Update Comments
				ArrayList<String> comments = image.getComments();
				comments.clear();
				
				// Get comment count
				int commentCount = scrollPanePanel.getComponentCount();
				
				// Add every new comment to comments
				for (int commentId = 0; commentId < commentCount; commentId++) {
					CommentPanel commentPanel = (CommentPanel) scrollPanePanel.getComponent(commentId);
					comments.add(commentPanel.getUsername() + " " + commentPanel.getComment());
				}	
				
				// Update Image Data
				try {
					ImageOperations.writePictureData(image);
				} catch (IOException e1) {
					e1.printStackTrace();
					baseLogger.info().log("Image Updated: " + image.getImageUUID());
				}
			}
		});
	}


	@Override
	public void initializeComponents(JPanel jPanel) {
		/*
		 * Components 
		 *
		 * 
		 * 
		 */
		
		// Image Label
		lblImage = lblImageConfiguration.getComponent();
		lblImageConfiguration.getGridBagConstraints().gridwidth = 7;
		lblImageConfiguration.getGridBagConstraints().gridheight = 13;
		addComponent(jPanel, lblImageConfiguration);
		
		// Thumbnail
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Thumbnail", new Font("Ariel", Font.BOLD, 12), new Insets(10, 80, 30, 0), 2, 14));
		
		txtThumbnail = txtThumbnailConfiguration.getComponent();
		txtThumbnail.setEditable(false);
		addComponent(jPanel, txtThumbnailConfiguration);
		
		// Caption
		txtCaption = txtCaptionConfiguration.getComponent();
		txtCaption.setEditable(false);
		addComponent(jPanel, txtCaptionConfiguration);
		
		addComponent(jPanel, ComponentGenerator.generateCenteredLabel(
				"Caption", new Font("Ariel", Font.BOLD, 12), new Insets(10, 50, 30, 0), 4, 14));
		
		// Visibility/Delete Buttons	
		if (GUIContainer.getCurrentUser().getUsername().matches(pImage.getUsername()) || user.getUsername().matches("admin")) {
			
			btnChangeVisibility = btnChangeVisibilityConfiguration.getComponent();
			addComponent(jPanel, btnChangeVisibilityConfiguration);
			
			btnDeleteImage = btnDeleteConfiguration.getComponent();
			btnDeleteConfiguration.getGridBagConstraints().gridwidth = 2;
			addComponent(jPanel, btnDeleteConfiguration);
			
			// Set Change Visibility Button Text
			if (pImage.getImageEnum() == ImageEnum.PRIVATE) {
				btnChangeVisibility.setText("Make Public");
			} else {
				btnChangeVisibility.setText("Make Private");
			}
			
			/*
			 * Action Listeners
			 */
			// Delete
			btnDeleteImage.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						ImageOperations.deletePicture(pImage);
						dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
						baseLogger.error().log("Failed to delete image at: " + pImage.getImgPath());
					}
				}
			});
			
			// Change Visibility
			btnChangeVisibility.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if (pImage.getImageEnum() == ImageEnum.PRIVATE) {
							pImage.setImageEnum(ImageEnum.PUBLIC);
						} else {
							pImage.setImageEnum(ImageEnum.PRIVATE);
						}
						
						ImageOperations.writePictureData(pImage);
						dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		
		
		
		// Profile Picture
		lblProfilePicture = lblProfilePictureConfiguration.getComponent();
		lblProfilePictureConfiguration.getGridBagConstraints().gridwidth = 2;
		addComponent(jPanel, lblProfilePictureConfiguration);
		
		lblUsername = lblUsernameConfiguration.getComponent();
		lblUsernameConfiguration.getGridBagConstraints().gridwidth = 2;
		addComponent(jPanel, lblUsernameConfiguration);
		
		// Like & Dislike Buttons
		btnLike = btnLikeConfiguration.getComponent();
		btnLikeConfiguration.getGridBagConstraints().fill = GridBagConstraints.BOTH;
		
		btnDislike = btnDislikeConfiguration.getComponent();
		btnDislikeConfiguration.getGridBagConstraints().fill = GridBagConstraints.BOTH;
		
		addComponent(contentPane, btnLikeConfiguration);
		addComponent(contentPane, btnDislikeConfiguration);
		
		// Scroll Pane Grid - 20 comments max
		GridLayout gridLayout = new GridLayout(20, 1);
		scrollPanePanel = new JPanel(gridLayout);

		// Scroll Pane
		scrollPaneComments = new JScrollPane(scrollPanePanel);
		scrollPaneComments.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneComments.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridx = 10;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridy = 4;
		gbc_scrollPane.gridheight = 10;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 20, 10, 20);
		getContentPane().add(scrollPaneComments, gbc_scrollPane);
		
		// Comments
		txtComment = txtCommentConfiguration.getComponent();
		txtCommentConfiguration.getGridBagConstraints().gridwidth = 2;
		addComponent(jPanel, txtCommentConfiguration);
		
		btnComments = btnCommentsConfiguration.getComponent();
		btnCommentsConfiguration.getGridBagConstraints().gridwidth = 2;
		addComponent(jPanel, btnCommentsConfiguration);

	}
	
	
	/**
	 * Displays the comments in the database
	 */
	public void displayComments() {
		ArrayList<String> comments = pImage.getComments();
		
		// Iterate through comments and add to scroll panel
		for (String commentLine : comments) {
			
			// Split username and comment
			String[] commentData = commentLine.split(" ", 2);
			
			CommentPanel commentPanel = new CommentPanel(commentData[0], commentData[1]);
			
			/*
			 * Add Mouse Listeners to Comment Panel
			 */
			commentPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					
					// Update Comments
					ArrayList<String> comments = pImage.getComments();
					
					// Get comment count
					int commentCount = scrollPanePanel.getComponentCount();
					comments.clear();
					
					// Add every new comment to comments
					for (int commentId = 0; commentId < commentCount; commentId++) {
						CommentPanel commentPanel = (CommentPanel) scrollPanePanel.getComponent(commentId);
						comments.add(commentPanel.getUsername() + " " + commentPanel.getComment());
					}	
					
					// Update Image Data
					try {
						ImageOperations.writePictureData(pImage);
					} catch (IOException e1) {
						e1.printStackTrace();
						baseLogger.info().log("Image Updated: " + pImage.getImageUUID());
					}
					
					// Generate new profile page
					GUIContainer.getProfilePage().dispose();
					dispose();
					GUIContainer.updateProfilePage(commentPanel.getUsername(), commentPanel.getUsername().matches(user.getUsername()));
					GUIContainer.getProfilePage().setFrameStatus(FrameStatus.VISIBLE);
					GUIContainer.updateGUI();
				}
			});
			
			scrollPanePanel.add(commentPanel);
		}
	}
	
	
	//
	// Getters & Setters
	//
	
	private void setUser(User user) {
		this.user = user;
	}
}
