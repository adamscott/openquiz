package ca.openquiz.webservice.tools;

import java.util.logging.Logger;

public class ExceptionLogger {
	private static final Logger logger = Logger.getLogger("ExceptionLogger");
	
	public static final Logger getLogger(){
		return logger;
	}
}
