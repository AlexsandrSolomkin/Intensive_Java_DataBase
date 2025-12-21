User Service API

Проект — REST API для управления пользователями, реализованный на Spring Boot с использованием PostgreSQL, Spring Data JPA, MapStruct, Lombok, HATEOAS и Swagger (Springdoc OpenAPI).

Оглавление
- Технологии
- Требования
- Настройка базы данных
- Запуск проекта
- Документация API
- Примеры запросов

Технологии
- Java 17
- Spring Boot 3.3.3
- PostgreSQL
- Spring Data JPA
- Lombok
- MapStruct
- Spring HATEOAS
- Springdoc OpenAPI (Swagger UI)
- Maven

Требования
- JDK 17 или выше
- Maven
- PostgreSQL
- Среда разработки (IntelliJ IDEA, VSCode и др.)

Настройка базы данных
1. Установи PostgreSQL (если не установлен).
2. Создай базу данных:

   CREATE DATABASE user_service_db;

3. Создай пользователя с паролем (можно изменить в application.yml):

   CREATE USER user_service_user WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE user_service_db TO user_service_user;

4. Проверь настройки в src/main/resources/application.yml:

spring:
datasource:
url: jdbc:postgresql://localhost:5432/user_service_db
username: user_service_user
password: your_password
jpa:
hibernate:
ddl-auto: update
show-sql: true
properties:
hibernate:
format_sql: true
server:
port: 8080

Запуск проекта
1. Склонируй репозиторий или скачай код проекта.
2. В терминале перейди в папку проекта.
3. Собери и запусти проект командой:

   mvn clean spring-boot:run

Или запусти главный класс UserServiceApplication из IDE.

Документация API
После запуска приложение доступно по адресу:
http://localhost:8080/swagger-ui/index.html

Там можно увидеть всю документацию по API, протестировать запросы через удобный веб-интерфейс.

Примеры основных эндпоинтов

Метод  | URL          | Описание
-------|--------------|--------------------------
GET    | /users       | Получить всех пользователей
GET    | /users/{id}  | Получить пользователя по ID
POST   | /users       | Создать пользователя
PUT    | /users/{id}  | Обновить пользователя
DELETE | /users/{id}  | Удалить пользователя

Пример запроса на создание пользователя (POST /users)

{
"name": "Oleg",
"email": "oleg@mail.com",
"age": 22
}

Пример успешного ответа с HATEOAS (GET /users/1)

{
"id": 1,
"name": "Oleg",
"email": "oleg@mail.com",
"age": 22,
"_links": {
"self": {
"href": "http://localhost:8080/users/1"
},
"users": {
"href": "http://localhost:8080/users"
}
}
}