User & Notification Services

Учебный проект с двумя микросервисами:

1. user-service – управление пользователями (CRUD, PostgreSQL, Kafka producer)
2. notification-service – отправка уведомлений на почту (Kafka consumer, Email)

Проект использует: Java 17+, Spring Boot, PostgreSQL, Kafka, Docker, Docker Compose.

Структура проекта:

project-root/
├── docker-compose.yml        # запуск всех сервисов и зависимостей
├── user-service/
│   ├── pom.xml
│   └── src/
└── notification-service/
├── pom.xml
└── src/

Требования:

- JDK 17+ (подходит и для Java 25)
- Maven 3.9+
- Docker и Docker Compose
- Почтовый аккаунт для отправки писем (SMTP)

Подготовка Docker:

Все сервисы и зависимости можно запускать через Docker Compose:

docker-compose up --build

Сервисы будут доступны на портах:

- PostgreSQL: localhost:5432
- Kafka: localhost:9092
- user-service: http://localhost:8080
- notification-service: http://localhost:8081

Сборка jar файлов:

Перед запуском контейнеров убедитесь, что собраны jar-файлы:

# Сборка user-service
cd user-service
mvn clean package

# Сборка notification-service
cd ../notification-service
mvn clean package

Docker контейнеры используют эти jar-файлы.

Конфигурация сервисов:

user-service:

Переменные окружения (docker-compose.yml):

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/usersdb
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=password
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092

notification-service:

Переменные окружения:

SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
SPRING_MAIL_HOST=smtp.example.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=your_email@example.com
SPRING_MAIL_PASSWORD=your_email_password

> Настройте реальные данные SMTP для отправки писем.

Запуск проекта:

1. Собрать jar-файлы (см. выше)
2. В корне проекта запустить Docker Compose:

docker-compose up --build

3. Проверьте логи сервисов:

docker-compose logs -f user-service
docker-compose logs -f notification-service

4. После запуска сервисов можно:
    - Создавать/удалять пользователей через user-service REST API
    - Получать уведомления по почте через notification-service

Тестирование:

- Unit tests – Maven + Mockito
- Integration tests – Testcontainers (PostgreSQL, Kafka)

Запуск тестов:

cd user-service
mvn test

cd ../notification-service
mvn test

REST API (user-service):

Пример одного endpoint:

- POST /users – создать пользователя
- DELETE /users/{id} – удалить пользователя (генерирует Kafka event)

> DTO используется для передачи данных, entity напрямую не возвращается.

Почтовые уведомления:

- При создании пользователя: "Здравствуйте! Ваш аккаунт на сайте успешно создан."
- При удалении пользователя: "Здравствуйте! Ваш аккаунт был удалён."

Уведомления приходят на email, указанный в пользователе.

Советы по отладке:

- Проверяйте логи контейнеров: docker-compose logs -f
- Kafka топики: user-events (по умолчанию)
- PostgreSQL: usersdb, пользователь user, пароль password
