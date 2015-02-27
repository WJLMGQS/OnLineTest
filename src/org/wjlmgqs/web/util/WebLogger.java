package org.wjlmgqs.web.util;

import java.util.logging.Logger;

public class WebLogger {

	private static Logger logger = Logger.getLogger(WebLogger.class.getName());
	
	public static void showInfor(String message){
		logger.info(message);
	}
	
}
