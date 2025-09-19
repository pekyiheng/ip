package hamlet.exception;

/**
 * Represents an exception specific to the Hamlet application.
 * This exception is typically thrown when a user provides a command that is not recognized.
 */
public class HamletException extends Exception {

    @Override
    public String toString() {
        return "I'm sorry, this command is not recognized. Accepted commands:\n- what is your name\n- bye\n- list\n"
                + "- mark [index]\n- unmark [index]\n- todo [desc]\n- deadline [desc] /by [when]\n"
                + "- event [desc] /from [when] /to [when]\n- delete [index]\n- happening /on [when]";
    }
}
