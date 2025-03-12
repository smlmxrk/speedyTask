package com.mark.taskmanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<Task> tasks;
    private static final String TASKS_FILE = System.getProperty("user.home") + File.separator + "tasks.ser";


    public TaskManager() {
        this.tasks = loadTasks();
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public void editTask(int index, Task task) {
       int actualIndex = index - 1;
       if (actualIndex >= 0 && actualIndex < tasks.size()) {
           tasks.set(actualIndex, task);
           saveTasks();
           // System.out.println("Task at index " + index + " updated to: " + task);
       } else {
           System.out.println("Error: Invalid task index. No task updated.");
       }
    }

    public void deleteTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.remove(taskIndex);
            saveTasks();
        }
    }

    public void clearTasks() {
        tasks.clear();
        System.out.println("All tasks have been cleared!");
        saveTasks();
    }

    public void markTaskAsCompleted(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()+1) {
            tasks.get(taskIndex-1).markAsCompleted();
            saveTasks();
            System.out.println("Task " + taskIndex + " marked as completed.");
        } else if (taskIndex >= tasks.size()+1) {
            System.out.println("Error: Invalid task index. No task marked as completed.");
        }
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i).toString());
            }
        }
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public boolean containsTask(int taskIndex) {
        return taskIndex >= 0 && taskIndex < tasks.size();
    }

    // load tasks from file
    private List<Task> loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TASKS_FILE))) {
            return (List<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // if it doesn't exist, return an empty list
        }
    }

    // save tasks to file
    private void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TASKS_FILE))) {
            oos.writeObject(tasks);
        } catch (IOException e) { // handle error
            System.err.println("Error saving tasks.");
        }
    }
}
