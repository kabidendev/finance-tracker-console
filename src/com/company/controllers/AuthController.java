package com.company.controllers;

import com.company.controllers.interfaces.IAuthController;
import com.company.models.User;
import com.company.models.enums.Role;
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
    public boolean register(String name, String email, String password) {
        User existing = userRepository.getByEmail(email);
        if (existing != null) {
            return false;
        }
        User user = new User(name, email, password, Role.USER);
        boolean created = userRepository.create(user);
        if (!created) {
            throw new RuntimeException("Registration failed");
        }
        return true;
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }
}
