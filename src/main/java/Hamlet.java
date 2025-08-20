package main.java;
import java.util.Scanner;

public class Hamlet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String lineBreaks = "____________________________________________________________";
        System.out.println(lineBreaks);
        System.out.println("Hello! I'm Hamlet\nHow may I help you?");
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(lineBreaks);
            if (input.equals("what is your name")) {
                System.out.println("\t" + "I am Hamlet!");
            } else {
                System.out.println("\t" + input);
            }
            System.out.println(lineBreaks);
            input = scanner.nextLine();
        }
        System.out.println(lineBreaks);
        System.out.println("Bye. Hope to see you again!\n");
        System.out.println(lineBreaks);
    }
}
