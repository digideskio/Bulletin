package com.bulletin.exception;

/**
 * Created by geoffroy on 12/11/14.
 */
public class MatiereNotFoundException extends Throwable {

    public MatiereNotFoundException(String m) {
        System.err.println("Matiere not found: " + m);
    }
}
