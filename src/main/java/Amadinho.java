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

            // trigger for mark / unmark
            if(command.startsWith("mark")) {
                mark(toDo, command);
            } else if (command.startsWith("unmark")) {
                unmark(toDo, command);
            } else {
                // trigger for other commands (add / list / bye)
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
    }

    public static void mark(Task[] toDo, String command) {
        int numberPosition = command.indexOf(" ");
        int number = Integer.parseInt(command.substring(numberPosition + 1)) - 1;

        // counter for do while loop
        int i = 0;

        do {
            // exception for out of bounds
            if (toDo[i] == null) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }

            if (i == number) {
                toDo[i].markAsDone();
                break;
            }

            i++;
        } while (i <= number);

        System.out.println("____________________________________________________________");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   [" + toDo[i].getStatusIcon() + "] " + toDo[i].getDescription());
        System.out.println("____________________________________________________________");
    }

    public static void unmark(Task[] toDo, String command) {
        int numberPosition = command.indexOf(" ");
        int number = Integer.parseInt(command.substring(numberPosition + 1)) - 1;

        // counter for do while loop
        int i = 0;

        do {
            // exception for out of bounds
            if (toDo[i] == null) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }

            if (i == number) {
                toDo[i].markAsUndone();
                break;
            }

            i++;
        } while (i <= number);

        System.out.println("____________________________________________________________");
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("   [" + toDo[i].getStatusIcon() + "] " + toDo[i].getDescription());
        System.out.println("____________________________________________________________");
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
