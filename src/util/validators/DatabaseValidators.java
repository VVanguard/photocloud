package util.validators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import util.exceptions.InvalidFieldEntryException;

public class DatabaseValidators {
	

	/**
	 * Validates the uniqueness of username and email by going through the users
	 * 
	 * @param username	userame of the new user	
	 * @param email		email of the new user
	 * 
	 * @throws InvalidFieldEntryException
	 * @throws FileNotFoundException
	 */
	public static void validateUniqueness(String username, String email) throws InvalidFieldEntryException, FileNotFoundException {

		File file = new File("resources//users//userdatabase.txt");
		
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e1) {
			throw new FileNotFoundException("Error in locating database!");
		}

		while (scanner.hasNext()) {
			// Get User Data
			String[] rawData = scanner.nextLine().split(" ");

			// Check username uniqueness
			if (rawData[0].matches(username)) {
				scanner.close();
				throw new InvalidFieldEntryException("Username must be unique!");
			}

			// Check email uniqueness
			if (rawData[1].matches(email)) {
				scanner.close();
				throw new InvalidFieldEntryException("Email address must be unique");
			}
		}
		scanner.close();
	}
	
	
	/**
	 * Validates LogIn Credentials
	 * 
	 * @param username		username entered
	 * @param password		password entered
	 * 
	 * @return				returns username of the user
	 * 
	 * @throws InvalidFieldEntryException
	 * @throws FileNotFoundException
	 */
	public static String ValideLogInCredentials(String username, String password) throws InvalidFieldEntryException, FileNotFoundException {
		
		File file = new File("resources//users//" + username + "//" + username + ".txt");
		
		// Check if the username exists
		if (!file.exists()) {
			throw new InvalidFieldEntryException("Username or Password is not correct!");
		}
		
		// If username exists
		else {
			
			Scanner scanner = new Scanner(file);
			
			String usernameData = scanner.nextLine().split(" ")[1];
			String passwordData = scanner.nextLine().split(" ")[1];
			
			// Check if the credentials match
			if (usernameData.matches(username) && passwordData.matches(password)) {
				scanner.close();
				return username;
			}
			
			scanner.close();
		}
		
		// If no username that matches with a password found
		throw new InvalidFieldEntryException("Username or Password is not correct!");
	}
}
