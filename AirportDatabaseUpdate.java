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
 *   @filename  AirportDatabaseUpdate.java                                     
 *                                                                           
 *   @description  Class to define GUI and GUI functions
 *                                                                           
 *   @author jonathan.pearson
 *   @date 07-JUN-2017
 *   @summary  The AirportDatabaseUpdate is the main program class. It 
 *             defines the GUI environment, provides actions to components, 
 *             and displays the GUI upon program initialization.
 */

// class packages
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.JFileChooser;
import javax.swing.border.*;
import javax.swing.filechooser.*;

public class AirportDatabaseUpdate extends JFrame implements ItemListener {

    // define class variables
    private static final int GUIWIDTH = 400, GUIHEIGHT = 400;
    private JFileChooser chooser;
    private FileNameExtensionFilter filter;
    private final StringBuffer choices = new StringBuffer("shmlerc"); // check box tag
    private static final Color BLUE = new Color(0x25, 0x3A, 0x65),
            SILVER = new Color(0xB1, 0xB8, 0xBC); // GUI color pallet

    // Define and initialize GUI variables
    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel(),
            helpPanel = new JPanel(),
            btnPanel = new JPanel(),
            checkPanel = new JPanel();
    private final JButton openBtn = new JButton("Find Airports"),
            airportBtn = new JButton("Open Airports"),
            editBtn = new JButton("View / Edit Rules"),
            buildBtn = new JButton("Build"),
            noRuleBtn = new JButton("Airports w/ No Rules"),
            exitBtn = new JButton("Exit"),
            autoBtn = new JButton("Auto Build"),
            compareBtn = new JButton("Compare");
    private final JCheckBox helo = new JCheckBox("Heliport", true),
            smallAP = new JCheckBox("Small Airfield", true),
            medAP = new JCheckBox("Medium Airfield", true),
            closedAP = new JCheckBox("Closed Airfield", true),
            seaAP = new JCheckBox("Seaplane Base", true),
            balloon = new JCheckBox("Balloon Port", true),
            largeAP = new JCheckBox("Large Airfield", true);
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JTextArea textArea = new JTextArea(20, 32);
    private final JScrollPane scrollPane = new JScrollPane(textArea);

