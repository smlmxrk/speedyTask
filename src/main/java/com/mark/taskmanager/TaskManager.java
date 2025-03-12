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

    public void deleteTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.remove(taskIndex);
            saveTasks();
        }
    }

    public void clearTasks() {
        tasks.clear();
        System.out.println("All tasks have been cleared!");
    }

    public void markTaskAsCompleted(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.get(taskIndex).markAsCompleted();
            saveTasks();
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
