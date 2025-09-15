package hamlet.task;

import hamlet.enums.Command;
import hamlet.exception.HamletException;
import hamlet.parser.Parser;
import hamlet.ui.Ui;
import hamlet.utils.TaskComparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages a list of tasks, including adding, deleting, and marking tasks as done.
 * <p>
 *     This class encapsulates the logic for managing tasks within the Hamlet application.
 * </p>
 */
public class TaskList {
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private ArrayList<Task> taskList = new ArrayList<>(100);
    private int taskCount = 0;
    private File taskFile;

    /**
     * Constructs a TaskList object, loading tasks from a specified file.
     * <p>
     *     If file exists, reads the contents and populates the private task list.
     *     If file does not exist, prints an error message
     * </p>
     * @param taskFile The file object which contains the task data.
     */
    public TaskList(File taskFile) {
        this.taskFile = taskFile;
        try {
            readFileContents();
        } catch (FileNotFoundException err) {
            System.out.println("File not found");
        }
    }

    /**
     * Returns current number of tasks in the list
     *
     * @return The number of tasl in list
     */
    public int getCount() {
        return this.taskCount;
    }

    /**
     * Returns the ArrayList containing all the tasks.
     *
     * @return List of tasks
     */
    public ArrayList<Task> gettaskList() {
        return taskList;
    }

    /**
     * Marks a task as done and prints a mark as done statement
     *
     * @param indexToEdit A 0-indexed position of the task to mark as done
     */
    public String markTaskAsDone(int indexToEdit) {
        Task curTask = this.taskList.get(indexToEdit);
        curTask.markAsDone();
        return Ui.markTaskAsDone(curTask);
    }

    /**
     * Marks a task as undone and prints a mark as undone statement
     *
     * @param indexToEdit A 0-indexed position of the task to mark as undone
     */
    public String markTaskAsUndone(int indexToEdit) {
        Task curTask = this.taskList.get(indexToEdit);
        curTask.markAsUndone();
        return Ui.markTaskAsUndone(curTask);
    }

    /**
     * Deletes a task based on its 0-indexed position in ArrayList
     *
     * @param indexToEdit A 0-indexed position of the task to delete
     */
    public String deleteTask(int indexToEdit) {
        Task curTask = this.taskList.get(indexToEdit);
        this.taskList.remove(indexToEdit);
        this.taskCount--;
        return Ui.removeTask(curTask, this.taskCount);
    }

    /**
     * Adds a new task to the list based on the command type and user input
     *
     * @param commandType The type of task to add to list
     * @param taskInput The user input string for the task
     * @throws HamletException If the input for the task is invalid
     */
    public String addTask(Command commandType, String taskInput) throws HamletException {
        Task newTask = null;
        switch (commandType) {
        case TODO: {
            newTask = Parser.matchInputToDo(taskInput);
            break;
        }

        case DEADLINE: {
            newTask = Parser.matchInputDeadline(taskInput);
            break;
        }

        case EVENT: {
            newTask = Parser.matchInputEvent(taskInput);
            break;
        }
        }

        this.taskList.add(newTask);
        this.taskCount++;
        return Ui.addNewTask(newTask, this.taskCount);
    }

    /**
     * Reads the contents of the file and populates the task list
     *
     * @throws FileNotFoundException If specified file does not exist
     */
    private void readFileContents() throws FileNotFoundException {
        Scanner s = new Scanner(taskFile); // create a Scanner using the File as the source

        while (s.hasNext()) {
            String currentLine = s.nextLine();
            readLine(currentLine);
        }
    }

    /**
     * Reads the contents of the file and populates the task list
     *
     * @param currentLine The current line to be read and added to taskList
     */
    private void readLine(String currentLine) {
            String[] values = currentLine.split(",");
            this.taskCount++;
            switch (values[0]) {
            case "T":
                Todo newTodo = new Todo(values[2]);
                if (values[1].equals("1")) {
                    newTodo.markAsDone();
                }
                this.taskList.add(newTodo);
                break;
            case "D":
                LocalDate deadlineDate = LocalDate.parse(values[3], DATE_TIME_FORMATTER);
                Deadline newDeadline = new Deadline(values[2], deadlineDate);
                if (values[1].equals("1")) {
                    newDeadline.markAsDone();
                }
                this.taskList.add(newDeadline);
                break;
            case "E":
                LocalDate fromDate = LocalDate.parse(values[3], DATE_TIME_FORMATTER);
                LocalDate toDate = LocalDate.parse(values[4], DATE_TIME_FORMATTER);
                Event newEvent = new Event(values[2], fromDate, toDate);
                if (values[1].equals("1")) {
                    newEvent.markAsDone();
                }
                this.taskList.add(newEvent);
                break;
            }
    }

    /**
     * Sorts the task list first by chronological order then by alphabetical of description
     */
    public void sortTaskList() {
        this.taskList.sort(new TaskComparator());
    }
}

