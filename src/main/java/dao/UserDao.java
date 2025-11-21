package dao;

import entity.User;

import java.util.List;

public interface UserDao {
    void create(User user);
    User read(Long id);
    List<User> readAll();
    void update(User user);
    void delete(User user);
}
