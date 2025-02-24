package amadinho.tasktypes;

public class Task {

    protected String description;
    protected boolean isDone;

    private static final int TASKTYPE_POSITION = 1;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : "_");
    }

    public char getTaskType() {
        return toString().charAt(TASKTYPE_POSITION);
    }

    public void setStatusIcon(String statusIcon) {
        if (statusIcon.equals("X")) {
            markAsDone();
        } else if (statusIcon.equals("_")) {
            markAsUndone();
        }
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsUndone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
