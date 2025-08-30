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
        inputs = storage.load();
        count = storage.getCount();
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
                        int indexToEdit = Parser.getIndexToEdit(input);
                        Task curTask = inputs.get(indexToEdit);
                        curTask.markAsDone();
                        ui.markTaskAsDone(curTask);
                        break;
                    }

                    case UNMARK: {
                        int indexToEdit = Parser.getIndexToEdit(input);
                        Task curTask = inputs.get(indexToEdit);
                        curTask.markAsUndone();
                        ui.markTaskAsUndone(curTask);
                        break;
                    }

                    case TODO, DEADLINE, EVENT: {
                        switch (commandType) {
                            case TODO: {
                                newTask = Parser.matchInputToDo(input);
                                break;
                            }

                            case DEADLINE: {
                                newTask = Parser.matchInputDeadline(input);
                                break;
                            }

                            case EVENT: {
                                newTask = Parser.matchInputEvent(input);
                                break;
                            }
                        }
                        inputs.add(newTask);
                        count++;
                        ui.addNewTask(newTask, count);
                        break;
                    }


                    case DELETE: {
                        int indexToEdit = Parser.getIndexToEdit(input);
                        Task curTask = inputs.get(indexToEdit);
                        inputs.remove(indexToEdit);
                        count--;
                        ui.removeTask(curTask, count);
                        break;
                    }
                    case HAPPENING: {

                        String[] resultFromMatchHappenings = Parser.matchHappenings(input, inputs, count);
                        ui.showHappenings(resultFromMatchHappenings[0], resultFromMatchHappenings[1]);

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

        String textToSave = Parser.convertArrToString(inputs);

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

}
