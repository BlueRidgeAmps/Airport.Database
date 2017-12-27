package airportdatabaseupdate;

/**                                                                                                           
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
