package com.company.controllers.interfaces;

import com.company.models.User;

public interface IAuthController {
    User register(String name, String email, String password);
    User login(String email, String password);
    User getCurrentUser();
    void logout();
}
