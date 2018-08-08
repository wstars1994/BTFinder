package com.boomzz.main;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
	
	private static FileHandler fileHandler = null;
	
	public static void log(Class clazz,String text){
		try {
			LogRecord lr = new LogRecord(Level.INFO,text);
			getLogger(clazz).log(lr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Logger getLogger(Class clazz) throws Exception {
		Logger logger = Logger.getLogger(clazz.getName());
		if(fileHandler==null) {
			fileHandler = new FileHandler("log.log",true);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		}
		return logger;
	}
}
