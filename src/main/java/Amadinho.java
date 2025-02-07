import java.util.Scanner;

public class Amadinho {
    public static final int LIST_MAX_VALUE = 100;
    public static final int LIST_COUNTER_START = 1;
    public static final int ARRAY_INCREMENT = 1;
    public static final int COUNTER_START = 0;

    public static final String COMMAND_BYE = "bye";
    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_MARK = "mark";
    public static final String COMMAND_UNMARK = "unmark";
    public static final String COMMAND_TODO = "todo";
    public static final String COMMAND_DEADLINE = "deadline";
    public static final String COMMAND_EVENT = "event";

    public static final String MESSAGE_WELCOME_LINE1 = "Hello! I'm Amadinho!";
    public static final String MESSAGE_WELCOME_LINE2 = "What can I do for you?";
    public static final String MESSAGE_EXIT = "Bye. Hope to see you again soon!";

    public static final String MESSAGE_LIST_INTRO = "Here are the tasks in your list:";
    public static final String MESSAGE_MARK_COMPLETE = "Nice! I've marked this task as done:";
    public static final String MESSAGE_UNMARK_COMPLETE = "OK, I've marked this task as not done yet:";
    public static final String MESSAGE_ADDED_TASK = "Got it. I've added this task:";

    public static final String MESSAGE_ERROR_INVALID_COMMAND = "Invalid command. Please try again.";
    public static final String MESSAGE_ERROR_OUTOFBOUNDS = "Number provided is not in the list.";

    public static final String BORDER_LINE = "____________________________________________________________";


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Task[] taskList = new Task[LIST_MAX_VALUE];

        welcomeMessage();

        while (true) {
            String userCommand = readCommand(in);
            String information = readInfo(in);

            if (isCompleted(userCommand)) break;
            executeCommand(taskList, userCommand, information);
        }

        exitMessage();
    }

    /**
     * COMMAND-RELATED METHODS
     */

    public static String readCommand(Scanner in) {
        return in.next();
    }

    public static String readInfo(Scanner in) {
        return in.nextLine().trim();
    }

    public static boolean isCompleted(String userCommand) {
        return userCommand.equals(COMMAND_BYE);
    }

    public static void executeCommand(Task[] taskList, String userCommand, String information) {
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
            errorInvalidCommand();
            break;
        }
    }

    public static void commandList(Task[] taskList) {
        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_LIST_INTRO);
        printList(taskList);
        System.out.println(BORDER_LINE);
    }

    public static void printList(Task[] taskList) {
        int taskCounter = LIST_COUNTER_START;

        for (int arrayCounter = COUNTER_START; arrayCounter < taskList.length; arrayCounter++) {
            if (taskList[arrayCounter] == null) {
                break;
            } else {
                System.out.println(taskCounter + "." + taskList[arrayCounter]);
                taskCounter++;
            }
        }
    }

    public static void commandMark(Task[] taskList, String information) {
        executeMark(taskList, information, true);
    }

    public static void commandUnmark(Task[] taskList, String information) {
        executeMark(taskList, information, false);
    }

    public static void executeMark(Task[] taskList, String information, boolean toMark) {
        int taskCount = Integer.parseInt(information);

        // counter for do while loop
        int arrayCounter = COUNTER_START;

        do {
            if (taskList[arrayCounter] == null) {
                errorOutOfBounds();
            }

            if (arrayCounter == taskCount - ARRAY_INCREMENT) {
                if (toMark) {
                    taskList[arrayCounter].markAsDone();
                } else {
                    taskList[arrayCounter].markAsUndone();
                }

                break;
            }

            arrayCounter++;
        } while (arrayCounter <= taskCount);

        markCommandMessage(taskCount, taskList[arrayCounter], toMark);
    }

    public static void commandTodo(Task[] taskList, String information) {
        Todo newTodo = new Todo(information);

        insertIntoTaskList(taskList, newTodo);
    }
    
    // incomplete
    public static void commandDeadline(Task[] taskList, String information) {
        Todo newTodo = new Todo(information);
        insertIntoTaskList(taskList, newTodo);
    }

    // incomplete
    public static void commandEvent(Task[] taskList, String information) {
        Todo newTodo = new Todo(information);
        insertIntoTaskList(taskList, newTodo);
    }

    public static void insertIntoTaskList(Task[] taskList, Todo newTodo) {
        for (int arrayCounter = COUNTER_START; arrayCounter < taskList.length; arrayCounter++) {
            if (taskList[arrayCounter] == null) {
                taskList[arrayCounter] = newTodo;
                addCommandMessage(newTodo, arrayCounter);
                break;
            }
        }
    }


    /**
     * MESSAGE-RELATED METHODS
     */

    public static void welcomeMessage() {
        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_WELCOME_LINE1);
        System.out.println(MESSAGE_WELCOME_LINE2);
        System.out.println(BORDER_LINE);
    }

    public static void exitMessage() {
        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_EXIT);
        System.out.println(BORDER_LINE);
    }

    private static void markCommandMessage(int taskCount, Task taskList, boolean toMark) {
        System.out.println(BORDER_LINE);

        if (toMark) {
            System.out.println(MESSAGE_MARK_COMPLETE);
        } else {
            System.out.println(MESSAGE_UNMARK_COMPLETE);
        }

        System.out.println(taskCount + "." + taskList);
        System.out.println(BORDER_LINE);
    }

    public static void addCommandMessage(Task newTask, int arrayCounter) {
        int totalTasks = arrayCounter + ARRAY_INCREMENT;

        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_ADDED_TASK);
        System.out.println("  " + newTask);
        System.out.println(printTotalTasks(totalTasks));
        System.out.println(BORDER_LINE);
    }

    public static String printTotalTasks(int totalTasks) {
        return "Now you have " + totalTasks + " tasks in the list.";
    }


    /**
     * ERROR-RELATED METHODS
     */

    public static void errorOutOfBounds() {
        throw new IndexOutOfBoundsException(MESSAGE_ERROR_OUTOFBOUNDS);
    }

    public static void errorInvalidCommand() {
        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_ERROR_INVALID_COMMAND);
        System.out.println(BORDER_LINE);
    }


}
