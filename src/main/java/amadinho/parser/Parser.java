package amadinho.parser;

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
            case Constants.COMMAND_LIST:
                commandList(taskList);
                break;
            case Constants.COMMAND_MARK:
                commandMark(taskList, information);
                break;
            case Constants.COMMAND_UNMARK:
                commandUnmark(taskList, information);
                break;
            case Constants.COMMAND_DELETE:
                commandDelete(taskList, information);
                break;
            case Constants.COMMAND_TODO:
                commandTodo(taskList, information);
                break;
            case Constants.COMMAND_DEADLINE:
                commandDeadline(taskList, information);
                break;
            case Constants.COMMAND_EVENT:
                commandEvent(taskList, information);
                break;
            default:
                errorInvalidCommand(Constants.MESSAGE_ERROR_INVALID_COMMAND);
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
                errorEmptyList(Constants.MESSAGE_LIST_EMPTY);
            } else {
                System.out.println(Constants.BORDER_LINE);
                System.out.println(Constants.MESSAGE_LIST_INTRO);
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
            printNumberFormatExceptionMessage(Constants.COMMAND_DELETE);
            return;
        }

        try {
            Task taskToDelete = taskList.get(taskCount - Constants.ARRAY_INCREMENT);
            taskList.remove(taskCount - Constants.ARRAY_INCREMENT);

            Storage.writeToTextFile(taskList);
            Ui.deleteCommandMessage(taskList, taskToDelete);
        } catch (IndexOutOfBoundsException e) {
            printIndexOutOfBoundsException();
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
                errorInvalidCommand(Constants.MESSAGE_ERROR_INVALID_COMMAND_TODO);
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
            if (isMissing(information, Constants.IDENTIFIER_BY)) {
                errorInvalidCommand(Constants.MESSAGE_ERROR_INVALID_COMMAND_DEADLINE);
            }

            int descriptionPosition = findIndex(information, Constants.IDENTIFIER_BY);

            String description = generateSubstring(information, Constants.START_OF_STRING, descriptionPosition);
            String by = generateSubstring(information, descriptionPosition + Constants.LENGTH_BY);

            Deadline newDeadline = new Deadline(description, by);
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
            if (isMissing(information, Constants.IDENTIFIER_FROM) || isMissing(information, Constants.IDENTIFIER_TO)) {
                errorInvalidCommand(Constants.MESSAGE_ERROR_INVALID_COMMAND_EVENT);
            }

            int descriptionPosition = findIndex(information, Constants.IDENTIFIER_FROM);
            int toPosition = findIndex(information, Constants.IDENTIFIER_TO);

            String description = generateSubstring(information, Constants.START_OF_STRING, descriptionPosition);
            String from = generateSubstring(information, descriptionPosition + Constants.LENGTH_FROM, toPosition);
            String to = generateSubstring(information, toPosition + Constants.LENGTH_TO);

            Event newEvent = new Event(description, from, to);
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
    public static void printIndexOutOfBoundsException() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(Constants.MESSAGE_ERROR_OUTOFBOUNDS);
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Prints an error message when parsing into a number cannot be performed.
     *
     * @param message Command input of the method where the NumberFormatException was thrown.
     */
    public static void printNumberFormatExceptionMessage(String message) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(Constants.MESSAGE_ERROR_INVALID_COMMAND);

        if (message.equals(Constants.COMMAND_MARK)) {
            System.out.println(Constants.MESSAGE_ERROR_INVALID_COMMAND_MARK);
        } else {
            System.out.println(Constants.MESSAGE_ERROR_INVALID_COMMAND_DELETE);
        }

        System.out.println(Constants.BORDER_LINE);
    }
}