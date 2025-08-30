package main.java;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Hamlet {
    final static String filePath = "./hamlet.txt";

    static ArrayList<Task> inputs = new ArrayList<>(100);
    static int count = 0;
    static DateTimeFormatter dateTimeFormatterYYYYMMDD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Storage storage;
    private Ui ui;

    public Hamlet() {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        ui.welcomeMessage();

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            try {
                ui.printLineBreak();
                Command commandType = Command.checkCommand(input);
                Task newTask = null;
                switch (commandType) {
                    case NAME:
                        ui.showName();
                        break;

                    case LIST:
                        ui.showTasks(inputs, count);
                        break;

                    case MARK: {
                        int indexToEdit = Integer.parseInt(input.replaceAll("[^\\d]", "")) - 1; //inputs is 0-indexed
                        Task curTask = inputs.get(indexToEdit);
                        curTask.markAsDone();
                        ui.markTaskAsDone(curTask);
                        break;
                    }

                    case UNMARK: {
                        int indexToEdit = Integer.parseInt(input.replaceAll("[^\\d]", "")) - 1; //inputs is 0-indexed
                        Task curTask = inputs.get(indexToEdit);
                        curTask.markAsUndone();
                        ui.markTaskAsUndone(curTask);
                        break;
                    }

                    case TODO, DEADLINE, EVENT: {
                        switch (commandType) {
                            case TODO: {
                                Pattern pattern = Pattern.compile("todo (.+)");
                                Matcher matcher = pattern.matcher(input);
                                if (matcher.matches()) {
                                    String todoTask = matcher.group(1);
                                    newTask = new Todo(todoTask);
                                } else {
                                    newTask = null;
                                    throw new TodoException();
                                }
                                break;
                            }

                            case DEADLINE: {
                                Pattern pattern = Pattern.compile("deadline (.*) /by (.*)");
                                Matcher matcher = pattern.matcher(input);

                                if (matcher.matches()) {
                                    String deadlineTask = matcher.group(1);
                                    String by = matcher.group(2);
                                    LocalDate deadlineDate = LocalDate.parse(by, dateTimeFormatterYYYYMMDD);
                                    newTask = new Deadline(deadlineTask, deadlineDate);
                                } else {
                                    throw new DeadlineException();
                                }
                                break;
                            }

                            case EVENT: {
                                Pattern pattern = Pattern.compile("event (.+) /from (.+) /to (.+)");
                                Matcher matcher = pattern.matcher(input);
                                if (matcher.matches()) {
                                    String eventTask = matcher.group(1);
                                    String from = matcher.group(2);
                                    String to = matcher.group(3);
                                    LocalDate fromDate = LocalDate.parse(from, dateTimeFormatterYYYYMMDD);
                                    LocalDate toDate = LocalDate.parse(to, dateTimeFormatterYYYYMMDD);
                                    newTask = new Event(eventTask, fromDate, toDate);
                                } else {
                                    newTask = null;
                                    throw new EventException();
                                }
                                break;
                            }
                        }
                        inputs.add(newTask);
                        count++;
                        ui.addNewTask(newTask, count);
                        break;
                    }


                    case DELETE: {
                        int indexToEdit = Integer.parseInt(input.replaceAll("[^\\d]", "")) - 1; //inputs is 0-indexed
                        Task curTask = inputs.get(indexToEdit);
                        inputs.remove(indexToEdit);
                        count--;
                        ui.removeTask(curTask, count);
                        break;
                    }
                    case HAPPENING: {
                        Pattern pattern = Pattern.compile("happening /on (.+)");
                        Matcher matcher = pattern.matcher(input);
                        LocalDate dateToCheck;
                        if (matcher.matches()) {
                            dateToCheck = LocalDate.parse(matcher.group(1), dateTimeFormatterYYYYMMDD);

                            String deadlinesOnDate = "";
                            String eventsOnDate = "";
                            for (int i = 0; i < count; i++) {
                                Task curTask = inputs.get(i);
                                if (curTask instanceof Deadline && ((Deadline) curTask).by.equals(dateToCheck)) {
                                    deadlinesOnDate += curTask.toString();
                                } else if (curTask instanceof Event && ((Event) curTask).from.equals(dateToCheck)) {
                                    eventsOnDate += curTask.toString();
                                }
                            }

                            ui.showHappenings(deadlinesOnDate, eventsOnDate);
                        }
                        break;
                    }
                    case INVALID:
                        throw new HamletException();
                }
            } catch (HamletException err) {
                ui.showErrorMessage(err.toString());
            } catch (DateTimeParseException dateTimeParseException) {
                ui.showDateTimeParseExceptionMessage();
            } finally {
                ui.printLineBreak();
                input = scanner.nextLine();
            }
        }

        String textToSave = convertArrToString();

        //writes and saves to file in csv format
        try {
            storage.writeToFile(textToSave);
            ui.writeToFileMessage(Result.SUCCESS);

        } catch (IOException e) {
            ui.writeToFileMessage(Result.FAILURE);
        }

        ui.goodbyeMessage();

    }


    public static void main(String[] args) {
        new Hamlet().run();
    }


    private static String convertArrToString() {
        String finalString = "";
        for (Task task : inputs) {
            finalString = finalString + task.getShorthand() + "," + task.isDone() + "," + task.description;
            if (task instanceof Deadline) {
                finalString += "," + ((Deadline) task).by;
            } else if (task instanceof Event) {
                finalString += "," + ((Event) task).from + "," + ((Event) task).to;
            }

            finalString += "\n";
        }

        return finalString;
    }

}
