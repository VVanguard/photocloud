package user;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * User I/O Operations
 * @author bkaym
 *
 */
public class UserOperations {

	private static final String USER_PATH = "resources//users//";
	
	
	/**
	 * Writes user details to a file
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
		pWriter.printf("username: %s\npassword: %s\nemail: %s\nname: %s\nsurname: %s\nage: %d\nimgPath: %s\ntier: %s\n",
				user.getUsername(),
				user.getPassword(),
				user.getEmail(),
				user.getName(),
				user.getSurname(),
				user.getAge(),
				user.getImgPath(),
				user.getTier()
				);
		
		pWriter.close();
	}
	
	
	/**
	 * Gets user from resources/users database
	 * 
	 * @param username			username to search in database
	 * @return					return new user from database
	 * 
	 * @throws IOException
	 */
	public static User getUserFromDatabase(String username) throws IOException {
		
		// User file
		File file = new File(USER_PATH + username + ".txt");
		
		Scanner scanner = new Scanner(file);
		scanner.nextLine();
		
		// Read Data
		String password = scanner.nextLine().split(" ")[1];
		String email = scanner.nextLine().split(" ")[1];
		String name = scanner.nextLine().split(" ")[1];
		String surname = scanner.nextLine().split(" ")[1];
		int age = Integer.valueOf(scanner.nextLine().split(" ")[1]);
		String imgPath = scanner.nextLine().split(" ")[1];
		UserTiers tier = UserTiers.valueOf(scanner.nextLine().split(" ")[1]);
		
		scanner.close();
		
		// Create user from database
		User newUser = new User(name, surname, email, age, username, password, tier);
		
		// Set PP Image Path
		newUser.setPpImg(imgPath);
		
		return newUser;
	}
}
