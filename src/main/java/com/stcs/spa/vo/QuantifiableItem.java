package com.stcs.spa.vo;

/**
 * Created by mroshan on 6/1/2016.
 */
public class QuantifiableItem {
    private String name,description;
    private  Unit unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
