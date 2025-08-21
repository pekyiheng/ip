package main.java;

public class TodoException extends HamletException{

    @Override
    public String toString() {
        return "The description of a todo cannot be empty.";
    }
}
