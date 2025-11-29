package util;

import org.hibernate.SessionFactory;

public class HibernateUtilTest {

    private static SessionFactory sessionFactory;

    public static void setSessionFactory(SessionFactory factory) {
        sessionFactory = factory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
