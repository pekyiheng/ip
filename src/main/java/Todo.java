package main.java;

public class Todo extends Task{
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getShorthand() {
        return "T";
    }

    @Override
    public String toString() {
        return String.format("[T]%s%n", super.toString());

    }
}
