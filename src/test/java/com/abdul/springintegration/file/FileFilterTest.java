package com.abdul.springintegration.file;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

 import static org.junit.Assert.*;

public class FileFilterTest {

	@Test
	public void testValidFile() throws IOException{
		String validFile = "valid-file-1.txt";
		FileFilter filter = new FileFilter("true","^([\\d]*[-\\d]*)$","\\n");
		boolean isValid = filter.isValid(IOUtils.toString(FileFilterTest.class.getResourceAsStream("/input/"+validFile)));
		assertTrue("File :"+ validFile +" should be valid", isValid);
	}
	
	@Test
	public void testInvalidFile() throws IOException{
		String invalidFile = "invalid-file-11.txt";
		FileFilter filter = new FileFilter("true","^([\\d]*[-\\d]*)$","\\n");
		boolean isValid = filter.isValid(IOUtils.toString(FileFilterTest.class.getResourceAsStream("/input/"+invalidFile)));
		assertFalse("File :"+ invalidFile +" should be invalid", isValid);
	}
}
