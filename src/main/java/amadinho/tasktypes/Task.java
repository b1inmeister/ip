package amadinho.tasktypes;

/**
 * Custom data type that contains information of each Task that the user wants to add to their list of Tasks.
 */
public class Task {

    protected String description;
    protected boolean isDone;

    private static final int TASKTYPE_POSITION = 1;

    /**
     * Constructor for a Task to be instantiated.
     *
     * @param description Description of the Task to be instantiated.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Getter function for the description of a Task.
     *
     * @return Description of the chosen Task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter function for the completion status icon of a Task. X means complete, while _ means incomplete.
     *
     * @return Completion status icon of the chosen Task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : "_");
    }

    /**
     *  Getter function of the type of a Task. T means todo, D means deadline, and E means event.
     *
     * @return Type of the chosen Task in a char form.
     */
    public char getTaskType() {
        return toString().charAt(TASKTYPE_POSITION);
    }

    /**
     * Setter function of the completion status of a Task. This method is only called when adding
     * the list of Tasks in the text file to the program.
     *
     * @param statusIcon Completion status icon of the Task to be added.
     */
    public void setStatusIcon(String statusIcon) {
        if (statusIcon.equals("X")) {
            markAsDone();
        } else if (statusIcon.equals("_")) {
            markAsUndone();
        }
    }

    /**
     * Marks a Task as complete.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks a Task as incomplete.
     */
    public void markAsUndone() {
        isDone = false;
    }

    /**
     * Converts a Task to a String for printing purposes.
     *
     * @return String equivalent of the Task .
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
