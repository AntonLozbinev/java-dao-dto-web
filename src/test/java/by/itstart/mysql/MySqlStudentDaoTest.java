package by.itstart.mysql;

import by.itstart.dao.GenericDao;
import by.itstart.dao.DaoException;
import by.itstart.dto.Student;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@SuppressWarnings("unchecked")
@Transactional
public class MySqlStudentDaoTest {

    @Autowired
    private GenericDao studentDao;
    private static Student student;

    @BeforeClass
    public static void setUp() throws DaoException, SQLException {
        student = new Student();
        student.setId(1);
        student.setFirstName("Anton");
        student.setSecondName("Lozbinev");
        student.setEnterYear(2015);
    }

    @Test
    public void testRead() throws DaoException {
        Student student = (Student) studentDao.read(1);
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
        String nameBefore = ((Student)studentDao.read(1)).getFirstName();
        assertTrue(studentDao.update(student));
        String nameAfter = ((Student)studentDao.read(1)).getFirstName();
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