package com.company.controllers.interfaces;

import com.company.models.User;
import java.util.List;

public interface IAuthController {
    User login(String email, String password);
    boolean register(String name, String email, String password);
    void logout();
    User getCurrentUser();
    List<User> getAllUsers();
}
