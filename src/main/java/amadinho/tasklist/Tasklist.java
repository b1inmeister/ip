package amadinho.tasklist;

import amadinho.main.Constants;
import amadinho.ui.Ui;
import amadinho.parser.Parser;
import amadinho.storage.Storage;
import amadinho.tasktypes.Task;

import java.util.ArrayList;

/**
 * Contains methods that manipulate the list of Tasks.
 */
public class Tasklist {

    /**
     * Prints all the Tasks in the list of Tasks.
     *
     * @param taskList List of Tasks.
     */
    public static void printList(ArrayList<Task> taskList) {
        int taskCounter = Constants.LIST_COUNTER_START;

        for (Task task : taskList) {
            System.out.println(taskCounter + Constants.LIST_DOT + task);
            taskCounter++;
        }
    }

    /**
     * Marks or unmarks a Task that is within the list of Tasks.
     * If the information parameter does not contain a number, NumberFormatException is thrown.
     * If number provided in the information parameter exceeds the number of Tasks
     * in the list, IndexOutOfBoundsException is thrown.
     *
     * @param taskList List of Tasks.
     * @param information Index of Task to be marked or unmarked.
     * @param toMark Boolean value to determine whether to mark or unmark the Task chosen.
     *               toMark = true --> Task is to be marked.
     *               toMark = false --> Task is to be unmarked.
     */
    public static void executeMark(ArrayList<Task> taskList, String information, boolean toMark) {
        int taskCount;

        try {
            taskCount = Integer.parseInt(information);
        } catch (NumberFormatException e) {
            Parser.printNumberFormatExceptionMessage(Constants.COMMAND_MARK);
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
            Parser.printIndexOutOfBoundsException();
        }

    }

    /**
     * Inserts a Task to the end of the list of Tasks.
     *
     * @param taskList List of Tasks.
     * @param newTask Task to be added to the list of Tasks.
     * @param isStart Boolean value to determine the purpose of using this method.
     *                isStart = true --> Method is used to transfer data from a provided text file to
     *                                   the program.
     *                isStart = false --> Method is used to insert a Task that is provided from user input.
     */
    public static void insertIntoTaskList(ArrayList<Task> taskList, Task newTask, boolean isStart) {
        taskList.add(newTask);

        if (!isStart) {
            Ui.addCommandMessage(taskList, newTask);
        }
    }
}