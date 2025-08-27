package main.java;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    static DateTimeFormatter dateTimeFormatterMMMDDYYYY = DateTimeFormatter.ofPattern("MMM dd yyyy");
    protected java.time.LocalDate by;


    public Deadline(String description, java.time.LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getShorthand() {
        return "D";
    }

    @Override
    public String toString() {
        String formattedDate = this.by.format(dateTimeFormatterMMMDDYYYY);
        return String.format("[D]%s (by: %s)%n", super.toString(), formattedDate);
    }
}
