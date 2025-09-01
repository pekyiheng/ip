package hamlet.enums;

public enum Command {
    NAME,
    BYE,
    LIST,
    MARK,
    UNMARK,
    DELETE,
    TODO,
    DEADLINE,
    EVENT,
    INVALID,
    HAPPENING;

    public static Command checkCommand(String input) {
        String trimmedInput = input.trim();
        if (trimmedInput.equals("what is your name")) {
            return NAME;
        }
        if (trimmedInput.equals("bye")) {
            return BYE;
        }
        if (trimmedInput.equals("list")) {
            return LIST;
        }
        if (trimmedInput.matches("mark \\d+")) {
            return MARK;
        }
        if (trimmedInput.matches("unmark \\d+")) {
            return UNMARK;
        }
        if (trimmedInput.matches("delete \\d+")) {
            return DELETE;
        }
        if (trimmedInput.startsWith("todo")) {
            return TODO;
        }
        if (trimmedInput.startsWith("deadline")) {
            return DEADLINE;
        }
        if (trimmedInput.startsWith("event")) {
            return EVENT;
        }
        if (trimmedInput.startsWith("happening")) {
            return HAPPENING;
        }
        return INVALID;
    }
}
