package baselogger;

import java.io.IOException;
import java.nio.file.Paths;


/**
 * Logger Class for Error and Info
 * @author bkaym
 *
 */
public class BaseLogger {
	
	private static final String ERROR_FILE = "logs//application_error.txt";
    private static final String INFO_FILE = "logs//application_info.txt";
    
    private static BaseWritter errorWriter;
    private static BaseWritter infoWriter;
	
	public BaseLogger(){
		try {
			initializeLoggers();
		} catch (Exception e) {
			System.err.println("Error occured in initializing Loggers. Check logger file path strings!");
			System.err.println(e);
		}
		
	}
	
	/**
	 * Initializing loggers from file path strings, throwing IO Exception
	 * @throws IOException
	 */
	private static void initializeLoggers() throws IOException {
		infoWriter = new BaseWritter(INFO_FILE, true, "INFO");
		errorWriter = new BaseWritter(ERROR_FILE, true, "ERROR");
	}
	
	
	/**
	 * Returns the info writer in a static way
	 * @return	infoWriter
	 */
	public static BaseWritter info() {
		return infoWriter;
	}
	
	
	/**
	 * Returns the error writer in a static way
	 * @return	errorWriter
	 */
	public static BaseWritter error() {
		return errorWriter;
	}
	
	
}
