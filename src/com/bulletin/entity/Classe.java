package com.bulletin.entity;

import com.bulletin.exception.EleveNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoffroy on 13/11/14.
 */
public class Classe {
    List<Eleve> eleves;

    public Classe() {
        eleves = new ArrayList<Eleve>();
    }

    public Classe(List<Eleve> eleves) {
        this.eleves = eleves;
    }

    public Classe addEleve(Eleve e) {
        if(!eleves.contains(e)) {
            eleves.add(e);
        }
        return this;
    }

    public Eleve getEleveByNomAndPrenom(String nom, String prenom) throws EleveNotFoundException {
        for(Eleve eleve : eleves) {
            if(eleve.getNom().equals(nom) && eleve.getPrenom().equals(prenom)) {
                return eleve;
            }
        }
        throw new EleveNotFoundException();
    }

}
