package com.bulletin.entity;

/**
 * Created by geoffroy on 12/11/14.
 */
public class Note {

    private Eleve eleve;
    private Matiere matiere;
    private NoteType noteType;

    public Note(Eleve eleve, Matiere matiere, NoteType noteType) {
        this.eleve = eleve;
        eleve.getNotes().add(this);
        this.matiere = matiere;
        this.noteType = noteType;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

}
