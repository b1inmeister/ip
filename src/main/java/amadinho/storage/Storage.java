package amadinho.storage;

import amadinho.main.Constants;
import amadinho.ui.Ui;
import amadinho.tasklist.Tasklist;
import amadinho.tasktypes.Task;
import amadinho.tasktypes.Todo;
import amadinho.tasktypes.Deadline;
import amadinho.tasktypes.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    /*
     * Key Methods
     */

    public static void readTextFile(ArrayList<Task> taskList) {
        File listFile = new File(Constants.LISTFILE_PATHNAME);
        fileExistCheck(listFile);

        readList(taskList, listFile);
    }

    private static void readList(ArrayList<Task> taskList, File listFile) {
        try {
            Scanner fileInput = new Scanner(listFile);

            while (fileInput.hasNext()) {
                String listFileCommand = Ui.readCommand(fileInput);
                String listFileInfo = Ui.readInfo(fileInput, true);

                executeCommandFromListFile(taskList, listFileCommand, listFileInfo);
            }

            fileInput.close();
        } catch (FileNotFoundException e) {
            printFileExceptionsMessage(Constants.MESSAGE_ERROR_FILENOTFOUND);
        }
    }

    public static void writeToTextFile(ArrayList<Task> taskList) {
        try {
            FileWriter fileWriter = new FileWriter(Constants.LISTFILE_PATHNAME);

            writeList(taskList, fileWriter);

            fileWriter.close();
        } catch (IOException e) {
            printFileExceptionsMessage(Constants.MESSAGE_ERROR_WRITEFAILED);
        }
    }

    private static void writeList(ArrayList<Task> taskList, FileWriter fileWriter) throws IOException {
        for (Task task : taskList) {
            char taskType = getTaskType(task);
            String defaultString = makeDefaultString(task, taskType);

            switch (taskType) {
            case Constants.CHAR_TODO:
                fileWriter.write(defaultString + Constants.LISTFILE_NEWLINE);
                break;
            case Constants.CHAR_DEADLINE:
                fileWriter.write(defaultString + Constants.LISTFILE_DIVIDER + getBy((Deadline) task) + Constants.LISTFILE_NEWLINE);
                break;
            case Constants.CHAR_EVENT:
                fileWriter.write(defaultString + Constants.LISTFILE_DIVIDER + getFrom((Event) task)
                        + Constants.LISTFILE_DIVIDER + getTo((Event) task) + Constants.LISTFILE_NEWLINE);
                break;
            default:
                printFileExceptionsMessage(Constants.MESSAGE_ERROR_WRITEFAILED);
                break;
            }
        }
    }


    /*
     * Secondary Methods (for readTextFile)
     */

    private static void fileExistCheck(File listFile) {
        try {
            File directory = listFile.getParentFile();

            directoryCheck(directory);
            fileCheck(listFile);
        } catch (IOException e) {
            printFileExceptionsMessage(Constants.MESSAGE_ERROR_IO);
        }
    }

    private static void directoryCheck(File directory) throws IOException {
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            createdCheck(isCreated);
        }
    }

    private static void fileCheck(File listFile) throws IOException {
        if (!listFile.exists()) {
            boolean isCreated = listFile.createNewFile();
            createdCheck(isCreated);
        }
    }

    private static void createdCheck(boolean isCreated) throws IOException {
        if (!isCreated) {
            throw new IOException();
        }
    }

    private static void executeCommandFromListFile(ArrayList<Task> taskList,
                                                  String listFileCommand, String listFileInfo) {
        switch (listFileCommand) {
        case Constants.STRING_SPACE:
            readTodo(taskList, listFileInfo);
            break;
        case Constants.STRING_DEADLINE:
            readDeadline(taskList, listFileInfo);
            break;
        case Constants.STRING_EVENT:
            readEvent(taskList, listFileInfo);
            break;
        default:
            printFileExceptionsMessage(Constants.MESSAGE_ERROR_READFAILED);
        }
    }

    private static void readTodo(ArrayList<Task> taskList, String listFileInfo) {
        String[] infoParts = splitInfo(listFileInfo);

        Todo newTodo = new Todo(getDescription(infoParts));
        newTodo.setStatusIcon(getStatusIcon(infoParts));
        Tasklist.insertIntoTaskList(taskList, newTodo, false);
    }

    private static void readDeadline(ArrayList<Task> taskList, String listFileInfo) {
        String[] infoParts = splitInfo(listFileInfo);

        Deadline newDeadline = new Deadline(getDescription(infoParts), getBy(infoParts));
        newDeadline.setStatusIcon(getStatusIcon(infoParts));
        Tasklist.insertIntoTaskList(taskList, newDeadline, false);
    }

    private static void readEvent(ArrayList<Task> taskList, String listFileInfo) {
        String[] infoParts = splitInfo(listFileInfo);

        Event newEvent = new Event(getDescription(infoParts), getFrom(infoParts), getTo(infoParts));
        newEvent.setStatusIcon(getStatusIcon(infoParts));
        Tasklist.insertIntoTaskList(taskList, newEvent, false);
    }

    private static String[] splitInfo(String listFileInfo) {
        return listFileInfo.split(Constants.SPLIT_PARAMETER);
    }

    private static String getDescription(String[] infoParts) {
        return infoParts[Constants.LISTFILE_DESCRIPTION].trim();
    }

    private static String getStatusIcon(String[] infoParts) {
        return infoParts[Constants.LISTFILE_STATUSICON].trim();
    }

    private static String getBy(String[] infoParts) {
        return infoParts[Constants.LISTFILE_BY].trim();
    }

    private static String getFrom(String[] infoParts) {
        return infoParts[Constants.LISTFILE_FROM].trim();
    }

    private static String getTo(String[] infoParts) {
        return infoParts[Constants.LISTFILE_TO].trim();
    }

    private static char getTaskType(Task task) {
        return task.getTaskType();
    }


    /*
     * Secondary Methods (for writeTextFile)
     */

    private static String makeDefaultString(Task task, char taskType) {
        return taskType + Constants.LISTFILE_DIVIDER + task.getStatusIcon() +
                Constants.LISTFILE_DIVIDER + task.getDescription();
    }

    private static String getBy(Deadline task) {
        return task.getBy();
    }

    private static String getFrom(Event task) {
        return task.getFrom();
    }

    private static String getTo(Event task) {
        return task.getTo();
    }

    private static void printFileExceptionsMessage(String message) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(message);
        System.out.println(Constants.BORDER_LINE);
    }
}