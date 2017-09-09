package com.abdul.springintegration.file;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Activator to tweek the message for post processing 
 * e.g moving-out the original files to "processed" directory
 * 
 * @author abdul.mohsin
 *
 */
public class FileActivator {

	private static final Logger logger = LoggerFactory.getLogger(FileActivator.class);
	 
	/**
	 * handleMessage : method to tweek the message for post processing
	 * @param message , original message after placing files to OUT directory
	 * @return message - tweeked message to do post processing like moving to PROCESSED dir
	 */
	public Message<?> handleMessage(Message<?> message) {
		logger.info("Post processing message:" + message);
		MessageHeaders headers = message.getHeaders();

		// Messaging integration is storing the original file name in : file_originalFile
				
		Object originalFileName = headers.get("file_originalFile");

		System.out.println("********** Changes part 2 on another clone"); 
		if(originalFileName !=null){
		
			File original_file = new File(originalFileName.toString());
			Message<?> messageNew = MessageBuilder.createMessage(original_file, headers);
			return messageNew;
		}
		else{
			logger.error(" Original file name : file_originalFile is missing in the header , please check for any changes in the integration flow.");
		}
		return null;
	}
}
