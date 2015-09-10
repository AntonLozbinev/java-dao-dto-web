package by.itstart.dao;

import java.util.List;

public interface GenericDao<T> {

    boolean insert(T object) throws DaoException;

    T read(int id) throws DaoException;

    boolean update(T object) throws DaoException;

    boolean delete(int id) throws DaoException;

    List<T> getAll() throws DaoException;

    List<T> getAllWithMarksByStudentId(int id) throws DaoException;

    List<T> getAllByStudentId(int id) throws DaoException;

    List<T> getAllBySubjectId(int id) throws DaoException;

    void closeDao() throws DaoException;
}
