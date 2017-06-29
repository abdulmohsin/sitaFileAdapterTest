package com.abdul.springintegration.file;

import org.springframework.messaging.Message;

/**
 * Generic file name generator e.g output , errored, processed etc
 * 
 * @author abdul.mohsin
 *
 */
public class FileNameGenerator implements org.springframework.integration.file.FileNameGenerator {

	private String suffix;
	public FileNameGenerator(String suffix){
		this.suffix = suffix;
	}
	
	public String generateFileName(Message<?> message){
		System.out.println("************* Message in filename generator : " + message);
		String fileName = message.getHeaders().get("file_name").toString() + suffix;
		
		return fileName;
	}
	
}
