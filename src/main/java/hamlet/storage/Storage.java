package hamlet.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles the loading and writing of data into a text file.
 * <p>
 * This class provides methods to ensure a file exists, load its content,
 * and write new content to it. It uses a specified file path for all its operations.
 * </p>
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath the path of the file to store task data
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the text file as specified in the constructor.
     * <p>
     * If file does not exist, creates a new file.
     * </p>
     *
     * @return File object representing the loaded file
     */
    public File load() {
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

    /**
     * Writes specified text to the file, overwriting any existing text.
     *
     * @param textToAdd the string content in CSV format to be written to the file
     * @throws IOException if an error occurs while writing to file
     */
    public void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }
}
