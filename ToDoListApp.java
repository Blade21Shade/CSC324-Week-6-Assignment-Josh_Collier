import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A simple To-Do List application that allows users to:
 * 1. Add tasks
 * 2. Move task to category
 * 3. View tasks
 * 4. View tasks by category
 * 5. View categories
 * 6. Remove tasks
 * 7. Make new category
 * 8. Remove category
 * 9. Exit the program
 *
 * Uses an ArrayList to store tasks and Scanner for user input.
 */
public class ToDoListApp {
    // Stores the list of tasks
    private static ArrayList<String> tasks = new ArrayList<>();
    private static ArrayList<String> categoriesList = new ArrayList<>();
    private static HashMap<String, String> categoriesMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner for user input
        int choice; // Stores user menu choice

        categoriesList.add("Non-Categorized"); // Final category in categories list, used for non-categorized tasks

        // Loop to continuously show menu until user exits
        do {
            // Display menu options
            System.out.println("\n--- To-Do List ---");
            System.out.println("1. Add Task");
            System.out.println("2. Move Task To Category");
            System.out.println("3. View Tasks");
            System.out.println("4. View Tasks By Category");
            System.out.println("5. View Categories");
            System.out.println("6. Remove Task");
            System.out.println("7. Make New Category");
            System.out.println("8. Remove Category");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            // Read user input (menu choice)
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character left after nextInt()

            // Handle user choice using a switch statement
            switch (choice) {
                case 1 -> addTask(scanner); // Call method to add a task
                case 2 -> moveTaskToCategory(scanner); // Moves a task to a given category
                case 3 -> viewTasks(); // Call method to display tasks
                case 4 -> viewTasksByCategory(); // Call method to display tasks via their category
                case 5 -> viewCategories(); // Call method to display categories
                case 6 -> removeTask(scanner); // Call method to remove a task
                case 7 -> makeTaskCategory(scanner); // Call method to make a category
                case 8 -> removeTaskCategory(scanner); // Call method to remove a category
                case 9 -> System.out.println("Exiting..."); // Exit message
                default -> System.out.println("Invalid choice. Try again."); // Handle invalid input
            }
        } while (choice != 9); // Loop until user selects option 9 (Exit)

