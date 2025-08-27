package main.java;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Hamlet {
    final static String filePath = "./hamlet.txt";
    static ArrayList<Task> inputs = new ArrayList<>(100);
    static int count = 0;
    static DateTimeFormatter dateTimeFormatterYYYYMMDD = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String lineBreaks = "____________________________________________________________";

        //handle file does not exist
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                readFileContents(filePath);
            }

        } catch (IOException e) {
            System.out.println("Cannot create file");
        }

        System.out.println(lineBreaks);
        System.out.println("Hello! I'm Hamlet\nHow may I help you?");
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            try {
                System.out.println(lineBreaks);
                Command commandType = Command.checkCommand(input);
                Task newTask = null;
                switch (commandType) {
                    case NAME:
                        System.out.println("\t" + "I am Hamlet!");
                        break;

                    case LIST:
                        for (int i = 0; i < count; i++) {
                            Task curTask = inputs.get(i);
                            System.out.printf("\t%d.%s", i+1, curTask);
                        }
                        break;

                    case MARK: {
                        int indexToEdit = Integer.parseInt(input.replaceAll("[^\\d]", "")) - 1; //inputs is 0-indexed
                        Task curTask = inputs.get(indexToEdit);
                        curTask.markAsDone();
                        System.out.print("Nice! I've marked this task as done:\n");
                        System.out.print(curTask);
                        break;
                    }

                    case UNMARK: {
                        int indexToEdit = Integer.parseInt(input.replaceAll("[^\\d]", "")) - 1; //inputs is 0-indexed
                        Task curTask = inputs.get(indexToEdit);
                        curTask.markAsUndone();
                        System.out.print("Ok, I've marked this task as not done yet:\n");
                        System.out.print(curTask);
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
                        System.out.print("Got it. I've added this task:\n");
                        System.out.print(newTask);
                        System.out.printf("Now you have %d tasks in the list.\n", count);
                        break;
                    }


                    case DELETE: {
                        int indexToEdit = Integer.parseInt(input.replaceAll("[^\\d]", "")) - 1; //inputs is 0-indexed
                        Task curTask = inputs.get(indexToEdit);
                        inputs.remove(indexToEdit);
                        count--;
                        System.out.print(" Noted. I've removed this task:\n");
                        System.out.print(curTask);
                        System.out.printf("Now you have %d tasks in the list.\n", count);
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

                            System.out.println("Deadline:\n" + deadlinesOnDate + "\nEvents:\n" + eventsOnDate);
                        }
                        break;
                    }
                    case INVALID:
                        throw new HamletException();
                }
            } catch (HamletException err) {
                System.out.println(err);
            } catch (DateTimeParseException dateTimeParseException) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD");
            } finally {
                System.out.println(lineBreaks);
                input = scanner.nextLine();
            }
        }

        String textToSave = convertArrToString();

        //writes and saves to file in csv format
        try {
            writeToFile(filePath, textToSave);
            System.out.println("Wrote to file");
        } catch (IOException e) {
            System.out.println("Failed to write to file.");
        }


        System.out.println(lineBreaks);
        System.out.println("Bye. Hope to see you again!\n");
        System.out.println(lineBreaks);
    }

    private static void writeToFile(String filePath, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
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

    private static void readFileContents(String filePath) throws FileNotFoundException {
        File f = new File(filePath); // create a File for the given file path
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        while (s.hasNext()) {
            String curRow = s.nextLine();
            String[] values = curRow.split(",");
            count++;
            switch (values[0]) {
                case "T":
                    Todo newTodo = new Todo(values[2]);
                    if (values[1].equals("1")) {
                        newTodo.markAsDone();
                    }
                    inputs.add(newTodo);
                    break;
                case "D":
                    LocalDate deadlineDate = LocalDate.parse(values[3], dateTimeFormatterYYYYMMDD);
                    Deadline newDeadline = new Deadline(values[2], deadlineDate);
                    if (values[1].equals("1")) {
                        newDeadline.markAsDone();
                    }
                    inputs.add(newDeadline);
                    break;
                case "E":
                    LocalDate fromDate = LocalDate.parse(values[3], dateTimeFormatterYYYYMMDD);
                    LocalDate toDate = LocalDate.parse(values[4], dateTimeFormatterYYYYMMDD);
                    Event newEvent = new Event(values[2], fromDate, toDate);
                    if (values[1].equals("1")) {
                        newEvent.markAsDone();
                    }
                    inputs.add(newEvent);
                    break;
            }
        }
    }
}
