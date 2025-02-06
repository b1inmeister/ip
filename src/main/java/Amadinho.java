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
        System.out.println((i + 1) + "." + toDoList[i]);
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
        System.out.println((i + 1) + "." + toDoList[i]);
        System.out.println("____________________________________________________________");
    }

    public static void add(Task[] toDoList, String command) {
        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] == null) {
                if (command.contains("deadline")) {
                    int indexDeadline = command.indexOf("deadline");
                    int indexBy = command.indexOf("/by");

                    String description = command.substring(indexDeadline + 9, indexBy - 1);
                    String by = command.substring(indexBy + 4);

                    Deadline newDeadline = new Deadline(description, by);
                    toDoList[i] = newDeadline;

                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newDeadline);
                    System.out.println("Now you have " + (i + 1) + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    break;
                } else if (command.contains("event")) {
                    int indexEvent = command.indexOf("event");
                    int indexFrom = command.indexOf("/from");
                    int indexTo = command.indexOf("/to");

                    String description = command.substring(indexEvent + 6, indexFrom - 1);
                    String from = command.substring(indexFrom + 6, indexTo - 1);
                    String to = command.substring(indexTo + 4);

                    Event newEvent = new Event(description, from, to);
                    toDoList[i] = newEvent;

                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newEvent);
                    System.out.println("Now you have " + (i + 1) + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    break;
                } else if (command.contains("todo")) {
                    int indexTodo = command.indexOf("todo");

                    String description = command.substring(indexTodo + 5);

                    Todo newTodo = new Todo(description);
                    toDoList[i] = newTodo;

                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTodo);
                    System.out.println("Now you have " + (i + 1) + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    break;
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println("Invalid command. Please try again.");
                    System.out.println("____________________________________________________________");
                    break;
                }
            }
        }
    }

    public static void list(Task[] toDoList) {
        int counter = 1;

        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < toDoList.length; i++) {
            if (toDoList[i] == null) {
                break;
            } else {
                System.out.println(counter + "." + toDoList[i]);
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
