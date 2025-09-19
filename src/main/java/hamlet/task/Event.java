package hamlet.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a description, start date, and end date.
 * <p>
 * In Hamlet, events span over a period of time and are stored with both
 * a "from" and "to" date.
 * </p>
 */
public class Event extends Task {
    /** Formatter for displaying dates in "MMM dd yyyy" format. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate from;
    private final LocalDate to;

    /**
     * Constructs an Event with a description, start date, and end date.
     *
     * @param description the description of the event
     * @param from the start date of the event
     * @param to the end date of the event
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the end date of the event.
     *
     * @return the end date
     */
    public LocalDate getTo() {
        return this.to;
    }

    /**
     * Returns the start date of the event.
     *
     * @return the start date
     */
    public LocalDate getFrom() {
        return from;
    }

    @Override
    public String getShorthand() {
        return "E";
    }

    @Override
    public String toString() {
        String fromString = this.from.format(DATE_FORMATTER);
        String toString = this.to.format(DATE_FORMATTER);
        return String.format("[E]%s (from: %s to: %s)%n", super.toString(), fromString, toString);
    }
}
