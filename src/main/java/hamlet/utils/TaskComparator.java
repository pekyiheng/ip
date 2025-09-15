package hamlet.utils;

import hamlet.task.Event;
import hamlet.task.Deadline;
import hamlet.task.Task;

import java.time.LocalDate;
import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        LocalDate date1 = null;
        if (task1 instanceof Event) {
            date1 = ((Event) task1).getFrom();
        } else if (task1 instanceof Deadline) {
            date1 = ((Deadline) task1).getBy();
        }

        LocalDate date2 = null;
        if (task2 instanceof Event) {
            date2 = ((Event) task2).getFrom();
        } else if (task2 instanceof Deadline) {
            date2 = ((Deadline) task2).getBy();
        }

        if (date1 != null && date2 != null) {
            int dateCompare = date1.compareTo(date2);
            if (dateCompare != 0) {
                return dateCompare;
            }
        }

        if (date1 != null && date2 == null) {
            return 1;
        } else if (date1 == null && date2 != null) {
            return -1;
        }

        return task1.getDescription().compareTo(task2.getDescription());
    }
}
