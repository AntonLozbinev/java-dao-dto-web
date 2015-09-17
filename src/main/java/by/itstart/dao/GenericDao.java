package by.itstart.dao;

import by.itstart.dto.Mark;
import by.itstart.dto.Subject;

import java.util.List;

public interface GenericDao<T> {

    boolean insert(T object) throws DaoException;

    T read(Integer id) throws DaoException;

    boolean update(T object) throws DaoException;

    boolean delete(Integer id) throws DaoException;

    List<T> getAll() throws DaoException;

    List<T> getAllBySubjectId(Integer id) throws DaoException;

    List<T> getAllWithMarksByStudentId(Integer id) throws DaoException;

    List<T> getAllByStudentId(Integer id) throws DaoException;

    void closeDao();
}
