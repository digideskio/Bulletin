package com.bulletin.outputStream;

/**
 * Created by geoffroy on 27/06/15.
 */
public enum Trimester {
    FIRST_TRIMESTER(1, "er"),
    SECOND_TRIMESTER(2, "eme"),
    THIRD_TRIMESTER(3,"eme");

    private final Integer number;
    private final String upperText;

    Trimester(int number, String upperText) {
        this.number  = number;
        this.upperText = upperText;
    }

    public Integer getNumber() {
        return number;
    }

    public String getUpperText() {
        return upperText;
    }
}
