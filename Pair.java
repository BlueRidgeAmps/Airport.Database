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
 *   @filename  Pair.java                                     
 *                                                                            
 *   @description  class to define latitude and longitude of airport
 *                                                                           
 *   @author  jonathan.pearson
 *   @date  June 9, 2017
 *   @summary  The Pair class defines a String representation of the latitude 
 *             and longitude of a given airport.  It is called to build a rules
 *             HashMap as well as define latitude and longitude to compare
 *             airports to the HashMap.
 * 
 * @author jonathan.pearson 
 */
public class Pair {
    // define class variables
    private int latitude;
    private int longitude;
    
    /**
     * Default constructor
     * 
     * @param latitude  parameter for latitude
     * @param longitude  parameter for longitude
     */
    public Pair(int latitude, int longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * Setter method to set the latitude to the reference variable
     * 
     * @param latitude  airport latitude
     */
    public void setLatitude(int latitude){
        this.latitude = latitude;
    }
    
    /**
     * Setter method to set the longitude to the reference variable
     * 
     * @param longitude  airport longitude
     */
    public void setLongitude(int longitude){
        this.longitude = longitude;
    }
    
   /**
    * Defines the latitude and longitude as whole string to make comparisons
    * 
    * @return 
    */
    @Override
    public String toString(){
        return (String.valueOf(latitude) + " " + String.valueOf(longitude));
    }
}
