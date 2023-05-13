package util.validators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import util.exceptions.InvalidFieldEntryException;

public class UniqueValidators {
	

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
}
