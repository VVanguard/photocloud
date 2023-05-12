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
			throw new InvalidFieldEntryException("Password length must be greater than 0");
		}
		
		if (!(password.matches(".*[a-zA-Z].*"))) {
			throw new InvalidFieldEntryException("Password must include at least one alphabetic character");
		}
		
		if (!(password.matches(".*[0-9].*"))) {
			throw new InvalidFieldEntryException("Password must include at least one number");
		}
	}

}
