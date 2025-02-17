package amadinho.ui;

import static amadinho.ui.Constants.*;
import amadinho.tasktypes.*;
import amadinho.exceptions.*;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Amadinho {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Task[] taskList = new Task[LIST_MAX_VALUE];

        readTextFile(taskList);
        welcomeMessage();

        while (true) {
            String userCommand = readCommand(in);
            String information = readInfo(in);

            if (isCompleted(userCommand)) {
                break;
            }

            executeCommand(taskList, userCommand, information);
        }

        exitMessage();
    }


    /*
     * COMMAND-RELATED METHODS
     */

    private static String readCommand(Scanner in) {
        return in.next();
    }

    private static String readInfo(Scanner in) {
        return in.nextLine().trim();
    }

    private static boolean isCompleted(String userCommand) {
        return userCommand.equals(COMMAND_BYE);
    }

    private static void executeCommand(Task[] taskList, String userCommand, String information) {
        try {
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
                errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND);
            }
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    private static void commandList(Task[] taskList) {
        try {
            if (taskList[LIST_COUNTER_START] == null) {
                errorEmptyList(MESSAGE_LIST_EMPTY);
            } else {
                System.out.println(BORDER_LINE);
                System.out.println(MESSAGE_LIST_INTRO);
                printList(taskList);
                System.out.println(BORDER_LINE);
            }
        } catch (EmptyList e) {
            errorPrinting(e);
        }
    }

    private static void printList(Task[] taskList) {
        int taskCounter = LIST_COUNTER_START;

        for (Task task : taskList) {
            if (task == null) {
                break;
            } else {
                System.out.println(taskCounter + "." + task);
                taskCounter++;
            }
        }
    }

    private static void commandMark(Task[] taskList, String information) {
        executeMark(taskList, information, true);
    }

    private static void commandUnmark(Task[] taskList, String information) {
        executeMark(taskList, information, false);
    }

    private static void executeMark(Task[] taskList, String information, boolean toMark) {
        int taskCount;

        try {
            taskCount = Integer.parseInt(information);
        } catch (NumberFormatException e) {
            printNumberFormatExceptionMessage();
            return;
        }

        // counter for do while loop
        int arrayCounter = COUNTER_START;

        try {
            do {
                if (taskList[arrayCounter] == null) {
                    errorOutOfBounds(MESSAGE_ERROR_OUTOFBOUNDS);
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
        } catch (IndexOutOfBoundsException e) {
            errorPrinting(e);
        }
    }

    private static void commandTodo(Task[] taskList, String information) {
        try {
            if (isEmpty(information)) {
                errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND_TODO);
            }

            Todo newTodo = new Todo(information);
            insertIntoTaskList(taskList, newTodo, true);
            writeToTextFile(COMMAND_TODO, newTodo);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    private static void commandDeadline(Task[] taskList, String information) {
        try {
            if (isMissing(information, IDENTIFIER_BY)) {
                errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND_DEADLINE);
            }

            int descriptionPosition = findIndex(information, IDENTIFIER_BY);

            String description = generateSubstring(information, START_OF_STRING, descriptionPosition);
            String by = generateSubstring(information, descriptionPosition + LENGTH_BY);

            Deadline newDeadline = new Deadline(description, by);
            insertIntoTaskList(taskList, newDeadline, true);
            writeToTextFile(COMMAND_DEADLINE, newDeadline);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    private static void commandEvent(Task[] taskList, String information) {
        try {
            if (isMissing(information, IDENTIFIER_FROM) || isMissing(information, IDENTIFIER_TO)) {
               errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND_EVENT);
            }

            int descriptionPosition = findIndex(information, IDENTIFIER_FROM);
            int toPosition = findIndex(information, IDENTIFIER_TO);

            String description = generateSubstring(information, START_OF_STRING, descriptionPosition);
            String from = generateSubstring(information, descriptionPosition + LENGTH_FROM, toPosition);
            String to = generateSubstring(information, toPosition + LENGTH_TO);

            Event newEvent = new Event(description, from, to);
            insertIntoTaskList(taskList, newEvent, true);
            writeToTextFile(COMMAND_EVENT, newEvent);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    private static void insertIntoTaskList(Task[] taskList, Task newTask, boolean isStart) {
        for (int arrayCounter = COUNTER_START; arrayCounter < taskList.length; arrayCounter++) {
            if (taskList[arrayCounter] == null) {
                taskList[arrayCounter] = newTask;

                if (isStart) {
                    addCommandMessage(newTask, arrayCounter);
                }

                break;
            }
        }
    }

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
     * FILE-RELATED METHODS
     */
    
    private static void readTextFile(Task[] taskList) {
        File listFile = new File(LISTFILE_PATHNAME);
        fileExistCheck(listFile);

        try {
            Scanner fileInput = new Scanner(listFile);

            while (fileInput.hasNext()) {
                String userCommand = readCommand(fileInput);
                String information = fileInput.nextLine();

                switch (userCommand) {
                case COMMAND_TODO:
                    readTodo(taskList, information);
                    break;
                case COMMAND_DEADLINE:
                    readDeadline(taskList, information);
                    break;
                case COMMAND_EVENT:
                    readEvent(taskList, information);
                    break;
                default:
                    printFileExceptionsMessage(MESSAGE_ERROR_READFAILED);
                }
            }

            fileInput.close();

            System.out.println(BORDER_LINE);
            System.out.println("You have ongoing tasks. Procrastinator.");
        } catch (FileNotFoundException e) {
            printFileExceptionsMessage(MESSAGE_ERROR_FILENOTFOUND);
        }
    }

    private static void fileExistCheck(File listFile) {
        try {
            File directory = listFile.getParentFile();

            if (!directory.exists()) {
                boolean isCreated = directory.mkdirs();

                if (!isCreated) {
                    throw new IOException();
                }
            }

            if (!listFile.exists()) {
                boolean isCreated = listFile.createNewFile();

                if (!isCreated) {
                    throw new IOException();
                }
            }
        } catch (IOException e) {
            printFileExceptionsMessage(MESSAGE_ERROR_IO);
        }
    }

    private static void readTodo(Task[] taskList, String information) {
        String[] parts = information.split("\\|");

        Todo newTodo = new Todo(parts[2].trim());
        newTodo.setStatusIcon(parts[1].trim());
        insertIntoTaskList(taskList, newTodo, false);
    }

    private static void readDeadline(Task[] taskList, String information) {
        String[] parts = information.split("\\|");

        Deadline newDeadline = new Deadline(parts[2].trim(), parts[3].trim());
        newDeadline.setStatusIcon(parts[1].trim());
        insertIntoTaskList(taskList, newDeadline, false);
    }

    private static void readEvent(Task[] taskList, String information) {
        String[] parts = information.split("\\|");

        Event newEvent = new Event(parts[2].trim(), parts[3].trim(), parts[4].trim());
        newEvent.setStatusIcon(parts[1].trim());
        insertIntoTaskList(taskList, newEvent, false);
    }

    private static void writeToTextFile(String userCommand, Task newTask) {
        try {
            FileWriter fileWriter = new FileWriter(LISTFILE_PATHNAME, true);

            String defaultString = userCommand + LISTFILE_DIVIDER + newTask.getStatusNumbers() +
                    LISTFILE_DIVIDER + newTask.getDescription();

            switch (userCommand) {
            case COMMAND_TODO:
                fileWriter.write(defaultString + LISTFILE_NEWLINE);
                break;
            case COMMAND_DEADLINE:
                fileWriter.write(defaultString + LISTFILE_DIVIDER + ((Deadline) newTask).getBy()
                        + LISTFILE_NEWLINE);
                break;
            case COMMAND_EVENT:
                fileWriter.write(defaultString + LISTFILE_DIVIDER + ((Event) newTask).getFrom()
                        + LISTFILE_DIVIDER + ((Event) newTask).getTo() + LISTFILE_NEWLINE);
                break;
            default:
                printFileExceptionsMessage(MESSAGE_ERROR_WRITEFAILED);
                break;
            }

            fileWriter.close();
        } catch (IOException e) {
            printFileExceptionsMessage(MESSAGE_ERROR_WRITEFAILED);
        }

    }


    /*
     * MESSAGE-RELATED METHODS
     */

    private static void welcomeMessage() {
        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_WELCOME);
        System.out.println(BORDER_LINE);
    }

    private static void exitMessage() {
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

    private static void addCommandMessage(Task newTask, int arrayCounter) {
        int totalTasks = arrayCounter + ARRAY_INCREMENT;

        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_ADDED_TASK);
        System.out.println("  " + newTask);
        System.out.println(printTotalTasks(totalTasks));
        System.out.println(BORDER_LINE);
    }

    private static String printTotalTasks(int totalTasks) {
        return MESSAGE_TOTALTASKS + totalTasks;
    }


    /*
     * ERROR-RELATED METHODS
     */

    public static void errorInvalidCommand(String message) throws InvalidCommand {
        throw new InvalidCommand(message);
    }

    public static void errorOutOfBounds(String message) throws IndexOutOfBoundsException {
        throw new IndexOutOfBoundsException(message);
    }

    public static void errorEmptyList(String message) throws EmptyList {
        throw new EmptyList(message);
    }

    public static void errorPrinting(Exception e) {
        System.out.println(BORDER_LINE);
        System.out.println(e.getMessage());
        System.out.println(BORDER_LINE);
    }

    private static void printNumberFormatExceptionMessage() {
        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_ERROR_INVALID_COMMAND);
        System.out.println(MESSAGE_ERROR_INVALID_COMMAND_MARK);
        System.out.println(BORDER_LINE);
    }

    private static void printFileExceptionsMessage(String message) {
        System.out.println(BORDER_LINE);
        System.out.println(message);
        System.out.println(BORDER_LINE);
    }
}
