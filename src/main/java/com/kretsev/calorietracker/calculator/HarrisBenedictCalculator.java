package com.kretsev.calorietracker.calculator;

import com.kretsev.calorietracker.model.Goal;
import com.kretsev.calorietracker.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HarrisBenedictCalculator {
    private static final double ACTIVITY_FACTOR = 1.375;
    private static final double BASE_COEFFICIENT = 88.36;
    private static final double WEIGHT_COEFFICIENT = 13.4;
    private static final double HEIGHT_COEFFICIENT = 4.8;
    private static final double AGE_COEFFICIENT = 5.7;
    private static final double WEIGHT_LOSS_COEFFICIENT = 0.8;
    private static final double MUSCLE_GAIN_COEFFICIENT = 1.2;

    public static int calculate(User user) {
        double bmr;

        if (user.getGoal() == null) {
            throw new IllegalArgumentException("Цель пользователя не может быть null");
        }

        bmr = BASE_COEFFICIENT
                + (WEIGHT_COEFFICIENT * user.getWeight())
                + (HEIGHT_COEFFICIENT * user.getHeight())
                + (AGE_COEFFICIENT * user.getAge());

        double dailyCalories = bmr * ACTIVITY_FACTOR;

        if (user.getGoal() == Goal.WEIGHT_LOSS) {
            dailyCalories *= WEIGHT_LOSS_COEFFICIENT;
        } else if (user.getGoal() == Goal.MUSCLE_GAIN) {
            dailyCalories *= MUSCLE_GAIN_COEFFICIENT;
        }

        return (int) Math.round(dailyCalories);
    }
}
