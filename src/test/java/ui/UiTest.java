package ui;

import hamlet.enums.Result;
import hamlet.task.Task;
import hamlet.task.Todo;
import hamlet.ui.Ui;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UiTest {
    static final String LINE_BREAKS = "____________________________________________________________";

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture the output
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        // Restore original System.out
        System.setOut(originalOut);
    }

    @Test
    void welcomeMessage_printsCorrectMessage() {
        Ui.welcomeMessage();
        String expectedOutput = LINE_BREAKS + System.lineSeparator() +
                "Hello! I'm Hamlet\nHow may I help you?" + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void goodbyeMessage_printsCorrectMessage() {
        Ui.goodbyeMessage();
        String expectedOutput = LINE_BREAKS + System.lineSeparator() +
                "Bye. Hope to see you again!\n" + System.lineSeparator() +
                LINE_BREAKS + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void showTasks_printsCorrectTaskAndFormatting() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("buy groceries"));
        Ui.showTasks(tasks, tasks.size());

        String expectedOutput = "\t1.[T][ ] read book" + System.lineSeparator() +
        "\t2.[T][ ] buy groceries" + System.lineSeparator() ;
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void markTaskAsDone_printsCorrectMessage() {
        Task task = (Task) new Todo("finish homework");
        task.markAsDone();
        Ui.markTaskAsDone(task);

        String expectedOutput = "Nice! I've marked this task as done:\n" +
                "[T][X] finish homework" + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void removeTask_printsCorrectMessageAndCount() {
        Task task = new Todo("delete this");
        int count = 5;
        Ui.removeTask(task, count);

        String expectedOutput = " Noted. I've removed this task:\n" +
                "[T][ ] delete this" + System.lineSeparator() +
                "Now you have 5 tasks in the list.\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void showHappenings_printsCorrectOutput() {
        String deadlines = "D,0,return book,2025-10-26" + System.lineSeparator();
        String events = "E,0,project meeting,2025-10-26,2025-10-27" + System.lineSeparator();
        Ui.showHappenings(deadlines, events);

        String expectedOutput = "Deadline:\n" + deadlines + "\nEvents:\n" + events + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void writeToFileMessage_success_printsCorrectMessage() {
        Ui.writeToFileMessage(Result.SUCCESS);
        String expectedOutput = "Wrote to file" + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    void writeToFileMessage_failure_printsCorrectMessage() {
        Ui.writeToFileMessage(Result.FAILURE);
        String expectedOutput = "Failed to write to file." + System.lineSeparator();
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}