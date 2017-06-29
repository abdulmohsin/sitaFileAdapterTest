package com.abdul.springintegration.controller;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Configuration
@PropertySource(
          value={"classpath:application.properties"},
          ignoreResourceNotFound = true)
public class WelcomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	
	@Autowired
    Environment env;
		
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(Model model) {

		
		logger.info(" Property from file:"+ env.getProperty("file.inputDirectory"));
		
		model.addAttribute("inputDir", env.getProperty("file.inputDirectory"));
		String processedDirStr = env.getProperty("file.processedDirectory");
		String errorDirStr = env.getProperty("file.errorDirectory");
		
		File processedDir = new File(processedDirStr);
		long cutoff = System.currentTimeMillis() - (30*60*1000);
		FileFilter filter =new AgeFileFilter(cutoff,false);
		File[] files = processedDir.listFiles(filter);
		model.addAttribute("processedFiles",Arrays.toString(files).replace(",", "<br />"));
		
		File errorDir = new File(errorDirStr);
		File[] filesError = errorDir.listFiles(filter);
		model.addAttribute("erroredFiles",Arrays.toString(filesError).replace(",", "<br />"));
		
		return "welcome";

	}

}