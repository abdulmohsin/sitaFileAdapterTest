# Sita File Adapter Test
Project to demo basic file transfer using spring integration 

## Task Details 

1.There will be a series of files placed into the directory (C:\SITA_TEST_TASK\IN) with a number on each line.  Attached are some example files   (invalid and valid).  

2. We would like the application to automatically process these files by polling that folder for new files every 5 seconds. If a new file is found, then the application should sum all the numbers in the file and create a new file containing the resulting value in the directory (C:\SITA_TEST_TASK\OUT). 

3. The output file should have the same name as the input file with .OUTPUT appended to the end of the filename. 

4. When the input file is successfully processed it should be moved to the following directory (C:\SITA_TEST_TASK\PROCESSED) with .PROCESSED appended to the end of the file name. 

4. If an error occurs while processing the input file then the input file should be moved into the following directory (C:\SITA_TEST_TASK\ERROR) with .ERROR appended to the end of the filename. 

## Dependencies
* spring integration framework 4.1.1.RELEASE
* spring framework 4.1.1.RELEASE
* java 1.8
* junit 4.11
* apache commons 1.3.2


## Maven repository to download dependencies
http://repo1.maven.org/maven2/


## Build the application
1. From the command prompt run mvn clean install

## Note
1. It is assumed that the input files will be placed under C:\SITA_TEST_TASK\IN, however we can configure this value in application.properties which is available at src/main/resources

## Testing the application.
1. Application will autostart from the web context , place the input files under C:\SITA_TEST_TASK\IN.
2. Verify the results in C:\SITA_TEST_TASK\OUT and C:\SITA_TEST_TASK\ERROR.
3. Integration can be tested through the Junits(e.g FileIntegrationTest etc)

## Process Flow
1. The inbound-channel-adapter will start automatically , poller configured will poll for the new messages from the configured input directory.
2. For each message , integration will do the following steps:
3. Configured "file-to-string-transformer" converts the data of the input file to string.
4. Configured "header-enricher" is setting a default "error-channel" to handle unknown errors in the integration flow if occured.
5. Message/File is passed to the "filter" to validate the content : 
   if the content is valid as per the configured rules (e.g only integers are allowed ) then 
     * Message is moved to 

3. All the messages one by one enter to router and the router will do the message validation. The validation will pass if the lines in the file contains numbers. If the validation fails then router will send the message to errorChannel.
Otherwise the message will send to outputChannel.
4. On the errorChannel we have outbound-channel-adapter and this is responsible to put message into the directory specified in configuration.
5. On the outputChannel we have service-activator and the bean referred for this channel is responsible to generate the file content (sum of numbers) and will send to writeOutputChannel.
6. On the writeOutputChannel we have outbound-channel-adapter which is responsible to generate the output in the configured directory. Since we have used configured file-name-generator then the application is having the control to decide the name of the file. This generator will add ".PROCESSED" at the end of original file name.
