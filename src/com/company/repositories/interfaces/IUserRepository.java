package com.company.repositories.interfaces;

import com.company.models.User;

import java.util.List;

public interface IUserRepository extends IRepository<User> {
    User create(User user);
    User getByEmail(String email);
    List<User> getAll();
}
