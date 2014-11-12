package com.bulletin.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoffroy on 12/11/14.
 */
public class Eleve {

    private String nom;

    private String prenom;

    private List<Note> notes;


    public Eleve() {
        this.notes = new ArrayList<Note>();
    }

    public Eleve(String nom, String prenom) {
        this();
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Note getNoteByMatiere(Matiere matiere) {
        for(Note note : notes) {
            if(note.getMatiere().equals(matiere)) {
                return note;
            }
        }
        return null;
    }
}
