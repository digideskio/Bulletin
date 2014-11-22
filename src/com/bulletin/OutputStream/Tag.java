package com.bulletin.outputStream;

/**
 * Created by geoffroy on 20/11/14.
 */
public enum Tag {

    ROOT_MATIERE("##ROOTMATIERE##"),
    NOM("##NOM##"),
    PRENOM("##PRENOM##"),
    BASIC_LINE("##BASICLINE##"),
    MATIERE_NAME("#MATIERE_NAME#"),
    R("#R#"),
    RF("#RF#"),
    NR("#NR#"),
    RM("#RM#"),
    RR("#RR#"),
    //FIRST_PAGE("##FIRST_PAGE##"),
    //SECOND_PAGE("##SECOND_PAGE##"),
    CONTENT("##CONTENT##"),
    REMARQUE("##REMARQUE##"),
    FIRST_YEAR_3_CHARS("#FIRST_YEAR_3_CHARS#"),
    FIRST_YEAR_LAST_CHAR("#FIRST_YEAR_LAST_CHAR#"),
    SECOND_YEAR_3_CHARS("#SECOND_YEAR_3_CHARS#"),
    SECOND_YEAR_LAST_CHAR("#SECOND_YEAR_LAST_CHARS#"),
    ONE_STUDENT("##ONE_STUDENT##");


    private String shortName;

    private Tag(String name) {
        this.shortName = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
