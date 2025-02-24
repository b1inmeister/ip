package amadinho.tasklist;

import amadinho.main.Constants;
import amadinho.ui.Ui;
import amadinho.parser.Parser;
import amadinho.storage.Storage;
import amadinho.tasktypes.Task;

import java.util.ArrayList;

public class Tasklist {

    /*
     * taskList Manipulation Methods
     */

    public static void printList(ArrayList<Task> taskList) {
        int taskCounter = Constants.LIST_COUNTER_START;

        for (Task task : taskList) {
            System.out.println(taskCounter + Constants.LIST_DOT + task);
            taskCounter++;
        }
    }

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

    public static void insertIntoTaskList(ArrayList<Task> taskList, Task newTask, boolean isStart) {
        taskList.add(newTask);

        if (isStart) {
            Ui.addCommandMessage(taskList, newTask);
        }
    }
}