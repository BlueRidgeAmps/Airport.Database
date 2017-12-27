package airportdatabaseupdate;

/**                                                                                       
 *   @filename  ReviewAirport.java                               
 *                                                                            
 *   @description  this class writes a file of the of airports that have no time
 *                 zone rules to be appended
 *                                                                           
 *   @@author  jonathan.pearson
 *   @date  June 8, 2017
 *   @summary  this class defines and builds the "Removed_Airports.csv" file that
 *             list all airports not to be listed in the exported "Airports.csv" 
 *             file created by this program. This list is user created via the 
 *             check box filters on the GUI or it is implicitly implied by the
 *             database file by coordinate location or statement on airport name
 *             as "Erase_Me"
 * 
 */
// class packages
import java.io.*;

public class ReviewAirport {

    // define class variables
    private StringBuilder output = new StringBuilder();

    // define instance of LoggerFile class
    LoggerFile logger = new LoggerFile();

    // default constructor
    public ReviewAirport() {
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

    /**
     * Writes Removed_Airports.csv file that contains list of 
     * airports filtered out by GUI check box filters or airports
     * missing rules
     */
    public void writeFile() {
        // define variable for output
        String text = getString();
        // define header
        String header = "id,ident,type,name,latitude_deg,longitude_deg,"
                + "elevation_ft,continent,iso_country,iso_region,municipality,"
                + "scheduled_service,gps_code,iata_code,local_code,"
                + System.lineSeparator() + text;

        // define FileWriter
        FileWriter writer;

        try {
            writer = new FileWriter("Removed_Airports.csv", false);
            writer.write(header);
            writer.close();
        } catch (IOException ex) {
            logger.buildString(ex.toString()); // log occurance
        }
    }
}
