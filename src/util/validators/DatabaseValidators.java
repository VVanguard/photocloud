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
		
		File[] files = new File("resources//users").listFiles();
		
		for (File file : files) {
			try {
				Scanner scanner = new Scanner(file);
				
				while (scanner.hasNext()) {
					
					String[] rawData = scanner.nextLine().split(" ");
					
					// Check username uniqueness
					if (rawData[1].matches(username)) {
						throw new InvalidFieldEntryException("Username must be unique!");
					}
					
					// Check email uniqueness
					if (rawData[1].matches(email)) {
						throw new InvalidFieldEntryException("Email address must be unique");
					}
				}
				
				scanner.close();
				
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException();
			}
		}
	}
	
	
	public static String ValideLogInCredentials(String username, String password) throws InvalidFieldEntryException, FileNotFoundException {
		
		File[] files = new File("resources//users").listFiles();

		for (File file : files) {
			try {
				Scanner scanner = new Scanner(file);
				
				String usernameData = scanner.nextLine().split(" ")[1];
				String passwordData = scanner.nextLine().split(" ")[1];
				
				if (usernameData.matches(username) && passwordData.matches(password)) {
					scanner.close();
					return username;
				}

				scanner.close();

			} catch (FileNotFoundException e) {
				throw new FileNotFoundException();
			}
		}
		
		// If no username that matches with a password found
		throw new InvalidFieldEntryException("Username or Password is not correct!");
	}
}
