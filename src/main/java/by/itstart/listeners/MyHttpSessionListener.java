package by.itstart.listeners;

import by.itstart.dao.DaoException;
import by.itstart.dto.Student;
import by.itstart.dto.Subject;
import by.itstart.mysql.MySqlDaoFactory;
import by.itstart.mysql.MySqlStudentDao;
import by.itstart.mysql.MySqlSubjectDao;

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
