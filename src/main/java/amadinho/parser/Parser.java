package amadinho.parser;

import static amadinho.parser.ParserConstants.*;

import amadinho.main.Constants;
import amadinho.tasklist.Tasklist;
import amadinho.storage.Storage;
import amadinho.tasktypes.Task;
import amadinho.tasktypes.Todo;
import amadinho.tasktypes.Deadline;
import amadinho.tasktypes.Event;
import amadinho.exceptions.EmptyString;
import amadinho.exceptions.InvalidCommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Contains methods that handles the command input from the user.
 */
public class Parser {

    /*
     * MAIN COMMAND EXECUTION METHOD
     */

    /**
     * Deciphers which command input to execute, based on the user input. If the user input does not match
     * any of the existing commands, InvalidCommand is thrown.
     *
     * @param taskList List of Tasks.
     * @param userCommand Command input from the user.
     * @param information Information input from the user.
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
     * PRIMARY COMMAND METHODS
     */

    /**
     * Executes the "list" command which prints the list of Tasks.
     *
     * @param taskList List of Tasks to be printed.
     */
    private static void commandList(ArrayList<Task> taskList) {
        Tasklist.executeList(taskList, false);

    }

    /**
     * Executes the "find" command which searches for Tasks in the list that contains the search criteria.
     *
     * @param taskList List of Tasks to be searched.
     * @param information Search criteria for the searching process.
     */
    private static void commandFind(ArrayList<Task> taskList, String information) {
        ArrayList<Task> foundTasks = new ArrayList<>();

        try {
            if (information.isEmpty()) {
                errorEmptyString();
            }

            for (Task task : taskList) {
                if (isContains(information, task)) {
                    Tasklist.insertIntoTaskList(foundTasks, task, true);
                }
            }

            Tasklist.executeList(foundTasks, true);
        } catch (EmptyString e) {
            errorPrinting(e);
        }
    }

    /**
     * Executes the "mark" command which marks a Task in the list of Tasks as complete.
     *
     * @param taskList List of Tasks.
     * @param information Index of the Task to be marked (within the list).
     */
    private static void commandMark(ArrayList<Task> taskList, String information) {
        Tasklist.executeMark(taskList, information, true);
    }

    /**
     * Executes the "unmark" command which unmarks a Task that was marked as complete in the list of Tasks.
     *
     * @param taskList List of Tasks.
     * @param information Index of the Task to be unmarked (within the list).
     */
    private static void commandUnmark(ArrayList<Task> taskList, String information) {
        Tasklist.executeMark(taskList, information, false);
    }

    /**
     * Executes the "delete" command which removes a Task from the list of Tasks.
     *
     * @param taskList List of Tasks that the Task is removed from.
     * @param information Index of the Task to be removed within the list of Tasks.
     */
    private static void commandDelete(ArrayList<Task> taskList, String information) {
        Tasklist.executeDelete(taskList, information);
    }

    /**
     * Executes the "todo" command which adds a Todo to the end of the list of Tasks.
     *
     * @param taskList List of Tasks that the Todo will be added to.
     * @param information Information of the Todo to be inserted, which includes the description.
     */
    private static void commandTodo(ArrayList<Task> taskList, String information) {
        try {
            if (isEmpty(information)) {
                errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND_TODO);
            }

            Todo newTodo = new Todo(information);

            Tasklist.insertIntoTaskList(taskList, newTodo, false);
            Storage.writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    /**
     * Executes the "deadline" command which adds a Deadline to the end of the list of Tasks.
     *
     * @param taskList List of Tasks that the Deadline will be added to.
     * @param information Information of the Deadline to be inserted, which includes
     *                    the description and deadline timing.
     */
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

            Tasklist.insertIntoTaskList(taskList, newDeadline, false);
            Storage.writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    /**
     * Executes the "event" command which adds an Event to the end of the list of Tasks.
     *
     * @param taskList List of Tasks that the Event will be added to.
     * @param information Information of the Event to be inserted, which includes
     *                    the description, as well as the start and end timing.
     */
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

            Tasklist.insertIntoTaskList(taskList, newEvent, false);
            Storage.writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }


    /*
     * SECONDARY COMMAND METHODS
     */

    /**
     * Checks if a String can be found within the description of a Task.
     *
     * @param information String to check within the reference description.
     * @param task Task containing the description that acts as the reference.
     * @return Boolean value that indicates if the information String is present.
     */
    private static boolean isContains(String information, Task task) {
        return task.getDescription().contains(information);
    }

