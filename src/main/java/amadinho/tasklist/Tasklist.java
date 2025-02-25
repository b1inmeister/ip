package amadinho.tasklist;

import amadinho.exceptions.EmptyList;
import amadinho.main.Constants;
import amadinho.parser.ParserConstants;
import amadinho.ui.Ui;
import amadinho.parser.Parser;
import amadinho.storage.Storage;
import amadinho.tasktypes.Task;

import java.util.ArrayList;

public class Tasklist {

    public static final String MESSAGE_FIND_INTRO = "Tasks found:";
    public static final String MESSAGE_FIND_FAILED = "No tasks found. Skill issue.";

    /*
     * Primary taskList Manipulation Methods
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

    public static void printList(ArrayList<Task> list) {
        int taskCounter = Constants.LIST_COUNTER_START;

        for (Task task : list) {
            System.out.println(taskCounter + Constants.LIST_DOT + task);
            taskCounter++;
        }
    }

    public static void executeMark(ArrayList<Task> taskList, String information, boolean toMark) {
        int taskCount;

        try {
            taskCount = Integer.parseInt(information);
        } catch (NumberFormatException e) {
            Parser.printNumberFormatExceptionMessage(ParserConstants.COMMAND_MARK);
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
            Parser.printIndexOutOfBoundsExceptionMessage();
        }

    }

    public static void insertIntoTaskList(ArrayList<Task> taskList, Task newTask, boolean isStart) {
        taskList.add(newTask);

        if (isStart) {
            Ui.addCommandMessage(taskList, newTask);
        }
    }


    /*
     * Secondary Methods
     */

    private static void isErrorListOrFind(boolean isFind) throws EmptyList {
        if (isFind) {
            errorEmptyList(MESSAGE_FIND_FAILED);
        } else {
            errorEmptyList(Constants.MESSAGE_LIST_EMPTY);
        }
    }

    private static void isIntroListOrFind(boolean isFind) {
        if (isFind) {
            System.out.println(MESSAGE_FIND_INTRO);
        } else {
            System.out.println(Constants.MESSAGE_LIST_INTRO);
        }
    }


    /*
     * Exception Handling Method for EmptyList
     */

    public static void errorEmptyList(String message) throws EmptyList {
        throw new EmptyList(message);
    }
}