package com.bulletin.entity;

/**
 * Created by geoffroy on 12/11/14.
 */
public enum NoteType {
    NR("NR","Non Réussite", 0, "X"),
    RR("RR", "Réussite Rare", 1, "X"),
    R("R","Réussite", 4, "X"),
    RM("RM", "Réussite Moyenne", 2, "X"),
    RF("RF", "Réussite Fréquente", 3, "X");

    private String shortName;
    private String name;
    private String displayValue;
    private int numericalValue;

    NoteType(String shortName, String name, int numericalValue, String displayValue) {
        this.name = name;
        this.shortName = shortName;
        this.numericalValue = numericalValue;
        this.displayValue = displayValue;
    }

    public String getShortName() {
        return shortName;
    }

    public String getName() {
        return name;
    }

    public int getNumericalValue() {
        return numericalValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public static NoteType fromShortName(String shortName) {
        for(NoteType noteType : values()) {
            if(noteType.getShortName().equals(shortName)) {
                return noteType;
            }
        }
        throw new IllegalArgumentException();
    }
}