    /**
     * Checks if the inputted String is empty.
     *
     * @param information String to check.
     * @return Boolean value that indicates if the inputted String is empty.
     */
    private static boolean isEmpty(String information) {
        return information.isEmpty();
    }

    /**
     * Checks if an identifier String is absent from another inputted String.
     *
     * @param information String that acts as the reference.
     * @param identifier String to check within the reference String.
     * @return Boolean value that indicates if the identifier is absent.
     */
    private static boolean isMissing(String information, String identifier) {
        return !(information.contains(identifier));
    }

    /**
     * Returns the index of an identifier String within another inputted String.
     *
     * @param information String that acts as the reference.
     * @param identifier String to check within the reference String.
     * @return Index of the identifier String within the reference String.
     */
    private static int findIndex(String information, String identifier) {
        return information.indexOf(identifier);
    }

    /**
     * Creates a substring from an inputted String within the specified range.
     *
     * @param information String that acts as the reference.
     * @param start Index of the start of the substring (within the reference String).
     * @param end Index of the end of the substring (within the reference String).
     * @return Substring of the reference String within the specified range.
     */
    private static String generateSubstring(String information, int start, int end) {
        return information.substring(start, end).trim();
    }

    /**
     * Creates a substring from an inputted String within the specified range.
     * In this method, the end index will be the end of the inputted string.
     *
     * @param information String that acts as the reference.
     * @param start Index of the start of the substring (within the reference String).
     * @return Substring of the reference String within the specified range.
     */
    private static String generateSubstring(String information, int start) {
        return information.substring(start).trim();
    }


    /*
     * DATE AND TIME EXTRACTION METHODS
     */

    /**
     * Extracts the date and time from the by String, and creates a Deadline.
     *
     * @param by Unformatted deadline timing of the Deadline to be created.
     * @param description Description of the Deadline to be created.
     * @return Deadline with the formatted date and time.
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

    /**
     * Extracts the date and time from the from and to String, and creates an Event.
     *
     * @param from Unformatted start timing of the Event to be created.
     * @param to Unformatted end timing of the Event to be created.
     * @param description Description of the Event to be created.
     * @return Event with the formatted date and time.
     */
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

    /**
     * Gets the format for the date and time input.
     *
     * @return Format for the date and time input.
     */
    private static DateTimeFormatter getDateTimeInputFormat() {
        return DateTimeFormatter.ofPattern(DATETIME_INPUT_FORMAT);
    }

    /**
     * Gets the format for the date and time output.
     *
     * @return Format for the date and time output.
     */
    private static DateTimeFormatter getDateTimeOutputFormat() {
        return DateTimeFormatter.ofPattern(DATETIME_OUTPUT_FORMAT);
    }

    /**
     * Parses the inputted String into a LocalDateTime instance.
     *
     * @param info String to be parsed.
     * @param dateTimeInputFormat Format of the LocalDateTime instance.
     * @return LocalDateTime containing the date and time from the String.
     */
    private static LocalDateTime parseDateTime(String info, DateTimeFormatter dateTimeInputFormat) {
        return LocalDateTime.parse(info, dateTimeInputFormat);
    }

    /**
     * Converts the LocalDateTime instance into a String with the correct format for output.
     *
     * @param dateTime LocalDateTime to be converted and re-formatted.
     * @param dateTimeOutputFormat Format for re-formatting.
     * @return String containing the re-formatted date and time.
     */
    private static String getFormattedDateTime(LocalDateTime dateTime, DateTimeFormatter dateTimeOutputFormat) {
        return dateTime.format(dateTimeOutputFormat);
    }


    /*
     * EXCEPTION HANDLING METHODS
     */

    /**
     * Throws EmptyString
     *
     * @throws EmptyString If the String provided is empty.
     */
    private static void errorEmptyString() throws EmptyString {
        throw new EmptyString(MESSAGE_ERROR_INVALID_COMMAND_FIND);
    }

    /**
     * Throws InvalidCommand.
     *
     * @param message Error message to be paired with the InvalidCommand thrown.
     * @throws InvalidCommand If the command input is in the incorrect format.
     */
    private static void errorInvalidCommand(String message) throws InvalidCommand {
        throw new InvalidCommand(message);
    }

    /**
     * Prints an error message when parsing a String into a LocalDateTime with the specified date / time format
     * cannot be performed.
     *
     * @param message Command input of the method where the DateTimeParseException was thrown.
     */
    private static void printDateTimeParseExceptionMessage(String message) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(message);
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Prints the corresponding error message of an exception caught.
     *
     * @param e Exception caught.
     */
    public static void errorPrinting(Exception e) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(e.getMessage());
        System.out.println(Constants.BORDER_LINE);
    }
}