package hamlet.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a description and a due date.
 * <p>
 * In Hamlet, deadlines are tasks that must be completed by a specific date.
 * </p>
 */
public class Deadline extends Task {
    /** Formatter for displaying dates in "MMM dd yyyy" format. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate by;

    /**
     * Constructs a Deadline task with the given description and due date.
     *
     * @param description the description of the task
     * @param by the due date of the task
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the due date of this deadline.
     *
     * @return the due date
     */
    public LocalDate getBy() {
        return this.by;
    }

    @Override
    public String getShorthand() {
        return "D";
    }

    @Override
    public String toString() {
        String formattedDate = this.by.format(DATE_FORMATTER);
        return String.format("[D]%s (by: %s)%n", super.toString(), formattedDate);
    }
}
