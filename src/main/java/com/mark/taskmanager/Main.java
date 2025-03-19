package com.mark.taskmanager;
import java.util.Scanner;

// TODO: Configure library for colors
// TODO: Implement task archives
// TODO: Fix bugs as they appear
// TODO: Think of new features
// TODO: REFACTOR parseCommand() into ENUM

public class Main {
    private static final TaskManager taskManager = new TaskManager();
    private static Scanner scanner;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Welcome to speedyTask!\n");
            scanner = sc;
            showHelp();

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
    }

    private enum Command {
        FETCH, ADD, EDIT, COMPLETE, DELETE, HELP, CLEAR, EXIT, UNKNOWN;

        public static Command fromString(String command) {
            try {
                return Command.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                return UNKNOWN;
            }
        }
    }

    private static void parseCommand(String command) {
        String[] parts = command.split(" ", 2);
        Command cmd = Command.fromString(parts[0]);

        switch (cmd) {
            case FETCH -> fetchTasks();
            case ADD -> addTask(parts);
            case EDIT -> editTask(parts);
            case COMPLETE -> completeTask(parts);
            case DELETE -> deleteTask(parts);
            case HELP -> showHelp();
            case CLEAR -> clearTasks();
            case EXIT -> System.out.println("Use 'exit' to exit.");
            default -> System.out.println("Unknown command: " + command);
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
                return;
            }

            String inputDetails = args[1];
            int lastSpaceIndex = inputDetails.lastIndexOf(" ");

            if (lastSpaceIndex == -1) {
                System.out.println("Invalid format! Usage: edit <taskIndex> <newTaskName> <dueDate>");
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
                return;
            } else { taskManager.markTaskAsCompleted(taskIndex); }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task index. Please provide a valid task index.");
            return;
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
        while(true) {
        System.out.println("Are you sure? (y/n)");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y")) {
            System.out.println("Clearing tasks...");
            taskManager.clearTasks();
            break;
        } else if (response.equals("n")) {
            System.out.println("Returning to command line.");
            break;
        } else {
            System.out.println("Invalid response, returning to command line");
            }
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