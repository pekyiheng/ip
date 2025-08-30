package main.java;

import java.util.ArrayList;

public class Ui {
    static final String LINE_BREAKS = "____________________________________________________________";

    public static void printLineBreak() {
        System.out.println(LINE_BREAKS);
    }

    public static void welcomeMessage() {
        printLineBreak();
        System.out.println("Hello! I'm Hamlet\nHow may I help you?");
    }

    public static void goodbyeMessage() {
        printLineBreak();
        System.out.println("Bye. Hope to see you again!\n");
        printLineBreak();
    }

    public static void showName() {
        System.out.println("\t" + "I am Hamlet!");
    }

    public static void showTasks(ArrayList<Task> inputs, int count) {
        for (int i = 0; i < count; i++) {
            Task curTask = inputs.get(i);
            System.out.printf("\t%d.%s", i+1, curTask);
        }
    }

    public static void markTaskAsDone(Task curTask) {
        System.out.print("Nice! I've marked this task as done:\n");
        System.out.print(curTask);
    }

    public static void markTaskAsUndone(Task curTask) {
        System.out.print("Ok, I've marked this task as not done yet:\n");
        System.out.print(curTask);
    }

    public static void addNewTask(Task newTask, int count) {
        System.out.print("Got it. I've added this task:\n");
        System.out.print(newTask);
        System.out.printf("Now you have %d tasks in the list.\n", count);
    }

    public static void removeTask(Task taskToRemove, int count) {
        System.out.print(" Noted. I've removed this task:\n");
        System.out.print(taskToRemove);
        System.out.printf("Now you have %d tasks in the list.\n", count);
    }

    public static void showHappenings(String deadlinesOnDate, String eventsOnDate) {
        System.out.println("Deadline:\n" + deadlinesOnDate + "\nEvents:\n" + eventsOnDate);
    }

    public static void showErrorMessage(String message) {
        System.out.println(message);
    }

    public static void showDateTimeParseExceptionMessage() {
        System.out.println("Invalid date format. Please use YYYY-MM-DD");
    }

    public static void writeToFileMessage(Result result) {
        if (result.equals(Result.SUCCESS)) {
            System.out.println("Wrote to file");
        } else {
            System.out.println("Failed to write to file.");
        }
    }
}
