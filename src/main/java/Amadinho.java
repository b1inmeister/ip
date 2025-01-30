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

    public static void echo(Scanner in, boolean quit) {
        while (!quit) {
            String command = in.nextLine();

            if (command.equals("bye")) {
                quit = true;
            } else {
                System.out.println("___________________________________________________________");
                System.out.println(command);
                System.out.println("____________________________________________________________");
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean quit = false;

        intro();
        echo(in, quit);
        exit();
    }
}
