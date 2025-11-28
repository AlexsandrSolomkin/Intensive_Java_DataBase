package dao;

import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.log4j.Logger;
import util.HibernateUtil;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public void create(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            // Сохраняем пользователя
            session.persist(user);

            tx.commit();
            logger.info("User created: " + user);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                logger.warn("Transaction rollback due to exception during create.", e);
            }
            logger.error("Failed to create user: " + user, e);
        }
    }

    @Override
    public User read(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                logger.info("User not found with id: " + id);
            } else {
                logger.info("User read: " + user);
            }
            return user;
        } catch (Exception e) {
            logger.error("Failed to read user with id: " + id, e);
            return null;
        }
    }

    @Override
    public List<User> readAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("from User", User.class).list();
            logger.info("Read all users, count: " + users.size());
            return users;
        } catch (Exception e) {
            logger.error("Failed to read all users", e);
            return null;
        }
    }

    @Override
    public void update(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.merge(user);

            tx.commit();
            logger.info("User updated: " + user);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                logger.warn("Transaction rollback due to exception during update.", e);
            }
            logger.error("Failed to update user: " + user, e);
        }
    }

    @Override
    public void delete(User user) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.remove(user);

            tx.commit();
            logger.info("User deleted: " + user);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                logger.warn("Transaction rollback due to exception during delete.", e);
            }
            logger.error("Failed to delete user: " + user, e);
        }
    }
}