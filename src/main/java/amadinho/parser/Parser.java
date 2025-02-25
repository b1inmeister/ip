package amadinho.parser;

import static amadinho.parser.ParserConstants.*;

import amadinho.main.Constants;
import amadinho.ui.Ui;
import amadinho.tasklist.Tasklist;
import amadinho.storage.Storage;

import amadinho.exceptions.EmptyList;
import amadinho.exceptions.InvalidCommand;
import amadinho.tasktypes.Deadline;
import amadinho.tasktypes.Event;
import amadinho.tasktypes.Task;
import amadinho.tasktypes.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Contains methods that handles the command input from the user.
 */
public class Parser {

    /*
     * Main Command Execution Method
     */

    /**
     * Deciphers which command input to execute, based on the user input.
     * If the user input does not match any of the existing commands, InvalidCommand is thrown.
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

    /**
     * Executes the "list" command that prints the list of Tasks.
     *
     * @param taskList List of Tasks to be printed.
     */
    private static void commandList(ArrayList<Task> taskList) {
        try {
            if (taskList.isEmpty()) {
                errorEmptyList(MESSAGE_LIST_EMPTY);
            } else {
                System.out.println(Constants.BORDER_LINE);
                System.out.println(MESSAGE_LIST_INTRO);
                Tasklist.printList(taskList);
                System.out.println(Constants.BORDER_LINE);
            }
        } catch (EmptyList e) {
            errorPrinting(e);
        }
    }

    /**
     * Executes the "mark" command that marks a Task in the list of Tasks as completed.
     *
     * @param taskList List of Tasks.
     * @param information Index of Task to be marked within the list of Tasks.
     */
    private static void commandMark(ArrayList<Task> taskList, String information) {
        Tasklist.executeMark(taskList, information, true);
    }

    /**
     *  Executes the "unmark" command that marks a Task in the list of Tasks as incomplete.
     *
     * @param taskList List of Tasks.
     * @param information Index of Task to be unmarked within the list of Tasks.
     */
    private static void commandUnmark(ArrayList<Task> taskList, String information) {
        Tasklist.executeMark(taskList, information, false);
    }

    /**
     * Executes the "delete" command that removes a Task from the list of Tasks.
     *
     * @param taskList List of Tasks.
     * @param information Index of Task to be removed within the list of Tasks.
     */
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

    /**
     * Executes the "todo" command that adds a Todo to the end of the list of Tasks.
     *
     * @param taskList List of Tasks that the Todo will be added to .
     * @param information Information of the Todo to be inserted, which is the description.
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
     * Executes the "deadline" command that adds a Deadline to the end of the list of Tasks.
     *
     * @param taskList List of Tasks that the Deadline will be added to.
     * @param information Information of the Deadline to be inserted, which includes
     *                    the description and the deadline timing.
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
     * Executes the "event" command that adds an Event to the end of the list of Tasks.
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
     * Secondary Command Methods
     */

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
     * Checks if an identifier String is present within another inputted String.
     *
     * @param information String that acts as the reference.
     * @param identifier String to check within the reference String.
     * @return Boolean value that indicates if the identifier is present.
     */
    private static boolean isMissing(String information, String identifier) {
        return !(information.contains(identifier));
    }

    /**
     * Returns index of the identifier String within another inputted String.
     *
     * @param information String that acts as the reference.
     * @param identifier String to check within the reference String.
     * @return Index of the identifier String within the reference String.
     */
    private static int findIndex(String information, String identifier) {
        return information.indexOf(identifier);
    }

    /**
     * Creates a substring from an inputted String within the range provided.
     *
     * @param information String that acts as the reference.
     * @param start Index of the start of the substring within the reference String.
     * @param end Index of the end of the substring within the reference String.
     * @return Substring of the reference String within the range provided.
     */
    private static String generateSubstring(String information, int start, int end) {
        return information.substring(start, end).trim();
    }

    /**
     * Creates a substring from an inputted String within the range provided.
     * In this method, the end index will be the end of the inputted string.
     *
     * @param information String that acts as the reference.
     * @param start Index of the start of the substring within the reference String.
     * @return Substring of the reference String within the range provided.
     */
    private static String generateSubstring(String information, int start) {
        return information.substring(start).trim();
    }


    /*
     * Date and Time Extraction Methods
     */

    /**
     * Extracts the date / time from the by String, and creates a Deadline.
     *
     * @param by Unformatted deadline timing of the Deadline to be created.
     * @param description Description of the Deadline to be created.
     * @return Deadline with the formatted date / time.
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
     * Extracts the date / time from the from and to String, and creates an Event.
     *
     * @param from Unformatted start timing of the Event to be created.
     * @param to Unformatted end timing of the Event to be created.
     * @param description Description of the Event to be created.
     * @return Event with the formatted date / time.
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
     * Gets the format for the date / time input.
     *
     * @return Correct format for the date / time input.
     */
    private static DateTimeFormatter getDateTimeInputFormat() {
        return DateTimeFormatter.ofPattern(DATETIME_INPUT_FORMAT);
    }

    /**
     * Gets the format for the date / time output.
     *
     * @return Correct format for the date / time output.
     */
    private static DateTimeFormatter getDateTimeOutputFormat() {
        return DateTimeFormatter.ofPattern(DATETIME_OUTPUT_FORMAT);
    }

    /**
     * Parses the inputted String into a LocalDateTime instance.
     *
     * @param info String to be parsed.
     * @param dateTimeInputFormat Format of the LocalDateTime instance.
     * @return LocalDateTime instance containing the date and time from the String.
     */
    private static LocalDateTime parseDateTime(String info, DateTimeFormatter dateTimeInputFormat) {
        return LocalDateTime.parse(info, dateTimeInputFormat);
    }

    /**
     * Formats the LocalDateTime instance with the correct format for output.
     *
     * @param dateTime LocalDateTime instance to be re-formatted.
     * @param dateTimeOutputFormat Correct format for re-formatting.
     * @return Re-formatted LocalDateTime instance.
     */
    private static String getFormattedDateTime(LocalDateTime dateTime, DateTimeFormatter dateTimeOutputFormat) {
        return dateTime.format(dateTimeOutputFormat);
    }


    /*
     * Error Printing Methods
     */

    /**
     * Prints the corresponding error message of the exception caught.
     *
     * @param e Exception caught.
     */
    private static void errorPrinting(Exception e) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(e.getMessage());
        System.out.println(Constants.BORDER_LINE);
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
     * Throws EmptyList.
     *
     * @param message Error message to be paired with the EmptyList thrown.
     * @throws EmptyList If the list of Tasks is empty.
     */
    public static void errorEmptyList(String message) throws EmptyList {
        throw new EmptyList(message);
    }

    /**
     * Prints an error message when an index exceeds the size of the list of Tasks.
     */
    public static void printIndexOutOfBoundsExceptionMessage() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_ERROR_OUTOFBOUNDS);
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Prints an error message when parsing into a number cannot be performed.
     *
     * @param message Command input of the method where the NumberFormatException was thrown.
     */
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

    /**
     * Prints an error message when parsing into the specified date / time format
     * cannot be performed.
     *
     * @param message Command input of the method where the DateTimeParseException was thrown.
     */
    private static void printDateTimeParseExceptionMessage(String message) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(message);
        System.out.println(Constants.BORDER_LINE);
    }
}