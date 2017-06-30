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

/**
 * Controller to move the control to the view component
 * @author abdul.mohsin
 *
 */
@Controller
@Configuration
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = true)
public class WelcomeController {

	private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	@Autowired
	Environment env;

	/**
	 * Method to handle the root request content.
	 * 
	 * @param model, model to add any attributes for the view/jsp
	 * @return name of the jsp without suffix
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(Model model) {

		logger.info(" Property from file:" + env.getProperty("file.inputDirectory"));

		String processedFilesList = "";
		String erroredFilesList = "";

		if (env != null) {
			model.addAttribute("inputDir", env.getProperty("file.inputDirectory"));

			String processedDirStr = env.getProperty("file.processedDirectory");
			String errorDirStr = env.getProperty("file.errorDirectory");

			File processedDir = new File(processedDirStr);
			// Cutoff time : 30 mins
			long cutoff = System.currentTimeMillis() - (30 * 60 * 1000);
			// Filter to get processed files in last 30 mins
			FileFilter filter = new AgeFileFilter(cutoff, false);

			if (processedDir.exists()) {
				File[] files = processedDir.listFiles(filter);
				if (files != null && files.length > 0) {
					processedFilesList = Arrays.toString(files).replace(",", "<br />");
				}
			}

			File errorDir = new File(errorDirStr);
			if (errorDir.exists()) {
				File[] filesError = errorDir.listFiles(filter);
				if (filesError != null && filesError.length > 0) {
					erroredFilesList = Arrays.toString(filesError).replace(",", "<br />");
				}
			}

		} else {
			logger.error(
					" Error :  Not able to load env properties , Please check if application.properties is present on the deployed location");
		}

		model.addAttribute("processedFiles", processedFilesList);
		model.addAttribute("erroredFiles", erroredFilesList);
		return "welcome";

	}

}