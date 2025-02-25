package amadinho.exceptions;

/**
 * Exception for the scenario when
 * the command input is of incorrect format.
 */
public class InvalidCommand extends Exception {

    /**
     * Constructor for the InvalidCommand exception.
     *
     * @param message Error message to be printed when InvalidCommand is thrown.
     */
    public InvalidCommand(String message) {
        super(message);
    }
}
