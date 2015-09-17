package by.itstart.hibernate;

import by.itstart.dao.DaoException;

import by.itstart.dao.GenericDao;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class HibernateDaoFactoryTest {

    @Autowired
    @Qualifier("subjectDao")
    private GenericDao subjectDao;
    @Autowired
    @Qualifier("studentDao")
    private GenericDao studentDao;
    @Autowired
    @Qualifier("markDao")
    private GenericDao markDao;


    @Test
    public void testGetDao() throws DaoException {
        assertNotNull("Can not created studentDao", studentDao);
        assertNotNull("Can not created studentDao", subjectDao);
        assertNotNull("Can not created studentDao", markDao);
    }
}