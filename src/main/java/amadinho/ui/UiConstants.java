package amadinho.ui;

/**
 * Contains constants that are used only within the Ui class.
 */
public class UiConstants {
    protected static final String COMMAND_BYE = "bye";
    protected static final String MESSAGE_WELCOME = """
            To add new Tasks                    todo / deadline / event
            To mark Tasks as complete or not    mark / unmark
            To list all Tasks                   list
            To delete Tasks                     delete
            To give up and exit                 bye""";
    protected static final String MESSAGE_EXIT = "Congrats! You have given up.";
    protected static final String MESSAGE_MARK_COMPLETE = "Task marked as done:";
    protected static final String MESSAGE_UNMARK_COMPLETE = "Task marked as not done:";
    protected static final String MESSAGE_DELETED_TASK = "Task deleted:";
    protected static final String MESSAGE_ADDED_TASK = "Task added:";
    protected static final String MESSAGE_TOTALTASKS = "Number of tasks: ";
}