package xmorph.edu.usu.reporting;

/* The TypeInformation class holds static information related to a single 
 * evaluation of a query. The information is used for reporting purposes.
 * 
 * @author Curtis
 */
public class TypeInformation {

    static String information = "";
    static public Boolean captureTypeInformation = true;

    /* The constructor should never be called, all methods will be static. */
    public TypeInformation() {
    }


    /* Adds to the type information string. 
     */
    static public void appendLn(String s) {
        information += s + "\n";
    }

    /* Adds to the type information string. 
     */
    static public void append(String s) {
        information += s;
    }

    /* Get the type report information.
     */
    static public String report() {
        return information;

    }

    /* Get the type report information. And clear
     * the report.
     */
    static public String reportAndReset() {
        String s = report();
        reset();
        return s;
    }

    /* Reset the type report information.
     */
    static public void reset() {
        information = "";
    }
}
