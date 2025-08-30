package main.java.parser;

import main.java.exception.DeadlineException;
import main.java.exception.EventException;
import main.java.exception.TodoException;
import main.java.task.Deadline;
import main.java.task.Event;
import main.java.task.Task;
import main.java.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static int getIndexToEdit(String input) {
        return Integer.parseInt(input.replaceAll("[^\\d]", "")) - 1; //inputs is 0-indexed
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
