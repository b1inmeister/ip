package amadinho.main;

import amadinho.ui.Ui;
import amadinho.parser.Parser;
import amadinho.storage.Storage;
import amadinho.tasktypes.Task;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Main class for execution of Amadinho. Program starts and terminates here.
 */
public class Amadinho {

    /**
     * Method for execution of Amadinho. When the program is started, this method is called, and the program
     * terminates when this method has finished execution.
     *
     * @param args Command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

        Storage.readTextFile(taskList);
        Ui.welcomeMessage();

        while (true) {
            String userCommand = Ui.readCommand(in);

            if (Ui.isCompleted(userCommand)) {
                break;
            }

            String information = Ui.readInfo(in, false);

            Parser.executeCommand(taskList, userCommand, information);
        }

        Ui.exitMessage();
    }
}
