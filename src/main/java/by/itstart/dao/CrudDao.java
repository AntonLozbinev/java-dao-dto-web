package main.java.by.itstart.dao;

import java.util.List;

public interface CrudDao<T> {

    boolean insert(T object) throws DaoException;

    T read(int id) throws DaoException;

    boolean update(T object) throws DaoException;

    boolean delete(int id) throws DaoException;

    List<T> getAll() throws DaoException;

    void closeDao() throws DaoException;
}
