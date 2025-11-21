# User Service (Intensive_Java_DataBase_New)

Консольное Java-приложение для управления пользователями (CRUD) с использованием Hibernate и PostgreSQL.  
Проект реализован без Spring, с использованием DAO-паттерна и транзакционности.

---

## Оглавление
- [Требования](#требования)
- [Установка и настройка](#установка-и-настройка)
- [Запуск проекта](#запуск-проекта)
- [Использование](#использование)
- [Структура проекта](#структура-проекта)
- [Логирование](#логирование)
- [Технические детали](#технические-детали)

---

## Требования
- JDK 17+
- Maven
- PostgreSQL (рекомендуется версия 12+)
- Подключение к базе PostgreSQL с созданной базой и пользователем (см. ниже)

---

## Установка и настройка

1. **Установка PostgreSQL**

    Скачать и установить PostgreSQL с официального сайта:  
    https://www.postgresql.org/download/

2. **Создание базы и пользователя**

    В psql или PgAdmin выполнить команды (замените имена и пароль на свои):

    ```sql
    CREATE DATABASE user_service_db;
    CREATE USER user_service_user WITH ENCRYPTED PASSWORD 'password';
    GRANT ALL PRIVILEGES ON DATABASE user_service_db TO user_service_user;
3. **Настройка hibernate.cfg.xml**

    В проекте в src/main/resources/hibernate.cfg.xml проверьте параметры подключения:

    ```
    <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/user_service_db</property>
    <property name="hibernate.connection.username">user_service_user</property>
    <property name="hibernate.connection.password">password</property>
    ```
   
    Убедитесь, что параметры соответствуют вашей базе.

4. **Сборка проекта**

    В терминале, находясь в корне проекта, выполните:

    ```
    mvn clean package
    ```

    Это скачает все зависимости и соберёт jar.

---

## Запуск проекта

   Вы можете запустить проект из IDE, вызвав класс Main с методом main.

   Или из консоли:
   ```
   java -cp target/Intensive_Java_DataBase_New-1.0-SNAPSHOT.jar;путь_к_зависимостям/* Main
   ```
   В Windows в качестве разделителя путей используйте ;, в Linux/macOS — :.

---

## Использование

   После запуска в консоли появится меню:
   ```
   --- User Service Menu ---
   1. Create User
   2. Read User
   3. Update User
   4. Delete User
   5. Exit
   Choose an option:
   ```

- Введите номер нужного действия и нажмите Enter.
- Следуйте подсказкам (вводите имя, email, возраст и т.д.).
- Для выхода выберите пункт 5.

---

## Структура проекта

- entity — класс User, описывает сущность пользователя.
- dao — реализация интерфейса для работы с базой данных (CRUD).
- service — бизнес-логика приложения.
- menu — классы для взаимодействия с пользователем через консоль.
- util — классы для настройки Hibernate.

---

## Логирование

   Для логирования используется SLF4J с Log4j. Конфигурация в файле src/main/resources/log4j.properties.
   Выводит информацию об ошибках и основных действиях.

---

## Технические детали

- Hibernate ORM 6.3.0.Final 
- PostgreSQL JDBC Driver 42.6.0 
- Java Persistence API (Jakarta Persistence)
- Maven для управления зависимостями и сборки 
- JDK 17+