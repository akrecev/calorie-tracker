CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       age INT CHECK (age >= 0 AND age <= 150),
                       weight DOUBLE PRECISION CHECK (weight > 0 AND weight <= 350),
                       height DOUBLE PRECISION CHECK (height > 0 AND height <= 250),
                       goal VARCHAR(50) NOT NULL DEFAULT 'MAINTENANCE',
                       daily_calorie_norm INT NOT NULL
);

CREATE TABLE dishes (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        calories INT NOT NULL CHECK (calories >= 0),
                        proteins DOUBLE PRECISION NOT NULL CHECK (proteins >= 0),
                        fats DOUBLE PRECISION NOT NULL CHECK (fats >= 0),
                        carbs DOUBLE PRECISION NOT NULL CHECK (carbs >= 0)
);

CREATE TABLE meals (
                       id BIGSERIAL PRIMARY KEY,
                       user_id BIGINT NOT NULL,
                       meal_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE meal_dishes (
                             meal_id BIGINT NOT NULL,
                             dish_id BIGINT NOT NULL,
                             PRIMARY KEY (meal_id, dish_id),
                             FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE,
                             FOREIGN KEY (dish_id) REFERENCES dishes(id) ON DELETE CASCADE
);
