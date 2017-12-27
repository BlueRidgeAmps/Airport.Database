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
 *                                                                           
 *   @filename  WriteAirport.java                               
 *                                                                            
 *   @description  this class writes a file of the all airports with time zone
 *                 rules
 *                                                                           
 *   @@author  jonathan.pearson
 *   @date  June 9, 2017
 *   @summary  The WriteAirport class defines and exports the "Airports.csv" file 
 *             with region time zone rules appended to each airport.  Only 
 *             airport types selected by the GUI filters will be displayed in 
 *             this file.
 * 
 */

// class packages
import java.io.*;

public class WriteAirport {

    // define class variables
    private StringBuilder output = new StringBuilder();
    
    // define instance of package classes
    Move move = new Move();
    LoggerFile logger = new LoggerFile();

    // default constructor
    public WriteAirport() {
    }

    /**
     * Applies removed airport to accumulated list of removed airports
     * 
     * @param airport  airport line to be removed from main database
     */
    public void buildString(String airport) {
        // append timezone rules to each airport
        output.append(airport).append(System.lineSeparator());
    }

    /**
     * Defines output as a String for writing a file
     * 
     * @return 
     */
    public String getString() {
        return output.toString();
    }

    /**
     * The variable output is defined as new so that a new
     * file can be written if the database update is performed
     * again before the program terminates.
     */
    public void eraseBuilder() {
        output = new StringBuilder();
    }

    // writeFile() to create list of airports with rules applied
    public void writeFile() throws IOException {
        // define local variables
        String name = "Airports.csv";
        // check if file exist in directory
        // if file exist, move to new directory
        move.copyFile(name);
        
        // define FileWriter
        FileWriter writer;

        try {
            writer = new FileWriter(name, false);
            writer.write(getString());
            writer.close();
        } catch (IOException ex) {
            logger.buildString(ex.toString()); // log occurance
        }
    }
}
