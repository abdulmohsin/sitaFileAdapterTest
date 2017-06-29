# Sita File Adapter Test
Project to demo basic file transfer using spring integration 

## Task Details 

1.There will be a series of files placed into the directory (C:\SITA_TEST_TASK\IN) with a number on each line.  Attached are some 
  example files   (invalid and valid).  

2. We would like the application to automatically process these files by polling that folder for new files every 5 seconds.
   If a new file is found, then the application should sum all the numbers in the file and create a new file containing the resulting
   value in the directory (C:\SITA_TEST_TASK\OUT). 

3. The output file should have the same name as the input file with .OUTPUT appended to the end of the filename. 

4. When the input file is successfully processed it should be moved to the following directory (C:\SITA_TEST_TASK\PROCESSED) 
   with .PROCESSED appended to the end of the file name. 

4. If an error occurs while processing the input file then the input file should be moved into the following directory 
   (C:\SITA_TEST_TASK\ERROR) with .ERROR appended to the end of the filename. 

## Dependencies
* spring integration framework 4.1.1.RELEASE
* spring framework 4.1.1.RELEASE
* java 1.8
* junit 4.11
* apache commons 1.3.2


## Maven repository to download dependencies
http://repo1.maven.org/maven2/

1. From the command prompt run mvn clean install

## Note
1. It is assumed that the input files will be placed under C:\SITA_TEST_TASK\IN, however we can configure this value in application.properties which is available at src/main/resources

## Testing the application.
1. Application will autostart from the web context , place the input files under C:\SITA_TEST_TASK\IN.
2. Verify the results in C:\SITA_TEST_TASK\OUT and C:\SITA_TEST_TASK\ERROR.
3. Integration can be tested through the Junits(e.g FileIntegrationTest etc)

## Happy Scenario Process Flow
1. The inbound-channel-adapter with poller will poll for the new files in the configured input directory.
2. For each message , integration will perform the following steps:.
3.  The __file-to-string-transformer__ converts the input file data to string.
4.  The output of #3 is passed to the __header-enricher__ for setting a default __error-channel__ (e.g errorUnknownChannel) to 
    handle  unknown errors in the integration flow if occured.
5.  The Message/File is then passed to the __filter__ to filter out invalid content files.

    The filter is only accepting the files based on rule : "Only the file with numeric content is accepted" , this rule can be 
    further changed/configured through property file.

    In case the file content is invalid , message is passed to the error/discard channel(e.g __errorContentChannel__) 
    ( Refer : Negative   Scenario : Invalid content).

6.  The valid file/message gone through the filter is then passed to the  __transformer__ for processing.
    The transformer has the logic to generate the new file contents , the logic is to sum-up the integers present in the original file.
    In case of any unknown error in transforming ( even though the filter has already filtered-out the invalid content files) , message 
    is moved to "errorUnknownChannel" for further processing.

7. Once the transformer has processed the file its output is moved to the __outbound-gateway__ to transfer it to the "OUT" directory.

   The outbound-gateway creates the processed file to the "OUT" directory with .OUTPUT suffix (configured through fileNameGenerator).

   The outbound-gateway is also configured with a __reply-channel__ to do any post processing (e.g moving of original files to 
   PROCESSED dir).
 
8. On successful transfer of processed files in "OUT" directory , service activator will pick the messagee from the __reply-channel__ 
   and post the message to __postProcessed__ channel for post processing.
9. The __postProcessed__ channel will __move__ the original file from input to the PROCESSED directory with .PROCESSED suffix (configured through fileNameGenerator).
 

## Negative   Scenario : Invalid content

1. In case __filter__ in step #5 of Happy scenario rejected the file based on content , message is moved to the __errorContentChannel__
2. The file __outbound-channel-adapter__ will pick the message from the __errorContentChannel__ and __move__ the file to the ERROR directory with .ERROR as suffix( configured through fileNameGenerator).

## Negative Scenario : Any unknown error in flow
1. In case of any unknown error anywhere in flow , message is moved to the default error-channel : __errorUnknownChannel__
2. Service activator will listen to this error channel and get the failing message from the messaging exception.
3. The failing message is then pushed to the above __errorContentChannel__ (Ref : invalid content negative scenario) for further processing. 
