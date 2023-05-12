package gui;

import java.awt.EventQueue;

import java.io.IOException;

import baselogger.BaseLogger;

/************** Pledge of Honor ******************************************
I hereby certify that I have completed this programming project on my own
without any help from anyone else. The effort in the project thus belongs
completely to me. I did not search for a solution, or I did not consult any
program written by others or did not copy any program from other sources. I
read and followed the guidelines provided in the project description.
READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
SIGNATURE: <Bora Kaymakçıoğlu, 82860>
*************************************************************************/

public class Main {
	
	// Frames
	private static Login login = new Login();
	
	//Logger
	public BaseLogger baseLogger = new BaseLogger();

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
