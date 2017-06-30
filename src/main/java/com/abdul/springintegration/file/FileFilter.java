package com.abdul.springintegration.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Filter to move-out invalid content files to errorContent channel for further action.
 * 
 * @author abdul.mohsin
 *
 */
public class FileFilter {
	private static final Logger logger = LoggerFactory.getLogger(FileFilter.class);
	// configured choice to accept blank file or not
	private boolean isBlankFileValid;
	// configured valid regex of each line of the file
	private String lineValidRegex;
	// configured string for data separation e.g newline \\n
	private String dataSplitter;

	/**
	 * FileFilter constructor for file filter
	 * @param isBlankFileValid , configured value to accept blank file to transfer or not
	 * @param lineValidRegex , configured regex for validating each line of the file
	 * @param dataSplitter , data splitter
	 */
	public FileFilter(String isBlankFileValid, String lineValidRegex, String dataSplitter) {
		this.isBlankFileValid = Boolean.valueOf(isBlankFileValid);
		this.lineValidRegex = lineValidRegex;
		this.dataSplitter = dataSplitter;

	}
/**
 * isValid : Method to check if the content of the file is valid or not.
 * @param fileData : content of the file
 * @return true if content is valid else return false
 */
	public boolean isValid(String fileData) {
		logger.info("Validating file's content" );
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
