package com.rogdanapp.stohastikalab1.data.pojo;

public class StepChance {
    private float sleepChance, upChance, downChance, leftChance, rightChance;

    public StepChance(float sleepChance, float upChance, float downChance, float leftChance, float rightChance) {
        this.sleepChance = sleepChance;
        this.upChance = upChance;
        this.downChance = downChance;
        this.leftChance = leftChance;
        this.rightChance = rightChance;
    }

    public float getSleepChance() {
        return sleepChance;
    }

    public void setSleepChance(float sleepChance) {
        this.sleepChance = sleepChance;
    }

    public float getUpChance() {
        return upChance;
    }

    public void setUpChance(float upChance) {
        this.upChance = upChance;
    }

    public float getDownChance() {
        return downChance;
    }

    public void setDownChance(float downChance) {
        this.downChance = downChance;
    }

    public float getLeftChance() {
        return leftChance;
    }

    public void setLeftChance(float leftChance) {
        this.leftChance = leftChance;
    }

    public float getRightChance() {
        return rightChance;
    }

    public void setRightChance(float rightChance) {
        this.rightChance = rightChance;
    }

    public static StepChance autoComplete() {
        //// TODO: 02.10.2017 random generation
        return new StepChance(0.2f, 0.2f, 0.2f, 0.2f, 0.2f);
    }
}
