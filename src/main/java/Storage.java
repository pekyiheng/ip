package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String filePath;
    static ArrayList<Task> inputs = new ArrayList<>(100);
    public int count = 0;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<Task> load() {
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
        } finally {
            return inputs;
        }
    }

    private void readFileContents(String filePath) throws FileNotFoundException {
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
                    LocalDate deadlineDate = LocalDate.parse(values[3], DATE_TIME_FORMATTER);
                    Deadline newDeadline = new Deadline(values[2], deadlineDate);
                    if (values[1].equals("1")) {
                        newDeadline.markAsDone();
                    }
                    inputs.add(newDeadline);
                    break;
                case "E":
                    LocalDate fromDate = LocalDate.parse(values[3], DATE_TIME_FORMATTER);
                    LocalDate toDate = LocalDate.parse(values[4], DATE_TIME_FORMATTER);
                    Event newEvent = new Event(values[2], fromDate, toDate);
                    if (values[1].equals("1")) {
                        newEvent.markAsDone();
                    }
                    inputs.add(newEvent);
                    break;
            }
        }
    }

    public void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }
}
