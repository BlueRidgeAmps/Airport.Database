package airportdatabaseupdate;

/**
 * (c) Copyright 2015 Flight Display Systems
 *
 *    FLIGHT DISPLAY SYSTEMS PROPRIETARY INFORMATION - ALL RIGHTS RESERVED
 *
 * -----------------------------------------------------------------------
 *
 *    This software is supplied under the terms of a license agreement or
 *    non-disclosure agreement with Flight Display Systems and may not be copied or
 *    disclosed except in accordance with the terms of that agreement.
 *
 *    This software may not be copied, transmitted, provided to or otherwise made
 *    available to any other person, company, corporation or other entity except as
 *    specified in the terms of said license.
 *
 *    This copyright notice may not be removed or altered without the prior written
 *    permission of Flight Display Systems.
 *
 *
 *    @filename ReadAirport.java
 *
 *    @description Class to read in airport and rule files and append time zone
 *                 rules to each airport based on identifier and lat and long
 *
 *    @author jonathan.pearson
 *    @date June 8, 2017
 *    @summary The ReadAirport class will read in the "Rules.csv" file, build maps
 *             of rules and regions, and then use those maps to determine rules for airports
 *             being added to the database.
 */
// class packages
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.swing.JOptionPane;

public class ReadAirport {

    // define class variables
    private String line = "";
    private final String csvSplit
            = String.format(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    private boolean getState = true;
    // Map for previously defined rules
    private final HashMap<String, String> regionRules = new HashMap<>();
    // Map to define rules based on coordinates
    private final HashMap<String, String> map = new HashMap<>();
    // Map to default rules based on region
    private final HashMap<String, String> otherMap = new HashMap<>();

    // define instance of package classes
    UpdateAirport update = new UpdateAirport();
    Coordinates cord = new Coordinates(0, 0);
    HardCodeRules rules = new HardCodeRules("");
    Rules newRules = new Rules("", "");
    ReviewAirport review = new ReviewAirport();
    WriteAirport write = new WriteAirport();
    FilterAirports filter = new FilterAirports("");
    LoggerFile logger = new LoggerFile();

    // default constructor
    public ReadAirport() {
    }

    /**
     * Reads in the rules.csv file and builds three HashMaps that define keys
     * for identifier code, coordinates, and region. The values for each are the
     * rule data associated with each key. The three HashMaps allow for multiple
     * ways to compare new airports in the database with older airports.
     */
    public void readRuleFile() {
        // define local variables
        BufferedReader file = null;

        // define path to time zone rules
        String csvFile = "Control/rules.csv";

        try {
            // read in rules.csv file
            file = new BufferedReader(new FileReader(csvFile));

            // iterate each line of file and parse data
            while ((line = file.readLine()) != null) {
                // split each line into elements
                String[] data = line.split(csvSplit);
                // pass elements for buildString method
                String zoneRule = rulesString(data[2], data[3], data[4], data[5], data[6]);
                //define a new Pair of latitude and longitude
                Pair coordinates = new Pair(cord.convertLat(data[7]),
                        cord.convertLong(data[8]));
                // define map of existing region rules
                regionRules.put(getRegion(data[0]), zoneRule);
                // define map of latitude and longitude rules
                map.put(coordinates.toString(), zoneRule);
                // define map of country code
                otherMap.put(getRegion(data[1]), zoneRule);
            }
        } catch (FileNotFoundException ex) {
            getError(ex.toString()); // call getError to show message
            ex.printStackTrace();
        } catch (IOException ex) {
            getError(ex.toString()); // call getError to show message
            ex.printStackTrace();
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException ex) {
                logger.buildString(ex.toString()); // log occurance
                ex.printStackTrace();
            }
        }
    }

