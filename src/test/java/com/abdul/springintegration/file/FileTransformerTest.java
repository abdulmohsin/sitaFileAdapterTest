package com.abdul.springintegration.file;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class FileTransformerTest {

	@Test
	public void fileTransformationSuccessCase() throws IOException{
		
		FileTransformer trans =new FileTransformer();
		
		String validFile = "valid-file-1.txt";
		String data = IOUtils.toString(FileTransformerTest.class.getResourceAsStream("/input/"+validFile));
		String transformedData = trans.transformData(data);
		
		assertEquals("Transformer is not transforming correctly ","133",transformedData);
	}
	
	@Test(expected=NumberFormatException.class)
	public void fileTransformationFailureCase() throws IOException{
		
		FileTransformer trans =new FileTransformer();
		
		String validFile = "invalid-file-11.txt";
		String data = IOUtils.toString(FileTransformerTest.class.getResourceAsStream("/input/"+validFile));
		String transformedData = trans.transformData(data);
		
		assertEquals("Transformer is not transforming correctly ","133",transformedData);
	}
}
