package com.rogdanapp.stohastikalab1.data.pojo;

import java.util.Random;

public class Field {
    private int width, height;
    private ExperimentPoint points[][];

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.points = new ExperimentPoint[width][height];
    }

    public void clear() {
        points = new ExperimentPoint[width][height];
    }

    public void incOnPointSteps(int pointX, int pointY, int moveDirection) {
        ExperimentPoint experimentPoint = points[pointX][pointY];
        if (experimentPoint == null) {
            experimentPoint = new ExperimentPoint();
            experimentPoint.setxCoordinate(pointX);
            experimentPoint.setyCoordinate(pointY);
        }

        experimentPoint.incMovesCount(moveDirection);
        points[pointX][pointY] = experimentPoint;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isOnField(int coordinateX, int coordinateY) {
        return coordinateX >= 0 && coordinateX < width &&
                coordinateY >= 0 && coordinateY < height;
    }

    public static Field autoComplete() {
        Random random = new Random();
        int x = random.nextInt(MAX_RANDOM_VALUE - MIN_RANDOM_VALUE) + MIN_RANDOM_VALUE;
        int y = random.nextInt(MAX_RANDOM_VALUE - MIN_RANDOM_VALUE) + MIN_RANDOM_VALUE;

        return new Field(x, y);
    }

    public static final int MIN_RANDOM_VALUE = 1;
    public static final int MAX_RANDOM_VALUE = 12;
}
