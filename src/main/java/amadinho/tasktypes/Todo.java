package amadinho.tasktypes;

/**
 * Subclass of Task for Tasks that do
 * not have a provided deadline.
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
     * Converts a Todo to a String type for printing purposes.
     * Builds upon the similarly named method in the Task superclass.
     *
     * @return String version of the Todo chosen.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
