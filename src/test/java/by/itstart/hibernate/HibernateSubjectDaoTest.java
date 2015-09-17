package by.itstart.hibernate;

import by.itstart.dao.GenericDao;
import by.itstart.dao.DaoException;
import by.itstart.dto.Subject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
@SuppressWarnings("unchecked")
@Transactional
public class HibernateSubjectDaoTest {

    @Autowired
    private GenericDao subjectDao;

    @Test
    public void testRead() throws DaoException {
        Subject subject = (Subject) subjectDao.read(1);
        assertNotNull(subject);
        assertNotNull(subject.getId());
    }

    @Test
    public void testInsert() throws DaoException {
        Subject subject = new Subject();
        subject.setId(1);
        subject.setStudentId(1);
        subject.setTitle("Анатомия");
        List<Subject> subjectsBefore = subjectDao.getAll();
        assertTrue(subjectDao.insert(subject));
        List<Subject> subjectsAfter = subjectDao.getAll();
        assertEquals(1, subjectsAfter.size() - subjectsBefore.size());
    }

    @Test
    public void testUpdate() throws DaoException {
        Subject subject = new Subject();
        subject.setId(2);
        subject.setStudentId(2);
        subject.setTitle("Анатомия");
        String titleBefore = ((Subject)subjectDao.read(2)).getTitle();
        assertTrue(subjectDao.update(subject));
        String titleAfter = ((Subject)subjectDao.read(2)).getTitle();
        assertNotEquals(titleBefore, titleAfter);
    }

    @Test
    public void testDelete() throws DaoException {
        List<Subject> subjectsBefore = subjectDao.getAll();
        assertTrue(subjectDao.delete(9));
        List<Subject> subjectsAfter = subjectDao.getAll();
        assertEquals(1, subjectsBefore.size() - subjectsAfter.size());
    }

    @Test
    public void testGetAll() throws DaoException {
        List<Subject> subjects = subjectDao.getAll();
        assertNotNull(subjects);
        assertEquals(11, subjects.size());
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