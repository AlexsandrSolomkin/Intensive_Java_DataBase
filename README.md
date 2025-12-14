User Service

Микросервис для управления пользователями.
При добавлении или удалении пользователя отправляет сообщение в Kafka.

Требования:
- Java 17+
- Maven
- PostgreSQL
- Kafka (локальный брокер на localhost:9092)

Настройка базы данных:
1. Создайте базу данных и пользователя PostgreSQL:

CREATE DATABASE user_service_db;
CREATE USER user_service_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE user_service_db TO user_service_user;

2. Таблица "users" будет создана автоматически при запуске приложения.

Настройка Kafka:
- Kafka должен быть запущен на localhost:9092.
- Топик "user-events" создается автоматически или вручную:

kafka-topics.sh --create --topic user-events --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

Запуск приложения:
1. Перейдите в директорию user-service.
2. Выполните команду:

mvn spring-boot:run

3. Приложение будет доступно на http://localhost:8080.

API:

Создать пользователя:
POST /users
Content-Type: application/json

{
"name": "Иван",
"email": "ivan@example.com",
"age": 25
}

Удалить пользователя:
DELETE /users/{id}

> При этих операциях отправляются сообщения в Kafka в топик "user-events".

Проверка Kafka:
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic user-events --from-beginning
