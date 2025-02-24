package amadinho.main;

public class Constants {

    public static final int LIST_COUNTER_START = 1;
    public static final int ARRAY_INCREMENT = 1;
    public static final int START_OF_STRING = 0;

    public static final int LISTFILE_STATUSICON = 1;
    public static final int LISTFILE_DESCRIPTION = 2;
    public static final int LISTFILE_BY = 3;
    public static final int LISTFILE_FROM = 3;
    public static final int LISTFILE_TO = 4;

    public static final String LIST_SPACE = " ";
    public static final String LIST_DOT = ".";

    public static final char CHAR_TODO = 'T';
    public static final char CHAR_DEADLINE = 'D';
    public static final char CHAR_EVENT = 'E';
    public static final String STRING_SPACE = "T";
    public static final String STRING_DEADLINE = "D";
    public static final String STRING_EVENT = "E";

    public static final String COMMAND_BYE = "bye";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_MARK = "mark";
    public static final String COMMAND_UNMARK = "unmark";
    public static final String COMMAND_DELETE = "delete";
    public static final String COMMAND_TODO = "todo";
    public static final String COMMAND_DEADLINE = "deadline";
    public static final String COMMAND_EVENT = "event";

    public static final String IDENTIFIER_BY = "/by";
    public static final String IDENTIFIER_FROM = "/from";
    public static final String IDENTIFIER_TO = "/to";

    public static final int LENGTH_BY = IDENTIFIER_BY.length();
    public static final int LENGTH_FROM = IDENTIFIER_FROM.length();
    public static final int LENGTH_TO = IDENTIFIER_TO.length();

    public static final String MESSAGE_WELCOME = """
            To add new Tasks                    todo / deadline / event
            To mark Tasks as complete or not    mark / unmark
            To list all Tasks                   list
            To delete Tasks                     delete
            To give up and exit                 bye""";
    public static final String MESSAGE_EXIT = "Congrats! You have given up.";

    public static final String MESSAGE_LIST_INTRO = "Tasks in the list:";
    public static final String MESSAGE_MARK_COMPLETE = "Task marked as done:";
    public static final String MESSAGE_UNMARK_COMPLETE = "Task marked as not done:";
    public static final String MESSAGE_DELETED_TASK = "Task deleted:";
    public static final String MESSAGE_ADDED_TASK = "Task added:";
    public static final String MESSAGE_TOTALTASKS = "Number of tasks: ";

    public static final String MESSAGE_LIST_EMPTY = "The list is empty. Lazy bum.";
    public static final String MESSAGE_ERROR_OUTOFBOUNDS = "Number provided is not in the list. Lousy.";
    public static final String MESSAGE_ERROR_FILENOTFOUND = "File not found. Just give up.";
    public static final String MESSAGE_ERROR_IO = "Error creating file / directory. Just give up.";
    public static final String MESSAGE_ERROR_WRITEFAILED = "Error writing to file. Just give up.";
    public static final String MESSAGE_ERROR_READFAILED = "Error reading file. Just give up.";
    public static final String MESSAGE_ERROR_INVALID_COMMAND = "Invalid command. Skill issue.";
    public static final String MESSAGE_ERROR_INVALID_COMMAND_MARK = "mark / unmark <integer>";
    public static final String MESSAGE_ERROR_INVALID_COMMAND_DELETE = "delete <integer>";
    public static final String MESSAGE_ERROR_INVALID_COMMAND_TODO = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "todo <description>";
    public static final String MESSAGE_ERROR_INVALID_COMMAND_DEADLINE = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "deadline <description> /by <deadline>";
    public static final String MESSAGE_ERROR_INVALID_COMMAND_EVENT = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" +  "event <description> /from <start> /to <end>";

    public static final String LISTFILE_PATHNAME = "./data/amadinho.txt";
    public static final String LISTFILE_DIVIDER = " | ";
    public static final String LISTFILE_NEWLINE = "\n";
    public static final String SPLIT_PARAMETER = "\\|";
    public static final String BORDER_LINE = "____________________________________________________________";
}
