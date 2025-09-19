package hamlet.utils;

import java.util.Comparator;

import hamlet.task.Task;

/**
 * A comparator that sorts Task objects based on their completion status.
 * <p>
 * Unfinished tasks (isDone() == false) are considered "less than" finished tasks
 * (isDone() == true), causing them to be sorted first.
 */
public class TaskStatusComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        return Integer.compare(task1.isDone(), task2.isDone());
    }
}
