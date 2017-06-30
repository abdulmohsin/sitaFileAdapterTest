package com.abdul.springintegration.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;


/**
 * This is a generic error handler for the complete message flow , this is 
 * configured in the beginning of the flow using message enricher
 * 
 * @author abdul.mohsin
 *
 */
public class FileErrorHandler {
	private static final Logger logger = LoggerFactory.getLogger(FileErrorHandler.class);
	
	/**
	 * handleError : Method is taking the messaging generic exception message
	 * and take-out the failing message from it
	 * @param errorMessage : message thrown by the spring integration in case of any unknow errors
	 * @return message : failing message.
	 */
	public Message<?> handleError(ErrorMessage errorMessage){
		logger.info("Handling error message : "+ errorMessage);
		/*
		 *  As per doc , spring integration is wrapping any internal errors in the MessagingException
		    and pack it on the payload of ErrorMessage.
		 */
		Message<?> failedMessage = ((MessagingException)errorMessage.getPayload()).getFailedMessage();
		return failedMessage;
	}
}
