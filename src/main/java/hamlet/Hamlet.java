package hamlet;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import hamlet.enums.Command;
import hamlet.enums.Result;
import hamlet.exception.HamletException;
import hamlet.parser.Parser;
import hamlet.storage.Storage;
import hamlet.task.TaskList;
import hamlet.ui.Ui;


/**
 * Main application class for Hamlet task manager.
 */
public class Hamlet {
    /** File path where tasks are stored. */
    private static final String FILE_PATH = "./hamlet.txt";

    private final Storage storage;
    private final TaskList taskList;
    private Command commandType;

    /**
     * Constructs a new Hamlet instance.
     * <p>
     *     Constructor initializes a Storage and TaskList component,
     *     which loads tasks saved in text form from the specified file
     *     to be stored as a TaskList.
     * </p>
     */
    public Hamlet() {
        this.storage = new Storage(FILE_PATH);
        this.taskList = new TaskList(storage.load());
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param userInput user command as string
     * @return formatted response string
     */
    public String getResponse(String userInput) {
        assert this.taskList != null : "taskList should be initialized";
        assert this.storage != null : "storage should be initialized";

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

            case TODO:
            case DEADLINE:
            case EVENT:
                return taskList.addTask(commandType, userInput);

            case DELETE: {
                int indexToEdit = Parser.getIndexToEdit(userInput);
                return taskList.deleteTask(indexToEdit);
            }

            case HAPPENING: {
                String[] resultFromMatchHappenings =
                        Parser.matchHappenings(userInput, taskList.gettaskList(), taskList.getCount());
                return Ui.showHappenings(resultFromMatchHappenings[0], resultFromMatchHappenings[1]);
            }

            case FIND: {
                String resultFromMatchFind =
                        Parser.matchFind(userInput, taskList.gettaskList(), taskList.getCount());
                return Ui.showFinds(resultFromMatchFind);
            }

            case SORT:
                this.taskList.sortTaskList();
                return Ui.showTasks(taskList.gettaskList(), taskList.getCount());

            case SORTBYDONE:
                this.taskList.sortTaskListByDone();
                return Ui.showTasks(taskList.gettaskList(), taskList.getCount());

            case BYE: {
                String textToSave = Parser.convertArrToString(taskList.gettaskList());
                StringBuilder returnString = new StringBuilder();

                // Writes and saves to file in csv format
                try {
                    storage.writeToFile(textToSave);
                    returnString.append(Ui.writeToFileMessage(Result.SUCCESS)).append("\n");
                } catch (IOException e) {
                    returnString.append(Ui.writeToFileMessage(Result.FAILURE)).append("\n");
                }
                returnString.append(Ui.goodbyeMessage());
                return returnString.toString();
            }

            case INVALID:
                throw new HamletException();

            default:
                // Required by Checkstyle: default branch in switch
                throw new HamletException();
            }

        } catch (HamletException err) {
            return Ui.showErrorMessage(err.toString());
        } catch (DateTimeParseException dateTimeParseException) {
            return Ui.showDateTimeParseExceptionMessage();
        }
    }

    public Command getCommandType() {
        return commandType;
    }

    /**
     * The main entry point of the application.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // new Hamlet().run();
    }
}
