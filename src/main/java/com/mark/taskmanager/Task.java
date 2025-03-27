package com.mark.taskmanager;
import java.io.Serializable;
import org.fusesource.jansi.Ansi;
import static org.fusesource.jansi.Ansi.ansi;


public class Task implements Serializable {

    private String taskName;
    private String dueDate;
    private boolean isCompleted;

    public Task(String taskName, String dueDate) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return "Task: " + taskName + " | Due: " + dueDate + " | Status: " +
                (isCompleted
                ? ansi().fg(Ansi.Color.GREEN).a("Completed").reset().toString()
                : ansi().fg(Ansi.Color.RED).a("Not Completed").reset().toString());
    }

}
