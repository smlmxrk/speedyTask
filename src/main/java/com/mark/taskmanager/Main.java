package com.mark.taskmanager;
import javax.management.monitor.StringMonitor;
import java.util.Scanner;

public class Main {
    private static final TaskManager taskManager = new TaskManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Task Manager!\n");
        showHelp();

        while (true) {
            System.out.print("\n Enter command: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("Exiting...");
                break;
            }

            parseCommand(input);
        }
    }

    private static void parseCommand(String command) {
        String[] parts = command.split(" ", 2);

        switch (parts[0]) {
            case "fetch":
                fetchTasks();
                break;

            case "add":
                addTask(parts);
                break;

            case "complete":
                completeTask(parts);
                break;

            case "delete":
                deleteTask(parts);
                break;

            case "help":
                showHelp();
                break;

            default:
                System.out.println("Invalid command. Type 'help' for available commands.");
                break;
        }
    }

    private static void fetchTasks() {
        taskManager.listTasks();
    }
    
    private static void addTask(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: add <taskName> <dueDate>");
            return;
        }

        int lastSpaceIndex = parts[1].lastIndexOf(" "); // split up name and due date
        if (lastSpaceIndex == -1) {
            System.out.println("Invalid format! Usage: add <taskName> <dueDate>");
            return;
        }

        String taskName = parts[1].substring(0, lastSpaceIndex);
        String dueDate = parts[1].substring(lastSpaceIndex + 1);
        Task newTask = new Task(taskName, dueDate);
        taskManager.addTask(newTask);
        System.out.println("Task added: " + newTask);
    }

    // mark a task as completed

    private static void completeTask(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: complete <taskIndex>");
            return;
        }

        try {
            int taskIndex = Integer.parseInt(parts[1]);
            taskManager.markTaskAsCompleted(taskIndex);
            System.out.println("Task " + taskIndex + " marked as completed.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid task index. Please provide a valid task index.");
        }
    }

    private static void deleteTask(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: delete <taskIndex>");
            return;
        }

        try {
            int taskIndex = Integer.parseInt(parts[1]);
            taskManager.deleteTask(taskIndex);
            System.out.println("Task " + taskIndex + " deleted.");
        }   catch (NumberFormatException e) {
            System.out.println("Invalid task index. Please provide a valid task index.");
        }
    }

    private static void showHelp() {
        System.out.println("\n Available commands:");
        System.out.println("fetch                    - List all tasks");
        System.out.println("add <taskName> <dueDate> - Add a new task");
        System.out.println("complete <taskIndex>     - Mark task as completed");
        System.out.println("delete <taskIndex>       - Delete a task");
        System.out.println("help                     - Show available commands");
        System.out.println("exit                     - Exit the program");
    }


}