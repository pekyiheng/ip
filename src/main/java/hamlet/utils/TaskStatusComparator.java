package hamlet.utils;

import hamlet.task.Task;

import java.util.Comparator;

public class TaskStatusComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        return Integer.compare(task1.isDone(), task2.isDone());
    }
}
