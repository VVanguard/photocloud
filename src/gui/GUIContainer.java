package gui;

import java.util.ArrayList;

import user.User;
import util.customframes.FrameFactory;

public class GUIContainer {
	
	// Current User
	private static User currentUser;
	
	// Frames
	private static Login logIn = new Login();
	private static SignUp signUp = new SignUp(); 
	private static ProfilePage profilePage = new ProfilePage("dummyuser", true, currentUser);
	private static ImageEditor imageEditor = new ImageEditor();
	private static EditInfo editInfo = new EditInfo();
	private static DiscoveryPage discoveryPage = new DiscoveryPage(currentUser);
	
	private static ArrayList<FrameFactory> frames = new ArrayList<>();
	
	
	/**
	 * Constructor of GUI Container
	 * Adds the frames to the list
	 */
	public GUIContainer() {
		frames.add(logIn);
		frames.add(signUp);
		frames.add(profilePage);
		frames.add(imageEditor);
		frames.add(editInfo);
		frames.add(discoveryPage);
	}


	/**
	 * Updates the frames by visibility
	 */
	public static void updateGUI() {
		
		// Update Frame Status for each frame
		frames.forEach((frame) -> {
			
			if (frame.getFrameStatus() == FrameStatus.HIDE) {
				frame.setVisible(false);
			} 
			
			else if (frame.getFrameStatus() == FrameStatus.VISIBLE) {
				frame.setVisible(true);
			}
			
			else if (frame.getFrameStatus() == FrameStatus.DISPOSED) {
				frame.dispose();
			}
		});
	}
	
	
	/**
	 * Updates frames when profile page is directed from a comment or label
	 */
	public static void updateInOrderComment() {
		frames.get(5).setVisible(true);
		frames.get(2).setVisible(true);
	}
	
	
	//
	// Getters 
	//
	
	
	public static Login getLogIn() {
		return logIn;
	}
	

	public static SignUp getSignUp() {
		return signUp;
	}
	
	
	public static ProfilePage getProfilePage() {
		return profilePage;
	}
	
	
	public static ImageEditor getImageEditor() {
		return imageEditor;
	}
	
	
	public static EditInfo getEditInfo() {
		return editInfo;
	}
	
	
	public static User getCurrentUser() {
		return currentUser;
	}
	
	
	public static DiscoveryPage getDiscoveryPage() {
		return discoveryPage;
	}
	
	
	//
	// Setters
	//
	
	
	public static void updateProfilePage(String username, boolean isUserSelf) {
		profilePage = new ProfilePage(username, isUserSelf, currentUser);
		// Replace profile page reference
		frames.set(2, profilePage);
	}
	
	
	public static void updateImageEditor(String imgPath, User user) {
		imageEditor = new ImageEditor(imgPath, user);
		// Replace image editor reference
		frames.set(3, imageEditor);
	}
	
	
	public static void updateEditInfo(User user) {
		editInfo = new EditInfo(user);
		frames.set(4, editInfo);
	}
	
	
	public static void updateDiscoveryPage() {
		discoveryPage = new DiscoveryPage(getCurrentUser());
		frames.set(5, discoveryPage);
	}
	
	
	public static void setCurrentUser(User currentUser) {
		GUIContainer.currentUser = currentUser;
	}
}
