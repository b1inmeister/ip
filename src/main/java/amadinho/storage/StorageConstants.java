package amadinho.storage;

public class StorageConstants {
    public static final String LISTFILE_PATHNAME = "./data/amadinho.txt";

    public static final String LISTFILE_DIVIDER = " | ";
    public static final String LISTFILE_NEWLINE = "\n";
    public static final String SPLIT_PARAMETER = "\\|";

    public static final int LISTFILE_STATUSICON = 1;
    public static final int LISTFILE_DESCRIPTION = 2;
    public static final int LISTFILE_BY = 3;
    public static final int LISTFILE_FROM = 3;
    public static final int LISTFILE_TO = 4;

    public static final char CHAR_TODO = 'T';
    public static final char CHAR_DEADLINE = 'D';
    public static final char CHAR_EVENT = 'E';
    public static final String STRING_SPACE = "T";
    public static final String STRING_DEADLINE = "D";
    public static final String STRING_EVENT = "E";

    public static final String MESSAGE_ERROR_FILENOTFOUND = "File not found. Just give up.";
    public static final String MESSAGE_ERROR_IO = "Error creating file / directory. Just give up.";
    public static final String MESSAGE_ERROR_WRITEFAILED = "Error writing to file. Just give up.";
    public static final String MESSAGE_ERROR_READFAILED = "Error reading file. Just give up.";
}