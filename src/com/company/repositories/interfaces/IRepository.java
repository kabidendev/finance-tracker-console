package com.company.repositories.interfaces;

public interface IRepository<T> {
    boolean create (T entity);
    T getById(int id);
}