    /**
     * Reads in the new airport database file and appends time zone rules to
     * each airport. As each line is read in, information not needed in the file
     * will be removed and all lines will be defined to a standard number of
     * commas. The selection statements will determine if the identifier key
     * exist and append the rules. If the new airport does not contain a key
     * already in the rules file, the airport will be compared to coordinates
     * and apply the rules that are closest to those coordinates. Finally, the
     * region code will be compared if there are no coordinates or identifiers
     * matching new airports in the database.
     *
     * @param airportFile the path to the airport database that will have rules
     * appended.
     * @param types filter that limits which airports to append rules to
     */
    private void readAirportFile(String airportFile, String types) {
        // define local variables
        String input, timezone, newRule, c = ",";
        BufferedReader file = null;

        // pass airport types to parse
        filter.filter(types);

        try {
            // read in file to add rules to
            // force program to use UTF-8 character set
            file = new BufferedReader(new InputStreamReader(
                    new FileInputStream(airportFile), StandardCharsets.UTF_8));

            // while loop to iterate through each line of airport file
            while ((line = file.readLine()) != null) {
                // define each line locally to remove unwanted data in airport line
                input = fixDataLine(line);

                // split line into array of elements for manipulation
                String[] data = line.split(csvSplit);
                // define a new Pair of latitiude and longitude
                Pair latAndLong = new Pair(cord.convertLat(data[4]), cord.convertLong(data[5]));

                // Remove quotes if present for uniform data comparison
                String data1 = removeQuotes(data[1]);
                String data2 = removeQuotes(data[2]);
                String data3 = removeQuotes(data[3]);
                String data8 = removeQuotes(data[8]);
                String data9 = removeQuotes(data[9]);

                // only apply selection if airport type returns true
                if (filter.checkFilter(data2) == true) {
                    // selection statement to determine if region is in Airports.csv
                    // if in file, append rules to airport
                    // check for airports that specifically say "Erase Me"
                    if (data3.contains("Erase Me")) {
                        timezone = input;
                        review.buildString(timezone);
                    } else if (regionRules.containsKey(data9)) {
                        // determine if the region falls into a category that
                        // has multiple rules for different airports
                        if ((data1).equals("SCTC") || (data1).equals("SCTO")
                                || (data1).equals("FM-ULI")
                                || (data1).equals("FM-0001")
                                || (data1).equals("PTYA")
                                || (data1).equals("MMCS")
                                || (data1).equals("NTGJ")) {
                            // Hard code data of exceptions
                            String rule = rules.getRules(data1);
                            timezone = input.concat(rule); // add rules
                            write.buildString(timezone); // write to output
                        } else {
                            // add rules
                            timezone = input.concat(regionRules.get(data9));
                            write.buildString(timezone); // write to output
                        }
                    } else if (!regionRules.containsKey(data9)) {
                        // define rules based on coordinate matching
                        if (map.containsKey(latAndLong.toString())) {
                            // define new coordinates for rule
                            String getCoordinates = map.get(latAndLong.toString());

                            // add rules
                            timezone = input.concat(getCoordinates);
                            write.buildString(timezone); // write to output
                            // add definition to rules list
                            regionRules.put(data9, getCoordinates);
                            // define rule String
                            newRule = data8.concat(c).concat(getCoordinates)
                                    .concat(data[4]).concat(c).concat(data[5]);
                            newRules.getMap(data9, newRule);

                        } else if (otherMap.containsKey(data8)) {
                            // define latitude and longitude
                            int lat_cord = cord.convertLat(data[4]);
                            int long_cord = cord.convertLong(data[5]);

                            // check if coordinates are in the ocean
                            // off the coast of Africa, remove from database
                            if ((lat_cord == 0) && (long_cord == 0)) {
                                // if coordinates are Lat 0/Long 0
                                // write new file
                                timezone = input;
                                review.buildString(timezone);
                            } else {
                                // if not region code
                                // define rule by country code
                                timezone = input.concat(otherMap.get(data8));
                                write.buildString(timezone);

                                // add definition to rules list
                                regionRules.put(data9, otherMap.get(data8));
                                // define rule String
                                newRule = data8.concat(c).concat(otherMap.get(data8))
                                        .concat(data[4]).concat(c).concat(data[5]);
                                newRules.getMap(data9, newRule);
                            }
                        } else {
                            // if no match then append comma and
                            // write new file with list of rules
                            // needing to be defined
                            timezone = input;
                            // create list of airports without rules
                            update.buildString(timezone);
                        }
                    }
                } else {
                    // copy airports with false return to
                    // list of removed airports
                    review.buildString(line);
                }
            }
        } catch (FileNotFoundException ex) {
            getError(ex.toString()); // call getError to show message
            ex.printStackTrace();
        } catch (IOException ex) {
            getError(ex.toString()); // call getError to show message
            ex.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException ex) {
            getError(ex.toString()); // call getError to show message
            ex.printStackTrace();
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException ex) {
                logger.buildString(ex.toString()); // log occurance
                ex.printStackTrace();
            }
        }
    }

    /**
     * method to log exception and inform user
     *
     * @param ex exception being thrown
     */
    private void getError(String ex) {
        // if exception thrown, no file airport file will be written
        getState = false;
        JOptionPane.showMessageDialog(null, ex); // show message
        logger.buildString(ex); // log occurance
    }

    /**
     * Defines the time zone rules to be applied to each airport in proper
     * format
     *
     * @param gmtOffset standard time offset from GMT
     * @param dstOffset daylight saving time offset from GMT
     * @param stdAbbr standard time region
     * @param dstAbbr daylight saving time zone region
     * @param rule for determining time change date
     * @return combines all variables into a string to append to airport
     */
    private String rulesString(String gmtOffset, String dstOffset,
            String stdAbbr, String dstAbbr, String rule) {
        // define Stringbuilder to create database rule tag
        StringBuilder combineAll = new StringBuilder();

        // combine all elements and add comma
        combineAll.append(gmtOffset).append(",").append(dstOffset)
                .append(",").append(stdAbbr).append(",").append(dstAbbr)
                .append(",").append(rule).append(",");

        return combineAll.toString();
    }

    /**
     * Gets the airport database file path and filters from main class
     *
     * @param airportFile airport database file path
     * @param types determine which airports to append rules to
     */
    public void getAirportFile(String airportFile, String types) {
        readAirportFile(airportFile, types);
    }

    /**
     * Gets the requested data component of the rule file
     *
     * @param isoRegion contains a selected variable to define a key in a map
     * @return
     */
    private String getRegion(String isoRegion) {
        return isoRegion;
    }

    /**
     * Removes delimiter quotes from parameter so that comparisons can be
     * consistent among all data in the database file
     *
     * @param data selected parameter to remove quotes if existent
     * @return
     */
    public String removeQuotes(String data) {
        // modify input to remove quotes
        String noQuotes = data;
        noQuotes = noQuotes.replace("\"", ""); // relace \ with no space

        return noQuotes;
    }

    /**
     * Appends all lines with "!!!" then cuts the line exactly to 14 elements.
     * This is so that lines of different lengths will all be the same and
     * irregularities will not happen.
     *
     * @param line each line of the airport database file being read in
     * @return
     */
    private String fixDataLine(String line) {
        // append '!!!' line so that all lines have same 
        // number of elements when parsed to eliminate
        // line length discrepancies
        line = line.concat("!!!");

        // split line into data elements
        String[] data = line.split(csvSplit);
        String newLine = "";

        // for loop to iterate through each element of each line
        // and create a new correctly formatted line
        for (int i = 0; i < data.length; i++) {
            // set to <= 14 for set length for all lines
            // before appending rules and offsets
            if (i <= 14) {
                newLine = newLine.concat(data[i]).concat(",");
            }
        }
        return newLine;
    }

    /**
     * calls methods to write newly created database files for Airports.csv,
     * No_TimeZone_Rules.csv, Removed_Airports.csv as well as update the
     * Rules.csv file with newly created rules. All data is then erased in the
     * event that the program is not terminated before another operation takes
     * place.  Files will only be written if all program operations run 
     * satisfactory.
     *
     * @throws IOException
     */
    public void writeFile() throws IOException {
        // if state is true, write files
        if (getState == true) {
            // update rules
            newRules.writeFile();
            // write file of airports with no timezone rules
            update.writeFile();
            // write airports removed
            review.writeFile();
            // write updated airport database
            write.writeFile();
            // display successful file write
            JOptionPane.showMessageDialog(null, "Airport.csv Build Complete!");
        } else {
            logger.writeFile();
        }
        // clear output data for next build
        write.eraseBuilder();
        newRules.eraseBuilder();
        update.eraseBuilder();
        review.eraseBuilder();
    }
}
