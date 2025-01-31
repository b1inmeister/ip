import java.util.Scanner;

public class Amadinho {

    public static void intro() {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Amadinho!");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public static void exit() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    public static void readInput(Scanner in, boolean quit, Task[] toDo) {
        while (!quit) {
            String command = in.nextLine();

            switch (command) {
            case "bye":
                quit = true;
                break;
            case "list":
                list(toDo);
                break;
            default:
                add(toDo, command);
            }
        }
    }

    public static void add(Task[] toDo, String command) {
        for (int i = 0; i < toDo.length; i++) {
            if (toDo[i] == null) {
                Task newTask = new Task(command);
                toDo[i] = newTask;
                break;
            }
        }

        System.out.println("____________________________________________________________");
        System.out.println("added: " + command);
        System.out.println("____________________________________________________________");
    }

    public static void list(Task[] toDo) {
        int counter = 1;

        System.out.println("____________________________________________________________");

        for (int i = 0; i < toDo.length; i++) {
            if (toDo[i] == null) {
                break;
            } else {
                System.out.println(counter + ". [" + toDo[i].getStatusIcon() + "] " +
                        toDo[i].getDescription());
                counter++;
            }
        }

        System.out.println("____________________________________________________________");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        Task[] toDo = new Task[100];

        intro();
        readInput(in, quit, toDo);
        exit();
    }
}
