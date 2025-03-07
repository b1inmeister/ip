package amadinho.tasktypes;

/**
 * Subclass of Task for Tasks that have a start and end timing.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Constructor for an Event to be instantiated.
     *
     * @param description Description of the Event to be instantiated.
     * @param from Start date and time of the Event.
     * @param to End date and time of the Event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Getter function for the start date and time of an Event.
     *
     * @return Start date and time of the Event.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Getter function for the end date and time of an Event.
     *
     * @return End date and time of the Event.
     */
    public String getTo() {
        return to;
    }

    /**
     * Converts an Event to a String for printing purposes.
     *
     * @return String equivalent of the Event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
