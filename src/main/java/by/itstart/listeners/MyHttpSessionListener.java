package main.java.by.itstart.listeners;

import main.java.by.itstart.dao.DaoException;
import main.java.by.itstart.dto.Student;
import main.java.by.itstart.dto.Subject;
import main.java.by.itstart.mysql.MySqlDaoFactory;
import main.java.by.itstart.mysql.MySqlStudentDao;
import main.java.by.itstart.mysql.MySqlSubjectDao;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        MySqlDaoFactory factory = new MySqlDaoFactory();
        try {
            MySqlStudentDao studentDao = (MySqlStudentDao) factory.getDao(factory.getConnection(), Student.class);
            MySqlSubjectDao subjectDao = (MySqlSubjectDao) factory.getDao(factory.getConnection(), Subject.class);
            httpSessionEvent.getSession().setAttribute("studentDao", studentDao);
            httpSessionEvent.getSession().setAttribute("subjectDao", subjectDao);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        MySqlStudentDao studentDao = (MySqlStudentDao) httpSessionEvent.getSession().getAttribute("studentDao");
        MySqlSubjectDao subjectDao = (MySqlSubjectDao) httpSessionEvent.getSession().getAttribute("subjectDao");
        try {
            if (studentDao != null) {
                studentDao.closeDao();
            }
            if (subjectDao != null) {
                subjectDao.closeDao();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
