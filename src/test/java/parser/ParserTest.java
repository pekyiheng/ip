package parser;

import hamlet.exception.DeadlineException;
import hamlet.exception.EventException;
import hamlet.exception.TodoException;
import hamlet.parser.Parser;
import hamlet.task.Deadline;
import hamlet.task.Event;
import hamlet.task.Task;
import hamlet.task.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private ArrayList<Task> taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
        taskList.add(new Todo("read book"));
        taskList.add(new Deadline("return book", LocalDate.of(2025, 10, 26)));
        taskList.add(new Event("project meeting", LocalDate.of(2025, 10, 26), LocalDate.of(2025, 10, 27)));
    }

    @Test
    void getIndexToEdit_validInput_returnsCorrectIndex() {
        assertEquals(0, Parser.getIndexToEdit("mark 1"));
        assertEquals(1, Parser.getIndexToEdit("delete 2"));
    }

    @Test
    void matchInputToDo_validInput_returnsTodoObject() throws TodoException {
        Task todo = Parser.matchInputToDo("todo write code");
        assertTrue(todo instanceof Todo);
        assertEquals("write code", todo.getDescription());
    }

    @Test
    void matchInputToDo_invalidInput_throwsTodoException() {
        assertThrows(TodoException.class, () -> Parser.matchInputToDo("todo"));
    }

    @Test
    void matchInputDeadline_validInput_returnsDeadlineObject() throws DeadlineException {
        Task deadline = Parser.matchInputDeadline("deadline project report /by 2025-11-01");
        assertTrue(deadline instanceof Deadline);
        assertEquals("project report", deadline.getDescription());
        assertEquals(LocalDate.of(2025, 11, 1), ((Deadline) deadline).getBy());
    }

    @Test
    void matchInputDeadline_invalidInput_throwsDeadlineException() {
        assertThrows(DeadlineException.class, () -> Parser.matchInputDeadline("deadline project report"));
    }

    @Test
    void matchInputEvent_validInput_returnsEventObject() throws EventException {
        Task event = Parser.matchInputEvent("event company dinner /from 2025-12-25 /to 2025-12-26");
        assertTrue(event instanceof Event);
        assertEquals("company dinner", event.getDescription());
        assertEquals(LocalDate.of(2025, 12, 25), ((Event) event).getFrom());
        assertEquals(LocalDate.of(2025, 12, 26), ((Event) event).getTo());
    }

    @Test
    void matchInputEvent_invalidInput_throwsEventException() {
        assertThrows(EventException.class, () -> Parser.matchInputEvent("event company dinner /from 2025-12-25"));
    }

    @Test
    void matchHappenings_validInputWithMatches_returnsCorrectArrays() {
        String[] result = Parser.matchHappenings("happening /on 2025-10-26", taskList, taskList.size());
        assertNotNull(result);
        assertTrue(result[0].contains("return book"));
        assertTrue(result[1].contains("project meeting"));
    }

    @Test
    void matchHappenings_noMatches_returnsEmptyArrays() {
        String[] result = Parser.matchHappenings("happening /on 2025-01-01", taskList, taskList.size());
        assertNotNull(result);
        assertEquals("", result[0]);
        assertEquals("", result[1]);
    }

    @Test
    void convertArrToString_validInput_returnsCorrectString() {
        String expected = "T,0,read book\nD,0,return book,2025-10-26\nE,0,project meeting,2025-10-26,2025-10-27\n";
        String actual = Parser.convertArrToString(taskList);
        assertEquals(expected, actual);
    }

    @Test
    void matchFind_validInput_returnsCorrectString() {
        String expected = "1. [T][ ] read book" + System.lineSeparator() + "2. [D][ ] return book (by: Oct 26 2025)" + System.lineSeparator();
        String actual = Parser.matchFind("find book", taskList, 3);
        assertEquals(expected, actual);
    }

    @Test
    void matchFind_noMatch_returnsEmptyString() {
        String expected = "";
        String actual = Parser.matchFind("find non existent", taskList, 3);
        assertEquals(expected, actual);
    }
}
