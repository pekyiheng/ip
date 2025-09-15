package hamlet.ui;

import hamlet.enums.Result;
import hamlet.task.Task;

import java.util.ArrayList;

public class Ui {
    static final String LINE_BREAKS = "___________________________________________________";

    public static String printLineBreak() {
        return LINE_BREAKS;
    }

    public static String welcomeMessage() {
        return LINE_BREAKS + "Hello! I'm Hamlet\nHow may I help you?";
    }

    public static String goodbyeMessage() {
        return LINE_BREAKS + "Bye. Hope to see you again!\n\n" + LINE_BREAKS;
    }

    public static String showName() {
        return "\t" + "I am Hamlet!";
    }

    public static String showTasks(ArrayList<Task> inputs, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            Task curTask = inputs.get(i);
            // Append each task to the string builder, adding a newline for separation
            sb.append(String.format("\t%d.%s%n", i + 1, curTask));
        }
        return sb.toString();
    }

    public static String markTaskAsDone(Task curTask) {
        return "Nice! I've marked this task as done:\n" + curTask;
    }

    public static String markTaskAsUndone(Task curTask) {
        return "Ok, I've marked this task as not done yet:\n" + curTask;
    }

    public static String addNewTask(Task newTask, int count) {
        return String.format("Got it. I've added this task:\n%s\nNow you have %d tasks in the list.\n",
                newTask, count);
    }

    public static String removeTask(Task taskToRemove, int count) {
        return String.format("Noted. I've removed this task:\n%s\nNow you have %d tasks in the list.\n",
                taskToRemove, count);
    }

    public static String showHappenings(String deadlinesOnDate, String eventsOnDate) {
        return "Deadline:\n" + deadlinesOnDate + "\nEvents:\n" + eventsOnDate;
    }

    public static String showFinds(String resultFromMatchFind) {
        return LINE_BREAKS +
                "Here are the matching tasks in your list:\n" +
                resultFromMatchFind + "\n" +
                LINE_BREAKS;
    }

    public static String showErrorMessage(String message) {
        return message;
    }

    public static String showDateTimeParseExceptionMessage() {
        return "Invalid date format. Please use YYYY-MM-DD";
    }

    public static String writeToFileMessage(Result result) {
        if (result.equals(Result.SUCCESS)) {
            return "Wrote to file";
        } else {
            return "Failed to write to file.";
        }
    }
}
