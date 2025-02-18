package amadinho.ui;

public class Constants {
    protected static final int LIST_COUNTER_START = 1;
    protected static final int ARRAY_INCREMENT = 1;
    protected static final int START_OF_STRING = 0;

    protected static final int LISTFILE_STATUSICON = 1;
    protected static final int LISTFILE_DESCRIPTION = 2;
    protected static final int LISTFILE_BY = 3;
    protected static final int LISTFILE_FROM = 3;
    protected static final int LISTFILE_TO = 4;

    protected static final String LIST_SPACE = " ";
    protected static final String LIST_DOT = ".";

    protected static final char CHAR_TODO = 'T';
    protected static final char CHAR_DEADLINE = 'D';
    protected static final char CHAR_EVENT = 'E';
    protected static final String STRING_SPACE = "T";
    protected static final String STRING_DEADLINE = "D";
    protected static final String STRING_EVENT = "E";

    protected static final String COMMAND_BYE = "bye";
    protected static final String COMMAND_LIST = "list";
    protected static final String COMMAND_MARK = "mark";
    protected static final String COMMAND_UNMARK = "unmark";
    protected static final String COMMAND_DELETE = "delete";
    protected static final String COMMAND_TODO = "todo";
    protected static final String COMMAND_DEADLINE = "deadline";
    protected static final String COMMAND_EVENT = "event";

    protected static final String IDENTIFIER_BY = "/by";
    protected static final String IDENTIFIER_FROM = "/from";
    protected static final String IDENTIFIER_TO = "/to";

    protected static final int LENGTH_BY = IDENTIFIER_BY.length();
    protected static final int LENGTH_FROM = IDENTIFIER_FROM.length();
    protected static final int LENGTH_TO = IDENTIFIER_TO.length();

    protected static final String MESSAGE_WELCOME = """
            To add new Tasks                    todo / deadline / event
            To mark Tasks as complete or not    mark / unmark
            To list all Tasks                   list
            To delete Tasks                     delete
            To give up and exit                 bye""";
    protected static final String MESSAGE_EXIT = "Congrats! You have given up.";

    protected static final String MESSAGE_LIST_INTRO = "Tasks in the list:";
    protected static final String MESSAGE_MARK_COMPLETE = "Task marked as done:";
    protected static final String MESSAGE_UNMARK_COMPLETE = "Task marked as not done:";
    protected static final String MESSAGE_DELETED_TASK = "Task deleted:";
    protected static final String MESSAGE_ADDED_TASK = "Task added:";
    protected static final String MESSAGE_TOTALTASKS = "Number of tasks: ";

    protected static final String MESSAGE_LIST_EMPTY = "The list is empty. Lazy bum.";
    protected static final String MESSAGE_ERROR_OUTOFBOUNDS = "Number provided is not in the list. Lousy.";
    protected static final String MESSAGE_ERROR_FILENOTFOUND = "File not found. Just give up.";
    protected static final String MESSAGE_ERROR_IO = "Error creating file / directory. Just give up.";
    protected static final String MESSAGE_ERROR_WRITEFAILED = "Error writing to file. Just give up.";
    protected static final String MESSAGE_ERROR_READFAILED = "Error reading file. Just give up.";
    protected static final String MESSAGE_ERROR_INVALID_COMMAND = "Invalid command. Skill issue.";
    protected static final String MESSAGE_ERROR_INVALID_COMMAND_MARK = "mark / unmark <integer>";
    protected static final String MESSAGE_ERROR_INVALID_COMMAND_DELETE = "delete <integer>";
    protected static final String MESSAGE_ERROR_INVALID_COMMAND_TODO = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "todo <description>";
    protected static final String MESSAGE_ERROR_INVALID_COMMAND_DEADLINE = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "deadline <description> /by <deadline>";
    protected static final String MESSAGE_ERROR_INVALID_COMMAND_EVENT = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" +  "event <description> /from <start> /to <end>";

    protected static final String LISTFILE_PATHNAME = "./data/amadinho.txt";
    protected static final String LISTFILE_DIVIDER = " | ";
    protected static final String LISTFILE_NEWLINE = "\n";
    protected static final String SPLIT_PARAMETER = "\\|";
    protected static final String BORDER_LINE = "____________________________________________________________";
}
