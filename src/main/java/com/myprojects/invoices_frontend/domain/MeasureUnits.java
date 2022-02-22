package com.myprojects.invoices_frontend.domain;

public enum MeasureUnits {

    ITEM("szt."), KILOGRAM("kg"), METER("mb."), SET("kpl.");

    private final String measureUnit;

    MeasureUnits(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public int getId() {
        return ordinal();
    }

    public String getMeasureUnit() {
        return measureUnit;
    }
}
