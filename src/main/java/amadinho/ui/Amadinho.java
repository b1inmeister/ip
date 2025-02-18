package amadinho.ui;

import static amadinho.ui.Constants.*;
import amadinho.tasktypes.*;
import amadinho.exceptions.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Amadinho {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

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

    private static void executeCommand(ArrayList<Task> taskList, String userCommand, String information) {
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
            case COMMAND_DELETE:
                commandDelete(taskList, information);
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

    private static void commandList(ArrayList<Task> taskList) {
        try {
            if (taskList.isEmpty()) {
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

    private static void printList(ArrayList<Task> taskList) {
        int taskCounter = LIST_COUNTER_START;

        for (Task task : taskList) {
            System.out.println(taskCounter + "." + task);
            taskCounter++;
        }
    }

    private static void commandMark(ArrayList<Task> taskList, String information) {
        executeMark(taskList, information, true);
    }

    private static void commandUnmark(ArrayList<Task> taskList, String information) {
        executeMark(taskList, information, false);
    }

    private static void executeMark(ArrayList<Task> taskList, String information, boolean toMark) {
        int taskCount;

        try {
            taskCount = Integer.parseInt(information);
        } catch (NumberFormatException e) {
            printNumberFormatExceptionMessage(COMMAND_MARK);
            return;
        }

        Task taskToMark = taskList.get(taskCount - ARRAY_INCREMENT);

        if(toMark) {
            taskToMark.markAsDone();
        } else {
            taskToMark.markAsUndone();
        }

        writeToTextFile(taskList);
        markCommandMessage(taskCount, taskToMark, toMark);
    }

    private static void commandDelete(ArrayList<Task> taskList, String information) {
        int taskCount;

        try {
            taskCount = Integer.parseInt(information);
        } catch (NumberFormatException e) {
            printNumberFormatExceptionMessage(COMMAND_DELETE);
            return;
        }

        Task taskToDelete = taskList.get(taskCount - ARRAY_INCREMENT);
        taskList.remove(taskCount - ARRAY_INCREMENT);

        writeToTextFile(taskList);
        deleteCommandMessage(taskList, taskToDelete);
    }

    private static void commandTodo(ArrayList<Task> taskList, String information) {
        try {
            if (isEmpty(information)) {
                errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND_TODO);
            }

            Todo newTodo = new Todo(information);
            insertIntoTaskList(taskList, newTodo, true);
            writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    private static void commandDeadline(ArrayList<Task> taskList, String information) {
        try {
            if (isMissing(information, IDENTIFIER_BY)) {
                errorInvalidCommand(MESSAGE_ERROR_INVALID_COMMAND_DEADLINE);
            }

            int descriptionPosition = findIndex(information, IDENTIFIER_BY);

            String description = generateSubstring(information, START_OF_STRING, descriptionPosition);
            String by = generateSubstring(information, descriptionPosition + LENGTH_BY);

            Deadline newDeadline = new Deadline(description, by);
            insertIntoTaskList(taskList, newDeadline, true);
            writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    private static void commandEvent(ArrayList<Task> taskList, String information) {
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
            writeToTextFile(taskList);
        } catch (InvalidCommand e) {
            errorPrinting(e);
        }
    }

    private static void insertIntoTaskList(ArrayList<Task> taskList, Task newTask, boolean isStart) {
        taskList.add(newTask);

        if (isStart) {
            addCommandMessage(taskList, newTask);
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
    
    private static void readTextFile(ArrayList<Task> taskList) {
        File listFile = new File(LISTFILE_PATHNAME);
        fileExistCheck(listFile);

        try {
            Scanner fileInput = new Scanner(listFile);

            while (fileInput.hasNext()) {
                String userCommand = readCommand(fileInput);
                String information = fileInput.nextLine();

                switch (userCommand) {
                case "T":
                    readTodo(taskList, information);
                    break;
                case "D":
                    readDeadline(taskList, information);
                    break;
                case "E":
                    readEvent(taskList, information);
                    break;
                default:
                    printFileExceptionsMessage(MESSAGE_ERROR_READFAILED);
                }
            }

            fileInput.close();
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

    private static void readTodo(ArrayList<Task> taskList, String information) {
        String[] parts = information.split("\\|");

        Todo newTodo = new Todo(parts[2].trim());
        newTodo.setStatusIcon(parts[1].trim());
        insertIntoTaskList(taskList, newTodo, false);
    }

    private static void readDeadline(ArrayList<Task> taskList, String information) {
        String[] parts = information.split("\\|");

        Deadline newDeadline = new Deadline(parts[2].trim(), parts[3].trim());
        newDeadline.setStatusIcon(parts[1].trim());
        insertIntoTaskList(taskList, newDeadline, false);
    }

    private static void readEvent(ArrayList<Task> taskList, String information) {
        String[] parts = information.split("\\|");

        Event newEvent = new Event(parts[2].trim(), parts[3].trim(), parts[4].trim());
        newEvent.setStatusIcon(parts[1].trim());
        insertIntoTaskList(taskList, newEvent, false);
    }

    private static void writeToTextFile(ArrayList<Task> taskList) {
        try {
            FileWriter fileWriter = new FileWriter(LISTFILE_PATHNAME);

            for (Task task : taskList) {
                char taskType = task.getTaskType();
                String defaultString = taskType + LISTFILE_DIVIDER + task.getStatusIcon() +
                        LISTFILE_DIVIDER + task.getDescription();

                switch (taskType) {
                case 'T':
                    fileWriter.write(defaultString + LISTFILE_NEWLINE);
                    break;
                case 'D':
                    fileWriter.write(defaultString + LISTFILE_DIVIDER + ((Deadline) task).getBy()
                            + LISTFILE_NEWLINE);
                    break;
                case 'E':
                    fileWriter.write(defaultString + LISTFILE_DIVIDER + ((Event) task).getFrom()
                            + LISTFILE_DIVIDER + ((Event) task).getTo() + LISTFILE_NEWLINE);
                    break;
                default:
                    printFileExceptionsMessage(MESSAGE_ERROR_WRITEFAILED);
                    break;
                }
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

    private static void markCommandMessage(int taskCount, Task taskToMark, boolean toMark) {
        System.out.println(BORDER_LINE);

        if (toMark) {
            System.out.println(MESSAGE_MARK_COMPLETE);
        } else {
            System.out.println(MESSAGE_UNMARK_COMPLETE);
        }

        System.out.println(taskCount + "." + taskToMark);
        System.out.println(BORDER_LINE);
    }

    private static void deleteCommandMessage(ArrayList<Task> taskList, Task taskToDelete) {
        int totalTasks = taskList.size();

        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_DELETED_TASK);
        System.out.println("  " + taskToDelete);
        System.out.println(printTotalTasks(totalTasks));
        System.out.println(BORDER_LINE);
    }

    private static void addCommandMessage(ArrayList<Task> taskList, Task newTask) {
        int totalTasks = taskList.size();

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

    public static void errorEmptyList(String message) throws EmptyList {
        throw new EmptyList(message);
    }

    public static void errorPrinting(Exception e) {
        System.out.println(BORDER_LINE);
        System.out.println(e.getMessage());
        System.out.println(BORDER_LINE);
    }

    private static void printNumberFormatExceptionMessage(String message) {
        System.out.println(BORDER_LINE);
        System.out.println(MESSAGE_ERROR_INVALID_COMMAND);

        if (message.equals(COMMAND_MARK)) {
            System.out.println(MESSAGE_ERROR_INVALID_COMMAND_MARK);
        } else {
            System.out.println(MESSAGE_ERROR_INVALID_COMMAND_DELETE);
        }

        System.out.println(BORDER_LINE);
    }

    private static void printFileExceptionsMessage(String message) {
        System.out.println(BORDER_LINE);
        System.out.println(message);
        System.out.println(BORDER_LINE);
    }
}
