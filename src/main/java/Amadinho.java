import java.util.Scanner;

public class Amadinho {
    public static void readInput(Scanner in, boolean isFinished, Task[] toDoList) {
        while (!isFinished) {
            String command = in.nextLine();

            // trigger for mark / unmark
            if (command.startsWith("mark")) {
                mark(toDoList, command);
            } else if (command.startsWith("unmark")) {
                unmark(toDoList, command);
            } else {
                // trigger for other commands (add / list / bye)
                switch (command) {
                case "bye":
                    isFinished = true;
                    break;
                case "list":
                    list(toDoList);
                    break;
                default:
                    add(toDoList, command);
                }
            }
        }
    }

    public static void mark(Task[] toDoList, String command) {
        int numberPosition = command.indexOf(" ");
        int number = Integer.parseInt(command.substring(numberPosition + 1)) - 1;

        // counter for do while loop
        int i = 0;

        do {
            // exception for out of bounds
            if (toDoList[i] == null) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }

            if (i == number) {
                toDoList[i].markAsDone();
                break;
            }

            i++;
        } while (i <= number);

        System.out.println("____________________________________________________________");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   [" + toDoList[i].getStatusIcon() + "] " +
                toDoList[i].getDescription());
        System.out.println("____________________________________________________________");
    }

    public static void unmark(Task[] toDoList, String command) {
        int numberPosition = command.indexOf(" ");
        int number = Integer.parseInt(command.substring(numberPosition + 1)) - 1;

        // counter for do while loop
        int i = 0;

        do {
            // exception for out of bounds
            if (toDoList[i] == null) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }

            if (i == number) {
                toDoList[i].markAsUndone();
                break;
            }

            i++;
        } while (i <= number);

        System.out.println("____________________________________________________________");
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("   [" + toDoList[i].getStatusIcon() + "] " +
                toDoList[i].getDescription());
        System.out.println("____________________________________________________________");
    }

    public static void add(Task[] toDoList, String command) {
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] == null) {
                Task newTask = new Task(command);
                toDoList[i] = newTask;
                break;
            }
        }

        System.out.println("____________________________________________________________");
        System.out.println("added: " + command);
        System.out.println("____________________________________________________________");
    }

    public static void list(Task[] toDoList) {
        int counter = 1;

        System.out.println("____________________________________________________________");

        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] == null) {
                break;
            } else {
                System.out.println(counter + ". [" + toDoList[i].getStatusIcon() + "] " +
                        toDoList[i].getDescription());
                counter++;
            }
        }

        System.out.println("____________________________________________________________");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean isFinished = false;
        Task[] toDoList = new Task[100];

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Amadinho!");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        readInput(in, isFinished, toDoList);

        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
