package hamlet.parser;

import hamlet.exception.DeadlineException;
import hamlet.exception.EventException;
import hamlet.exception.TodoException;
import hamlet.task.Deadline;
import hamlet.task.Event;
import hamlet.task.Task;
import hamlet.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input and converts it into a strucutred format that the program can handle
 * <p>
 *     This class provides static methods to interpret user commands, extract relevant information, and creates
 *     the correct Task objects
 * </p>
 */
public class Parser {
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     *Extracts and returns a 0-indexed integer from a user's input. This method is used to identify which task
     * to perform a task on.
     *
     * @param input The user's input containing a number
     * @return 0-indexed integer based on input
     */
    public static int getIndexToEdit(String input) {
        return Integer.parseInt(input.replaceAll("[^\\d]", "")) - 1; //inputs is 0-indexed
    }

    /** Matches user input to the todo command and creates a Todo task
     *
     * @param input the user's input command
     * @return A new Todo object
     * @throws TodoException If input does not match todo command format
     */
    public static Task matchInputToDo(String input) throws TodoException {
        Pattern pattern = Pattern.compile("todo (.+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String todoTask = matcher.group(1);
            return new Todo(todoTask);
        } else {
            throw new TodoException();
        }
    }

    /** Matches user input to the deadline command and creates a Deadline task
     *
     * @param input the user's input command
     * @return A new Deadline object
     * @throws DeadlineException If input does not match deadline command format
     */
    public static Task matchInputDeadline(String input) throws DeadlineException {
        Pattern pattern = Pattern.compile("deadline (.*) /by (.*)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String deadlineTask = matcher.group(1);
            String by = matcher.group(2);
            LocalDate deadlineDate = LocalDate.parse(by, DATE_TIME_FORMATTER);
            return new Deadline(deadlineTask, deadlineDate);
        } else {
            throw new DeadlineException();
        }
    }

    /** Matches user input to the event command and creates a Event task
     *
     * @param input the user's input command
     * @return A new Event object
     * @throws EventException If input does not match event command format
     */
    public static Task matchInputEvent(String input) throws EventException {
        Pattern pattern = Pattern.compile("event (.+) /from (.+) /to (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String eventTask = matcher.group(1);
            String from = matcher.group(2);
            String to = matcher.group(3);
            LocalDate fromDate = LocalDate.parse(from, DATE_TIME_FORMATTER);
            LocalDate toDate = LocalDate.parse(to, DATE_TIME_FORMATTER);
            return new Event(eventTask, fromDate, toDate);
        } else {
            throw new EventException();
        }
    }

    /** Matches user input to the happening command and finds deadlines and events that occur on a specific date
     *
     * @param input The user's input command
     * @param inputs The list of all tasks
     * @param count The current number of tasks in the list
     * @return An array of 2 stringsc containing a list of deadlines and events
     */
    public static String[] matchHappenings(String input, ArrayList<Task> inputs, int count) {
        Pattern pattern = Pattern.compile("happening /on (.+)");
        Matcher matcher = pattern.matcher(input);
        LocalDate dateToCheck;
        String[] returnArray = {"", ""};
        if (matcher.matches()) {
            dateToCheck = LocalDate.parse(matcher.group(1), DATE_TIME_FORMATTER);

            String deadlinesOnDate = "";
            String eventsOnDate = "";
            for (int i = 0; i < count; i++) {
                Task curTask = inputs.get(i);
                if (curTask instanceof Deadline && ((Deadline) curTask).getBy().equals(dateToCheck)) {
                    deadlinesOnDate += curTask.toString();
                } else if (curTask instanceof Event && ((Event) curTask).getFrom().equals(dateToCheck)) {
                    eventsOnDate += curTask.toString();
                }
            }

            returnArray[0] = deadlinesOnDate;
            returnArray[1] = eventsOnDate;

        }
        return returnArray;
    }

    /**
     * Converts an ArrayList of tasks into a single string separated by commas and newlines.
     *
     * @param inputs The ArrayList of tasks to convert
     * @return A string representation of all tasks to be stored in text file
     */
    public static String convertArrToString(ArrayList<Task> inputs) {
        String finalString = "";
        for (Task task : inputs) {
            finalString = finalString + task.getShorthand() + "," + task.isDone() + "," + task.getDescription();
            if (task instanceof Deadline) {
                finalString += "," + ((Deadline) task).getBy();
            } else if (task instanceof Event) {
                finalString += "," + ((Event) task).getFrom() + "," + ((Event) task).getTo();
            }

            finalString += "\n";
        }

        return finalString;
    }
}
