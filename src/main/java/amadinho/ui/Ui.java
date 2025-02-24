package amadinho.ui;

import static amadinho.ui.UiConstants.*;

import amadinho.main.Constants;
import amadinho.tasktypes.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    /*
     * Input Reading Methods
     */

    public static String readCommand(Scanner in) {
        return in.next();
    }

    public static String readInfo(Scanner in, boolean isStart) {

        if (isStart) {
            return in.nextLine();
        } else {
            return in.nextLine().trim();
        }

    }

    public static boolean isCompleted(String userCommand) {
        return userCommand.equals(COMMAND_BYE);
    }


    /*
     * Message Printing Methods
     */

    public static void welcomeMessage() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_WELCOME);
        System.out.println(Constants.BORDER_LINE);
    }

    public static void exitMessage() {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_EXIT);
        System.out.println(Constants.BORDER_LINE);
    }

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

    public static void deleteCommandMessage(ArrayList<Task> taskList, Task taskToDelete) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_DELETED_TASK);
        System.out.println(Constants.LIST_SPACE + taskToDelete);
        System.out.println(printTotalTasks(taskList));
        System.out.println(Constants.BORDER_LINE);
    }

    public static void addCommandMessage(ArrayList<Task> taskList, Task newTask) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(MESSAGE_ADDED_TASK);
        System.out.println(Constants.LIST_SPACE + newTask);
        System.out.println(printTotalTasks(taskList));
        System.out.println(Constants.BORDER_LINE);
    }

    public static String printTotalTasks(ArrayList<Task> taskList) {
        int totalTasks = taskList.size();

        return MESSAGE_TOTALTASKS + totalTasks;
    }
}