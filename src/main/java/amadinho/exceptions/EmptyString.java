package amadinho.exceptions;

/**
 * Exception for the scenario when the String provided is empty.
 */
public class EmptyString extends Exception {

    /**
     * Constructor for the EmptyString exception.
     *
     * @param message Error message to be printed when EmptyString is thrown.
     */
    public EmptyString(String message) {
        super(message);
    }
}
