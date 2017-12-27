package airportdatabaseupdate;

/**                                                                            
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
