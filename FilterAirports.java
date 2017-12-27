package airportdatabaseupdate;

/**                                                                                                          
 *   @filename  FilterAirports.java                                   
 *                                                                            
 *   @description  determines the airports to include in database
 *                                                                           
 *   @author  jonathan.pearson
 *   @date  June 9, 2017
 *   @summary  The FilterAirports class defines a which airports will be applied
 *             to the new "Airports.csv" file.  A false value denotes the airport
 *             will be excluded.
 * 
 */
public class FilterAirports {

    // define class variables
    private String check;
    private boolean state;

    /**
     * Default constructor
     * 
     * @param check  reference defined for variable to store 
     *               airport filter selection
     */
    public FilterAirports(String check) {
        this.check = check;
    }

    /**
     * Define which airports to filter
     * 
     * @param choices  parameter passed from main class that specifically
     *                 defines airport filter selections
     */
    public void filter(String choices) {
        check = choices;
    }
    
    /**
     * Getter method to return the airport filter selection
     * 
     * @return 
     */
    public String getFilter(){
        return check;
    }
    
    /**
     * Compares the airport filter to airport type.  If the filter contains 
     * a defined letter, the airport will be added to the database and time
     * zone rules will be appended.
     * 
     * @param airport  parameter for airport type to compare to airport filter
     * @return 
     */
    public boolean checkFilter(String airport){
        // use switch statement to determine filter
        switch(airport){
            case "type":
                return true; // to always return csv file header
            case "small_airport":
                return state = getFilter().contains("s");
            case "medium_airport":
                return state = getFilter().contains("m");
            case "large_airport":
                return state = getFilter().contains("l");
            case "heliport":
                return state = getFilter().contains("h");
            case "seaplane_base":
                return state = getFilter().contains("e");
            case "balloonport":
                return state = getFilter().contains("r");
            case "closed":
                return state = getFilter().contains("c");
            default:
                break;
        }
        return state;
    }
}
