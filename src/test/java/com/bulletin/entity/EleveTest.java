package com.bulletin.entity;

import com.bulletin.exception.NoteNotFoundException;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by geoffroy on 18/06/15.
 */
public class EleveTest {

    @Test
    public void testGetNoteByMatiere() throws Exception {
        Eleve eleve = new Eleve("nom", "prenom");
        Matiere matiere = new Matiere();
        Note note = new Note(eleve,matiere,NoteType.ABS);
        eleve.setNotes(Arrays.asList(note));

        assertThat(eleve.getNoteByMatiere(matiere), is(note));
    }

    @Test(expected = NoteNotFoundException.class)
    public void testGetNoteByMatiere_noteNotFound() throws Exception {
        Eleve eleve = new Eleve("nom", "prenom");
        Matiere matiere = new Matiere();
        Note note = new Note(eleve,matiere,NoteType.ABS);
        eleve.setNotes(Arrays.asList(note));

        eleve.getNoteByMatiere(new Matiere());
    }
}