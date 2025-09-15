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
    private Command commandType;

    /**
     * Constructs a new Hamlet instance.
     * <p>
     *     Constructor initializes a Storage and TaskList component, which loads tasks saved in text form from
     *     specified file to be stored as a TaskList
     * </p>
     */
    public Hamlet() {
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.load());
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String userInput) {
        assert this.taskList != null: "taskList should be initialized";
        assert this.storage != null: "storage should be initialized";

        try {
            commandType = Command.checkCommand(userInput);
            switch (commandType) {
            case NAME:
                return Ui.showName();

            case LIST:
                return Ui.showTasks(taskList.gettaskList(), taskList.getCount());

            case MARK: {
                int indexToEdit = Parser.getIndexToEdit(userInput);
                return taskList.markTaskAsDone(indexToEdit);
            }

            case UNMARK: {
                int indexToEdit = Parser.getIndexToEdit(userInput);
                return taskList.markTaskAsUndone(indexToEdit);
            }

            case TODO, DEADLINE, EVENT: {
                return taskList.addTask(commandType, userInput);
            }

            case DELETE: {
                int indexToEdit = Parser.getIndexToEdit(userInput);
                return taskList.deleteTask(indexToEdit);
            }
            case HAPPENING: {
                String[] resultFromMatchHappenings = Parser.matchHappenings(userInput, taskList.gettaskList(), taskList.getCount());
                return Ui.showHappenings(resultFromMatchHappenings[0], resultFromMatchHappenings[1]);
            }
            case FIND:
                String resultFromMatchFind = Parser.matchFind(userInput, taskList.gettaskList(), taskList.getCount());
                return Ui.showFinds(resultFromMatchFind);
            case SORT:
                this.taskList.sortTaskList();
                return Ui.showTasks(taskList.gettaskList(), taskList.getCount());
            case SORTBYDONE:
                this.taskList.sortTaskListByDone();
                return Ui.showTasks(taskList.gettaskList(), taskList.getCount());
            case BYE:
                String textToSave = Parser.convertArrToString(taskList.gettaskList());
                StringBuilder returnString = new StringBuilder();

                //writes and saves to file in csv format
                try {
                    storage.writeToFile(textToSave);
                    returnString.append(Ui.writeToFileMessage(Result.SUCCESS)).append("\n");

                } catch (IOException e) {
                    Ui.writeToFileMessage(Result.FAILURE);
                    returnString.append(Ui.writeToFileMessage(Result.FAILURE)).append("\n");
                }
                returnString.append(Ui.goodbyeMessage());
                return returnString.toString();
            case INVALID:
                throw new HamletException();
            }

            assert false: "The code should return a response or throw an error that is handled by the try-catch block";

        } catch (HamletException err) {
            return Ui.showErrorMessage(err.toString());
        } catch (DateTimeParseException dateTimeParseException) {
            return Ui.showDateTimeParseExceptionMessage();
        }
        return Ui.printLineBreak();

    }

    public Command getCommandType() {
        return commandType;
    }

    /*
    /**
     * The main execution loop of application
     * <p>
     *     This method displays the welcome message, then enters a loop that continuously reads the user input, process
     *     commands, and executes the corresponding tasks.
     *     The loop terminates when user enters "bye" command.
     * </p>
     * @throws IOException If an I/O error occurs while writing to file
     * @throws DateTimeParseException If date string provided by user cannot be parsed properly
     * @throws HamletException If an invalid command or format is provided.
     */

    /*
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Ui.welcomeMessage();

        String userInput = scanner.nextLine();

        while (!userInput.equals("bye")) {
            try {
                Ui.printLineBreak();
                Command commandType = Command.checkCommand(userInput);
                switch (commandType) {
                case NAME:
                    Ui.showName();
                    break;

                case LIST:
                    Ui.showTasks(taskList.gettaskList(), taskList.getCount());
                    break;

                case MARK: {
                    int indexToEdit = Parser.getIndexToEdit(userInput);
                    taskList.markTaskAsDone(indexToEdit);
                    break;
                }

                case UNMARK: {
                    int indexToEdit = Parser.getIndexToEdit(userInput);
                    taskList.markTaskAsUndone(indexToEdit);
                    break;
                }

                case TODO, DEADLINE, EVENT: {
                    taskList.addTask(commandType, userInput);
                    break;
                }

                case DELETE: {
                    int indexToEdit = Parser.getIndexToEdit(userInput);
                    taskList.deleteTask(indexToEdit);
                    break;
                }
                case HAPPENING: {
                    String[] resultFromMatchHappenings = Parser.matchHappenings(userInput, taskList.gettaskList(), taskList.getCount());
                    Ui.showHappenings(resultFromMatchHappenings[0], resultFromMatchHappenings[1]);

                    break;
                }
                case FIND:
                    String resultFromMatchFind = Parser.matchFind(userInput, taskList.gettaskList(), taskList.getCount());
                    Ui.showFinds(resultFromMatchFind);
                    break;
                case INVALID:
                    throw new HamletException();
                }
            } catch (HamletException err) {
                Ui.showErrorMessage(err.toString());
            } catch (DateTimeParseException dateTimeParseException) {
                Ui.showDateTimeParseExceptionMessage();
            } finally {
                Ui.printLineBreak();
                userInput = scanner.nextLine();
            }
        }

        String textToSave = Parser.convertArrToString(taskList.gettaskList());

        //writes and saves to file in csv format
        try {
            storage.writeToFile(textToSave);
            Ui.writeToFileMessage(Result.SUCCESS);

        } catch (IOException e) {
            Ui.writeToFileMessage(Result.FAILURE);
        }
        Ui.goodbyeMessage();
    }
     */


    /**
     * THe main entry point of application
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        //new Hamlet().run();
    }

}
