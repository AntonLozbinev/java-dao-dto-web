package by.itstart.hibernate;

import by.itstart.dao.GenericDao;
import by.itstart.dao.DaoFactory;
import by.itstart.dto.*;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.Map;

public class HibernateDaoFactory implements DaoFactory {

    private Map<Class, DaoCreator> daos;

    public HibernateDaoFactory() {
        daos = new HashMap<>();
        daos.put(Student.class, new DaoCreator() {
            @Override
            public GenericDao create(SessionFactory sessionFactory) {
                return new HibernateStudentDao(sessionFactory);
            }
        });
        daos.put(Subject.class, new DaoCreator() {
            @Override
            public GenericDao create(SessionFactory sessionFactory) {
                return new HibernateSubjectDao(sessionFactory);
            }
        });
        daos.put(Mark.class, new DaoCreator() {
            @Override
            public GenericDao create(SessionFactory sessionFactory) {
                return new HibernateMarkDao(sessionFactory);
            }
        });
    }

    @Override
    public GenericDao getDao(SessionFactory sessionFactory, Class dtoClass) {
        DaoCreator daoCreator = daos.get(dtoClass);
        return daoCreator.create(sessionFactory);
    }
}
