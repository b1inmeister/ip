package amadinho.tasktypes;

/**
 * Subclass of Task for Tasks that do not have any time-related information.
 */
public class Todo extends Task {

    /**
     * Constructor for a Todo to be instantiated.
     *
     * @param description Description of the Todo to be instantiated.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Converts a Todo to a String for printing purposes.
     *
     * @return String equivalent of the Todo.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
