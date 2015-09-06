package by.itstart.mysql;

import by.itstart.dao.DaoException;
import by.itstart.dto.Subject;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class MySqlSubjectDaoTest {

    private static MySqlDaoFactory factory;
    private static MySqlSubjectDao subjectDao;
    private static Connection connection;
    private static Subject subject;

    @BeforeClass
    public static void setUp() throws DaoException, SQLException {
        factory = new MySqlDaoFactory();
        connection = factory.getConnection();
        connection.setAutoCommit(false);
        subjectDao = (MySqlSubjectDao) factory.getDao(connection, Subject.class);
        subject = new Subject();
        subject.setId(1);
        subject.setStudentId(1);
        subject.setTitle("Анатомия");
    }

    @AfterClass
    public static void tearDown() throws DaoException, SQLException {
        connection.rollback();
        subjectDao.closeDao();
    }

    @Test
    public void testRead() throws DaoException {
        Subject subject = subjectDao.read(1);
        assertNotNull(subject);
        assertNotNull(subject.getId());
    }

    @Test
    public void testInsert() throws DaoException {
        List<Subject> subjectsBefore = subjectDao.getAll();
        assertTrue(subjectDao.insert(subject));
        List<Subject> subjectsAfter = subjectDao.getAll();
        assertEquals(1, subjectsAfter.size() - subjectsBefore.size());
    }

    @Test
    public void testUpdate() throws DaoException {
        String titleBefore = subjectDao.read(1).getTitle();
        assertTrue(subjectDao.update(subject));
        String titleAfter = subjectDao.read(1).getTitle();
        assertNotEquals(titleBefore, titleAfter);
    }

    @Test
    public void testDelete() throws DaoException {
        List<Subject> subjectsBefore = subjectDao.getAll();
        assertTrue(subjectDao.delete(11));
        List<Subject> subjectsAfter = subjectDao.getAll();
        assertEquals(1, subjectsBefore.size() - subjectsAfter.size());
    }

    @Test
    public void testGetAll() throws DaoException {
        List<Subject> subjects = subjectDao.getAll();
        assertNotNull(subjects);
        assertTrue(subjects.size() > 0);
    }

    @Test
    public void testGetAllByStudentId() throws DaoException {
        List<Subject> subjects = subjectDao.getAllByStudentId(1);
        assertNotNull(subjects);
        assertTrue(subjects.size() > 0);
        assertEquals(Integer.valueOf(1), subjects.get(1).getStudentId());
    }

    @Test
    public void testGetAllWithMarksByStudentId() throws DaoException {
        List<Subject> subjects = subjectDao.getAllWithMarksByStudentId(1);
        assertNotNull(subjects);
        assertTrue(subjects.size() > 0);
        assertEquals(Integer.valueOf(1), subjects.get(1).getStudentId());
        assertNotNull(subjects.get(1).getMarks());
    }
}