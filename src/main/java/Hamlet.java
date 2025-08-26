package main.java;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;

public class Hamlet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> inputs = new ArrayList<>(100);
        final String filePath = "./hamlet.txt";
        int count = 0;
        String lineBreaks = "____________________________________________________________";

        //handle file does not exist
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File exists!");
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
                                    newTask = new Deadline(deadlineTask, by);
                            /*
                            if (deadlineTask.trim().equals("")) {
                                newTask = null;
                                throw new DescException("deadline");
                            } else if (by.trim().equals("")) {
                                throw new HamletException();
                            }
                            else {
                                newTask = new Deadline(deadlineTask, by);
                            }
                             */
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
                                    newTask = new Event(eventTask, from, to);
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
                    case INVALID:
                        throw new HamletException();
                }
            } catch (HamletException err) {
                System.out.println(err);
            } finally {
                System.out.println(lineBreaks);
                input = scanner.nextLine();
            }
        }
        System.out.println(lineBreaks);
        System.out.println("Bye. Hope to see you again!\n");
        System.out.println(lineBreaks);
    }
}
