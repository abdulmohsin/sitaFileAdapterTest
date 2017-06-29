package com.abdul.springintegration.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * File transformer containing logic for transforming the input file content.
 * In case of any unknown error , "errorUnknown channel" will take care of it.
 * 
 * @author abdul.mohsin
 *
 */
public class FileTransformer {
	private static final Logger logger = LoggerFactory.getLogger(FileTransformer.class);

	public String transformData(String data){
		long output =0l;
		if(!StringUtils.isEmpty(data)){
			logger.info("Transforming Data : " + data);
			String[] lines =data.split("\\n");
			for(String line: lines){
				if(!StringUtils.isEmpty(line.trim()))
				output+= Long.parseLong(line.trim());
			}
			logger.info("Transforming Output : " + output);
		}
		return String.valueOf(output);
	}
}
