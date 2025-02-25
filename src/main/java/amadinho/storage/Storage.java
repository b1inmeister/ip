package amadinho.storage;

import static amadinho.storage.StorageConstants.*;

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

/**
 * Contains methods that pertain to the reading and writing of the list of Tasks
 * to an external provided text file.
 */
public class Storage {

    /*
     * Key Methods
     */

    /**
     * Instantiates the provided text file with data into the
     * program, and calls for an existence check.
     *
     * @param taskList List of Tasks that the data will be inserted to.
     */
    public static void readTextFile(ArrayList<Task> taskList) {
        File listFile = new File(LISTFILE_PATHNAME);
        fileExistCheck(listFile);

        readList(taskList, listFile);
    }

    /**
     * Reads data from the provided text file.
     * If text file cannot be found, FileNotFoundException is thrown.
     *
     * @param taskList List of Tasks that the data will be inserted to.
     * @param listFile Instance of the provided text file.
     */
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
            printFileExceptionsMessage(MESSAGE_ERROR_FILENOTFOUND);
        }
    }

    /**
     * Instantiates the provided text file for writing
     * If there are issues with the instantiation process, IOException is thrown.
     *
     * @param taskList Existing list of Tasks.
     */
    public static void writeToTextFile(ArrayList<Task> taskList) {
        try {
            FileWriter fileWriter = new FileWriter(LISTFILE_PATHNAME);

            writeList(taskList, fileWriter);

            fileWriter.close();
        } catch (IOException e) {
            printFileExceptionsMessage(MESSAGE_ERROR_WRITEFAILED);
        }
    }

    /**
     * Writes data from the list of Tasks onto the provided text file.
     *
     * @param taskList Existing list of Tasks.
     * @param fileWriter Instance of the provided text file for writing purposes.
     * @throws IOException If there are issues when writing to the provided text file.
     */
    private static void writeList(ArrayList<Task> taskList, FileWriter fileWriter) throws IOException {
        for (Task task : taskList) {
            char taskType = getTaskType(task);
            String defaultString = makeDefaultString(task, taskType);

            switch (taskType) {
            case CHAR_TODO:
                fileWriter.write(defaultString + LISTFILE_NEWLINE);
                break;
            case CHAR_DEADLINE:
                fileWriter.write(defaultString + LISTFILE_DIVIDER + getBy((Deadline) task) + LISTFILE_NEWLINE);
                break;
            case CHAR_EVENT:
                fileWriter.write(defaultString + LISTFILE_DIVIDER + getFrom((Event) task)
                        + LISTFILE_DIVIDER + getTo((Event) task) + LISTFILE_NEWLINE);
                break;
            default:
                printFileExceptionsMessage(MESSAGE_ERROR_WRITEFAILED);
                break;
            }
        }
    }


    /*
     * Secondary Methods (for readTextFile)
     */

    /**
     * Checks if the provided text file exists.
     *
     * @param listFile Instance of the provided text file.
     */
    private static void fileExistCheck(File listFile) {
        try {
            File directory = listFile.getParentFile();

            directoryCheck(directory);
            fileCheck(listFile);
        } catch (IOException e) {
            printFileExceptionsMessage(MESSAGE_ERROR_IO);
        }
    }

    /**
     * Checks if the parent directory of the provided text file exists.
     * Creates the parent directory if it does not exist.
     *
     * @param directory Instance of the parent directory of the provided text file.
     * @throws IOException If there are issues with the creation of the directory.
     */
    private static void directoryCheck(File directory) throws IOException {
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            createdCheck(isCreated);
        }
    }

    /**
     * Checks if the provided text file exists within the parent directory.
     * Creates the text file if it does not exist.
     *
     * @param listFile Instance of the provided text file.
     * @throws IOException If there are issues with the creation of the provided text file.
     */
    private static void fileCheck(File listFile) throws IOException {
        if (!listFile.exists()) {
            boolean isCreated = listFile.createNewFile();
            createdCheck(isCreated);
        }
    }

    /**
     * Checks if the creation of either the parent directory or the provided text file is successful.
     *
     * @param isCreated Boolean value resulting from the attempt to create the parent directory
     *                  or the provided text file.
     * @throws IOException If the creation is unsuccessful.
     */
    private static void createdCheck(boolean isCreated) throws IOException {
        if (!isCreated) {
            throw new IOException();
        }
    }

    /**
     * Executes the input from the command part of data from the provided text file.
     *
     * @param taskList List of Tasks that the data will be inserted into.
     * @param listFileCommand Input from the command part of data from the provided text file.
     * @param listFileInfo Input from the information part of data from the provided text file.
     */
    private static void executeCommandFromListFile(ArrayList<Task> taskList,
                                                  String listFileCommand, String listFileInfo) {
        switch (listFileCommand) {
        case STRING_SPACE:
            readTodo(taskList, listFileInfo);
            break;
        case STRING_DEADLINE:
            readDeadline(taskList, listFileInfo);
            break;
        case STRING_EVENT:
            readEvent(taskList, listFileInfo);
            break;
        default:
            printFileExceptionsMessage(MESSAGE_ERROR_READFAILED);
        }
    }

    /**
     * Adds a Todo from the provided text file to the list of Tasks.
     *
     * @param taskList List of Tasks that the Todo will be inserted into.
     * @param listFileInfo Information of the Todo such as description and completion status.
     */
    private static void readTodo(ArrayList<Task> taskList, String listFileInfo) {
        String[] infoParts = splitInfo(listFileInfo);

        Todo newTodo = new Todo(getDescription(infoParts));
        newTodo.setStatusIcon(getStatusIcon(infoParts));
        Tasklist.insertIntoTaskList(taskList, newTodo, true);
    }

    /**
     * Adds a Deadline from the provided text file to the list of Tasks.
     *
     * @param taskList List of Tasks that the Deadline will be inserted into.
     * @param listFileInfo Information of the Deadline such as description, completion
     *                     status and deadline timing.
     */
    private static void readDeadline(ArrayList<Task> taskList, String listFileInfo) {
        String[] infoParts = splitInfo(listFileInfo);

        Deadline newDeadline = new Deadline(getDescription(infoParts), getBy(infoParts));
        newDeadline.setStatusIcon(getStatusIcon(infoParts));
        Tasklist.insertIntoTaskList(taskList, newDeadline, true);
    }

    /**
     * Adds an Event from the provided text file to the list of Tasks.
     *
     * @param taskList List of Tasks that the Event will be inserted into.
     * @param listFileInfo Information of the Event such as description, completion
     *                     status as well as start and end timings.
     */
    private static void readEvent(ArrayList<Task> taskList, String listFileInfo) {
        String[] infoParts = splitInfo(listFileInfo);

        Event newEvent = new Event(getDescription(infoParts), getFrom(infoParts), getTo(infoParts));
        newEvent.setStatusIcon(getStatusIcon(infoParts));
        Tasklist.insertIntoTaskList(taskList, newEvent, true);
    }

    /**
     * Splits the information into its individual components.
     * These include description, as well as deadline, start and end timings,
     * depending on the type of Task.
     *
     * @param listFileInfo Information of the Task.
     * @return Array of Strings where each index contains an individual
     *         component of information from the Task.
     */
    private static String[] splitInfo(String listFileInfo) {
        return listFileInfo.split(SPLIT_PARAMETER);
    }

    /**
     * Gets the description of a Task from the array of individual information components.
     *
     * @param infoParts Array of the individual components of the information of the Task chosen.
     * @return Description of the Task chosen.
     */
    private static String getDescription(String[] infoParts) {
        return infoParts[LISTFILE_DESCRIPTION].trim();
    }

    /**
     * Gets the completion status of a Task from the array of individual information components.
     *
     * @param infoParts Array of the individual components of the information of the Task chosen.
     * @return Completion status of the Task chosen.
     */
    private static String getStatusIcon(String[] infoParts) {
        return infoParts[LISTFILE_STATUSICON].trim();
    }

    /**
     * Gets the deadline timing of a Deadline from the array of individual information components.
     *
     * @param infoParts Array of the individual components of the information of the Deadline chosen.
     * @return Deadline timing of the Deadline chosen.
     */
    private static String getBy(String[] infoParts) {
        return infoParts[LISTFILE_BY].trim();
    }

    /**
     * Gets the start timing of an Event from the array of individual information components.
     *
     * @param infoParts Array of the individual components of the information of the Event chosen.
     * @return Start timing of the Event chosen.
     */
    private static String getFrom(String[] infoParts) {
        return infoParts[LISTFILE_FROM].trim();
    }

    /**
     * Gets the end timing of an Event from the array of individual information components.
     *
     * @param infoParts Array of the individual components of the information of the Event chosen.
     * @return End timing of the Event chosen.
     */
    private static String getTo(String[] infoParts) {
        return infoParts[LISTFILE_TO].trim();
    }

    /**
     * Sets the completion status of a Task to the same as the format in the list of Tasks.
     *
     * @param task Task chosen.
     * @return Completion status symbol. (X or _)
     */
    private static char getTaskType(Task task) {
        return task.getTaskType();
    }


    /*
     * Secondary Methods (for writeTextFile)
     */

    /**
     * Inputs a Task and creates a default String format for writing to the provided text file.
     * The information in this String, which is the completion status and description,
     * are common among all types of Tasks.
     *
     * @param task Task chosen.
     * @param taskType Type of the Task chosen.
     * @return String of the common information of the Task chosen.
     */
    private static String makeDefaultString(Task task, char taskType) {
        return taskType + LISTFILE_DIVIDER + task.getStatusIcon() +
                LISTFILE_DIVIDER + task.getDescription();
    }

    /**
     * Gets the deadline timing of a Deadline.
     *
     * @param deadline Deadline chosen.
     * @return Deadline timing of the Deadline chosen.
     */
    private static String getBy(Deadline deadline) {
        return deadline.getBy();
    }

    /**
     * Gets the start timing of an Event.
     *
     * @param event Event chosen.
     * @return Start timing of the Event chosen.
     */
    private static String getFrom(Event event) {
        return event.getFrom();
    }

    /**
     * Gets the end timing of an Event.
     *
     * @param event Event chosen.
     * @return End timing of the Event chosen.
     */
    private static String getTo(Event event) {
        return event.getTo();
    }


    /*
     * Error Printing Method
     */

    /**
     * Prints error messages when there are issues with
     * reading or writing to the provided text file.
     *
     * @param message Error message to print.
     */
    private static void printFileExceptionsMessage(String message) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(message);
        System.out.println(Constants.BORDER_LINE);
    }
}