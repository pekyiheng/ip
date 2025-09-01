package hamlet.task;

import hamlet.enums.Command;
import hamlet.exception.HamletException;
import hamlet.parser.Parser;
import hamlet.ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskList {
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private ArrayList<Task> inputs = new ArrayList<>(100);
    private int count = 0;
    private File file;

    public TaskList(File file) {
        this.file = file;
        try {
            readFileContents();
        } catch (FileNotFoundException err) {
            System.out.println("File not found");
        }
    }

    public int getCount() {
        return this.count;
    }

    public ArrayList<Task> getInputs() {
        return inputs;
    }

    public void markTaskAsDone(int indexToEdit) {
        Task curTask = this.inputs.get(indexToEdit);
        curTask.markAsDone();
        Ui.markTaskAsDone(curTask);
    }

    public void markTaskAsUndone(int indexToEdit) {
        Task curTask = this.inputs.get(indexToEdit);
        curTask.markAsUndone();
        Ui.markTaskAsUndone(curTask);
    }

    public void deleteTask(int indexToEdit) {
        Task curTask = this.inputs.get(indexToEdit);
        this.inputs.remove(indexToEdit);
        this.count--;
        Ui.removeTask(curTask, this.count);
    }

    public void addTask(Command commandType, String input) throws HamletException {
        Task newTask = null;
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

        this.inputs.add(newTask);
        this.count++;
        Ui.addNewTask(newTask, this.count);
    }

    private void readFileContents() throws FileNotFoundException {
        Scanner s = new Scanner(file); // create a Scanner using the File as the source
        while (s.hasNext()) {
            String curRow = s.nextLine();
            String[] values = curRow.split(",");
            this.count++;
            switch (values[0]) {
                case "T":
                    Todo newTodo = new Todo(values[2]);
                    if (values[1].equals("1")) {
                        newTodo.markAsDone();
                    }
                    this.inputs.add(newTodo);
                    break;
                case "D":
                    LocalDate deadlineDate = LocalDate.parse(values[3], DATE_TIME_FORMATTER);
                    Deadline newDeadline = new Deadline(values[2], deadlineDate);
                    if (values[1].equals("1")) {
                        newDeadline.markAsDone();
                    }
                    this.inputs.add(newDeadline);
                    break;
                case "E":
                    LocalDate fromDate = LocalDate.parse(values[3], DATE_TIME_FORMATTER);
                    LocalDate toDate = LocalDate.parse(values[4], DATE_TIME_FORMATTER);
                    Event newEvent = new Event(values[2], fromDate, toDate);
                    if (values[1].equals("1")) {
                        newEvent.markAsDone();
                    }
                    this.inputs.add(newEvent);
                    break;
            }
        }
    }
}
