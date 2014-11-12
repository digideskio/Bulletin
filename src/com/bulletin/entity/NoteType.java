package com.bulletin.entity;

/**
 * Created by geoffroy on 12/11/14.
 */
public enum NoteType {
    NR("NR","Non Réussite", 0),
    RR("RR", "Réussite Rare", 1),
    R("R","Réussite", 2),
    RM("RM", "Réussite Moyenne", 3),
    RF("RF", "Réussite Fréquente", 4);

    private String shortName;
    private String name;
    private int numericalValue;

    NoteType(String shortName, String name, int numericalValue) {
        this.name = name;
        this.shortName = shortName;
        this.numericalValue = numericalValue;
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

}
