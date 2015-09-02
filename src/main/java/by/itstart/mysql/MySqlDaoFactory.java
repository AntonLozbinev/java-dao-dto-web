package main.java.by.itstart.mysql;

import main.java.by.itstart.dao.CrudDao;
import main.java.by.itstart.dao.DaoException;
import main.java.by.itstart.dao.DaoFactory;
import main.java.by.itstart.dto.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MySqlDaoFactory implements DaoFactory {

    private Map<Class, DaoCreater> daos;

    public MySqlDaoFactory() {
        daos = new HashMap<>();
        daos.put(Student.class, new DaoCreater() {
            @Override
            public CrudDao create(Connection connection) {
                return new MySqlStudentDao(connection);
            }
        });
        daos.put(Subject.class, new DaoCreater() {
            @Override
            public CrudDao create(Connection connection) {
                return new MySqlSubjectDao(connection);
            }
        });
        daos.put(Mark.class, new DaoCreater() {
            @Override
            public CrudDao create(Connection connection) {
                return new MySqlMarkDao(connection);
            }
        });
    }

    @Override
    public Connection getConnection() throws DaoException {
        Properties prop = new Properties();
        Connection connection;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("database.properties")){
            prop.load(in);
            String driver = prop.getProperty("mysql.driver");
            String url = prop.getProperty("mysql.url");
            String user = prop.getProperty("mysql.user");
            String pass = prop.getProperty("mysql.pass");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DaoException(e);
        }
        return connection;
    }

    @Override
    public CrudDao getDao(Connection connection, Class dtoClass) {
        DaoCreater daoCreater = daos.get(dtoClass);
        return daoCreater.create(connection);
    }
}
