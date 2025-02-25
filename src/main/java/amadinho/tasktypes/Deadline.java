package amadinho.tasktypes;

/**
 * Subclass of Task for Tasks that have a deadline timing.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Constructor for a Deadline to be instantiated.
     *
     * @param description Description of the Deadline to be instantiated.
     * @param by Date and time that the Deadline must be completed by.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Getter function for the date and time that a Deadline must be completed by.
     *
     * @return Date and time that the Deadline must be completed by.
     */
    public String getBy() {
        return by;
    }

    /**
     * Converts a Deadline to a String for printing purposes.
     *
     * @return String equivalent of the Deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
