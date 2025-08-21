package main.java;
import java.util.Scanner;

public class Hamlet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] inputs = new String[100];
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
                    System.out.printf("\t%d.%s%n", i+1, inputs[i]);
                }
            } else {
                System.out.println("\tadded: " + input);
                inputs[count] = input;
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
