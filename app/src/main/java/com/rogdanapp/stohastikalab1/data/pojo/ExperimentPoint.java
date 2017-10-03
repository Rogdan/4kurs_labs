package com.rogdanapp.stohastikalab1.data.pojo;

import static com.rogdanapp.stohastikalab1.data.Constants.*;

public class ExperimentPoint {
    private int xCoordinate;
    private int yCoordinate;
    private int [] experimentResults;

    public ExperimentPoint() {
        clear();
    }

    public void clear() {
        xCoordinate = 0;
        yCoordinate = 0;
        experimentResults = new int[DIRECTIONS_COUNT];
    }

    public void incMovesCount(int direction) {
        experimentResults[direction]++;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExperimentPoint)) return false;

        ExperimentPoint that = (ExperimentPoint) o;

        return xCoordinate == that.xCoordinate && yCoordinate == that.yCoordinate;

    }

    @Override
    public int hashCode() {
        int result = xCoordinate;
        result = 31 * result + yCoordinate;
        return result;
    }
}
