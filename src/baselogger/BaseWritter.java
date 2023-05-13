package baselogger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class BaseWritter extends FileWriter {

	private String loggerStatus;
	
	
	/**
	 * Constructor with file string and append boolean
	 *
	 * @param fileString 	file path in string
	 * @param append		appends to file if true
	 * @throws IOException	
	 */
	public BaseWritter(String fileString, boolean append, String loggerStatus) throws IOException {
		super(fileString, append);
		this.loggerStatus = loggerStatus;
	}
	
	
	/**
	 * Log method with a time stamp and logger status
	 * @throws IOException
	 */
	public void log(String logString) {
		try {
			
			write(String.format("[%s][%s] %s\n",
					getTimeStamp(),
					getLoggerStatus(),
					logString
				));
			
			flush();
		
		} catch (Exception e) {
			System.err.println("Error in logging!");
			System.out.println(e);
		}
		
	}
	
	
	/**
	 * Gets the time stamp in the format of "dayName day month detailedTime timeZone year"
	 * @return 	time stamp string
	 */
	private String getTimeStamp() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("E dd MM HH:mm:ss yyyy"));
	}

	
	/*
	 * Gets Logger status string
	 */
	public String getLoggerStatus() {
		return loggerStatus;
	}

}
