package by.itstart.dao;

import java.sql.Connection;

public interface DaoFactory {

    interface DaoCreater {
        GenericDao create(Connection connection);
    }

    Connection getConnection() throws DaoException;

    GenericDao getDao(Connection connection, Class dtoClass);
}
