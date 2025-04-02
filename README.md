# Calorie Tracker API

Calorie Tracker — это REST API сервис для отслеживания дневной нормы калорий и учета съеденных блюд. Приложение позволяет пользователям создавать профили, добавлять блюда и приемы пищи, а также получать отчеты о потреблении калорий.

## Функционал

### Основные возможности:
- **Управление пользователями**:
    - Создание, редактирование, удаление профилей пользователей.
    - Указание возраста, веса, роста и цели (поддержание веса, снижение веса, набор массы).
- **Управление блюдами**:
    - Добавление, редактирование, удаление блюд с указанием калорий, белков, жиров и углеводов.
- **Управление приемами пищи**:
    - Создание, редактирование, удаление приемов пищи с привязкой к пользователю и списку блюд.
- **Отчеты**:
    - Ежедневный отчет о потребленных калориях и количестве приемов пищи.
    - Проверка соответствия дневной норме калорий.
    - История питания за указанный период.
- **Пагинация**:
    - Пагинированный список пользователей, блюд и приемов пищи.

### Дополнительные возможности:
- Расчет дневной нормы калорий по формуле Харриса-Бенедикта.
- Валидация входящих данных.
- Обработка ошибок с понятными сообщениями.
- Документация API с использованием Swagger.

## Примененные технологии

- **Язык программирования**: Java 21
- **Фреймворк**: Spring Boot, Spring Data JPA
- **База данных**: PostgreSQL
- **Миграции базы данных**: Flyway
- **Сборка и управление зависимостями**: Gradle
- **Контейнеризация**: Docker, Docker Compose
- **Логирование**: SLF4J с Logback
- **Маппинг данных**: MapStruct
- **Тестирование**: JUnit, Mockito

## Структура проекта


calorietracker/
├── src/
│   ├── main/
│   │   ├── java/com/kretsev/calorietracker/
│   │   │   ├── calculator/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── mapper/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   │   └── impl/
│   │   │   └── CalorieTrackerApplication.java
│   │   └── resources/
│   │       ├── db/migration/
│   │       ├── application.yml
│   │       └── .env
│   └── test/
│       └── java/com/kretsev/calorietracker/
├── postman/
│   └── CalorieTracker.postman_collection.json
├── Dockerfile
├── docker-compose.yml
├── build.gradle
├── gradle.properties
├── settings.gradle
└── README.md


## Локальный запуск проекта

### Предварительные требования

1. Установите [Docker](https://docs.docker.com/get-docker/) и [Docker Compose](https://docs.docker.com/compose/install/).
2. Установите [Java 21](https://openjdk.org/projects/jdk/21/).
3. Установите [Maven](https://maven.apache.org/install.html).

### Шаги для запуска

1. **Клонируйте репозиторий**:
   ```bash
   git clone https://github.com/your-repo/calorietracker.git
   cd calorietracker
   ```

2. **Настройте переменные окружения:**:
   ```env
    CALORIE_DB=calorie_tracker
    CALORIE_USER=postgres
    CALORIE_PASSWORD=yourpassword
   ```
3. **Запустите проект с помощью Docker Compose:**:
   ```bash
   docker-compose up --build
   ```
   Это запустит:
- PostgreSQL базу данных.
- Spring Boot приложение.

4. **Остановка проекта:**:  
   Чтобы остановить проект, выполните:
    ```bash
   docker-compose down
   ```

### Миграции базы данных
Миграции базы данных выполняются автоматически с помощью Flyway при запуске приложения.
Все миграции находятся в папке src/main/resources/db/migration.

## Примеры запросов

### Создание пользователя
   ```bash
curl -X POST "http://localhost:8080/api/v1/users" \
-H "Content-Type: application/json" \
-d '{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "age": 30,
  "weight": 70.0,
  "height": 175.0,
  "goal": "MAINTENANCE"
}'
   ```

### Создание блюда
   ```bash
 curl -X POST "http://localhost:8080/api/v1/dishes" \
-H "Content-Type: application/json" \
-d '{
  "name": "Salad",
  "calories": 300,
  "proteins": 10.0,
  "fats": 5.0,
  "carbs": 20.0
}'
   ```

### Создание приема пищи
   ```bash
 curl -X POST "http://localhost:8080/api/v1/meals" \
-H "Content-Type: application/json" \
-d '{
  "userId": 1,
  "mealDate": "2025-04-01T12:00:00",
  "dishIds": [1]
}'
   ```

### Получение ежедневного отчета
   ```bash
 curl -X GET "http://localhost:8080/api/v1/users/1/report/daily?date=2025-04-01" \
-H "Content-Type: application/json"
   ```

### Проверка нормы калорий
   ```bash
 curl -X GET "http://localhost:8080/api/v1/users/1/report/check?date=2025-04-01" \
-H "Content-Type: application/json"
   ```

### Получение истории питания
   ```bash
 curl -X GET "http://localhost:8080/api/v1/users/1/report/history?startDate=2025-04-01&endDate=2025-04-05" \
-H "Content-Type: application/json"
   ```

### Тестирование

Для тестирования API используйте [Postman-коллекцию](./postman/CalorieTracker.postman_collection.json).

#### Автор: Андрей Крецев
#### GitHub: https://github.com/akrecev
#### Email: akrecev@gmail.com
#### @akrecev