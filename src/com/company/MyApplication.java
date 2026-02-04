package com.company;

import com.company.controllers.interfaces.IAccountController;
import com.company.controllers.interfaces.IAuthController;
import com.company.controllers.interfaces.ICategoryController;
import com.company.controllers.interfaces.IReportController;
import com.company.controllers.interfaces.ITransactionController;
import com.company.exceptions.ValidationException;
import com.company.models.Account;
import com.company.models.Category;
import com.company.models.User;
import com.company.models.dto.TransactionFullView;
import com.company.models.enums.CategoryType;
import com.company.models.enums.Role;
import com.company.utils.InputUtils;
import com.company.utils.ValidationUtils;

import java.util.List;
import java.util.Map;

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
                registerFlow();
                System.out.println();
                return true;
            case 2:
                loginFlow();
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

    private void registerFlow() {
        String name = InputUtils.readLine("Name: ");
        String email = InputUtils.readLine("Email: ");
        String password = InputUtils.readLine("Password: ");

        ValidationUtils.validateNotBlank(name);
        ValidationUtils.validateEmailLike(email);
        ValidationUtils.validateNotBlank(password);

        boolean ok = authController.register(name.trim(), email.trim(), password);
        if (!ok) {
            System.out.println("User with this email already exists.");
            return;
        }

        User user = authController.login(email.trim(), password);
        if (user == null) {
            System.out.println("Registered, but login failed.");
            return;
        }

        System.out.println("Registered and logged in.");
    }

    private void loginFlow() {
        String email = InputUtils.readLine("Email: ");
        String password = InputUtils.readLine("Password: ");

        ValidationUtils.validateEmailLike(email);
        ValidationUtils.validateNotBlank(password);

        User user = authController.login(email.trim(), password);
        if (user == null) {
            System.out.println("Invalid credentials.");
            return;
        }

        System.out.println("Logged in.");
    }

    private boolean mainMenu() {
        User user = authController.getCurrentUser();

        System.out.println("=== Main menu ===");
        System.out.println("User: " + user.getName() + " (" + user.getRole() + ")");
        System.out.println("1. Accounts");
        System.out.println("2. Categories");
        System.out.println("3. Transactions");
        System.out.println("4. Reports");

        boolean admin = user.getRole() == Role.ADMIN;
        if (admin) {
            System.out.println("5. Admin");
            System.out.println("6. Logout");
        } else {
            System.out.println("5. Logout");
        }
        System.out.println("0. Exit");

        int choice = InputUtils.readInt("Choose option: ");

        if (admin) {
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
                    reportsMenu(user.getId());
                    return true;
                case 5:
                    adminMenu(user);
                    return true;
                case 6:
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
        } else {
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
                    reportsMenu(user.getId());
                    return true;
                case 5:
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
                    Account account = accountController.createAccount(userId, name, balance);
                    System.out.println("Done: " + account);
                    System.out.println();
                    break;
                case 2:
                    if (accountController == null) {
                        System.out.println("Accounts module not ready.");
                        System.out.println();
                        break;
                    }
                    List<Account> accounts = accountController.listAccounts(userId);
                    printAccountList(accounts);
                    System.out.println();
                    break;
                case 3:
                    if (accountController == null) {
                        System.out.println("Accounts module not ready.");
                        System.out.println();
                        break;
                    }
                    int accountId = InputUtils.readInt("Account id: ");
                    Account details = accountController.showAccountDetails(accountId);
                    printAccountDetails(details);
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
                    Category category = categoryController.createCategory(userId, name, type);
                    System.out.println("Done: " + category);
                    System.out.println();
                    break;
                case 2:
                    if (categoryController == null) {
                        System.out.println("Categories module not ready.");
                        System.out.println();
                        break;
                    }
                    List<Category> income = categoryController.listCategories(userId, CategoryType.INCOME);
                    printCategoryList("INCOME", income);
                    System.out.println();
                    break;
                case 3:
                    if (categoryController == null) {
                        System.out.println("Categories module not ready.");
                        System.out.println();
                        break;
                    }
                    List<Category> expense = categoryController.listCategories(userId, CategoryType.EXPENSE);
                    printCategoryList("EXPENSE", expense);
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
                    String incomeComment = toNullIfBlank(InputUtils.readLine("Comment (optional): "));
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
                    String expenseComment = toNullIfBlank(InputUtils.readLine("Comment (optional): "));
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
                    String transferComment = toNullIfBlank(InputUtils.readLine("Comment (optional): "));
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

    private void reportsMenu(int userId) {
        while (true) {
            System.out.println("=== Reports ===");
            System.out.println("1. Last N transactions (full view)");
            System.out.println("2. Top expense categories");
            System.out.println("0. Back");

            int choice = InputUtils.readInt("Choose option: ");
            switch (choice) {
                case 1:
                    if (reportController == null) {
                        System.out.println("Reports module not ready.");
                        System.out.println();
                        break;
                    }
                    int n = InputUtils.readInt("N: ");
                    if (n <= 0) {
                        throw new ValidationException("N must be greater than 0");
                    }
                    List<TransactionFullView> list = reportController.getLastNTransactions(userId, n);
                    printTransactionFullViews(list);
                    System.out.println();
                    break;
                case 2:
                    if (reportController == null) {
                        System.out.println("Reports module not ready.");
                        System.out.println();
                        break;
                    }
                    Map<String, Double> stats = reportController.getTopExpenseCategories(userId);
                    printTopExpenseCategories(stats);
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

    private void adminMenu(User user) {
        while (true) {
            System.out.println("=== Admin ===");
            System.out.println("1. List users");
            System.out.println("2. Create global category");
            System.out.println("0. Back");

            int choice = InputUtils.readInt("Choose option: ");
            switch (choice) {
                case 1:
                    List<User> users = authController.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("No users.");
                    } else {
                        for (User u : users) {
                            System.out.println(u);
                        }
                    }
                    System.out.println();
                    break;
                case 2:
                    if (categoryController == null) {
                        System.out.println("Categories module not ready.");
                        System.out.println();
                        break;
                    }
                    String name = InputUtils.readLine("Category name: ");
                    String typeValue = InputUtils.readLine("Type (INCOME/EXPENSE): ");
                    CategoryType type = parseCategoryType(typeValue);
                    Category category = categoryController.createGlobalCategory(user, name, type);
                    System.out.println("Done: " + category);
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

    private String toNullIfBlank(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private CategoryType parseCategoryType(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("Invalid category type");
        }
        try {
            return CategoryType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid category type");
        }
    }

    private void printAccountList(List<Account> accounts) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts.");
            return;
        }
        System.out.println("Accounts (sorted by id):");
        int index = 1;
        for (Account account : accounts) {
            String id = account.getId() == null ? "-" : account.getId().toString();
            System.out.printf("%d) id=%s | name=%s | balance=%.2f%n",
                    index,
                    id,
                    account.getName(),
                    account.getBalance()
            );
            index++;
        }
    }

    private void printAccountDetails(Account account) {
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        String id = account.getId() == null ? "-" : account.getId().toString();
        System.out.printf("Account: id=%s | name=%s | balance=%.2f%n",
                id,
                account.getName(),
                account.getBalance()
        );
    }

    private void printCategoryList(String label, List<Category> categories) {
        if (categories.isEmpty()) {
            System.out.println("No categories.");
            return;
        }
        System.out.println("Categories " + label + " (sorted by id):");
        int index = 1;
        for (Category category : categories) {
            String userId = category.getUserId() == null ? "-" : category.getUserId().toString();
            System.out.printf("%d) id=%d | name=%s | type=%s | userId=%s%n",
                    index,
                    category.getId(),
                    category.getName(),
                    category.getType(),
                    userId
            );
            index++;
        }
    }

    private void printTransactionFullViews(List<TransactionFullView> list) {
        if (list.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }

        int index = 1;
        for (TransactionFullView t : list) {
            String from = t.getFromAccountName() == null ? "-" : t.getFromAccountName();
            String to = t.getToAccountName() == null ? "-" : t.getToAccountName();
            String category = t.getCategoryName() == null ? "-" : t.getCategoryName();
            String categoryType = t.getCategoryType() == null ? "-" : t.getCategoryType();
            String comment = t.getComment() == null || t.getComment().trim().isEmpty() ? "-" : t.getComment();
            String createdAt = t.getCreatedAt() == null ? "-" : t.getCreatedAt().toString();

            System.out.printf(
                    "%d) id=%d | type=%s | amount=%.2f | at=%s | from=%s | to=%s | category=%s (%s) | comment=%s%n",
                    index,
                    t.getId(),
                    t.getType(),
                    t.getAmount(),
                    createdAt,
                    from,
                    to,
                    category,
                    categoryType,
                    comment
            );
            index++;
        }
    }

    private void printTopExpenseCategories(Map<String, Double> stats) {
        if (stats.isEmpty()) {
            System.out.println("No expenses.");
            return;
        }

        int index = 1;
        for (Map.Entry<String, Double> entry : stats.entrySet()) {
            System.out.printf("%d) %s: %.2f%n", index, entry.getKey(), entry.getValue());
            index++;
        }
    }
}

