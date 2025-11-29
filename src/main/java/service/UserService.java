package service;

import dao.UserDao;
import dao.UserDaoImpl;
import entity.User;

import java.util.List;

public class UserService {

    private UserDao userDao; // убрали final

    // Конструктор по умолчанию
    public UserService() {
        this.userDao = new UserDaoImpl();
    }

    // Конструктор для тестов с Mockito
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (user.getAge() != null && user.getAge() <= 0) {
            throw new IllegalArgumentException("Age must be positive");
        }
        userDao.create(user);
    }

    public User getUser(Long id) {
        return userDao.read(id);
    }

    public List<User> getAllUsers() {
        return userDao.readAll();
    }

    public void updateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (user.getAge() != null && user.getAge() <= 0) {
            throw new IllegalArgumentException("Age must be positive");
        }
        userDao.update(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }
}