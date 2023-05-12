package baselogger;

import java.io.IOException;
import java.nio.file.Paths;

import javax.naming.InitialContext;


/**
 * Logger Class for Error and Info
 * @author bkaym
 *
 */
public class BaseLogger {
	
	private final String ERROR_FILE = "logs//application_error.txt";
    private final String INFO_FILE = "logs//application_info.txt";
    
    private BaseWritter errorWriter;
    private BaseWritter infoWriter;
	
    
    public BaseLogger() {
		init();
	}
    
    /**
     * Defines loggers
     */
    private void init() {
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
	private void initializeLoggers() throws IOException {
		infoWriter = new BaseWritter(INFO_FILE, true, "INFO");
		errorWriter = new BaseWritter(ERROR_FILE, true, "ERROR");
	}
	
	
	/**
	 * Returns the info writer in a static way
	 * @return	infoWriter
	 */
	public BaseWritter info() {
		return infoWriter;
	}
	
	
	/**
	 * Returns the error writer in a static way
	 * @return	errorWriter
	 */
	public BaseWritter error() {
		return errorWriter;
	}
	
	
}
