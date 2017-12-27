package airportdatabaseupdate;

/**                                                                          
 *                (c) Copyright 2015 Flight Display Systems                 
 *                                                                           
 *   FLIGHT DISPLAY SYSTEMS PROPRIETARY INFORMATION - ALL RIGHTS RESERVED    
 *                                                                           
 *  -----------------------------------------------------------------------  
 *                                                                           
 *    This software is supplied under the terms of a license agreement       
 *    or non-disclosure agreement with Flight Display Systems and may        
 *    not be copied or disclosed except in accordance with the terms         
 *    of that agreement.                                                     
 *                                                                           
 *    This software may not be copied, transmitted, provided to or otherwise 
 *    made available to any other person, company, corporation or other      
 *    entity except as specified in the terms of said license.               
 *                                                                           
 *    This copyright notice may not be removed or altered without the prior  
 *    written permission of Flight Display Systems.                          
 *                                                                           
 *                                                                           
 *   @filename  LoggerFile.java                                       
 *                                                                            
 *   @description  Class to log all runtime exception
 *                                                                           
 *   @author  jonathan.pearson
 *   @date  June 7, 2017
 *   @summary  The LoggerFile class determines methods to log any runtime errors
 *             that occur during operation of the program to assist in current 
 *             debugging problems in the event the program turns up failure 
 *             points in future operations.  Each line of the log file is formatted
 *             such that, each line will have a date and time stamp so there is
 *             a way to trace the problem if it is re-occuring.
 * 
 */

// import class packages
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoggerFile {
    
    /* output
    Allows for each occurance of exceptions to be appended while
    program is running */
    private final StringBuilder output = new StringBuilder();
    
    // default constructor
     public LoggerFile(){
     }
     
     /**
      * Defines a new String line for each exception occurance.  Each string 
      * contains the date and time followed by the exception.
      * 
      * @param exp  the runtime exception that occured
      */
     public void buildString(String exp){
         // define exception with date and time
         String strExp = getDate().concat("  -->  ").concat(exp).concat("\n");
         // append new exception to output seperated by a line
         output.append(System.lineSeparator()).append(strExp);
     }
     
     /**
      * Converts the variable output from StringBuilder to String
      * so that a file can be written in plain text.
      * 
      * @return  creates a String variable for writing a file
      */
     private String getString(){
         return output.toString();
     }
     
     /**
      * Writes file that contains all instances
      * of exceptions during runtime.
      * 
      * @throws IOException 
      */
     public void writeFile() throws IOException{
         // define variables
         String dir = "Control/error_log";
         String outputFile = getString();
         // defind filewriter
         FileWriter writer;
         
         try {
             writer = new FileWriter(dir, true);
             writer.write(outputFile);
             writer.close();
         } catch (IOException e){
             e.printStackTrace();
         }
     }
     
    /**
     * Gets the operating systems date and time and formats for
     * consistency
     * 
     * @return 
     */
    private String getDate() {
        return new SimpleDateFormat("yyyy-MMM-dd '@' HH:mm:ss z")
                .format(Calendar.getInstance().getTime());
    }
}
