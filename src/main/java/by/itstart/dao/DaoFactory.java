package by.itstart.dao;

import java.sql.Connection;

public interface DaoFactory {

    interface DaoCreater {
        CrudDao create(Connection connection);
    }

    Connection getConnection() throws DaoException;

    CrudDao getDao(Connection connection, Class dtoClass);
}
