package hamlet.exception;

public class HamletException extends Exception{

    @Override
    public String toString() {
        return "I'm sorry, this command is not recognized. Accepted commands:\n- what is your name\n- bye\n- list\n" +
                "- mark [index]\n- unmark [index]\n- todo [desc]\n- deadline [desc] /by [when]\n" +
                "- event [desc] /from [when] /to [when]\n- delete [index]\n- happening /on [when]";
    }
}
