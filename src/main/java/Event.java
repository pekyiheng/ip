package main.java;

public class Event extends Task{
    protected String to;
    protected String from;

    public Event(String description, String from, String to) {
        super(description);
        this.to = to;
        this.from = from;
    }

    @Override
    public String getShorthand() {
        return "E";
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)%n", super.toString(), this.from, this.to);
    }
}
