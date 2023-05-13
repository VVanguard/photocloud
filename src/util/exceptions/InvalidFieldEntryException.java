package util.exceptions;


public class InvalidFieldEntryException extends Exception {

	
	/**
	 * Exception thrown when a field entry is not valid.
	 * 
	 * @param fieldName 	name of the field entryS
	 * @param exception		exception details
	 */
	public InvalidFieldEntryException(String exception) {
		super(exception);
	}
}
