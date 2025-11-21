package service;

import dao.UserDao;
import dao.UserDaoImpl;
import entity.User;

import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDaoImpl();

    public void createUser(User user) { userDao.create(user); }
    public User getUser(Long id) { return userDao.read(id); }
    public List<User> getAllUsers() { return userDao.readAll(); }
    public void updateUser(User user) { userDao.update(user); }
    public void deleteUser(User user) { userDao.delete(user); }
}
