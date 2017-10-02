package com.rogdanapp.stohastikalab1.data;

import com.rogdanapp.stohastikalab1.data.pojo.Field;
import com.rogdanapp.stohastikalab1.data.pojo.Unit;

public class InMemoryStore {
    private Field field;
    private Unit unit;

    public void setField(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }
}
