package amadinho.tasktypes;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getStatusNumbers() {
        return (isDone ? "1" : "0");
    }

    public String getDescription() {
        return description;
    }

    public void setStatusIcon(String statusIcon) {
        if (statusIcon.equals("1")) {
            markAsDone();
        } else if (statusIcon.equals("0")) {
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
