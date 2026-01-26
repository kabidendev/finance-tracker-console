package com.company.controllers;

import com.company.controllers.interfaces.IAuthController;
import com.company.models.User;
import com.company.repositories.interfaces.IUserRepository;

import java.util.List;

public class AuthController implements IAuthController {
    private final IUserRepository userRepository;
    private User currentUser;

    public AuthController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            return null;
        }
        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            return null;
        }
        currentUser = user;
        return user;
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }
}
