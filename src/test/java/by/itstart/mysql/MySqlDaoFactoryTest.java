package by.itstart.mysql;

import by.itstart.dao.DaoException;
import by.itstart.dto.Mark;
import by.itstart.dto.Student;
import by.itstart.dto.Subject;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;


public class MySqlDaoFactoryTest {

    private static MySqlDaoFactory factory;
    private static Connection connection;
    private static MySqlStudentDao studentDao;
    private static MySqlSubjectDao subjectDao;
    private static MySqlMarkDao markDao;

    @BeforeClass
    public static void setUp() throws DaoException {
        factory = new MySqlDaoFactory();
    }

    @AfterClass
    public static void tearDown() throws DaoException, SQLException {
        studentDao.closeDao();
        subjectDao.closeDao();
        markDao.closeDao();
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testGetConnection() throws DaoException {
        connection = factory.getConnection();
        assertNotNull("Can not connected", connection);
    }

    @Test
    public void testGetDao() throws DaoException {
        studentDao = (MySqlStudentDao) factory.getDao(connection, Student.class);
        assertNotNull("Can not created studentDao", studentDao);
        subjectDao = (MySqlSubjectDao) factory.getDao(connection, Subject.class);
        assertNotNull("Can not created studentDao", subjectDao);
        markDao = (MySqlMarkDao) factory.getDao(connection, Mark.class);
        assertNotNull("Can not created studentDao", markDao);
    }
}