        scanner.close(); // Close scanner to prevent memory leaks
    }

    /**
     * Method to add a new task to the list.
     * @param scanner Scanner object for user input.
     */
    private static void addTask(Scanner scanner) {
        System.out.print("Enter task: ");
        String task = scanner.nextLine(); // Read task from user
        tasks.add(task); // Add task to the list
        System.out.println("Task added!"); // Confirmation message

        // See which category the user wants to add this task to
        System.out.print("Category to add task to: ");
        String category = scanner.nextLine();
        
        // See if the category exists, it not add it to the list
        boolean categoryExists = true;
        if (!categoriesList.contains(category)) {
            System.out.println("Category doesn't exist yet, create new category? Yes or No");
            String answer = scanner.nextLine();
            if (answer.equals("Yes") || answer.equals("Y")) {
                makeTaskCategory(scanner);
            } else {
                System.out.println("Category not created, adding task to \"Non-Categorized\"");
                categoryExists = false;
            }
        }

        // Add task to the category
        if (categoryExists) {
            categoriesMap.put(task, category);
        } else { // Add task to "Non-Categorized" category
            categoriesMap.put(task, "Non-Categorized");
        }
        System.out.println("Task added to category!");
    }

    /**
     * Method to display all tasks in the list.
     */
    private static void viewTasks() {
        if (tasks.isEmpty()) { // Check if the list is empty
            System.out.println("No tasks available.");
        } else {
            System.out.println("\nYour Tasks:");
            // Loop through the list and display each task with a number
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    /**
     * Method to display all tasks in the list via category
     */
    private static void viewTasksByCategory() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        // (Note: I know this is inefficient, I just wanted to complete the assignment)
        // Print each task by category
        System.out.println("\nTasks by category");
        for (int i = 0; i < categoriesList.size(); i++) {
            System.out.println(categoriesList.get(i));
            
            // See if the task is within the category, if so print
            int printNum = 1;
            for (int j = 0; j < tasks.size(); j++) {
                String tempCategory = categoriesMap.get(tasks.get(j));
                if (tempCategory.equals(categoriesList.get(i))) {
                    System.out.println(printNum + ". " + tasks.get(j));
                    printNum++;
                }
            }
            System.out.println();
        }
    }

    /**
     * Method to remove a task from the list.
     * @param scanner Scanner object for user input.
     */
    private static void removeTask(Scanner scanner) {
        viewTasks(); // Show current tasks before asking for input
        if (tasks.isEmpty()) return; // If no tasks, exit method

        System.out.print("Enter task number to remove: ");
        int index = scanner.nextInt(); // Get task number from user

        // Validate the task number before removing
        if (index > 0 && index <= tasks.size()) {
            categoriesList.remove(tasks.get(index-1)); // Remove from hashMap
            tasks.remove(index - 1); // Remove task (index is 1-based, ArrayList is 0-based)
            System.out.println("Task removed."); // Confirmation message
        } else {
            System.out.println("Invalid task number."); // Handle invalid input
        }
    }

    /**
     * Prints all the categories for the user
     */
    private static void viewCategories() {
        if (categoriesList.isEmpty()) {
            System.out.println("No categories");
            return;
        }

        System.out.println("\nYour Categories:");
        // Loop through the list and display each category
        for (int i = 0; i < categoriesList.size(); i++) {
            System.out.println(categoriesList.get(i));
        }
    }

    /**
     * Makes a new task category
     * @param scanner Scanner object for user input
     */
    private static void makeTaskCategory(Scanner scanner) {
        // Show current categories
        viewCategories();

        // Make a new category, make sure it isn't an already listed category
        System.out.print("Enter new category: ");
        String category = scanner.nextLine(); // Read category from user
        if (categoriesList.contains(category)) {
            System.out.println("Category already exists");
            return;
        }

        // Add to list one from the end
        if (categoriesList.size() < 2) {
            categoriesList.add(0, category); // Add before the last category, "Non-Categorized"
        } else {
            categoriesList.add(categoriesList.size() -1, category); // Add before the last category, "Non-Categorized"
        }
        
        System.out.println("Category added!"); // Confirmation message
    }

    /**
     * Removes a task category
     * @param scanner Scanner object for user input
     */
    private static void removeTaskCategory(Scanner scanner) {
        // Show current categories
        viewCategories();

        // Ask which category to remove
        System.out.print("Enter category to remove: ");
        String category = scanner.nextLine(); // Read category from user
        if (!categoriesList.contains(category)) {
            System.out.println("Category doesn't exist");
            return;
        }

        // For each task in the category, change the Hashmap value to "Non-Categorized"
        for (int i = 0; i < tasks.size(); i++) {
            String categoryForTask = categoriesMap.get(tasks.get(i));
            if (categoryForTask.equals(category)) {
                categoriesMap.replace(tasks.get(i), "Non-Categorized");
            }
        }

        // Finally remove the actual category
        categoriesList.remove(category);
    }

    /**
     * Moves a task from one category to another
     * @param scanner Scanner for user input
     */
    private static void moveTaskToCategory(Scanner scanner) {
        viewTasksByCategory();
        System.out.println("\nEnter task you wish to move: ");
        String taskToMove = scanner.nextLine();
        System.out.println("\nEnter category you wish to move task to: ");
        String categoryTo = scanner.nextLine();

        // If both the task and category exist, move the task
        if (tasks.contains(taskToMove) && categoriesList.contains(categoryTo)) {
            categoriesMap.replace(taskToMove, categoryTo);
            System.out.println("Task moved");
        } else {
            System.out.println("Task or category (or both) does not exist");
        }

        // Show user the task has moved
        viewTasksByCategory();
    }
}
