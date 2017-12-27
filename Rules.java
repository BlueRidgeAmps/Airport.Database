package airportdatabaseupdate;

/**                                                                                        
 *   @filename   Rules.java                                    
 *                                                                            
 *   @description  method to update rule database
 *                                                                           
 *   @author  jonathan.pearson
 *   @date  June 16, 2017
 *   @summary  The Rules class defines a method to update the rules list as 
 *             rules are defined.  The "Rules.csv" is copied to the Archive 
 *             directory before it is modified with new rules.
 */

// class packages
import java.io.*;
import java.util.*;

public class Rules {

    // define class variables
    private String key;
    private String value;
    private StringBuilder output = new StringBuilder();
    private final HashMap<String, String> map = new HashMap<>();

    // define instance of Move and LoggerFile class
    Move move = new Move();
    LoggerFile logger = new LoggerFile();

    /**
     * default constructor
     * 
     * @param key  variable to hold airport identifier
     * @param value  variable to hold rules for the identifier
     */
    public Rules(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * method to add the parameters used for keys and values in rules file
     * 
     * @param k  key
     * @param v  value
     */
    public void getMap(String k, String v) {
        // define key and value
        key = k;
        value = v;

        // put key and value into map
        map.put(key, value);

        // call buildString()
        buildString();
    }

    // buildString() to apply rules to airports
    /**
     * method to apply rules to the airports.  each rule is defined by
     * the key and then the value for the key is appended.
     */
    public void buildString() {
        // define rule data
        String ruleValue = map.get(key);       
        // define new rule
        String rule = key.concat(",").concat(ruleValue);

        // append timezone rules to each airport
        output.append(rule).append(System.lineSeparator());
    }

    /**
     * method to pass output as String in package
     * 
     * @return  output is set as String
     */
    public String getString() {
        return output.toString();
    }

    /**
     * define new output when called
     */
    public void eraseBuilder() {
        output = new StringBuilder();
    }

    /**
     * create a list of rules and offsets via region code
     * 
     * @throws IOException 
     */
    public void writeFile() throws IOException {
        // define variable
        String name = "rules.csv";
        String dir = "Control/";
        // copy existing file to new directory
        move.copyFile(dir.concat(name));
        // define FileWriter
        FileWriter writer;

        try {
            // write new file to directory
            writer = new FileWriter(dir.concat(name), true);
            writer.write(getString());
            writer.close();
        } catch (IOException ex) {
            logger.buildString(ex.toString()); // log occurance
        }
    }
}
