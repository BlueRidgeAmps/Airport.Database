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
 *   @filename  Move.java                                      
 *                                                                            
 *   @description  copies files from one directory to another
 *                                                                           
 *   @author  jonathan.pearson
 *   @date  June 9, 2017
 *   @summary  directory before being replaced by new versions.  The files will 
 *             have the date and time added to the name for record.  It is called
 *             when files need to be archived when a new file takes it's place.
 * 
 */

// class packages
import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Move {

    // Default constructor
    public Move() {
    }
    
    // copyFile() copies file in directory if exist
    /**
     * Retrieves the selected file from its directory and copies it to the
     * Archive directory.  The date and a time stamp are applied to the file name.
     * @param file
     * @throws IOException 
     */
    public void copyFile(String file) throws IOException {
        // define new file
        File f = new File(file);
        // define new directory and file name
        String k = "Archive/";
        String p = file.replaceAll(".csv", "_").replaceAll("Control/", "");
        String q = ".csv";

        // if file exist, rename and move to Archive
        if (f.exists() && !f.isDirectory()) {
            // define name
            String name = k + p + getDate() + q;
            //Copy file to new directory
            Files.copy(Paths.get(file), Paths.get(name), REPLACE_EXISTING);
        }
    }

    /**
     * Gets the local date and time from the system
     * 
     * @return 
     */
    public String getDate() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(Calendar.getInstance().getTime());
    }
}
