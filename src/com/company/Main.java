package com.company;

import com.company.controllers.AccountController;
import com.company.controllers.AuthController;
import com.company.controllers.CategoryController;
import com.company.controllers.ReportController;
import com.company.controllers.TransactionController;
import com.company.controllers.interfaces.IAccountController;
import com.company.controllers.interfaces.IAuthController;
import com.company.controllers.interfaces.ICategoryController;
import com.company.controllers.interfaces.IReportController;
import com.company.controllers.interfaces.ITransactionController;
import com.company.data.DBInitializer;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.AccountRepository;
import com.company.repositories.CategoryRepository;
import com.company.repositories.TransactionRepository;
import com.company.repositories.UserRepository;
import com.company.repositories.interfaces.IAccountRepository;
import com.company.repositories.interfaces.ICategoryRepository;
import com.company.repositories.interfaces.ITransactionRepository;
import com.company.repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/finance-tracker";
        String user = "postgres";
        String password = "1234";

        IDB db = new PostgresDB(url, user, password);
        DBInitializer initializer = new DBInitializer(db);
        initializer.init();

        IUserRepository userRepository = new UserRepository(db);
        IAuthController authController = new AuthController(userRepository);

        IAccountRepository accountRepository = new AccountRepository(db);
        ICategoryRepository categoryRepository = new CategoryRepository(db);
        ITransactionRepository transactionRepository = new TransactionRepository(db);

        IAccountController accountController = new AccountController(accountRepository);
        ICategoryController categoryController = new CategoryController(categoryRepository);
        ITransactionController transactionController = new TransactionController(transactionRepository, accountRepository);
        IReportController reportController = new ReportController();

        MyApplication app = new MyApplication(
                authController,
                accountController,
                categoryController,
                transactionController,
                reportController
        );
        app.start();
    }
}
