package amadinho.exceptions;

/**
 * Exception for the scenario
 * when the list of Tasks is empty.
 */
public class EmptyList extends Exception {

    /**
     * Constructor for the EmptyList exception.
     *
     * @param message Error message to be printed when EmptyList is thrown.
     */
    public EmptyList(String message){
        super(message);
    }
}
