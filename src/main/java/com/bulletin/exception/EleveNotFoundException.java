package com.bulletin.exception;

/**
 * Created by geoffroy on 13/11/14.
 */
public class EleveNotFoundException extends Exception {

    public EleveNotFoundException(String m) {
        System.err.println("Eleve not found: " + m);
    }
}
