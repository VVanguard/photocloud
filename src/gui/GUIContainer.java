package gui;

import java.util.ArrayList;

import util.customframes.FrameFactory;

public class GUIContainer {
	
	private static Login logIn = new Login();
	private static SignUp signUp = new SignUp(); 

	private static ArrayList<FrameFactory> frames = new ArrayList<>();
	
	
	/**
	 * Constructor of GUI Container
	 * Adds the frames to the list
	 */
	public GUIContainer() {
		frames.add(logIn);
		frames.add(signUp);
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
	
	
	//
	// Getters 
	//
	
	public static Login getLogIn() {
		return logIn;
	}
	

	public static SignUp getSignUp() {
		return signUp;
	}
}
