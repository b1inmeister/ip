package amadinho.tasktypes;

/**
 * Subclass of Task for Tasks that
 * have a provided deadline.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Constructor for a Deadline to be instantiated.
     *
     * @param description Description of the Deadline to be instantiated.
     * @param by Date / time that this Deadline must be completed by.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Getter function for the date and time that a Deadline must be completed by.
     *
     * @return Date / time that the Deadline chosen must be completed by.
     */
    public String getBy() {
        return by;
    }

    /**
     * Converts a Deadline to a String type for printing purposes.
     * Builds upon the similarly named method in the Task superclass.
     *
     * @return String version of the Deadline chosen.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
