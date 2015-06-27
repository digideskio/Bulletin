package com.bulletin.outputStream;

/**
 * Created by geoffroy on 27/06/15.
 */
public enum Trimester {
    FIRST_TRIMESTER(1, "er"),
    SECOND_TRIMESTER(2, "eme"),
    THIRD_TRIMESTER(3,"eme");

    private final Integer number;
    private final String exposant;

    Trimester(int number, String exposant) {
        this.number  = number;
        this.exposant = exposant;
    }

    public Integer getNumber() {
        return number;
    }

    public String getExposant() {
        return exposant;
    }
}
