package com.rogdanapp.stohastikalab1.data.pojo;

import java.util.Random;

import static com.rogdanapp.stohastikalab1.data.Constants.DIRECTIONS_COUNT;
import static com.rogdanapp.stohastikalab1.data.Constants.DOWN_DIRECTION;
import static com.rogdanapp.stohastikalab1.data.Constants.LEFT_DIRECTION;
import static com.rogdanapp.stohastikalab1.data.Constants.RIGHT_DIRECTION;
import static com.rogdanapp.stohastikalab1.data.Constants.UP_DIRECTION;

public class Unit {
    private int startX, startY;
    private int actualX, actualY;
    private StepChance stepChance;
    private int [] stepsCounter;

    public Unit(StepChance stepChance, int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        this.stepChance = stepChance;

        clear();
    }

    public void clear(){
        stepsCounter = new int[DIRECTIONS_COUNT];
        moveToStart();
    }

    public void moveToStart() {
        actualX = startX;
        actualY = startY;
    }

    public int getStepsCountForDirection(int direction) {
        return stepsCounter[direction];
    }

    public int randomStep(){
        int randomDirection = stepChance.randomDirection();

        switch (randomDirection) {
            case UP_DIRECTION:
                actualY++;
                break;
            case DOWN_DIRECTION:
                actualY--;
                break;
            case RIGHT_DIRECTION:
                actualX++;
                break;
            case LEFT_DIRECTION:
                actualX--;
                break;
        }

        stepsCounter[randomDirection]++;
        return randomDirection;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getActualX() {
        return actualX;
    }

    public void setActualX(int actualX) {
        this.actualX = actualX;
    }

    public int getActualY() {
        return actualY;
    }

    public void setActualY(int actualY) {
        this.actualY = actualY;
    }

    public StepChance getStepChance() {
        return stepChance;
    }

    public void setStepChance(StepChance stepChance) {
        this.stepChance = stepChance;
    }

    public static Unit autoComplete(Field field) {
        Random random = new Random();
        int startX = random.nextInt(field.getWidth());
        int startY = random.nextInt(field.getHeight());
        StepChance stepChance = StepChance.autoComplete();

        return new Unit(stepChance, startX, startY);
    }
}
