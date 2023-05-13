package util.validators;

import util.exceptions.InvalidFieldEntryException;

public class Validators {
	
	
	/**
	 * Validates Password while signing up
	 *
	 * Password must be equal or longer than 6 characters
	 * Password must include at least 1 alphabetic and one numeric character
	 * 
	 * @param password
	 * 
	 * @throws InvalidFieldEntryException
	 */
	public static void validatePassword(String password) throws InvalidFieldEntryException {
		
		if (password.length() < 6) {
			throw new InvalidFieldEntryException("Password length must be at least 6!");
		}
		
		if (!(password.matches(".*[a-zA-Z].*"))) {
			throw new InvalidFieldEntryException("Password must include at least one alphabetic character!");
		}
		
		if (!(password.matches(".*[0-9].*"))) {
			throw new InvalidFieldEntryException("Password must include at least one number!");
		}
	}
	
	
	/**
	 * Validates Username while signing up
	 * 
	 * Username should only contain alphabetic and numeric characters
	 * 
	 * @param username
	 * 
	 * @throws InvalidFieldEntryException
	 */
	public static void validateUsername(String username) throws InvalidFieldEntryException {
		
		if (!username.matches("[a-zA-Z0-9_]+")) {
			throw new InvalidFieldEntryException("Username should only contain alphabetic and numeric characters!");
		}
	}

	
	/**
	 * Validates age while signing up
	 * 
	 * User must be at least 7 and at most 100 years old to use this application
	 * 
	 * @param age
	 * @throws InvalidFieldEntryException
	 */
	public static void validateAge(String age) throws InvalidFieldEntryException {
		
		if (!age.matches("[0-9]+")) {
			throw new InvalidFieldEntryException("Age should be numeric!");
		}
		
		if (Integer.valueOf(age) <= 0 || Integer.valueOf(age) > 100) {
			throw new InvalidFieldEntryException("Please enter a valid age!");
		}	
	}
	
	
	/**
	 * Validates name spaces while signing up
	 * 
	 * Name spaces must be alphabetic
	 * 
	 * @param nameSpace
	 * @throws InvalidFieldEntryException
	 */
	public static void validateNameSpaces(String nameSpace) throws InvalidFieldEntryException {
		
		if (!nameSpace.matches("[a-zA-Z]+")) {
			throw new InvalidFieldEntryException("Name and Surname should be alphabetic!");
		}
	}
}
