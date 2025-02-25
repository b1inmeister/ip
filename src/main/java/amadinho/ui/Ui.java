package amadinho.ui;

import static amadinho.ui.UiConstants.*;

import amadinho.main.Constants;
import amadinho.tasktypes.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Contains methods used to interact with the user of Amadinho.
 */
public class Ui {

    /*
     * INPUT READING METHODS
     */

    /**
     * Reads the first word in the command input.
     *
     * @param in Command input from the user.
     * @return First word in the command input.
     */
    public static String readCommand(Scanner in) {
        return in.next();
    }

    /**
     * Reads the remaining words in the command input.
     *
     * @param in Command input from the user.
     * @param isStart Boolean value to indicate the purpose of using this method.
     *                isStart == true --> Method is used to transfer data from a text file to the program.
     *                                    In this case, the command input comes from the text file.
     *                isStart == false --> Method is used to read the command input from the user.
     * @return Remaining words in the command input.
     */
    public static String readInfo(Scanner in, boolean isStart) {
        if (isStart) {
            return in.nextLine();
        } else {
            return in.nextLine().trim();
        }
    }

    /**
     * Checks if the "bye" command has been inputted, which means that the user wants to terminate the program.
     *
     * @param userCommand Command inputted by the user.
     * @return Boolean value that indicates if the "bye" command has been inputted.
     */
    public static boolean isCompleted(String userCommand) {
        return userCommand.equals(COMMAND_BYE);
    }


    /*
     * MESSAGE PRINTING METHODS
     */

    /**
     * Displays a message when the program is started.
     */
    public static void welcomeMessage() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_WELCOME);
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Displays a message when the program is terminated.
     */
    public static void exitMessage() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_EXIT);
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Displays a message when marking or unmarking a Task is successful.
     *
     * @param taskCount Index of the Task that was marked or unmarked (within the list).
     * @param taskToMark Task that was marked or unmarked.
     * @param toMark Boolean value to indicate if marking or unmarking has occurred.
     *               toMark == true --> Task was marked.
     *               toMark == false --> Task was unmarked.
     */
    public static void markCommandMessage(int taskCount, Task taskToMark, boolean toMark) {
        System.out.println(Constants.BORDER_LINE);

        if (toMark) {
            System.out.println(MESSAGE_MARK_COMPLETE);
        } else {
            System.out.println(MESSAGE_UNMARK_COMPLETE);
        }

        System.out.println(taskCount + Constants.LIST_DOT + taskToMark);
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Displays a message when deleting a Task from the list is successful.
     *
     * @param taskList List of Tasks that the Task was deleted from.
     * @param taskToDelete Task that was deleted from the list.
     */
    public static void deleteCommandMessage(ArrayList<Task> taskList, Task taskToDelete) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_DELETE_COMPLETE);
        System.out.println(Constants.LIST_SPACE + taskToDelete);
        System.out.println(printTotalTasks(taskList));
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Displays a message when adding a Task to the list is successful.
     *
     * @param taskList List of Tasks that the Task was inserted into.
     * @param newTask Task that was inserted into the list.
     */
    public static void addCommandMessage(ArrayList<Task> taskList, Task newTask) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_ADD_COMPLETE);
        System.out.println(Constants.LIST_SPACE + newTask);
        System.out.println(printTotalTasks(taskList));
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Returns a String containing the total number of Tasks in the list.
     *
     * @param taskList List of Tasks to be counted.
     * @return Message indicating the number of Tasks in the list.
     */
    public static String printTotalTasks(ArrayList<Task> taskList) {
        return MESSAGE_TOTALTASKS + taskList.size();
    }
}