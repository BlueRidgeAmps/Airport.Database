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
 *   @filename  OpenFile.java                                     
 *                                                                            
 *   @description  opens selected file
 *                                                                           
 *   @author  jonathan.pearson
 *   @date  June 9, 2017
 *   @summary  The OpenFile class defines a method to open files in the 
 *             AirportDatabaseUpdate directory for viewing.
 * 
 */

// class packages
import java.awt.*;
import java.io.*;

public class OpenFile {

    // default constructor
    public OpenFile() {

    }

    /**
     * Checks to see if a file exist in the directory and not a directory
     * 
     * @param file  file path that will be compared
     * @return 
     */
    public boolean checkFile(String file) {
        File f = new File(file);
        return f.exists() && !f.isDirectory();
    }

    /**
     * method to open the selected file for viewing
     * 
     * @param file  file path for selected file to view
     * @throws IOException 
     */
    public void openFile(String file) throws IOException {
        if (Desktop.isDesktopSupported()) {
            File myFile = new File(file);
            Desktop.getDesktop().open(myFile);
        }
    }
}
