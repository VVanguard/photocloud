package user;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User I/O Operations
 * @author bkaym
 *
 */
public class UserOperations {

	private static final String USER_PATH = "resources//users//";
	
	
	/**
	 * Write user details to a file
	 * 
	 * @param user			user to write 
	 * 
	 * @throws IOException
	 */
	public static void writeUser(User user) throws IOException {
		
		// Create User File
		File newUserFile = new File(USER_PATH + user.getUsername() + ".txt");
		
		// Write User Details to a File
		PrintWriter pWriter = new PrintWriter(new FileWriter(newUserFile, true));
		pWriter.printf("username: %s\npassword: %s\nemail: %s\nname: %s\nsurname: %s\nage: %d\nimgPath: %s\n",
				user.getUsername(),
				user.getPassword(),
				user.getEmail(),
				user.getName(),
				user.getSurname(),
				user.getAge(),
				user.getImgPath()
				);
		
		pWriter.close();
	}
}
