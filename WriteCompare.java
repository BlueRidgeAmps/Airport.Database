package airportdatabaseupdate;

/**
 *
 * @author jonathan.pearson
 */
// class packages
import java.io.*;

public class WriteCompare {

    // define class variables
    private StringBuilder output = new StringBuilder();

    // define instance of LoggerFile class
    LoggerFile logger = new LoggerFile();

    public WriteCompare() {
    }

    // buildString() to apply airport data line to output
    public void buildString(String line) {
        // append timezone rules to each airport
        output.append(line).append(System.lineSeparator());
    }

    // getString() to pass airport database output
    public String getString() {
        return output.toString();
    }

    // define new output when called
    public void eraseBuilder() {
        output = new StringBuilder();
    }

    // writeFile() to create list of airports needing rules
    // or airports filtered out by GUI check box filters
    public void writeFile(String fileName ) {
        // define header
        String header = "id,ident,type,name,latitude_deg,longitude_deg,"
                + "elevation_ft,continent,iso_country,iso_region,municipality,"
                + "scheduled_service,gps_code,iata_code,local_code,"
                + System.lineSeparator() + getString();

        // define FileWriter
        FileWriter writer;

        try {
            writer = new FileWriter(fileName, false);
            writer.write(header);
            writer.close();
        } catch (IOException ex) {
            logger.buildString(ex.toString()); // log occurance
        }
    }
}
