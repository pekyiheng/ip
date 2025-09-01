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

public class Parser {
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static int getIndexToEdit(String input) {
        return Integer.parseInt(input.replaceAll("\\D", "")) - 1; //inputs is 0-indexed
    }

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

    public static String[] matchHappenings(String input, ArrayList<Task> inputs, int count) {
        Pattern pattern = Pattern.compile("happening /on (.+)");
        Matcher matcher = pattern.matcher(input);
        LocalDate dateToCheck;
        String[] returnArray = {"", ""};
        if (matcher.matches()) {
            dateToCheck = LocalDate.parse(matcher.group(1), DATE_TIME_FORMATTER);
            StringBuilder deadlinesOnDateSb = new StringBuilder();
            StringBuilder eventsOnDateSb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                Task curTask = inputs.get(i);
                if (curTask instanceof Deadline && ((Deadline) curTask).getBy().equals(dateToCheck)) {
                    deadlinesOnDateSb.append(curTask.toString());
                } else if (curTask instanceof Event && ((Event) curTask).getFrom().equals(dateToCheck)) {
                    eventsOnDateSb.append(curTask.toString());
                }
            }
            returnArray[0] = deadlinesOnDateSb.toString();
            returnArray[1] = eventsOnDateSb.toString();
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
        StringBuilder sb = new StringBuilder();
        for (Task task : inputs) {
            sb.append(task.getShorthand()).append(",").append(task.isDone()).append(",").append(task.getDescription());
            if (task instanceof Deadline) {
                sb.append(",").append(((Deadline) task).getBy());
            } else if (task instanceof Event) {
                sb.append(",").append(((Event) task).getFrom()).append(",").append(((Event) task).getTo());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Find tasks containing a specific keyword in its description
     *
     * @param input The user's input string
     * @param inputs The list of all tasks
     * @param count The current number of tasks in list
     * @return A formatted string containing all the tasks that contains keyword
     */
    public static String matchFind(String input, ArrayList<Task> inputs, int count) {
        Pattern pattern = Pattern.compile("find (\\w+)");
        Matcher matcher = pattern.matcher(input);

        int findCount = 1;
        StringBuilder returnStringSb = new StringBuilder();
        if (matcher.matches()) {
            String keyword = matcher.group(1);
            for (int i = 0; i < count; i++) {
                Task curTask = inputs.get(i);
                if (curTask.getDescription().contains(keyword)) {
                    returnStringSb.append(findCount).append(". ").append(curTask.toString());
                    findCount++;
                }
            }
        }
        return returnStringSb.toString();
    }
}
