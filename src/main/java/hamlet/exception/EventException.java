package hamlet.exception;

/**
 * Represents an exception specific to a Event object.
 * This exception is thrown when a user provides a EVENT command with an empty from or to date.
 */
public class EventException extends HamletException {
    @Override
    public String toString() {
        return "The description, /from, or /to of an event cannot be empty.";
    }
}
