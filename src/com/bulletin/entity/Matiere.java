package com.bulletin.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoffroy on 12/11/14.
 */
public class Matiere {

    private String nom;
    private String sheetName;
    private Matiere parentMatiere;
    private List<Matiere> childrenMatieres;

    public Matiere() {
        this.childrenMatieres = new ArrayList<Matiere>();
    }

    public Matiere(String nom, Matiere parentMatiere) {
        this();
        this.nom = nom;
        this.childrenMatieres = new ArrayList<Matiere>();
        this.parentMatiere = parentMatiere;
        addToParentChilds();

    }

    private void addToParentChilds() {
        if(this.parentMatiere != null) {
            this.parentMatiere.getChildrenMatieres().add(this);
        }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Matiere getParentMatiere() {
        return parentMatiere;
    }

    public void setParentMatiere(Matiere parentMatiere) {
        this.parentMatiere = parentMatiere;
        addToParentChilds();
    }

    public List<Matiere> getChildrenMatieres() {
        return childrenMatieres;
    }

    public void setChildrenMatieres(List<Matiere> childrenMatieres) {
        this.childrenMatieres = childrenMatieres;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matiere matiere = (Matiere) o;

        if (childrenMatieres != null ? !childrenMatieres.equals(matiere.childrenMatieres) : matiere.childrenMatieres != null)
            return false;
        if (nom != null ? !nom.equals(matiere.nom) : matiere.nom != null) return false;
        if (parentMatiere != null ? !parentMatiere.equals(matiere.parentMatiere) : matiere.parentMatiere != null)
            return false;
        if (sheetName != null ? !sheetName.equals(matiere.sheetName) : matiere.sheetName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nom != null ? nom.hashCode() : 0;
        result = 31 * result + (sheetName != null ? sheetName.hashCode() : 0);
        result = 31 * result + (parentMatiere != null ? parentMatiere.hashCode() : 0);
        result = 31 * result + (childrenMatieres != null ? childrenMatieres.hashCode() : 0);
        return result;
    }
}
