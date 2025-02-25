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
 * Contains methods that pertain to the reading and writing of the list of Tasks to an external text file.
 */
public class Storage {

    /*
     * PRIMARY METHODS
     */

    /**
     * Instantiates a text file with an existing list of Tasks into the
     * program, and calls for an existence check.
     *
     * @param taskList New list of Tasks that the existing list will be inserted to.
     */
    public static void readTextFile(ArrayList<Task> taskList) {
        File listFile = new File(LISTFILE_PATHNAME);
        fileExistCheck(listFile);

        readList(taskList, listFile);
    }

    /**
     * Reads an existing list of Tasks from the text file.
     * If text file cannot be found, FileNotFoundException is thrown.
     *
     * @param taskList New list of Tasks that the existing list will be inserted to.
     * @param listFile Instance of the text file containing the existing list of Tasks.
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
     * Instantiates a text file to write a list of Tasks to.
     * If there are issues with the instantiation process, IOException is thrown.
     *
     * @param taskList List of Tasks to write from.
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
     * Writes a list of Tasks onto a text file.
     *
     * @param taskList List of Tasks to write from.
     * @param fileWriter Instance of the text file to write to.
     * @throws IOException If there are issues writing to the text file.
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
     * SECONDARY METHODS (for readTextFile)
     */

    /**
     * Checks if a text file exists.
     *
     * @param listFile Instance of the text file.
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
     * Checks if the parent directory of a text file exists, and creates a parent directory if it does not exist.
     *
     * @param directory Instance of the parent directory of the text file.
     * @throws IOException If there are issues with the creation of the directory.
     */
    private static void directoryCheck(File directory) throws IOException {
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            createdCheck(isCreated);
        }
    }

    /**
     * Checks if a text file exists within a parent directory, and creates a text file if it does not exist.
     *
     * @param listFile Instance of the text file.
     * @throws IOException If there are issues with the creation of the text file.
     */
    private static void fileCheck(File listFile) throws IOException {
        if (!listFile.exists()) {
            boolean isCreated = listFile.createNewFile();
            createdCheck(isCreated);
        }
    }

    /**
     * Checks if the creation of either a parent directory or a text file is successful.
     *
     * @param isCreated Boolean value resulting from the attempt to create the parent directory
     *                  or the text file.
     * @throws IOException If the creation is unsuccessful.
     */
    private static void createdCheck(boolean isCreated) throws IOException {
        if (!isCreated) {
            throw new IOException();
        }
    }

    /**
     * Executes the reading of a Task from the existing list of Tasks in a text file, based on its type.
     *
     * @param taskList New list of Tasks that the existing list of Tasks will be inserted into.
     * @param listFileTaskType Type of the specified Task in the existing list.
     * @param listFileInfo Information of the specified Task in the existing list.
     */
    private static void executeCommandFromListFile(ArrayList<Task> taskList,
                                                  String listFileTaskType, String listFileInfo) {
        switch (listFileTaskType) {
        case STRING_TODO:
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
     * Adds a Todo from the existing list of Tasks in a text file to a new list of Tasks in the program.
     *
     * @param taskList New list of Tasks that the Todo will be inserted into.
     * @param listFileInfo Information of the Todo such as description and completion status.
     */
    private static void readTodo(ArrayList<Task> taskList, String listFileInfo) {
        String[] infoParts = splitInfo(listFileInfo);

        Todo newTodo = new Todo(getDescription(infoParts));
        newTodo.setStatusIcon(getStatusIcon(infoParts));

        Tasklist.insertIntoTaskList(taskList, newTodo, true);
    }

    /**
     * Adds a Deadline from the existing list of Tasks in a text file to a new list of Tasks in the program.
     *
     * @param taskList New list of Tasks that the Deadline will be inserted into.
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
     * Adds an Event from the existing list of Tasks in a text file to a new list of Tasks in the program.
     *
     * @param taskList New list of Tasks that the Event will be inserted into.
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
     * Splits the information of a Task into its individual components. These include description, as well as
     * deadline, start and end timings, depending on the type of Task.
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
     * @param infoParts Array of the individual components of the information of the Task.
     * @return Description of the Task.
     */
    private static String getDescription(String[] infoParts) {
        return infoParts[LISTFILE_DESCRIPTION].trim();
    }

    /**
     * Gets the completion status of a Task from the array of individual information components.
     *
     * @param infoParts Array of the individual components of the information of the Task.
     * @return Completion status of the Task.
     */
    private static String getStatusIcon(String[] infoParts) {
        return infoParts[LISTFILE_STATUSICON].trim();
    }

    /**
     * Gets the deadline timing of a Deadline from the array of individual information components.
     *
     * @param infoParts Array of the individual components of the information of the Deadline.
     * @return Deadline timing of the Deadline.
     */
    private static String getBy(String[] infoParts) {
        return infoParts[LISTFILE_BY].trim();
    }

    /**
     * Gets the start timing of an Event from the array of individual information components.
     *
     * @param infoParts Array of the individual components of the information of the Event.
     * @return Start timing of the Event.
     */
    private static String getFrom(String[] infoParts) {
        return infoParts[LISTFILE_FROM].trim();
    }

    /**
     * Gets the end timing of an Event from the array of individual information components.
     *
     * @param infoParts Array of the individual components of the information of the Event.
     * @return End timing of the Event.
     */
    private static String getTo(String[] infoParts) {
        return infoParts[LISTFILE_TO].trim();
    }

    /**
     * Sets the completion status icon of a Task to follow the format in a list of Tasks.
     *
     * @param task Task to get the completion status from.
     * @return Completion status icon (X or _).
     */
    private static char getTaskType(Task task) {
        return task.getTaskType();
    }


    /*
     * SECONDARY METHODS (for writeTextFile)
     */

    /**
     * Inputs a Task and creates a default String for writing to the text file. The information in this String,
     * which is the completion status and description, are common among all types of Tasks.
     *
     * @param task Task to get the description and completion status from.
     * @param taskType Type of the Task in char form.
     * @return String containing the common information of the Task.
     */
    private static String makeDefaultString(Task task, char taskType) {
        return taskType + LISTFILE_DIVIDER + task.getStatusIcon() +
                LISTFILE_DIVIDER + task.getDescription();
    }

    /**
     * Gets the deadline timing of a Deadline.
     *
     * @param deadline Deadline to get the deadline timing from.
     * @return Deadline timing of the Deadline.
     */
    private static String getBy(Deadline deadline) {
        return deadline.getBy();
    }

    /**
     * Gets the start timing of an Event.
     *
     * @param event Event to get the start timing from.
     * @return Start timing of the Event.
     */
    private static String getFrom(Event event) {
        return event.getFrom();
    }

    /**
     * Gets the end timing of an Event.
     *
     * @param event Event to get the end timing from.
     * @return End timing of the Event.
     */
    private static String getTo(Event event) {
        return event.getTo();
    }


    /*
     * ERROR PRINTING METHOD
     */

    /**
     * Prints error messages when there are issues with reading from or writing to the text file.
     *
     * @param message Error message to print.
     */
    private static void printFileExceptionsMessage(String message) {
        System.out.println(Constants.BORDER_LINE);
        System.out.println(message);
        System.out.println(Constants.BORDER_LINE);
    }
}