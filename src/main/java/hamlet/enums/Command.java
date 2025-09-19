package hamlet.enums;

/**
 * Represents the different types of commands supported by Hamlet.
 * <p>
 * Each constant corresponds to a user command such as {@code TODO}, {@code DEADLINE},
 * {@code EVENT}, or control commands like {@code BYE} and {@code LIST}.
 * </p>
 */
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
    HAPPENING,
    FIND,
    SORT,
    SORTBYDONE;

    /**
     * Determines the {@link Command} type from a given user input string.
     *
     * @param input the raw user input
     * @return the corresponding {@link Command} if matched, otherwise {@code INVALID}
     */
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
        if (trimmedInput.startsWith("find")) {
            return FIND;
        }
        if (trimmedInput.equals("sort")) {
            return SORT;
        }
        if (trimmedInput.equals("sort done")) {
            return SORTBYDONE;
        }
        return INVALID;
    }
}
