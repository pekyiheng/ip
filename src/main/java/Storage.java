package main.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Storage {
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public File load() {
        //handle file does not exist
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            }

        } catch (IOException e) {
            System.out.println("Cannot create file");
        }

        return file;
    }

    public void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }
}
