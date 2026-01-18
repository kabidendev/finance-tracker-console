package com.company;

import com.company.controllers.interfaces.IAccountController;
import com.company.controllers.interfaces.IAuthController;
import com.company.controllers.interfaces.ICategoryController;
import com.company.controllers.interfaces.IReportController;
import com.company.controllers.interfaces.ITransactionController;
import com.company.models.User;
import com.company.utils.InputUtils;

public class MyApplication {
    private final IAuthController authController;
    private final IAccountController accountController;
    private final ICategoryController categoryController;
    private final ITransactionController transactionController;
    private final IReportController reportController;

    public MyApplication(
            IAuthController authController,
            IAccountController accountController,
            ICategoryController categoryController,
            ITransactionController transactionController,
            IReportController reportController
    ) {
        this.authController = authController;
        this.accountController = accountController;
        this.categoryController = categoryController;
        this.transactionController = transactionController;
        this.reportController = reportController;
    }

    public void start() {
        while (true) {
            try {
                if (authController == null) {
                    System.out.println("Auth module not ready.");
                    return;
                }

                if (authController.getCurrentUser() == null) {
                    if (!authMenu()) {
                        return;
                    }
                } else {
                    if (!mainMenu()) {
                        return;
                    }
                }
            } catch (RuntimeException e) {
                String msg = e.getMessage();
                if (msg == null || msg.trim().isEmpty()) {
                    msg = "Unexpected error";
                }
                System.out.println("Error: " + msg);
                System.out.println();
            }
        }
    }

    private boolean authMenu() {
        System.out.println("=== Auth ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Exit");
        int choice = InputUtils.readInt("Choose option: ");

        switch (choice) {
            case 1:
                String name = InputUtils.readLine("Name: ");
                String email = InputUtils.readLine("Email: ");
                String password = InputUtils.readLine("Password: ");
                authController.register(name, email, password);
                System.out.println("Registered and logged in.");
                System.out.println();
                return true;
            case 2:
                String loginEmail = InputUtils.readLine("Email: ");
                String loginPassword = InputUtils.readLine("Password: ");
                authController.login(loginEmail, loginPassword);
                System.out.println("Logged in.");
                System.out.println();
                return true;
            case 0:
                return false;
            default:
                System.out.println("Unknown option.");
                System.out.println();
                return true;
        }
    }

    private boolean mainMenu() {
        User user = authController.getCurrentUser();

        System.out.println("=== Main меню ===");
        System.out.println("User: " + user.getName() + " (" + user.getRole() + ")");
        System.out.println("1. Accounts");
        System.out.println("2. Categories");
        System.out.println("3. Transactions");
        System.out.println("4. Logout");
        System.out.println("0. Exit");

        int choice = InputUtils.readInt("Choose option: ");
        switch (choice) {
            case 1:
                accountsMenu(user.getId());
                return true;
            case 2:
                categoriesMenu(user.getId());
                return true;
            case 3:
                transactionsMenu(user.getId());
                return true;
            case 4:
                authController.logout();
                System.out.println("Logged out.");
                System.out.println();
                return true;
            case 0:
                return false;
            default:
                System.out.println("Unknown option.");
                System.out.println();
                return true;
        }
    }

    private void accountsMenu(int userId) {
        while (true) {
            System.out.println("=== Accounts ===");
            System.out.println("1. Create account");
            System.out.println("2. Show accounts");
            System.out.println("3. Account details");
            System.out.println("0. Back");

            int choice = InputUtils.readInt("Choose option: ");
            switch (choice) {
                case 1:
                    if (accountController == null) {
                        System.out.println("Accounts module not ready.");
                        System.out.println();
                        break;
                    }
                    String name = InputUtils.readLine("Account name: ");
                    double balance = InputUtils.readDouble("Initial balance: ");
                    accountController.createAccount(userId, name, balance);
                    System.out.println("Done.");
                    System.out.println();
                    break;
                case 2:
                    if (accountController == null) {
                        System.out.println("Accounts module not ready.");
                        System.out.println();
                        break;
                    }
                    accountController.showAccounts(userId);
                    System.out.println();
                    break;
                case 3:
                    if (accountController == null) {
                        System.out.println("Accounts module not ready.");
                        System.out.println();
                        break;
                    }
                    int accountId = InputUtils.readInt("Account id: ");
                    accountController.showAccountDetails(accountId);
                    System.out.println();
                    break;
                case 0:
                    System.out.println();
                    return;
                default:
                    System.out.println("Unknown option.");
                    System.out.println();
            }
        }
    }

