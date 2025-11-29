package dao;

import com.github.dockerjava.api.DockerClient;
import entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoImplIT {

    private static UserDao userDao;
    private static SessionFactory sessionFactory;

    static {
        // Настройка подключения к Docker через Npipe (Windows)
        System.setProperty("DOCKER_HOST", "npipe:////./pipe/docker_engine");
        System.setProperty("DOCKER_TLS_VERIFY", "false");
    }

    // Контейнер PostgreSQL для тестов
    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("test_db")
            .withUsername("test_user")
            .withPassword("test_pass");

    @BeforeAll
    static void setUp() {
        System.out.println("DOCKER_HOST=" + System.getProperty("DOCKER_HOST"));

        // Настраиваем Hibernate на контейнер
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", postgresContainer.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgresContainer.getUsername());
        configuration.setProperty("hibernate.connection.password", postgresContainer.getPassword());
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.show_sql", "false");

        configuration.addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();

        userDao = new UserDaoImpl(sessionFactory);
    }

    @AfterAll
    static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void testCreateReadUpdateDeleteUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setAge(30);

        userDao.create(user);
        assertNotNull(user.getId(), "User ID should be assigned after creation");

        User fetchedUser = userDao.read(user.getId());
        assertEquals("Test User", fetchedUser.getName());
        assertEquals("test@example.com", fetchedUser.getEmail());
        assertEquals(30, fetchedUser.getAge());

        fetchedUser.setAge(35);
        userDao.update(fetchedUser);

        User updatedUser = userDao.read(fetchedUser.getId());
        assertEquals(35, updatedUser.getAge());

        userDao.delete(updatedUser);
        assertNull(userDao.read(updatedUser.getId()));
    }

    @Test
    void testReadAllUsers() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setEmail("u1@example.com");
        user1.setAge(20);

        User user2 = new User();
        user2.setName("User 2");
        user2.setEmail("u2@example.com");
        user2.setAge(25);

        userDao.create(user1);
        userDao.create(user2);

        List<User> users = userDao.readAll();
        assertTrue(users.size() >= 2, "There should be at least 2 users in DB");
    }

    @Test
    void testDockerConnection() {
        DockerClient client = DockerClientFactory.instance().client();
        System.out.println("Docker version: " + client.versionCmd().exec().getVersion());
        assertNotNull(client.versionCmd().exec().getVersion(), "Docker version should not be null");
    }
}
