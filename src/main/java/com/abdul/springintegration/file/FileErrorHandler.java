package com.abdul.springintegration.file;

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

	public Message handleError(ErrorMessage errorMessage){
		System.out.println("Handling error message : "+ errorMessage);
		Message<?> failedMessage = ((MessagingException)errorMessage.getPayload()).getFailedMessage();
		return failedMessage;
	}
}
