package com.abdul.springintegration.file;

import java.io.File;

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

	public Message handleMessage(Message<String> message) {
		System.out.println("*********** In FileActivator : post processing :" + message);
		MessageHeaders headers = message.getHeaders();
		File original_file = new File(headers.get("file_originalFile").toString());
		Message messageNew = MessageBuilder.createMessage(original_file, headers);
		return messageNew;
	}
}
