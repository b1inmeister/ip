package amadinho.parser;

public class ParserConstants {
    public static final int START_OF_STRING = 0;

    public static final String DATETIME_INPUT_FORMAT = "yyyy-MM-dd HHmm";
    public static final String DATETIME_OUTPUT_FORMAT = "dd MMM yyyy HHmm";

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

    public static final String MESSAGE_LIST_INTRO = "Tasks in the list:";
    public static final String MESSAGE_LIST_EMPTY = "The list is empty. Lazy bum.";

    public static final String MESSAGE_ERROR_OUTOFBOUNDS = "Number provided is not in the list. Lousy.";
    public static final String MESSAGE_ERROR_INVALID_COMMAND = "Invalid command. Skill issue.";
    public static final String MESSAGE_ERROR_INVALID_COMMAND_EVENT = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>";
    public static final String MESSAGE_ERROR_INVALID_COMMAND_DEADLINE = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "deadline <description> /by <yyyy-MM-dd HHmm>";
    public static final String MESSAGE_ERROR_INVALID_COMMAND_TODO = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "todo <description>";
    public static final String MESSAGE_ERROR_INVALID_COMMAND_MARK = "mark / unmark <integer>";
    public static final String MESSAGE_ERROR_INVALID_COMMAND_DELETE = "delete <integer>";
}