package amadinho.tasktypes;

/**
 * Subclass of Task for Tasks that have
 * a provided start and end time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructor for an Event to be instantiated.
     *
     * @param description Description of the Event to be instantiated.
     * @param from Start date / time of this Event.
     * @param to End date / time of this Event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Getter function for the start date and time of an Event.
     *
     * @return Start date / time of the Event chosen.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Getter function for the end date and time of an Event.
     *
     * @return End date / time of the Event chosen.
     */
    public String getTo() {
        return to;
    }

    /**
     * Converts an Event to a String type for printing purposes.
     * Builds upon the similarly named method in the Task superclass.
     *
     * @return String version of the Event chosen.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