    // define panel borders
    Font font = new Font("SansSerif", Font.BOLD, 12);
    TitledBorder btnBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SILVER), "Process",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font);
    TitledBorder chkBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(SILVER), "Filter",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, font);

    // Define layout manager for main function panel
    GroupLayout layout = new GroupLayout(btnPanel);
    GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
    GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

    // Define an instance of package classes
    ReadAirport airport = new ReadAirport();
    OpenFile file = new OpenFile();
    FilterAirports check = new FilterAirports("");
    LoggerFile logger = new LoggerFile();
    CompareDatabase compare = new CompareDatabase();

    
    
    
    public AirportDatabaseUpdate() {
        /**
         * Define the GUI frame
         *
         * Sets the menu bar title.  The GUI frame size is set by GUIWIDTH
         * and GUI HEIGHT.  The frame is not resizable and set to default to
         * the center of the monitor.  The background color is defined BLUE.
         */
        frame.setTitle("Airport Database Update"); // GUI title
        frame.setSize(GUIWIDTH, GUIHEIGHT); // set dimensions
        frame.setLocationRelativeTo(null); // set screen placement
        frame.setResizable(false); // set unresizable
        frame.getContentPane().setBackground(BLUE);

        // add tabs to GUI
        frame.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.addTab("About / How-To", null, helpPanel);
        tabbedPane.addTab("Update Airports", null, panel);
        tabbedPane.setForegroundAt(0, BLUE); // set Operation tab colors
        tabbedPane.setBackgroundAt(0, SILVER);
        tabbedPane.setForegroundAt(1, BLUE); // set Help tab colors
        tabbedPane.setBackgroundAt(1, SILVER);

        /**
         * Define help panel for the GUI
         *
         * Add text field to panel Set layout for panel Add text document to
         * panel
         */
        helpPanel.add(scrollPane);
        helpPanel.setLayout(new FlowLayout());
        helpPanel.setBackground(Color.WHITE);
        // wrap file text
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        getHelpFile(); // open about text document to GUI

        /**
         * Define the main function panel for the GUI
         *
         * Add nested panels to GUI panel; btnPanel, checkPanel Set the group
         * layout for btnPanel Set grid layout for checkPanel Add borders to
         * btnPanel & checkPanel Define color scheme for panel Set button
         * description pop ups Set listeners for check boxes
         */
        // add panels to tabs
        panel.setLayout(new GridLayout(2, 1));
        panel.setBackground(BLUE);
        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(checkPanel, BorderLayout.SOUTH);

        // define nested panels
        btnPanel.setLayout(layout);
        btnPanel.setBorder(btnBorder);
        buildBtn.setEnabled(false); // set button disabled

        // set layout for btnPanel
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        // set button size
        layout.linkSize(openBtn, airportBtn, editBtn, noRuleBtn,
                buildBtn, exitBtn, autoBtn, compareBtn);
        // Create two parallel groups of buttons
        // set horizontal group of buttons
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(openBtn)
                .addComponent(airportBtn)
                .addComponent(editBtn)
                .addComponent(noRuleBtn)).addGap(55);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(buildBtn)
                .addComponent(autoBtn)
                .addComponent(compareBtn)
                .addComponent(exitBtn)).addGap(55);
        layout.setHorizontalGroup(hGroup);
        // set vertical group of buttons
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(openBtn)
                .addComponent(buildBtn));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(airportBtn)
                .addComponent(autoBtn));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(editBtn)
                .addComponent(compareBtn));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(noRuleBtn)
                .addComponent(exitBtn));
        layout.setVerticalGroup(vGroup);

        // set layout for checkPanel
        checkPanel.setLayout(new GridLayout(4, 2, 0, 0));
        checkPanel.setBorder(chkBorder);
        // add components
        checkPanel.add(smallAP);
        checkPanel.add(helo);
        checkPanel.add(medAP);
        checkPanel.add(balloon);
        checkPanel.add(largeAP);
        checkPanel.add(seaAP);
        checkPanel.add(closedAP);

        // define GUI colors of components
        btnPanel.setBackground(BLUE);
        btnBorder.setTitleColor(SILVER);
        checkPanel.setBackground(BLUE);
        chkBorder.setTitleColor(SILVER);
        smallAP.setBackground(BLUE);
        smallAP.setForeground(SILVER);
        medAP.setBackground(BLUE);
        medAP.setForeground(SILVER);
        largeAP.setBackground(BLUE);
        largeAP.setForeground(SILVER);
        closedAP.setBackground(BLUE);
        closedAP.setForeground(SILVER);
        helo.setBackground(BLUE);
        helo.setForeground(SILVER);
        seaAP.setBackground(BLUE);
        seaAP.setForeground(SILVER);
        balloon.setBackground(BLUE);
        balloon.setForeground(SILVER);

        // set dialog for buttons when curser
        // lingers over button
        setToolTipText();

        // Register listener for check box
        smallAP.addItemListener(this);
        medAP.addItemListener(this);
        largeAP.addItemListener(this);
        helo.addItemListener(this);
        seaAP.addItemListener(this);
        balloon.addItemListener(this);
        closedAP.addItemListener(this);

        // call readRuleFile() to parse time zone rules
        airport.readRuleFile();
        // check if files are availiable for viewing
        checker();

        /* autoBtn 
        Allows for all actions of program to operate on a single user
        action.  Using a predefined URL, the button will download the correct 
        file, pass information across the package classes, and write files to
        correct directories */
        autoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // define file path and control name for download
                String saveTo = "Control/airports_download_current.csv";

                try {
                    // set cursor to wait
                    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    // define file path to airport.csv from OurAirports.com
                    URL webFile = new URL("http://ourairports.com/data/airports.csv");
                    // retrieve file from URL
                    BufferedInputStream in = new BufferedInputStream(webFile.openStream());
                    // copy to control directory
                    Files.copy(in, Paths.get(saveTo), StandardCopyOption.REPLACE_EXISTING);
                    // send to passFile() method
                    passFile(saveTo);
                    // update database
                    airport.writeFile();
                    // check if files are availiable for viewing
                    checker();
                } catch (MalformedURLException ex) {
                    logger.buildString(ex.toString()); // log occurance
                    JOptionPane.showMessageDialog(null, ex);
                } catch (IOException ex) {
                    logger.buildString(ex.toString()); // log occurance
                    JOptionPane.showMessageDialog(null, ex + "\n\n"
                            + "Unable to download file from current file path.");
                } finally {
                    // normal cursor when finished
                    frame.setCursor(Cursor.getDefaultCursor());
                }
            }
        });
        /* openBtn
        Opens JFileChooser dialog for user to select airport file to
        append time zone rules to */
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // open file
                chooseFile();
                // enable build button
                buildBtn.setEnabled(true);
            }
        });
        /* editBtn
        Opens the rules.csv file so the user can view and make changes
        without navigating to file directory */
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    // open rules file for editing
                    file.openFile("Control/rules.csv");
                } catch (IOException | IllegalArgumentException ex) {
                    logger.buildString(ex.toString()); // log occurance
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        /* buildBtn
        Processes request to append rules to airport database file
        and writes all files to program directories. */
        buildBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // local variable to define file path of chosen file
                String filePath = chooser.getSelectedFile().toString();
                
                try {
                    // set cursor to wait
                    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    // send to passFile() method
                    passFile(filePath);
                    // update database
                    airport.writeFile();
                    // check if files are availiable for viewing
                    checker();
                } catch (IOException ex) {
                    logger.buildString(ex.toString()); // log occurance
                    JOptionPane.showMessageDialog(null, ex);
                } finally {
                    // normal cursor when finished
                    frame.setCursor(Cursor.getDefaultCursor());
                }
            }
        });
        /* noRuleBtn
        Opens the No_TimeZone_Rules.csv file upon user request for viewing */
        noRuleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // open rules file for viewing
                    file.openFile("No_TimeZone_Rules.csv");
                } catch (IOException | IllegalArgumentException ex) {
                    logger.buildString(ex.toString()); // log occurance
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        /* airportBtn
        Opens the Airports.csv file with timezone rules appened 
        upon user request for viewing */
        airportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // open rules file for viewing
                    file.openFile("Airports.csv");
                } catch (IOException | IllegalArgumentException ex) {
                    logger.buildString(ex.toString()); // log occurance
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        /* exitBtn
        Terminates the program when clicked */
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fClose(); // close GUI frame
            }
        });
        /* compareBtn
        Opens dialog to compare the old airport database file to the
        new database to show added and removed airports */
        compareBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compare.getFilesToCompare();
            }
        });
        /* frame
        Applies listener to GUI main frame to perform actions
        when GUI frame is closed.*/
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fClose(); // close GUI frame
            }
        });
    }

    /**
     * Defines the string that will determine which JCheckBox is 
     * selected or deselected via ItemListener.  Result will be passed
     * to the FilterAirports class. 
     * 
     * @param  e get item listener state of JCheckBox selection.
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        // define variables
        int index = 0;
        char a = '-';
        Object source = e.getItemSelectable();

        if (source == smallAP) {
            index = 0;
            a = 's';
        } else if (source == helo) {
            index = 1;
            a = 'h';
        } else if (source == medAP) {
            index = 2;
            a = 'm';
        } else if (source == largeAP) {
            index = 3;
            a = 'l';
        } else if (source == seaAP) {
            index = 4;
            a = 'e';
        } else if (source == balloon) {
            index = 5;
            a = 'r';
        } else if (source == closedAP) {
            index = 6;
            a = 'c';
        }
        // determine if check box is selected
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            a = '-';
        }
        // Apply state change
        choices.setCharAt(index, a);
    }
    
    /**
     * Getter method that allows for passing of the JCheckBox filter
     * selection among the AirportDatabaseUpdate package
     * 
     * @return  define choices variable as a string
     */
    private String getTypes() {
        return choices.toString();
    }

    /**
     * Passes file name and JButton name to checkIfExist() method to
     * perform directory file check
     */
    private void checker() {
        // check if files are availiable for viewing
        checkIfExist("Airports.csv", airportBtn);
        checkIfExist("Control/Rules.csv", editBtn);
        checkIfExist("No_TimeZone_Rules.csv", noRuleBtn);
    }
    
    /**
     * Compares the files in the directory to files required by buttons for
     * actions.  If the file is not found, the button will not be enabled.
     * 
     * @param dir_file  file name that is required by associated button
     * @param button   JButton to have state set based on file comparison
     */
    private void checkIfExist(String dir_file, JButton button) {
        // define local variable to 
        boolean checkDirFile = file.checkFile(dir_file);
        
        if (checkDirFile == true) {
            // file found, enable button
            button.setEnabled(true); 
        } else {
            button.setEnabled(false);
        }
    }

    /**
     * Opens JFileChooser dialog so that the user can browse to
     * the Airports.csv file that will have time zone rules appended
     */
    private void chooseFile() {
        // define new file chooser
        chooser = new JFileChooser();

        // filter file type to only show *.csv files
        filter = new FileNameExtensionFilter("CSV (Comma delimited)", "csv");
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
    }

    /**
     * Opens definition document that will display 
     * on "About/How-To" tab of GUI
     */
    private void getHelpFile() {
        // define variables
        String def_file = "Control/definitions";
        FileReader readFile = null;

        try {
            readFile = new FileReader(def_file); // open file
            textArea.read(readFile, def_file); // apply text to GUI text area
        } catch (FileNotFoundException ex) {
            logger.buildString(ex.toString()); // log occurance
            ex.printStackTrace();
        } catch (IOException ex) {
            logger.buildString(ex.toString()); // log occurance
            ex.printStackTrace();
        } finally {
            if (readFile != null) {
                try {
                    readFile.close();
                } catch (IOException ex) {
                    logger.buildString(ex.toString()); // log occurance
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Method passes parameters to ReadAirport class getAirportFile() method.
     * 
     * @param file  The file path for the selected file.
     */
    private void passFile(String file) {
        // define local variable
        String types = getTypes(); 
        // pasa parameters to ReadAirport class that will 
        // read in the selected file and append date based on filters
        airport.getAirportFile(file, types);
    }

    /**
     * Registers the text to display a tool tip.  The text
     * displays when the cursor lingers over the component.
     */
    private void setToolTipText(){
        openBtn.setToolTipText("<html>Find new airport file to apply rules<html>");
        editBtn.setToolTipText("<html>Open saved rules file<html>");
        buildBtn.setToolTipText("<html>Process new airports file<br/> "
                + "with rule applied and filters set<html>");
        noRuleBtn.setToolTipText("<html>View airports removed from database<html>");
        airportBtn.setToolTipText("<html>Open airports file with rules applied<html>");
        exitBtn.setToolTipText("Exit Application");
        autoBtn.setToolTipText("<html>Automatically retrieve and build an updated"
                + "<br/>Airport Database with rules applied.");
        compareBtn.setToolTipText("<html>Compares new database to old database"
                +"<br/>and will write two CVS files that will"
                + "<br/> show airports added and airports removed.");
    }
    
    /**
     * Method to make the GUI visible when called.  A check will be made to 
     * determine if a rules.csv file exist that will allow the program to
     * function.
     */
    private void display() {
        // define local variable to 
        boolean checkDirFile = file.checkFile("Control/rules.csv");
        
        // frame is set visible
        frame.setVisible(true);

        // perform check to see if file exist required to operate program
        if (checkDirFile == false) {
            JOptionPane.showMessageDialog(null, "rules.csv file not found, "
                    + "program is degraded");
            openBtn.setEnabled(false); // disable button
        }
    }

    /**
     * Defines closing function of GUI when GUI frame "X" or "Exit" 
     * button is selected.  All errors in the program will be written 
     * to a log file.
     */
    public void fClose() {
        try {
            // write text file of errors for troublshooting
            logger.writeFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }

   /**
    * Program main method that will call display() when program is
    * initiated.
    * 
    * @param args 
    */
    public static void main(String[] args) {
        // create instance of main class
        AirportDatabaseUpdate update = new AirportDatabaseUpdate();
        // display file chooser on program execution
        update.display();
    }
}
