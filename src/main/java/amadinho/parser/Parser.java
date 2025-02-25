package amadinho.parser;

import static amadinho.parser.ParserConstants.*;

import amadinho.main.Constants;
import amadinho.ui.Ui;
import amadinho.tasklist.Tasklist;
import amadinho.storage.Storage;

import amadinho.exceptions.EmptyString;
import amadinho.exceptions.InvalidCommand;
import amadinho.tasktypes.Deadline;
import amadinho.tasktypes.Event;
import amadinho.tasktypes.Task;
import amadinho.tasktypes.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Parser {



    /*
     * Main Command Execution Method
     */

    public static void executeCommand(ArrayList<Task> taskList, String userCommand, String information) {
        try {
            switch (userCommand) {
            case COMMAND_LIST:
                commandList(taskList);
                break;
            case COMMAND_FIND:
                commandFind(taskList, information);
                break;
            case COMMAND_MARK:
                commandMark(taskList, information);
                break;
            case COMMAND_UNMARK:
                commandUnmark(taskList, information);
                break;
            case COMMAND_DELETE:
                commandDelete(taskList, information);
                break;
            case COMMAND_TODO:
                commandTodo(taskList, information);
                break;
            case COMMAND_DEADLINE:
                commandDeadline(taskList, information);
                break;
            case COMMAND_EVENT:
                commandEvent(taskList, information);
                break;
            default:
                errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND);
            }
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }


    /*
     * Primary Command Methods
     */

    private static void commandList(ArrayList<Task> taskList) {
        Tasklist.executeList(taskList, false);

    }

    private static void commandFind(ArrayList<Task> taskList, String information) {
        ArrayList<Task> foundTasks = new ArrayList<>();

        try {
            if (information.isEmpty()) {
                errorEmptyString();
            }

            for (Task task : taskList) {
                if (isContains(information, task)) {
                    Tasklist.insertIntoTaskList(foundTasks, task, false);
                }
            }

            Tasklist.executeList(foundTasks, true);
        } catch (EmptyString e) {
            errorPrinting(e);
        }
    }

    private static void commandMark(ArrayList<Task> taskList, String information) {
        Tasklist.executeMark(taskList, information, true);
    }

    private static void commandUnmark(ArrayList<Task> taskList, String information) {
        Tasklist.executeMark(taskList, information, false);
    }

    private static void commandDelete(ArrayList<Task> taskList, String information) {
        int taskCount;

        try {
            taskCount = Integer.parseInt(information);
        } catch (NumberFormatException e) {
            printNumberFormatExceptionMessage(COMMAND_DELETE);
            return;
        }

        try {
            Task taskToDelete = taskList.get(taskCount - Constants.ARRAY_INCREMENT);
            taskList.remove(taskCount - Constants.ARRAY_INCREMENT);

            Storage.writeToTextFile(taskList);
            Ui.deleteCommandMessage(taskList, taskToDelete);
        } catch (IndexOutOfBoundsException e) {
            printIndexOutOfBoundsExceptionMessage();
        }

    }

    private static void commandTodo(ArrayList<Task> taskList, String information) {
        try {
            if (isEmpty(information)) {
                errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND_TODO);
            }

            Todo newTodo = new Todo(information);
            Tasklist.insertIntoTaskList(taskList, newTodo, true);
            Storage.writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    private static void commandDeadline(ArrayList<Task> taskList, String information) {
        try {
            if (isMissing(information, IDENTIFIER_BY)) {
                errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND_DEADLINE);
            }

            int descriptionPosition = findIndex(information, IDENTIFIER_BY);

            String description = generateSubstring(information, START_OF_STRING, descriptionPosition);
            String by = generateSubstring(information, descriptionPosition + LENGTH_BY);

            Deadline newDeadline = extractDateDeadline(by, description);

            // check for incorrect input format (since newDeadline = null when format is wrong)
            if (newDeadline == null) {
                return;
            }

            Tasklist.insertIntoTaskList(taskList, newDeadline, true);
            Storage.writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    private static void commandEvent(ArrayList<Task> taskList, String information) {
        try {
            if (isMissing(information, IDENTIFIER_FROM) || isMissing(information, IDENTIFIER_TO)) {
                errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND_EVENT);
            }

            int descriptionPosition = findIndex(information, IDENTIFIER_FROM);
            int toPosition = findIndex(information, IDENTIFIER_TO);

            String description = generateSubstring(information, START_OF_STRING, descriptionPosition);
            String from = generateSubstring(information, descriptionPosition + LENGTH_FROM, toPosition);
            String to = generateSubstring(information, toPosition + LENGTH_TO);

            Event newEvent = extractDateEvent(from, to, description);

            // check for incorrect input format (since newEvent = null when format is wrong)
            if (newEvent == null) {
                return;
            }

            Tasklist.insertIntoTaskList(taskList, newEvent, true);
            Storage.writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }


    /*
     * Secondary Command Methods
     */

    private static boolean isContains(String information, Task task) {
        return task.getDescription().contains(information);
    }

    private static boolean isEmpty(String information) {
        return information.isEmpty();
    }

    private static boolean isMissing(String information, String identifier) {
        return !(information.contains(identifier));
    }

    private static int findIndex(String information, String identifier) {
        return information.indexOf(identifier);
    }

    private static String generateSubstring(String information, int start, int end) {
        return information.substring(start, end).trim();
    }

    private static String generateSubstring(String information, int start) {
        return information.substring(start).trim();
    }


    /*
     * Date and Time Extraction Methods
     */

    private static Deadline extractDateDeadline(String by, String description) {
        Deadline newDeadline;

        DateTimeFormatter dateTimeInputFormat = getDateTimeInputFormat();
        DateTimeFormatter dateTimeOutputFormat = getDateTimeOutputFormat();

        try {
            LocalDateTime dateTime = parseDateTime(by, dateTimeInputFormat);

            String formattedDateTime = getFormattedDateTime(dateTime, dateTimeOutputFormat);

            newDeadline = new Deadline(description, formattedDateTime);
        } catch (DateTimeParseException e) {
            printDateTimeParseExceptionMessage(MESSAGE_ERROR_INVALID_COMMAND_DEADLINE);
            return null;
        }

        return newDeadline;
    }

    private static Event extractDateEvent(String from, String to, String description) {
        Event newEvent;

        DateTimeFormatter dateTimeInputFormat = getDateTimeInputFormat();
        DateTimeFormatter dateTimeOutputFormat = getDateTimeOutputFormat();

        try {
            LocalDateTime dateTimeFrom = parseDateTime(from, dateTimeInputFormat);
            LocalDateTime dateTimeTo = parseDateTime(to, dateTimeInputFormat);

            String formattedDateTimeFrom = getFormattedDateTime(dateTimeFrom, dateTimeOutputFormat);
            String formattedDateTimeTo = getFormattedDateTime(dateTimeTo, dateTimeOutputFormat);

            newEvent = new Event(description, formattedDateTimeFrom, formattedDateTimeTo);
        } catch (DateTimeParseException e) {
            printDateTimeParseExceptionMessage(MESSAGE_ERROR_INVALID_COMMAND_EVENT);
            return null;
        }

        return newEvent;
    }

    private static DateTimeFormatter getDateTimeInputFormat() {
        return DateTimeFormatter.ofPattern(DATETIME_INPUT_FORMAT);
    }

    private static DateTimeFormatter getDateTimeOutputFormat() {
        return DateTimeFormatter.ofPattern(DATETIME_OUTPUT_FORMAT);
    }

    private static LocalDateTime parseDateTime(String by, DateTimeFormatter dateTimeInputFormat) {
        return LocalDateTime.parse(by, dateTimeInputFormat);
    }

    private static String getFormattedDateTime(LocalDateTime dateTime, DateTimeFormatter dateTimeOutputFormat) {
        return dateTime.format(dateTimeOutputFormat);
    }


    /*
     * Exception Handling Methods
     */

    private static void errorEmptyString() throws EmptyString {
        throw new EmptyString(MESSAGE_ERROR_INVALID_COMMAND_FIND);
    }

    private static void errorInvalidCommand(String message) throws InvalidCommand {
        throw new InvalidCommand(message);
    }

    public static void printIndexOutOfBoundsExceptionMessage() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_ERROR_OUTOFBOUNDS);
        System.out.println(Constants.BORDER_LINE);
    }

    public static void printNumberFormatExceptionMessage(String message) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_ERROR_INVALID_COMMAND);

        if (message.equals(COMMAND_MARK)) {
            System.out.println(MESSAGE_ERROR_INVALID_COMMAND_MARK);
        } else {
            System.out.println(MESSAGE_ERROR_INVALID_COMMAND_DELETE);
        }

        System.out.println(Constants.BORDER_LINE);
    }

    private static void printDateTimeParseExceptionMessage(String message) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(message);
        System.out.println(Constants.BORDER_LINE);
    }

    public static void errorPrinting(Exception e) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(e.getMessage());
        System.out.println(Constants.BORDER_LINE);
    }
}