package com.bulletin.exception;

/**
 * Created by geoffroy on 13/11/14.
 */
public class NoteNotFoundException extends Exception {

    public NoteNotFoundException(String eleveName, String matiereName) {
        System.err.println("Note not found for eleve: " + eleveName + " and matiere " +matiereName);
    }
}
