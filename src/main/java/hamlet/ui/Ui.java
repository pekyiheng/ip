package hamlet.ui;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hamlet.enums.Result;
import hamlet.task.Task;

/**
 * Handles the formatting of messages shown to the user.
 * <p>
 * Provides static utility methods to display responses such as
 * greetings, errors, task lists, and status updates.
 * </p>
 */
public class Ui {
    /** Line separator used across messages for consistency. */
    private static final String LINE_BREAKS = "___________________________________________________";

    /**
     * Returns a standard line break string.
     *
     * @return line break string
     */
    public static String printLineBreak() {
        return LINE_BREAKS;
    }

    /**
     * Returns the welcome message.
     *
     * @return welcome message
     */
    public static String welcomeMessage() {
        return LINE_BREAKS
                + "Hello! I'm Hamlet\nHow may I help you?";
    }

    /**
     * Returns the goodbye message.
     *
     * @return goodbye message
     */
    public static String goodbyeMessage() {
        return LINE_BREAKS
                + "Bye. Hope to see you again!\n\n"
                + LINE_BREAKS;
    }

    /**
     * Returns Hamlet's name.
     *
     * @return Hamlet introduction
     */
    public static String showName() {
        return "\tI am Hamlet!";
    }

    /**
     * Formats and shows the list of tasks.
     *
     * @param inputs the list of tasks
     * @param count the number of tasks
     * @return formatted string of tasks
     */
    public static String showTasks(ArrayList<Task> inputs, int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> String.format("\t%d.%s", i + 1, inputs.get(i)))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Returns a message indicating a task has been marked as done.
     *
     * @param curTask the task marked as done
     * @return formatted success message
     */
    public static String markTaskAsDone(Task curTask) {
        return "Nice! I've marked this task as done:\n" + curTask;
    }

    /**
     * Returns a message indicating a task has been marked as undone.
     *
     * @param curTask the task marked as undone
     * @return formatted success message
     */
    public static String markTaskAsUndone(Task curTask) {
        return "Ok, I've marked this task as not done yet:\n" + curTask;
    }

    /**
     * Returns a message confirming a new task has been added.
     *
     * @param newTask the task added
     * @param count the new task count
     * @return formatted confirmation message
     */
    public static String addNewTask(Task newTask, int count) {
        return String.format(
                "Got it. I've added this task:\n%s\nNow you have %d tasks in the list.\n",
                newTask, count
        );
    }

    /**
     * Returns a message confirming a task has been removed.
     *
     * @param taskToRemove the task removed
     * @param count the new task count
     * @return formatted confirmation message
     */
    public static String removeTask(Task taskToRemove, int count) {
        return String.format(
                "Noted. I've removed this task:\n%s\nNow you have %d tasks in the list.\n",
                taskToRemove, count
        );
    }

    /**
     * Returns a formatted message of deadlines and events happening on a given date.
     *
     * @param deadlinesOnDate deadlines occurring on the date
     * @param eventsOnDate events occurring on the date
     * @return formatted happenings message
     */
    public static String showHappenings(String deadlinesOnDate, String eventsOnDate) {
        return "Deadline:\n" + deadlinesOnDate
                + "\nEvents:\n" + eventsOnDate;
    }

    /**
     * Returns a formatted message of tasks found.
     *
     * @param resultFromMatchFind formatted list of found tasks
     * @return search result message
     */
    public static String showFinds(String resultFromMatchFind) {
        return LINE_BREAKS
                + "Here are the matching tasks in your list:\n"
                + resultFromMatchFind + "\n"
                + LINE_BREAKS;
    }

    /**
     * Returns an error message.
     *
     * @param message error message text
     * @return formatted error message
     */
    public static String showErrorMessage(String message) {
        return message;
    }

    /**
     * Returns a message for invalid date formats.
     *
     * @return error message about date format
     */
    public static String showDateTimeParseExceptionMessage() {
        return "Invalid date format. Please use YYYY-MM-DD";
    }

    /**
     * Returns a message about whether writing to file succeeded.
     *
     * @param result the result of file write
     * @return formatted status message
     */
    public static String writeToFileMessage(Result result) {
        if (result.equals(Result.SUCCESS)) {
            return "Wrote to file";
        } else {
            return "Failed to write to file.";
        }
    }
}
