package service;

import dao.UserDao;
import dao.UserDaoImpl;
import entity.User;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Сервисный слой для работы с пользователями.
 * Оборачивает DAO методы и логирует операции.
 */
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);
    private final UserDao userDao = new UserDaoImpl();

    public void createUser(User user) {
        try {
            userDao.create(user);
            logger.info("User created: " + user);
        } catch (Exception e) {
            logger.error("Failed to create user: " + user, e);
            throw e; // можно пробросить дальше, если нужно
        }
    }

    public User getUser(Long id) {
        try {
            return userDao.read(id);
        } catch (Exception e) {
            logger.error("Failed to read user with ID: " + id, e);
            return null;
        }
    }

    public List<User> getAllUsers() {
        try {
            return userDao.readAll();
        } catch (Exception e) {
            logger.error("Failed to read all users", e);
            return List.of(); // возвращаем пустой список, чтобы не падало
        }
    }

    public void updateUser(User user) {
        try {
            userDao.update(user);
            logger.info("User updated: " + user);
        } catch (Exception e) {
            logger.error("Failed to update user: " + user, e);
            throw e;
        }
    }

    public void deleteUser(User user) {
        try {
            userDao.delete(user);
            logger.info("User deleted: " + user);
        } catch (Exception e) {
            logger.error("Failed to delete user: " + user, e);
            throw e;
        }
    }
}