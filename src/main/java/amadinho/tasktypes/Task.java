package amadinho.tasktypes;

/**
 * Custom data type that contains data of each Task that
 * the user wants to add to their list of Tasks.
 */
public class Task {

    protected String description;
    protected boolean isDone;

    private static final int TASKTYPE_POSITION = 1;

    /**
     * Constructor for a Task to be instantiated.
     *
     * @param description Description of the task to be instantiated.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Getter function for the description within a Task.
     *
     * @return Description of the Task chosen.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter function for the completion status of a Task.
     * X means complete, while _ means incomplete.
     *
     * @return Completion status of the Task chosen.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : "_");
    }

    /**
     *  Getter function of the type of a Task.
     *  T means todo, D means deadline, and E means event.
     *
     * @return Type of the Task chosen.
     */
    public char getTaskType() {
        return toString().charAt(TASKTYPE_POSITION);
    }

    /**
     * Setter function of the completion status of a Task.
     * This method is called when transferring data of an existing list of Tasks
     * from a provided text file to the program.
     *
     * @param statusIcon Completion status of the Task to be added.
     */
    public void setStatusIcon(String statusIcon) {
        if (statusIcon.equals("X")) {
            markAsDone();
        } else if (statusIcon.equals("_")) {
            markAsUndone();
        }
    }

    /**
     * Marks the chosen Task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the chosen Task as incomplete.
     */
    public void markAsUndone() {
        isDone = false;
    }

    /**
     * Converts a Task to a String type for printing purposes.
     *
     * @return String version of the Task chosen.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
