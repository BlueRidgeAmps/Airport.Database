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
 *   @filename  UpdateAirport.java                                       
 *                                                                            
 *   @description  creates new list of airports with time zone rules
 *                                                                           
 *   @author  jonathan.pearson
 *   @date  June 16, 2017
 *   @summary  The UpdateAirport class builds and exports a list of airports
 *             that no rules were found for or defined through cross checking
 *             parameters with other airport data.
 * 
 */

// import class packages
import java.io.*;

public class UpdateAirport {

    // define StringBuilder to hold output until file is written
    private StringBuilder output = new StringBuilder();
    
    // define instace of LoggerFile class
    LoggerFile logger = new LoggerFile();

    /**
     * default constructor
     */
    public UpdateAirport() {
    }

    /**
     * builds output of all airports where time zone rules could not be applied
     * 
     * @param noTimezone  airports parsed where time zone rules could not be 
     *                    defines
     */
    public void buildString(String noTimezone) {
        // append timezone rules to each airport
        output.append(noTimezone).append(System.lineSeparator());
    }

    /**
     * method to pass airport database output to be written
     * 
     * @return  string representation of output for use in class
     */
    public String getString() {
        return output.toString();
    }

    /**
     * define a new output when called after file is written
     */
    public void eraseBuilder() {
        output = new StringBuilder();
    }

    /**
     * writes list of airports needing rules applied
     */
    public void writeFile() {
        // define FileWriter
        FileWriter writer;

        try {
            writer = new FileWriter("No_TimeZone_Rules.csv", false);
            writer.write(getString());
            writer.close();
        } catch (IOException ex) {
            logger.buildString(ex.toString()); // log occurance
        }
    }
}
