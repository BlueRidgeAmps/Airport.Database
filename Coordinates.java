package airportdatabaseupdate;

/**                                                                                         
 *   @filename  Coordinates.java                                   
 *                                                                            
 *   @description  converts latitude and longitude from String to Integer
 *                                                                           
 *   @author  jonathan.pearson
 *   @date  June 16, 2017
 *   @summary  The Coordinates class defines the latitude and longitude from the 
 *             airport file being parsed.  The latitude and longitude are 
 *             returned to the nearest whole number.
 *
 */

public class Coordinates {

    // define class variables
    int latitude;
    int longitude;

   /**
    * Class constructor
    * 
    * @param latitude  latitude variable
    * @param longitude  longitude variable
    */
    public Coordinates(int latitude, int longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Defines the latitude to the nearest whole number from 
     * String to Integer
     * 
     * @param zLatitude  parsed String value of latitude
     * @return  integer latitude to use as object in rest of package
     */
    public int convertLat(String zLatitude) {
        // modify input to remove quotes
        String mod = removeQuotes(zLatitude);

        // if not title then define
        if (!mod.equals("latitude_deg")) {
            double temp = Double.parseDouble(mod);
            latitude = (int) temp; // cast to intteger
        } else {
            // could set value to 0 and populate removed airport list
            // since it is ocean but someday an island with a runway could
            // be constructed at lat 0/long 0
            latitude = 500;
        }
        return latitude;
    }

    /**
     * Defines the longitude to the nearest whole number from 
     * String to Integer
     * 
     * @param zLongitude  parsed String value of longitude
     * @return  integer longitude to use as object in rest of package
     */
    public int convertLong(String zLongitude) {
        // modify input to remove quotes
        String mod = removeQuotes(zLongitude);

        // if not title then define
        if (!mod.equals("longitude_deg")) {
            double temp = Double.parseDouble(mod);
            longitude = (int) temp; // cast to integer
        } else {
            longitude = 500;
        }
        return longitude;
    }
    
    /**
     * Removes quotes from parsed latitude of longitude String if present
     * 
     * @param text  variable to remove quotes from
     * @return  variable with quotes removed for use in class
     */
    private String removeQuotes(String text){
        String noQuote = text;
        noQuote = noQuote.replace("\"", ""); // replace / with no space
        
        return noQuote;
    }
}
