package hamlet.exception;

/**
 * Represents an exception specific to a Deadline object.
 * This exception is thrown when a user provides a DEADLINE command with an empty by date.
 */
public class DeadlineException extends HamletException {
    @Override
    public String toString() {
        return "The description or /by of a deadline cannot be empty.";
    }
}
