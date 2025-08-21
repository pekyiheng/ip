package main.java;

public class EventException extends HamletException{
    @Override
    public String toString() {
        return "The description, /from, or /to of an event cannot be empty.";
    }
}
