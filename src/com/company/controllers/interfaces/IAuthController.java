package com.company.controllers.interfaces;

import com.company.models.User;

public interface IAuthController {
    User login(String email, String password);
    void logout();
    User getCurrentUser();
}
