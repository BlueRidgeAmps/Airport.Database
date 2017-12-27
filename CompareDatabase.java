package airportdatabaseupdate;

/**                                                                                 
 *   @filename  CompareDatabase.java                                      
 *                                                                            
 *   @description  compares new database to old database to determine new
 *                 airports added and airports removed
 *                                                                           
 *   @author  jonathan.pearson
 *   @date  June 22, 2017
 *   @summary  The CompareDatabase class defines a method to compare old and
 *             new database files.  A simple GUI panel is defined for a visual
 *             aide to allow for easy access to files for comparison.  Once
 *             each file is determined, the readInFile() method reads in each 
 *             file line by line, parsing the data and adding a key and value
 *             to a HashMap.  Once both files are read in, when the "OK" button
 *             is clicked, the two HashMaps created are compared and then
 *             the data is sent to be written to two files.  One with airports
 *             added and one with airports removed based on the new airport
 *             database file.
 */
// import class packages
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CompareDatabase {
        
    // define class variables
    private String line = "", file;
    private boolean state;
    private int status = 0;
    JFileChooser chooser;
    private final String csvSplit
            = String.format(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    private final HashMap<String, String> oldAirport = new HashMap<>();
    private final HashMap<String, String> newAirport = new HashMap<>();

    // define panel components for pop-up GUI
    JPanel panel = new JPanel(); // panel to impose on JOptionPane
    JButton newBtn = new JButton("Open Current Airport.csv");
    JButton oldBtn = new JButton("Open Old Airport.csv");
    // JButton okay = new JButton("OK");

    // define instance of package classes
    LoggerFile logger = new LoggerFile();
    ReadAirport quote = new ReadAirport();
    WriteCompare compare = new WriteCompare();

    /**
     * default constructor
     */
    public CompareDatabase() {

    }

    /**
     * reads in airport files to compare.  method opens file, removes
     * quotes from string values being compared and sets as key in HashMap.  The
     * value field of HashMap is the physical airport data line.
     * 
     * @param file  file path for airport.csv to read in
     */
    private void readInFile(String file) {
        // define local variables
        BufferedReader read = null;

        try {
            // read in file to add rules to
            // force program to use UTF-8 character set
            read = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), StandardCharsets.UTF_8));

            while ((line = read.readLine()) != null) {

                // split line into array of elements for manipulation
                String[] data = line.split(csvSplit);

                // Use data point "id" for comparison
                // By evaluation, it is the only identifyer 
                // that does not change over time
                // Remove quotes if present
                String data0 = quote.removeQuotes(data[0]);

                // call buildMap() method to sort into maps
                buildMap(data0, line);
            }
        } catch (FileNotFoundException ex) {
            logger.buildString(ex.toString()); // log occurance
        } catch (IOException ex) {
            logger.buildString(ex.toString()); // log occurance
        } finally {
            try {
                if (file != null) {
                    read.close();
                }
            } catch (IOException ex) {
                logger.buildString(ex.toString()); // log occurance
            }
        }
    }

    /**
     * method to determine whether the airport file read in is 
     * new or old.  the boolean variable setState is defined
     * true for new airport file and false for old airport file.
     * 
     * @param data  key for HashMap, airport numeric ID
     * @param lines  value for HashMap, airport data line
     */
    private void buildMap(String data, String lines) {
        boolean setState = getState();
        
        // use selection statement to determine if 
        // the old database file or new database file
        // is being used for correct map placement
        if (setState == true) {
            newAirport.put(data, lines);
        } else if (setState == false) {
            oldAirport.put(data, lines);
        }
    }

    /**
     * method to compare the new airport database file key set
     * to the old airport database key set.  only airports that 
     * have a unique id (added) will be written to newAirports.csv.
     */
    public void compareNewAirport() {
        try {
            // Check for all key in newAirport 
            // not contained in oldAirport
            for (String c : newAirport.keySet()) {
                if (!oldAirport.containsKey(c)) {
                    // get value assoaciated to key
                    compare.buildString(newAirport.get(c));
                }
            }
        } catch (NullPointerException ex) {
            logger.buildString(ex.toString());
        }
        compare.writeFile("test/newAirports.csv"); // write new airports added
        compare.eraseBuilder(); // erase stored data for operation
    }

    /**
     * method to compare the old airport database file key set
     * to the new airport database key set.  only airports that 
     * have a unique id (removed) will be written to newAirports.csv.
     */
    public void compareOldAirport() {
        try {
            // Check for all key in oldAirport 
            // not contained in newAirport
            for (String b : oldAirport.keySet()) {
                if (!newAirport.containsKey(b)) {
                    // get value assoaciated to key
                    compare.buildString(oldAirport.get(b));
                }
            }
        } catch (NullPointerException ex) {
            logger.buildString(ex.toString());
        }
        compare.writeFile("test/oldAirports.csv"); // write airports removed
        compare.eraseBuilder(); // erase stored data for operation
    }

    /**
     * set state of old or new database
     * 
     * @param s  is either true (new airport) or false (old airport)
     */
    public void setState(boolean s) {
        state = s;
    }

    /**
     * getter method to determine if the airport database is old or new
     * 
     * @return  true for new airport database, false for old airport database
     */
    public boolean getState() {
        return state;
    }

    /**
     * gets database file to parse and compare
     * 
     * @param file  airport database file directory
     */
    public void getAirportFile(String file) {
        readInFile(file);
    }

    /**
     * defines simple GUI that contains two buttons; newBtn and oldBtn
     * the newBtn opens a JFileChooser that will allow user to search for
     * the new airport database.  oldBtn searches for old database file.
     * requirements for operation are determined in selection statement which
     * will initiate comparison feature.
     */
    public void getFilesToCompare() {

        // set GUI compoenents
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        newBtn.setPreferredSize(new Dimension(180, 25));
        oldBtn.setPreferredSize(new Dimension(180, 25));
        panel.add(newBtn);
        panel.add(oldBtn);

        // define actionListener for each button
        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAction(true);
                status++; // set status to infer button was clicked
            }
        });
        oldBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAction(false);
                status++; // set status to infer button was clicked
            }
        });

        // define button object for custom JOptionPane
        Object[] object = {"OK"};
        int input = JOptionPane.showOptionDialog(null,
                panel,
                "Action Required!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                object,
                object[0]);
        
        // use if/else selection to determine if option is valid
        if ((input == JOptionPane.OK_OPTION) && (status < 2)) {
            JOptionPane.showMessageDialog(null, "New and old airport files required.");
        } else if ((input == JOptionPane.OK_OPTION) && (status == 2)) {
            compareNewAirport();
            compareOldAirport();
            JOptionPane.showMessageDialog(null, "Comparison Successfully Completed");
        }
    }

    /**
     * method to open file directory using JFileChooser to select *.csv file
     * 
     * @return  sets file path as String
     */
    public String chooseFile(){
        // define local variables for file chooser
        chooser = new JFileChooser();
        FileNameExtensionFilter filter
                = new FileNameExtensionFilter("CSV (Comma delimited)", "csv");
        chooser.setFileFilter(filter); // set filter
        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile().toString();
            logger.buildString(file + " opened for compare");
        } else {
            logger.buildString("Open command cancelled"
                    + " by user for compare."); // log occurance
        }

        try {
            logger.writeFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    /**
     * button action for choosing file when either newBtn or oldBtn are
     * clicked.  wait cursor is set so that user knows operation is 
     * happening. 
     * 
     * @param value  set the state: true for new airport file, false
     *               for old airport file.
     */
    public void btnAction(boolean value) {
        // define wait cursor
        Cursor wait = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        // set cursor
        panel.setCursor(wait);
        // set for new airport file
        setState(value);
        // browse for current airport database
        // pass to read in file
        getAirportFile(chooseFile());
        // normal cursor when finished processes
        panel.setCursor(Cursor.getDefaultCursor());
    }
}
