package ru.bellintegrator.myapp.ModelDAO;

import java.util.List;

/**
 * Created by MADmitriev on 30.05.2017.
 */
public interface BankDAO<T> {
    void create(T t);
    void insert(T t);
    T update(T t) throws Exception;
    List<T> getAll();
    void delete(Object id);
    T getById(Object id);
}
