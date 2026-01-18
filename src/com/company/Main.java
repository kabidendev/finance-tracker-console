package com.company;

import com.company.controllers.AuthController;
import com.company.controllers.interfaces.IAccountController;
import com.company.controllers.interfaces.IAuthController;
import com.company.controllers.interfaces.ICategoryController;
import com.company.controllers.interfaces.IReportController;
import com.company.controllers.interfaces.ITransactionController;
import com.company.data.DBInitializer;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.UserRepository;
import com.company.repositories.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/finance_tracker";
        String user = "postgres";
        String password = "postgres";

        IDB db = new PostgresDB(url, user, password);
        DBInitializer initializer = new DBInitializer(db);
        initializer.init();

        IUserRepository userRepository = new UserRepository(db);
        IAuthController authController = new AuthController(userRepository);

        IAccountController accountController = null;
        ICategoryController categoryController = null;
        ITransactionController transactionController = null;
        IReportController reportController = null;

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
