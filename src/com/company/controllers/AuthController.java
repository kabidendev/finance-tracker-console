package com.company.controllers;

import com.company.controllers.interfaces.IAuthController;
import com.company.exceptions.ValidationException;
import com.company.models.User;
import com.company.models.enums.Role;
import com.company.repositories.interfaces.IUserRepository;

public class AuthController implements IAuthController {
    private final IUserRepository userRepository;
    private User currentUser;

    public AuthController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String name, String email, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password cannot be empty");
        }

        User existing = userRepository.getByEmail(email.trim());
        if (existing != null) {
            throw new ValidationException("User already exists");
        }

        User user = new User(name.trim(), email.trim(), password, Role.USER);
        userRepository.create(user);
        currentUser = user;
        return user;
    }

    @Override
    public User login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password cannot be empty");
        }

        User user = userRepository.getByEmail(email.trim());
        if (user == null) {
            throw new ValidationException("User not found");
        }
        if (!password.equals(user.getPassword())) {
            throw new ValidationException("Invalid password");
        }

        currentUser = user;
        return user;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void logout() {
        currentUser = null;
    }
}
