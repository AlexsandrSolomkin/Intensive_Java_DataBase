package service;

import dao.UserDao;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserDao userDaoMock;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDaoMock = mock(UserDao.class);
        userService = new UserService(userDaoMock); // теперь работает корректно
    }

    @Test
    void testCreateUser() {
        User user = new User.Builder().name("John").email("john@example.com").age(30).build();
        doNothing().when(userDaoMock).create(user);

        userService.createUser(user);

        verify(userDaoMock, times(1)).create(user);
    }

    @Test
    void testCreateUserWithEmptyEmail() {
        User user = new User.Builder().name("John").email("").age(30).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });
        assertEquals("Email cannot be empty", exception.getMessage());
    }

    @Test
    void testCreateUserWithNegativeAge() {
        User user = new User.Builder().name("John").email("john@example.com").age(-5).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });
        assertEquals("Age must be positive", exception.getMessage());
    }

    @Test
    void testGetUser() {
        User user = new User.Builder().name("Alice").email("alice@example.com").age(25).build();
        when(userDaoMock.read(1L)).thenReturn(user);

        User result = userService.getUser(1L);
        assertEquals("Alice", result.getName());
        verify(userDaoMock).read(1L);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User.Builder().name("U1").email("u1@example.com").age(20).build();
        User user2 = new User.Builder().name("U2").email("u2@example.com").age(22).build();
        when(userDaoMock.readAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        verify(userDaoMock).readAll();
    }

    @Test
    void testUpdateUser() {
        User user = new User.Builder().name("Bob").email("bob@example.com").age(40).build();
        doNothing().when(userDaoMock).update(user);

        userService.updateUser(user);

        verify(userDaoMock).update(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User.Builder().name("Carol").email("carol@example.com").age(35).build();
        doNothing().when(userDaoMock).delete(user);

        userService.deleteUser(user);

        verify(userDaoMock).delete(user);
    }
}