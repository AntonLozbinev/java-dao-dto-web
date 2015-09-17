package by.itstart.hibernate;

import by.itstart.dao.AbstractDao;
import by.itstart.dao.DaoException;
import by.itstart.dto.Student;
import org.hibernate.SessionFactory;

import java.util.List;

public class HibernateStudentDao extends AbstractDao<Student> {

    public HibernateStudentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class getCurrentClass() {
        return Student.class;
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from Student where id=:id";
    }
}
