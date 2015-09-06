package by.itstart.mysql;

import by.itstart.dao.DaoException;
import by.itstart.dto.Student;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class MySqlStudentDaoTest {

    private static MySqlDaoFactory factory;
    private static MySqlStudentDao studentDao;
    private static Connection connection;
    private static Student student;

    @BeforeClass
    public static void setUp() throws DaoException, SQLException {
        factory = new MySqlDaoFactory();
        connection = factory.getConnection();
        connection.setAutoCommit(false);
        studentDao = (MySqlStudentDao) factory.getDao(connection, Student.class);
        student = new Student();
        student.setId(1);
        student.setFirstName("Anton");
        student.setSecondName("Lozbinev");
        student.setEnterYear(2015);
    }

    @AfterClass
    public static void tearDown() throws DaoException, SQLException {
        connection.rollback();
        studentDao.closeDao();
    }

    @Test
    public void testRead() throws DaoException {
        Student student = studentDao.read(1);
        assertNotNull(student);
        assertNotNull(student.getId());
    }

    @Test
    public void testInsert() throws DaoException {
        List<Student> before = studentDao.getAll();
        assertTrue(studentDao.insert(student));
        List<Student> after = studentDao.getAll();
        assertEquals(1, after.size() - before.size());
    }

    @Test
    public void testUpdate() throws DaoException {
        String nameBefore = studentDao.read(1).getFirstName();
        assertTrue(studentDao.update(student));
        String nameAfter = studentDao.read(1).getFirstName();
        assertNotEquals(nameBefore, nameAfter);
    }

    @Test
    public void testDelete() throws DaoException {
        List<Student> before = studentDao.getAll();
        assertTrue(studentDao.delete(6));
        List<Student> after = studentDao.getAll();
        assertEquals(1, before.size() - after.size());
    }

    @Test
    public void testGetAll() throws DaoException {
        List<Student> students = studentDao.getAll();
        assertNotNull(students);
        assertTrue(students.size() > 0);
    }
}