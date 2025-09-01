package hamlet;

import hamlet.enums.Command;
import hamlet.enums.Result;
import hamlet.exception.HamletException;
import hamlet.parser.Parser;
import hamlet.storage.Storage;
import hamlet.task.TaskList;
import hamlet.ui.Ui;

import java.util.Scanner;
import java.io.IOException;
import java.time.format.DateTimeParseException;


public class Hamlet {
    final static String filePath = "./hamlet.txt";

    private Storage storage;
    private TaskList taskList;

    public Hamlet() {
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.load());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        Ui.welcomeMessage();

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            try {
                Ui.printLineBreak();
                Command commandType = Command.checkCommand(input);
                switch (commandType) {
                    case NAME:
                        Ui.showName();
                        break;

                    case LIST:
                        Ui.showTasks(taskList.getInputs(), taskList.getCount());
                        break;

                    case MARK: {
                        int indexToEdit = Parser.getIndexToEdit(input);
                        taskList.markTaskAsDone(indexToEdit);
                        break;
                    }

                    case UNMARK: {
                        int indexToEdit = Parser.getIndexToEdit(input);
                        taskList.markTaskAsUndone(indexToEdit);
                        break;
                    }

                    case TODO, DEADLINE, EVENT: {
                        taskList.addTask(commandType, input);
                        break;
                    }

                    case DELETE: {
                        int indexToEdit = Parser.getIndexToEdit(input);
                        taskList.deleteTask(indexToEdit);
                        break;
                    }
                    case HAPPENING: {
                        String[] resultFromMatchHappenings = Parser.matchHappenings(input, taskList.getInputs(), taskList.getCount());
                        Ui.showHappenings(resultFromMatchHappenings[0], resultFromMatchHappenings[1]);

                        break;
                    }
                    case INVALID:
                        throw new HamletException();
                }
            } catch (HamletException err) {
                Ui.showErrorMessage(err.toString());
            } catch (DateTimeParseException dateTimeParseException) {
                Ui.showDateTimeParseExceptionMessage();
            } finally {
                Ui.printLineBreak();
                input = scanner.nextLine();
            }
        }

        String textToSave = Parser.convertArrToString(taskList.getInputs());

        //writes and saves to file in csv format
        try {
            storage.writeToFile(textToSave);
            Ui.writeToFileMessage(Result.SUCCESS);

        } catch (IOException e) {
            Ui.writeToFileMessage(Result.FAILURE);
        }
        Ui.goodbyeMessage();
    }


    public static void main(String[] args) {
        new Hamlet().run();
    }

}
