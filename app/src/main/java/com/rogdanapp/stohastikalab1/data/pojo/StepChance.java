package com.rogdanapp.stohastikalab1.data.pojo;

import java.util.Random;

import static com.rogdanapp.stohastikalab1.data.Constants.*;

public class StepChance {
    private Random random;
    private float chances[];

    public StepChance(float sleepChance, float upChance, float downChance, float leftChance, float rightChance) {
        this.random = new Random();
        chances = new float[DIRECTIONS_COUNT];

        setChance(SLEEP_DIRECTION, sleepChance);
        setChance(UP_DIRECTION, upChance);
        setChance(DOWN_DIRECTION, downChance);
        setChance(LEFT_DIRECTION, leftChance);
        setChance(RIGHT_DIRECTION, rightChance);
    }

    public int randomDirection() {
        float randomValue = random.nextFloat();

        for (int i = 0; i < DIRECTIONS_COUNT; i++) {
            float directionChance = chances[i];

            if (randomValue <= directionChance) {
                return i;
            } else {
                randomValue -= directionChance;
            }
        }

        return SLEEP_DIRECTION;
    }

    public void setChance(int direction, float value) {
        chances[direction] = value;
    }

    public float getChance(int direction) {
        return chances[direction];
    }

    public static StepChance autoComplete() {
        //// TODO: 02.10.2017 random generation
        return new StepChance(0.2f, 0.2f, 0.2f, 0.2f, 0.2f);
    }
}
