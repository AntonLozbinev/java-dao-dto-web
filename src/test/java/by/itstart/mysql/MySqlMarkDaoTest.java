package by.itstart.mysql;

import by.itstart.dao.GenericDao;
import by.itstart.dao.DaoException;
import by.itstart.dto.Mark;
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
public class MySqlMarkDaoTest {

    @Autowired
    private GenericDao markDao;
    private static Mark mark;

    @BeforeClass
    public static void setUp() throws DaoException, SQLException {
        mark = new Mark();
        mark.setId(1);
        mark.setStudentId(1);
        mark.setSubjectId(1);
        mark.setMark(1);
    }

    @Test
    public void testRead() throws DaoException {
        Mark mark = (Mark) markDao.read(1);
        assertNotNull(mark);
        assertNotNull(mark.getId());
    }

    @Test
    public void testInsert() throws DaoException {
        List<Mark> marksBefore = markDao.getAll();
        assertTrue(markDao.insert(mark));
        List<Mark> marksAfter = markDao.getAll();
        assertEquals(1, marksAfter.size() - marksBefore.size());
    }

    @Test
    public void testUpdate() throws DaoException {
        int markBefore = ((Mark)markDao.read(1)).getMark();
        assertTrue(markDao.update(mark));
        int markAfter = ((Mark)markDao.read(1)).getMark();
        assertNotEquals(markBefore, markAfter);
    }

    @Test
    public void testDelete() throws DaoException {
        List<Mark> marksBefore = markDao.getAll();
        assertTrue(markDao.delete(4));
        List<Mark> marksAfter = markDao.getAll();
        assertEquals(3, marksBefore.size() - marksAfter.size());
    }

    @Test
    public void testGetAll() throws DaoException {
        List<Mark> marks = markDao.getAll();
        assertNotNull(marks);
        assertTrue(marks.size() > 0);
    }

    @Test
    public void testCloseDao() throws DaoException {
        List<Mark> marks = markDao.getAllBySubjectId(1);
        assertNotNull(marks);
        assertTrue(marks.size() > 0);
        assertEquals(Integer.valueOf(1), marks.get(0).getId());
    }
}