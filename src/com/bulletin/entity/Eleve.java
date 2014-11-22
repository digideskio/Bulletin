package com.bulletin.entity;

import com.bulletin.exception.NoteNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoffroy on 12/11/14.
 */
public class Eleve {

    private String nom;

    private String prenom;

    private List<Note> notes;

    private String remarque;


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

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public Note getNoteByMatiere(Matiere matiere) throws NoteNotFoundException {
        for(Note note : notes) {
            if(note.getMatiere().equals(matiere)) {
                return note;
            }
        }
        throw new NoteNotFoundException(this.getNom(), matiere.getNom());
    }
}
