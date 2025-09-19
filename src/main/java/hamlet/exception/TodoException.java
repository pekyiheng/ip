package hamlet.exception;

/**
 * Represents an exception specific to a Todo object.
 * This exception is thrown when a user provides a TODO command with an empty description.
 */
public class TodoException extends HamletException {

    @Override
    public String toString() {
        return "The description of a todo cannot be empty.";
    }
}
