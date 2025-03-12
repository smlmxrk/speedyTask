package com.mark.taskmanager;
import javax.management.monitor.StringMonitor;
import java.text.NumberFormat;
import java.util.Scanner;

/* todo still:
-configure library for colors
-task archives
-fix more bugs as you find them
*/

public class Main {
    private static final TaskManager taskManager = new TaskManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to speedyTask!\n");
        showHelp();

        while (true) {
            System.out.print("\nEnter command: ");
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

            case "clear":
                clearTasks();
                break;

            case "edit":
                editTask(parts);
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

    // ** REMEMBER TO FIX THIS - not parsing correctly, indexes off as well **

    private static void editTask(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: edit <taskIndex> <newTask> <dueDate>");
            return;
        }

        String[] args = parts[1].split(" ", 2);
        if (args.length < 2) {
            System.out.println("Invalid format! Usage: edit <taskIndex> <newTaskName> <dueDate>");
            return;
        }

        try {
            int taskIndex = Integer.parseInt(args[0]);


            // check that the index is valid
            if (taskIndex < 1 || taskIndex > taskManager.getTaskCount()) {
                System.out.println("Invalid task index. Please enter a number between 1 and " + taskManager.getTaskCount() + ".");
            }

            String inputDetails = args[1];
            int lastSpaceIndex = inputDetails.lastIndexOf(" ");

            if (lastSpaceIndex == -1) {
                System.out.println("Invalid format! Usage: edit <taskIndex> <dueDate>");
                return;
            }

           String newTaskName = inputDetails.substring(0, lastSpaceIndex);
           String newDueDate = inputDetails.substring(lastSpaceIndex + 1);

            Task updatedTask = new Task(newTaskName, newDueDate);
            taskManager.editTask(taskIndex, updatedTask);

            System.out.println("Task " + (taskIndex) + " updated: " + updatedTask);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task index. Please enter a valid number.");
        }

    }


    // mark a task as completed
    private static void completeTask(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: complete <taskIndex>");
            return;
        }

        try {
            int taskIndex = Integer.parseInt(parts[1]);
            if (taskIndex < 1 || taskIndex > taskManager.getTaskCount()) {
                System.out.println("Invalid task index. Please provide a valid task index.");
            } else { taskManager.markTaskAsCompleted(taskIndex); }
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

            if (taskManager.containsTask(taskIndex-1)) {
                taskManager.deleteTask(taskIndex - 1);
                System.out.println("Task " + taskIndex + " deleted.");
            } else {
                System.out.println("No task to delete at that index!");
            }
        }   catch (NumberFormatException e) {
            System.out.println("Invalid task index. Please provide a valid task index.");
        }
    }

    private static void clearTasks() {
        System.out.println("Are you sure? (y/n)");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y")) {
            System.out.println("Clearing tasks...");
            taskManager.clearTasks();
        } else if (response.equals("n")) {
            System.out.println("Returning to command line.");
        } else {
            System.out.println("Invalid response, returning to command line");
        }
    }

    private static void showHelp() {
        System.out.println("\nAvailable commands:");
        System.out.println("fetch                     - List all tasks");
        System.out.println("add <taskName> <dueDate>  - Add a new task");
        System.out.println("edit <taskName> <dueDate> - Edit a task");
        System.out.println("complete <taskIndex>      - Mark task as completed");
        System.out.println("delete <taskIndex>        - Delete a task");
        System.out.println("help                      - Show available commands");
        System.out.println("exit                      - Exit the program");
        System.out.println("clear                     - Clear all tasks");
    }


}