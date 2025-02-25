package amadinho.tasklist;

import static amadinho.tasklist.TasklistConstants.*;

import amadinho.main.Constants;
import amadinho.parser.ParserConstants;
import amadinho.ui.Ui;
import amadinho.parser.Parser;
import amadinho.storage.Storage;
import amadinho.tasktypes.Task;
import amadinho.exceptions.EmptyList;

import java.util.ArrayList;

/**
 * Contains methods that manipulate the list of Tasks.
 */
public class Tasklist {

    /*
     * PRIMARY METHODS
     */

    /**
     * Checks if a list is empty, and formats the output for printing.
     *
     * @param list List of Tasks to print out.
     * @param isFind Boolean value to indicate what this method is used during.
     *               isFind == true --> Method is used during execution of "find" command.
     *               isFind == false --> Method is used during execution of "list" command.
     */
    public static void executeList(ArrayList<Task> list, boolean isFind) {
        try {
            if (list.isEmpty()) {
                isErrorListOrFind(isFind);
            } else {
                System.out.println(Constants.BORDER_LINE);
                isIntroListOrFind(isFind);
                Tasklist.printList(list);
                System.out.println(Constants.BORDER_LINE);
            }
        } catch (EmptyList e) {
            Parser.errorPrinting(e);
        }

    }

    /**
     * Prints each Task in the list of Tasks.
     *
     * @param list List of Tasks to print out.
     */
    public static void printList(ArrayList<Task> list) {
        int taskCounter = Constants.LIST_COUNTER_START;

        for (Task task : list) {
            System.out.println(taskCounter + Constants.LIST_DOT + task);
            taskCounter++;
        }
    }

    /**
     * Marks or unmarks a Task in the list of Tasks.
     * If the information String does not contain a number, NumberFormatException is thrown.
     * If number provided exceeds the number of Tasks in the list, IndexOutOfBoundsException is thrown.
     *
     * @param taskList List of Tasks containing the Task to be marked or unmarked.
     * @param information Index of Task to be marked or unmarked (within the list).
     * @param toMark Boolean value to determine whether to mark or unmark the Task.
     *               toMark == true --> Task is to be marked.
     *               toMark == false --> Task is to be unmarked.
     */
    public static void executeMark(ArrayList<Task> taskList, String information, boolean toMark) {
        int taskCount;

        try {
            taskCount = Integer.parseInt(information);
        } catch (NumberFormatException e) {
            printNumberFormatExceptionMessage(MESSAGE_ERROR_INVALID_COMMAND_MARK);
            return;
        }

        try {
            Task taskToMark = taskList.get(taskCount - Constants.ARRAY_INCREMENT);

            if (toMark) {
                taskToMark.markAsDone();
            } else {
                taskToMark.markAsUndone();
            }

            Storage.writeToTextFile(taskList);
            Ui.markCommandMessage(taskCount, taskToMark, toMark);
        } catch (IndexOutOfBoundsException e) {
            printIndexOutOfBoundsExceptionMessage();
        }

    }

    /**
     * Removes a Task from the list of Tasks.
     * If the information String does not contain a number, NumberFormatException is thrown.
     * If number provided exceeds the number of Tasks in the list, IndexOutOfBoundsException is thrown.
     *
     * @param taskList List of Tasks containing the Task to be deleted.
     * @param information Index of Task to be deleted (within the list).
     */
    public static void executeDelete(ArrayList<Task> taskList, String information) {
        int taskCount;

        try {
            taskCount = Integer.parseInt(information);
        } catch (NumberFormatException e) {
            printNumberFormatExceptionMessage(MESSAGE_ERROR_INVALID_COMMAND_DELETE);
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
     * Inserts a Task to the end of the list of Tasks.
     *
     * @param taskList List of Tasks that the Task will be inserted into.
     * @param newTask Task to be added to the list of Tasks.
     * @param isStart Boolean value to determine the purpose of using this method.
     *                isStart == true --> Method is used to transfer data from an existing list of Tasks
     *                                    in a text file to a new list of Tasks in the program.
     *                isStart == false --> Method is used to insert a Task that is provided from user input.
     */
    public static void insertIntoTaskList(ArrayList<Task> taskList, Task newTask, boolean isStart) {
        taskList.add(newTask);

        if (!isStart) {
            Ui.addCommandMessage(taskList, newTask);
        }
    }


    /*
     * SECONDARY METHODS
     */

    /**
     * Throws EmptyList with an error message that is dependent on the command input.
     *
     * @param isFind Boolean value to indicate the command input.
     *               isFind == true --> "find" command was the input.
     *               isFind == false --> "list" command was the input.
     * @throws EmptyList If the list provided is empty.
     */
    private static void isErrorListOrFind(boolean isFind) throws EmptyList {
        if (isFind) {
            errorEmptyList(MESSAGE_FIND_FAILED);
        } else {
            errorEmptyList(MESSAGE_LIST_EMPTY);
        }
    }

    /**
     * Prints the introduction message for list printing, depending on the command input.
     *
     * @param isFind Boolean value to indicate the command input.
     *               isFind == true --> "find" command was the input.
     *               isFind == false --> "list" command was the input.
     */
    private static void isIntroListOrFind(boolean isFind) {
        if (isFind) {
            System.out.println(MESSAGE_FIND_INTRO);
        } else {
            System.out.println(MESSAGE_LIST_INTRO);
        }
    }


    /*
     * EXCEPTION HANDLING METHODS
     */

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
     * Prints an error message when parsing a String into a number cannot be performed.
     *
     * @param message Command input of the method where the NumberFormatException was thrown.
     */
    public static void printNumberFormatExceptionMessage(String message) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(ParserConstants.MESSAGE_ERROR_INVALID_COMMAND);
        System.out.println(message);
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Prints an error message when an index exceeds the size of a list of Tasks.
     */
    public static void printIndexOutOfBoundsExceptionMessage() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_ERROR_OUTOFBOUNDS);
        System.out.println(Constants.BORDER_LINE);
    }


}