    private void categoriesMenu(int userId) {
        while (true) {
            System.out.println("=== Categories ===");
            System.out.println("1. Create category");
            System.out.println("2. Show INCOME categories");
            System.out.println("3. Show EXPENSE categories");
            System.out.println("0. Back");

            int choice = InputUtils.readInt("Choose option: ");
            switch (choice) {
                case 1:
                    if (categoryController == null) {
                        System.out.println("Categories module not ready.");
                        System.out.println();
                        break;
                    }
                    String name = InputUtils.readLine("Category name: ");
                    String type = InputUtils.readLine("Type (INCOME/EXPENSE): ");
                    categoryController.createCategory(userId, name, type);
                    System.out.println("Done.");
                    System.out.println();
                    break;
                case 2:
                    if (categoryController == null) {
                        System.out.println("Categories module not ready.");
                        System.out.println();
                        break;
                    }
                    categoryController.showCategories(userId, "INCOME");
                    System.out.println();
                    break;
                case 3:
                    if (categoryController == null) {
                        System.out.println("Categories module not ready.");
                        System.out.println();
                        break;
                    }
                    categoryController.showCategories(userId, "EXPENSE");
                    System.out.println();
                    break;
                case 0:
                    System.out.println();
                    return;
                default:
                    System.out.println("Unknown option.");
                    System.out.println();
            }
        }
    }

    private void transactionsMenu(int userId) {
        while (true) {
            System.out.println("=== Transactions ===");
            System.out.println("1. Add INCOME");
            System.out.println("2. Add EXPENSE");
            System.out.println("3. Transfer");
            System.out.println("4. Show transactions");
            System.out.println("0. Back");

            int choice = InputUtils.readInt("Choose option: ");
            switch (choice) {
                case 1:
                    if (transactionController == null) {
                        System.out.println("Transactions module not ready.");
                        System.out.println();
                        break;
                    }
                    int toId = InputUtils.readInt("Account to id: ");
                    int incomeCategoryId = InputUtils.readInt("Category id: ");
                    double incomeAmount = InputUtils.readDouble("Amount: ");
                    String incomeComment = InputUtils.readLine("Comment: ");
                    transactionController.addIncome(userId, toId, incomeCategoryId, incomeAmount, incomeComment);
                    System.out.println("Done.");
                    System.out.println();
                    break;
                case 2:
                    if (transactionController == null) {
                        System.out.println("Transactions module not ready.");
                        System.out.println();
                        break;
                    }
                    int fromId = InputUtils.readInt("Account from id: ");
                    int expenseCategoryId = InputUtils.readInt("Category id: ");
                    double expenseAmount = InputUtils.readDouble("Amount: ");
                    String expenseComment = InputUtils.readLine("Comment: ");
                    transactionController.addExpense(userId, fromId, expenseCategoryId, expenseAmount, expenseComment);
                    System.out.println("Done.");
                    System.out.println();
                    break;
                case 3:
                    if (transactionController == null) {
                        System.out.println("Transactions module not ready.");
                        System.out.println();
                        break;
                    }
                    int transferFrom = InputUtils.readInt("From account id: ");
                    int transferTo = InputUtils.readInt("To account id: ");
                    double transferAmount = InputUtils.readDouble("Amount: ");
                    String transferComment = InputUtils.readLine("Comment: ");
                    transactionController.transfer(userId, transferFrom, transferTo, transferAmount, transferComment);
                    System.out.println("Done.");
                    System.out.println();
                    break;
                case 4:
                    if (transactionController == null) {
                        System.out.println("Transactions module not ready.");
                        System.out.println();
                        break;
                    }
                    transactionController.showTransactions(userId);
                    System.out.println();
                    break;
                case 0:
                    System.out.println();
                    return;
                default:
                    System.out.println("Unknown option.");
                    System.out.println();
            }
        }
    }
}
