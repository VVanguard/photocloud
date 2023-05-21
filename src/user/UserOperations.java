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
		
		// Create user folder
		File newUserFolder = new File(USER_PATH + user.getUsername());
		if (!newUserFolder.exists()) {
			newUserFolder.mkdir();
		}
		
		// Create User File
		File newUserFile = new File(USER_PATH + user.getUsername() + "//" + user.getUsername() + ".txt");
		
		// Create Picture Data folder
		File newUserPictureDataFolder = new File(USER_PATH + user.getUsername() + "//picturedata");
		if (!newUserPictureDataFolder.exists()) {
			newUserPictureDataFolder.mkdir();
		}
		
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
		
		// Write User details to the database
		PrintWriter pWriter2 = new PrintWriter(new FileWriter(new File(USER_PATH + "userdatabase.txt"), true));
		pWriter2.printf("%s %s\n", user.getUsername(), user.getEmail());
		
		pWriter2.close();
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
		File file = new File(USER_PATH + username + "//" + username + ".txt");

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
	
	
	/**
	 * Updates user informations in the database
	 * 
	 * @param user			user to update
	 * 
	 * @throws IOException
	 */
	public static void updateUserInDatabase(User user) throws IOException {
		// User file
		File file = new File(USER_PATH + user.getUsername() + "//" + user.getUsername() + ".txt");
		file.delete();	
		
		// Rewrite the user
		// Create User File
		File newUserFile = new File(USER_PATH + user.getUsername() + "//" + user.getUsername() + ".txt");

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
		
		// Update Database
		File database = new File(USER_PATH + "userdatabase.txt");
        Scanner fileScanner = new Scanner(database);

        // Create temporary database
        File tempFile = new File(USER_PATH + "userdatabase_temp.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
        
        while (fileScanner.hasNext()) {
        	String[] userLine = fileScanner.nextLine().split(" ");
        	
        	// If username matches, update email
        	if (userLine[0].matches(user.getUsername())) {
				userLine[1] = user.getEmail();
			}
        	
        	// Write new line
        	pw.printf("%s %s\n", userLine[0], userLine[1]);
        }
        
        pw.close();
        fileScanner.close();
            
        // Delete the original file
        database.delete();
        // Rename the temp file
        tempFile.renameTo(database);
	}
}
