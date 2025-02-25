package amadinho.parser;

/**
 * Contains constants that are used mainly within the Parser class.
 * MESSAGE_ERROR_INVALID_COMMAND is also used in the Tasklist class.
 */
public class ParserConstants {
    protected static final int START_OF_STRING = 0;

    protected static final String DATETIME_INPUT_FORMAT = "yyyy-MM-dd HHmm";
    protected static final String DATETIME_OUTPUT_FORMAT = "dd MMM yyyy HHmm";

    protected static final String COMMAND_LIST = "list";
    protected static final String COMMAND_FIND = "find";
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

    public static final String MESSAGE_ERROR_INVALID_COMMAND = "Invalid command. Skill issue.";
    protected static final String MESSAGE_ERROR_INVALID_COMMAND_FIND = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "find <description>";
    protected static final String MESSAGE_ERROR_INVALID_COMMAND_TODO = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "todo <description>";
    protected static final String MESSAGE_ERROR_INVALID_COMMAND_DEADLINE = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "deadline <description> /by <yyyy-MM-dd HHmm>";
    protected static final String MESSAGE_ERROR_INVALID_COMMAND_EVENT = MESSAGE_ERROR_INVALID_COMMAND
            + "\n" + "event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>";
}