package airportdatabaseupdate;

/**                                                                                                        
 *   @filename  HardCodeRules.java                                    
 *                                                                            
 *   @description  airports that contain rules that are exceptions to the region
 *                                                                           
 *   @author  jonathan.pearson
 *   @date  June 9, 2017
 *   @summary  The HardCodeRules class defines rules of airports that are 
 *             exceptions to the corresponding region time zone rules.  It is 
 *             called only when the selected GPS codes are parsed
 * 
 * @author jonathan.pearson
 */
public class HardCodeRules {

    // define class variables
    private String rules;

    /**
     * Default constructor
     * 
     * @param rules reference defined for variable to store 
     *              defined rules for the specific airport
     */
    public HardCodeRules(String rules) {
        this.rules = rules;
    }

    // getRules() to define hard coded rule exceptions
    /**
     * Defines the hard coded rules for each airport.  A switch statment is
     * used to determine which airport is parsed and what time zone rule to 
     * apply.
     * 
     * @param region  contains the value for the GPS identifier for the airport
     * @return 
     */
    public String getRules(String region) {

        switch (region) {
            case "SCTC":
            case "SCTO":
                return rules = ",-240,-180,CLT,CLST,6,";
            case "FM-ULI":
                return rules = ",660,660,FALT,FALT,0,";
            case "FM-0001":
                return rules = ",600,600,WOLT,WOLT,0,";
            case "PTYA":
                return rules = ",600,600,YAPT,YAPT,0,";
            case "MMCS":
                return rules = ",-420,-360,MST,MDT,4,";
            case "NTGJ":
                return rules = ",-540,-540,GAMT,GAMT,0,";
            default:
                break;
        }
        return rules;
    }
}
