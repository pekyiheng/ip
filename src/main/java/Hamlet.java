package main.java;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    System.out.printf("\t%d.%s", i+1, curTask);
                }
            } else if (input.matches("mark \\d+")) {
                int indexToEdit = Integer.parseInt(input.replaceAll("[^\\d]", ""));
                Task curTask = inputs[indexToEdit - 1]; //inputs is 0-indexed
                curTask.markAsDone();
                System.out.print("Nice! I've marked this task as done:\n");
                System.out.print(curTask);
            } else if (input.matches("unmark \\d+")) {
                int indexToEdit = Integer.parseInt(input.replaceAll("[^\\d]", ""));
                Task curTask = inputs[indexToEdit - 1]; //inputs is 0-indexed
                curTask.markAsUndone();
                System.out.print("Ok, I've marked this task as not done yet:\n");
                System.out.print(curTask);
            } else {
                Task newTask;
                boolean validNewTask = true;
                if (input.split(" ")[0].equals("todo")) {
                    newTask = new Todo(input.replace("todo ", ""));
                } else if (input.split(" ")[0].equals("deadline")) {
                    Pattern pattern = Pattern.compile("deadline (.+) /by (.+)");
                    Matcher matcher = pattern.matcher(input);
                    if (matcher.matches()) {
                        String deadlineTask = matcher.group(1);
                        String by = matcher.group(2);
                        newTask = new Deadline(deadlineTask, by);
                    } else {
                        newTask = null;
                    }
                } else if (input.split(" ")[0].equals("event")) {
                    Pattern pattern = Pattern.compile("event (.+) /from (.+) /to (.+)");
                    Matcher matcher = pattern.matcher(input);
                    if (matcher.matches()) {
                        String eventTask = matcher.group(1);
                        String from = matcher.group(2);
                        String to = matcher.group(3);
                        newTask = new Event(eventTask, from, to);
                    } else {
                        newTask = null;
                    }
                } else {
                    validNewTask = false;
                    newTask = null;
                }

                if (validNewTask) {
                    inputs[count] = newTask;
                    count++;
                    System.out.print("Got it. I've added this task:\n");
                    System.out.print(newTask);
                    System.out.printf("Now you have %d tasks in the list.\n", count);
                }
            }
            System.out.println(lineBreaks);
            input = scanner.nextLine();
        }
        System.out.println(lineBreaks);
        System.out.println("Bye. Hope to see you again!\n");
        System.out.println(lineBreaks);
    }
}
