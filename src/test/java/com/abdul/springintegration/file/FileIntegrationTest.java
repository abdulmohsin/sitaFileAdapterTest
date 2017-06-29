package com.abdul.springintegration.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/FileIntegrationTest-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class FileIntegrationTest {

	public static final String IN_DIRECTORY="file.inputDirectory";
	public static final String OUT_DIRECTORY="file.outputDirectory";
	public static final String PROCESSED_DIRECTORY="file.processedDirectory";
	public static final String ERROR_DIRECTORY="file.errorDirectory";
	public static final String OUT_FILE_SUFFIX=".OUTPUT";
	public static final String ERROR_FILE_SUFFIX=".ERROR";
	public static final String PROCESSED_FILE_SUFFIX=".PROCESSED";
	
	/**
	 * Test method to test the integration end-to-end for the success scenario
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void testSuccessCase() throws IOException, InterruptedException {

		String validFile = "valid-file-1.txt";
		Properties properties = new Properties();
		properties.load(FileIntegrationTest.class.getResourceAsStream("/application.properties"));
		
		String inputDirectory =properties.getProperty(IN_DIRECTORY);
		String outputDirectory =properties.getProperty(OUT_DIRECTORY);
		String processedDirectory =properties.getProperty(PROCESSED_DIRECTORY);
		String errorDirectory =properties.getProperty(ERROR_DIRECTORY);
		
		System.out.println(inputDirectory);
		init(inputDirectory);
		init(outputDirectory);
		init(processedDirectory);
		init(errorDirectory);
		
		String content = getTestFileWriteInInput(validFile, inputDirectory);
		
		Thread.sleep(10000); // waiting for integration to complete
		
		// validating the output file and content
		validateFileAndContent(validFile,OUT_FILE_SUFFIX, outputDirectory,"133");
		validateFileAndContent(validFile,PROCESSED_FILE_SUFFIX, processedDirectory,content);
		validateFileMovement(inputDirectory,validFile);
		
	}
	
	/**
	 * Test method to test the messaging integration end-to-end for negative scenario.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void testFailureCase() throws IOException, InterruptedException {

		String invalidFile = "invalid-file-11.txt";
		Properties properties = new Properties();
		properties.load(FileIntegrationTest.class.getResourceAsStream("/application.properties"));
		
		String inputDirectory =properties.getProperty(IN_DIRECTORY);
		String outputDirectory =properties.getProperty(OUT_DIRECTORY);
		String processedDirectory =properties.getProperty(PROCESSED_DIRECTORY);
		String errorDirectory =properties.getProperty(ERROR_DIRECTORY);
		
		System.out.println(inputDirectory);
		init(inputDirectory);
		//init(outputDirectory);
		init(processedDirectory);
		init(errorDirectory);
		
		String content = getTestFileWriteInInput(invalidFile, inputDirectory);
		
		Thread.sleep(10000); // waiting for integration to complete
		
		// validating the output file and content
		validateFileAndContent(invalidFile,ERROR_FILE_SUFFIX, errorDirectory,content);
		validateFileMovement(inputDirectory,invalidFile);
		
	}
	
	private void validateFileMovement(String inputDirectory, String validFile) {

		File file = new File(inputDirectory+"/"+validFile);
		Assert.assertFalse("File :"+ validFile+" should not get exist", file.exists());
	}

	

	private String getTestFileWriteInInput(String validFile, String inputDirectory) throws IOException {
		InputStream stream = FileIntegrationTest.class.getResourceAsStream("/input/"+validFile);
		byte[] bytes = new byte[stream.available()];
		stream.read(bytes);
		
		FileWriter writer = new FileWriter(new File(inputDirectory+"\\"+validFile));
		String content = new String(bytes);
		System.out.println("********************** Contents : "+ content);
		writer.write(content );
		writer.flush(); writer.close();
		return content;
	}

	private void validateFileAndContent(String validFile, String suffix, String outputDirectory, String content)
			throws FileNotFoundException, IOException {
		File outFile = new File((new StringBuilder(outputDirectory)).append("\\").append(validFile).append(suffix).toString());
		
		Assert.assertTrue("Output file doesnt exists , messaging has failed to transfer file:"+validFile, outFile.exists());  // output file should exist
		
		FileInputStream reader = new FileInputStream(outFile);
		StringBuilder contentBuff = new StringBuilder();
		byte[] bytes = new byte[reader.available()];
		reader.read(bytes);
		contentBuff.append(new String(bytes));
		reader.close();
		Assert.assertEquals("Output file content is not as expected", content.trim(), contentBuff.toString().trim());
	}

	private void init(String directory) {
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		} else {
			for (File childFile : file.listFiles()) {
				childFile.delete();
			}
		}
	}
}
