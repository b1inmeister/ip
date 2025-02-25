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
     * Input Reading Methods
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
     * @param isStart Boolean value to determine the purpose of using this method.
     *                isStart == true --> Method is used to transfer data from a provided text file to
     *                                   the program. In this case, the command input comes from
     *                                   the provided text file.
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
     * Checks if the "bye" command is inputted, which means
     * that the user wants to terminate the program.
     *
     * @param userCommand Command inputted by the user.
     * @return Boolean value that indicates if the "bye" command is inputted.
     */
    public static boolean isCompleted(String userCommand) {
        return userCommand.equals(COMMAND_BYE);
    }


    /*
     * Message Printing Methods
     */

    /**
     * Message to display when the program is started.
     */
    public static void welcomeMessage() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_WELCOME);
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Message to display when the program is terminated.
     */
    public static void exitMessage() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_EXIT);
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Message to display when marking or unmarking a Task is successful.
     *
     * @param taskCount Position of the Task that was marked or unmarked within the list.
     * @param taskToMark Data of the Task that was marked or unmarked.
     * @param toMark Boolean value to determine if marking or unmarking has occurred.
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
     * Message to display when deleting a Task from the list is successful.
     *
     * @param taskList List of Tasks.
     * @param taskToDelete Data of the Task that was deleted from the list.
     */
    public static void deleteCommandMessage(ArrayList<Task> taskList, Task taskToDelete) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_DELETED_TASK);
        System.out.println(Constants.LIST_SPACE + taskToDelete);
        System.out.println(printTotalTasks(taskList));
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Message to display when adding a Task to the list is successful.
     *
     * @param taskList List of Tasks.
     * @param newTask Data of the Task that was added to the list.
     */
    public static void addCommandMessage(ArrayList<Task> taskList, Task newTask) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_ADDED_TASK);
        System.out.println(Constants.LIST_SPACE + newTask);
        System.out.println(printTotalTasks(taskList));
        System.out.println(Constants.BORDER_LINE);
    }

    /**
     * Returns a String of the total number of Tasks in the list.
     *
     * @param taskList List of Tasks.
     * @return Message indicating the number of Tasks in the list.
     */
    public static String printTotalTasks(ArrayList<Task> taskList) {
        int totalTasks = taskList.size();

        return MESSAGE_TOTALTASKS + totalTasks;
    }
}