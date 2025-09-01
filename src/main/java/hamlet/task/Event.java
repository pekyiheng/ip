package hamlet.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    static DateTimeFormatter dateTimeFormatterMMMDDYYYY = DateTimeFormatter.ofPattern("MMM dd yyyy");

    protected LocalDate to;
    protected LocalDate from;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.to = to;
        this.from = from;
    }

    public LocalDate getTo() {
        return this.to;
    }

    public LocalDate getFrom() {
        return from;
    }

    @Override
    public String getShorthand() {
        return "E";
    }

    @Override
    public String toString() {
        String fromString = this.from.format(dateTimeFormatterMMMDDYYYY);
        String toString = this.to.format(dateTimeFormatterMMMDDYYYY);
        return String.format("[E]%s (from: %s to: %s)%n", super.toString(), fromString, toString);
    }
}
