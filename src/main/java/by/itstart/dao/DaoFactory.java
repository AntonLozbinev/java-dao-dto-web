package by.itstart.dao;

import org.hibernate.SessionFactory;

public interface DaoFactory {

    interface DaoCreator {
        GenericDao create(SessionFactory sessionFactory);
    }

    GenericDao getDao(SessionFactory sessionFactory, Class dtoClass);
}
