package main.java;
import java.util.Scanner;

public class Hamlet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] inputs = new Task[100];
        int count = 0;
        String lineBreaks = "____________________________________________________________";
        System.out.println(lineBreaks);
        System.out.println("Hello! I'm Hamlet\nHow may I help you?");
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(lineBreaks);
            if (input.equals("what is your name")) {
                System.out.println("\t" + "I am Hamlet!");
            } else if (input.equals("list")) {
                for (int i = 0; i < count; i++) {
                    Task curTask = inputs[i];
                    System.out.printf("\t%d.[%s] %s%n", i+1, curTask.getStatusIcon(), curTask.description);
                }
            } else if (input.matches("mark \\d+")) {
                int indexToEdit = Integer.parseInt(input.replaceAll("[^\\d]", ""));
                Task curTask = inputs[indexToEdit - 1]; //inputs is 0-indexed
                curTask.markAsDone();
                System.out.print("Nice! I've marked this task as done:\n");
                System.out.printf("[%s] %s%n", curTask.getStatusIcon(), curTask.description);
            } else if (input.matches("unmark \\d+")) {
                int indexToEdit = Integer.parseInt(input.replaceAll("[^\\d]", ""));
                Task curTask = inputs[indexToEdit - 1]; //inputs is 0-indexed
                curTask.markAsUndone();
                System.out.print("Ok, I've marked this task as not done yet:\n");
                System.out.printf("[%s] %s%n", curTask.getStatusIcon(), curTask.description);
            }
            else {
                System.out.println("\tadded: " + input);
                inputs[count] = new Task(input);
                count++;
            }
            System.out.println(lineBreaks);
            input = scanner.nextLine();
        }
        System.out.println(lineBreaks);
        System.out.println("Bye. Hope to see you again!\n");
        System.out.println(lineBreaks);
    }
}
