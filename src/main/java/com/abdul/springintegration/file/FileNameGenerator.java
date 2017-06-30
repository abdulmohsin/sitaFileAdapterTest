package com.abdul.springintegration.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

/**
 * Generic file name generator e.g output , errored, processed etc
 * 
 * @author abdul.mohsin
 *
 */
public class FileNameGenerator implements org.springframework.integration.file.FileNameGenerator {

	private static final Logger logger = LoggerFactory.getLogger(FileNameGenerator.class);
	private String suffix;
	
	/**
	 * FileNameGenerator : For generating output file name.
	 * @param suffix
	 */
	public FileNameGenerator(String suffix){
		this.suffix = suffix;
	}
	
	/**
	 * generateFileName : method for generating the output file name.
	 */
	public String generateFileName(Message<?> message){
		String fileName = message.getHeaders().get("file_name").toString() + suffix;
		logger.info("Generated file name: "+ fileName);
		return fileName;
	}
}
