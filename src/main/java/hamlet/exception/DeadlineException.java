package hamlet.exception;

public class DeadlineException extends HamletException {
    @Override
    public String toString() {
        return "The description or /by of a deadline cannot be empty.";
    }
}
