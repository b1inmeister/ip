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

public class Parser {

    /*
     * Main Command Execution Method
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

    private static void commandTodo(ArrayList<Task> taskList, String information) {
        try {
            if (isEmpty(information)) {
                errorInvalidCommand(Constants.MESSAGE_ERROR_INVALID_COMMAND_TODO);
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
            if (isMissing(information, Constants.IDENTIFIER_BY)) {
                errorInvalidCommand(Constants.MESSAGE_ERROR_INVALID_COMMAND_DEADLINE);
            }

            int descriptionPosition = findIndex(information, Constants.IDENTIFIER_BY);

            String description = generateSubstring(information, Constants.START_OF_STRING, descriptionPosition);
            String by = generateSubstring(information, descriptionPosition + Constants.LENGTH_BY);

            Deadline newDeadline = new Deadline(description, by);
            Tasklist.insertIntoTaskList(taskList, newDeadline, true);
            Storage.writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

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
            Tasklist.insertIntoTaskList(taskList, newEvent, true);
            Storage.writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }


    /*
     * Secondary Command Methods
     */

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
     * Error Printing Methods
     */

    private static void errorPrinting(Exception e) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(e.getMessage());
        System.out.println(Constants.BORDER_LINE);
    }

    private static void errorInvalidCommand(String message) throws InvalidCommand {
        throw new InvalidCommand(message);
    }

    public static void errorEmptyList(String message) throws EmptyList {
        throw new EmptyList(message);
    }

    public static void printIndexOutOfBoundsException() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(Constants.MESSAGE_ERROR_OUTOFBOUNDS);
        System.out.println(Constants.BORDER_LINE);
    }

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