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
            String userCommand = in.next();
            String information = in.nextLine().trim();

            if (userCommand.equals(COMMAND_BYE)) {
                break;
            }

            executeCommand(taskList, userCommand, information);
        }

        exitMessage();
    }


    /*
     * COMMAND-RELATED FUNCTIONS
     */

    public static void executeCommand(Task[] taskList, String userCommand, String information) {
        switch (userCommand) {
        case COMMAND_LIST:
            commandList(taskList);
            break;
        case COMMAND_MARK:
            commandMark(taskList);
            break;
        case COMMAND_UNMARK:
            commandUnmark(taskList);
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
        int taskCounter = LIST_COUNTER_START;

        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_LIST_INTRO);

        for (int arrayCounter = COUNTER_START; arrayCounter < taskList.length; arrayCounter++) {
            if (taskList[arrayCounter] == null) {
                break;
            } else {
                System.out.println(taskCounter + "." + taskList[arrayCounter]);
                taskCounter++;
            }
        }

        System.out.println(BORDER_LINE);
    }

    // broken
    public static void commandMark(Task[] taskList) {
        Scanner in = new Scanner(System.in);
        int taskPosition = in.nextInt();

        // counter for do while loop
        int arrayCounter = COUNTER_START;

        do {
            if (taskList[arrayCounter] == null) {
                errorOutOfBounds();
            }

            if (arrayCounter == taskPosition - ARRAY_INCREMENT) {
                taskList[arrayCounter].markAsDone();
                break;
            }

            arrayCounter++;
        } while (arrayCounter <= taskPosition);

        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_MARK_COMPLETE);
        System.out.println((taskPosition) + "." + taskList[arrayCounter]);
        System.out.println(BORDER_LINE);
    }

    // broken
    public static void commandUnmark(Task[] taskList) {
        Scanner in = new Scanner(System.in);
        int taskPosition = in.nextInt();

        // counter for do while loop
        int arrayCounter = COUNTER_START;

        do {
            if (taskList[arrayCounter] == null) {
                errorOutOfBounds();
            }

            if (arrayCounter == taskPosition - ARRAY_INCREMENT) {
                taskList[arrayCounter].markAsUndone();
                break;
            }

            arrayCounter++;
        } while (arrayCounter <= taskPosition);

        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_UNMARK_COMPLETE);
        System.out.println((taskPosition) + "." + taskList[arrayCounter]);
        System.out.println(BORDER_LINE);
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
                completeMessage(newTodo, arrayCounter);
                break;
            }
        }
    }


    /*
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

    public static void completeMessage(Task newTask, int arrayCounter) {
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

    /*
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
