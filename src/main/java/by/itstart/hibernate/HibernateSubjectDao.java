package by.itstart.hibernate;

import by.itstart.dao.AbstractDao;
import by.itstart.dao.DaoException;
import by.itstart.dto.Subject;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class HibernateSubjectDao extends AbstractDao<Subject> {

    public HibernateSubjectDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from Subject where id=:id";
    }

    @Override
    protected Class getCurrentClass() {
        return Subject.class;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean delete(Integer id) throws DaoException {
        getCurrentSession().createQuery("delete from Mark where subjectId=:id").setInteger("id", id).executeUpdate();
        return super.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> getAllWithMarksByStudentId(Integer id) throws DaoException {
        return getAllByStudentId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> getAllByStudentId(Integer id) throws DaoException {
        List<Subject> subjects;
        try {
            subjects = getCurrentSession().createQuery("from Subject where studentId=:id").setInteger("id", id).list();
        } catch (Exception e) {
            throw new DaoException("Can not select all subjects by student id " + id, e);
        }
        return subjects;
    }
}
