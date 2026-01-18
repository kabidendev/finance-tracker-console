package com.company.repositories.interfaces;

import com.company.models.User;

public interface IUserRepository {
    int create(User user);
    User getByEmail(String email);
    User getById(int id);
}
