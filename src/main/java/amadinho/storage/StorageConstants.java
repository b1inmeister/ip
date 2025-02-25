package amadinho.storage;

/**
 * Contains constants that are used only within the Storage class.
 */
public class StorageConstants {
    protected static final String LISTFILE_PATHNAME = "./data/amadinho.txt";
    protected static final String LISTFILE_DIVIDER = " | ";
    protected static final String LISTFILE_NEWLINE = "\n";
    protected static final String SPLIT_PARAMETER = "\\|";

    protected static final int LISTFILE_STATUSICON = 1;
    protected static final int LISTFILE_DESCRIPTION = 2;
    protected static final int LISTFILE_BY = 3;
    protected static final int LISTFILE_FROM = 3;
    protected static final int LISTFILE_TO = 4;

    protected static final char CHAR_TODO = 'T';
    protected static final char CHAR_DEADLINE = 'D';
    protected static final char CHAR_EVENT = 'E';
    protected static final String STRING_TODO = "T";
    protected static final String STRING_DEADLINE = "D";
    protected static final String STRING_EVENT = "E";

    protected static final String MESSAGE_ERROR_FILENOTFOUND = "File not found. Just give up.";
    protected static final String MESSAGE_ERROR_IO = "Error creating file / directory. Just give up.";
    protected static final String MESSAGE_ERROR_WRITEFAILED = "Error writing to file. Just give up.";
    protected static final String MESSAGE_ERROR_READFAILED = "Error reading file. Just give up.";
}