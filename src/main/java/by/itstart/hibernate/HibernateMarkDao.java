package by.itstart.hibernate;

import by.itstart.dao.AbstractDao;
import by.itstart.dao.DaoException;
import by.itstart.dto.Mark;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class HibernateMarkDao extends AbstractDao<Mark> {

    public HibernateMarkDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class getCurrentClass() {
        return Mark.class;
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from Mark where subjectId=:id";
    }


    @Override
    @Transactional(readOnly = true)
    public List<Mark> getAllBySubjectId(Integer id) throws DaoException {
        List<Mark> marks;
        try {
            marks = getCurrentSession().createQuery("from Mark where subjectId=:id").setInteger("id", id).list();
        } catch (Exception e) {
            throw new DaoException("Can not select all Marks by subject id " + id, e);
        }
        return marks;
    }
}
