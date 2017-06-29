package com.abdul.springintegration.file;

import org.springframework.util.StringUtils;

/**
 * Filter to move-out invalid content files to errorContent channel for further action.
 * 
 * @author abdul.mohsin
 *
 */
public class FileFilter {

	// configured choice to accept blank file or not
	private boolean isBlankFileValid;
	// configured valid regex of each line of the file
	private String lineValidRegex;
	// configured string for data separation e.g newline \\n
	private String dataSplitter;

	public FileFilter(String isBlankFileValid, String lineValidRegex, String dataSplitter) {
		this.isBlankFileValid = Boolean.valueOf(isBlankFileValid);
		this.lineValidRegex = lineValidRegex;
		this.dataSplitter = dataSplitter;

	}

	public boolean isValid(String fileData) {
		System.out.println("*********** File filter called" );
		if (!StringUtils.isEmpty(fileData)) {
			String[] lines = fileData.split(dataSplitter);
			for (String line : lines) {
				if (!StringUtils.isEmpty(line)) {
					if (!line.trim().matches(lineValidRegex)) {
						return false;
					}
				}
			}
			return true;
		}
		return isBlankFileValid;
	}
}
