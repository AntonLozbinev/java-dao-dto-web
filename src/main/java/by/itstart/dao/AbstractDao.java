package by.itstart.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractDao<T> implements GenericDao<T> {

    private SessionFactory sessionFactory;

    public AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected abstract Class getCurrentClass();

    protected abstract String getDeleteQuery();

    @Override
    @Transactional(readOnly = true)
    public T read(Integer id) throws DaoException {
        return (T) getCurrentSession().get(getCurrentClass(), id);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean insert(T object) throws DaoException {
        try {
            getCurrentSession().save(object);
        } catch (Exception e) {
            throw new DaoException("Can not insert object", e);
        }
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean update(T object) throws DaoException {
        try {
            getCurrentSession().merge(object);
        } catch (Exception e) {
            throw new DaoException("Can not update object", e);
        }
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean delete(Integer id) throws DaoException {
        try {
            getCurrentSession().createQuery(getDeleteQuery()).setInteger("id", id).executeUpdate();
        } catch (Exception e) {
            throw new DaoException("Can not delete object", e);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() throws DaoException {
        List<T> objects;
        try {
            objects = getCurrentSession().createCriteria(getCurrentClass()).list();
        } catch (Exception e) {
            throw new DaoException("Can not select all objects", e);
        }
        return objects;
    }

    @Override
    public List<T> getAllBySubjectId(Integer id) throws DaoException {
        return null;
    }

    @Override
    public List<T> getAllByStudentId(Integer id) throws DaoException {
        return null;
    }

    @Override
    public List<T> getAllWithMarksByStudentId(Integer id) throws DaoException {
        return null;
    }

    @Override
    public void closeDao() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
