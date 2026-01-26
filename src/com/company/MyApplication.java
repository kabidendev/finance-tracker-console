package com.company;

import com.company.controllers.AuthController;
import com.company.controllers.CategoryController;
import com.company.data.PostgresDB;
import com.company.exceptions.AccessDeniedException;
import com.company.exceptions.ValidationException;
import com.company.models.Category;
import com.company.models.User;
import com.company.models.enums.CategoryType;
import com.company.models.enums.Role;
import com.company.repositories.UserRepository;
import com.company.utils.ValidationUtils;

import java.util.List;
import java.util.Scanner;

public class MyApplication {
    private final AuthController authController;
    private final CategoryController categoryController;
    private final Scanner scanner;

    public MyApplication() {
        this(new AuthController(new UserRepository(
                new PostgresDB("jdbc:postgresql://localhost:5432/finance_tracker", "postgres", "postgres")
        )), new CategoryController());
    }

    public MyApplication(AuthController authController, CategoryController categoryController) {
        this.authController = authController;
        this.categoryController = categoryController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleLogin();
                    break;
                case "2":
                    handleLogout();
                    break;
                case "3":
                    try {
                        handleCreateAccount();
                    } catch (RuntimeException e) {
                        printError(e);
                    }
                    break;
                case "4":
                    try {
                        handleCreateTransaction();
                    } catch (RuntimeException e) {
                        printError(e);
                    }
                    break;
                case "5":
                    try {
                        handleCreateCategory();
                    } catch (RuntimeException e) {
                        printError(e);
                    }
                    break;
                case "6":
                    try {
                        handleAdminListUsers();
                    } catch (RuntimeException e) {
                        printError(e);
                    }
                    break;
                case "7":
                    try {
                        handleAdminCreateGlobalCategory();
                    } catch (RuntimeException e) {
                        printError(e);
                    }
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option");
            }
        }
    }

    private void printMenu() {
        System.out.println("1. Login");
        System.out.println("2. Logout");
        System.out.println("3. Create account");
        System.out.println("4. Create transaction");
        System.out.println("5. Create category");
        if (isAdmin()) {
            System.out.println("Admin menu:");
            System.out.println("6. Admin: list users");
            System.out.println("7. Admin: create global category");
        }
        System.out.println("0. Exit");
    }

    private void handleLogin() {
        if (authController.getCurrentUser() != null) {
            System.out.println("Already logged in");
            return;
        }
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        ValidationUtils.validateEmailLike(email);
        ValidationUtils.validateNotBlank(password);
        User user = authController.login(email, password);
        if (user == null) {
            System.out.println("Invalid credentials");
            return;
        }
        System.out.println("Logged in as " + user.getRole());
    }

    private void handleLogout() {
        authController.logout();
        System.out.println("Logged out");
    }

    private void handleCreateAccount() {
        User user = requireLoggedIn();
        if (user == null) {
            return;
        }
        System.out.print("Account name: ");
        String name = scanner.nextLine();
        ValidationUtils.validateNotBlank(name);
        System.out.print("Initial balance: ");
        double balance = readDouble();
        ValidationUtils.validatePositiveAmount(balance);
        System.out.println("Account created");
    }

    private void handleCreateTransaction() {
        User user = requireLoggedIn();
        if (user == null) {
            return;
        }
        System.out.print("Amount: ");
        double amount = readDouble();
        ValidationUtils.validatePositiveAmount(amount);
        System.out.println("Transaction created");
    }

    private void handleCreateCategory() {
        User user = requireLoggedIn();
        if (user == null) {
            return;
        }
        System.out.print("Category name: ");
        String name = scanner.nextLine();
        ValidationUtils.validateNotBlank(name);
        CategoryType type = readCategoryType();
        Category category = new Category(user.getId(), name, type);
        System.out.println("Category created: " + category);
    }

    private void handleAdminListUsers() {
        requireAdmin();
        List<User> users = authController.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users");
            return;
        }
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void handleAdminCreateGlobalCategory() {
        User admin = requireAdmin();
        System.out.print("Category name: ");
        String name = scanner.nextLine();
        ValidationUtils.validateNotBlank(name);
        CategoryType type = readCategoryType();
        Category category = categoryController.createGlobalCategory(admin, name, type);
        System.out.println("Global category created: " + category);
    }

    private User requireLoggedIn() {
        User user = authController.getCurrentUser();
        if (user == null) {
            System.out.println("Please login first");
        }
        return user;
    }

    private User requireAdmin() {
        User user = authController.getCurrentUser();
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Access denied");
        }
        return user;
    }

    private boolean isAdmin() {
        User user = authController.getCurrentUser();
        return user != null && user.getRole() == Role.ADMIN;
    }

    private CategoryType readCategoryType() {
        System.out.print("Category type (INCOME/EXPENSE): ");
        String value = scanner.nextLine();
        ValidationUtils.validateNotBlank(value);
        try {
            return CategoryType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid category type");
        }
    }

    private double readDouble() {
        String value = scanner.nextLine();
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number");
        }
    }

    private void printError(RuntimeException e) {
        String message = e.getMessage();
        if (message == null || message.isBlank()) {
            System.out.println("Error");
        } else {
            System.out.println(message);
        }
    }
}
