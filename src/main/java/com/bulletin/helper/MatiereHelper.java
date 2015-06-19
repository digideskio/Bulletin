package com.bulletin.helper;

import com.bulletin.entity.Matiere;
import com.bulletin.exception.MatiereNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoffroy on 12/11/14.
 */
public class MatiereHelper {


    public static Matiere getMatiereByName(String name, List<Matiere> matieres) throws MatiereNotFoundException {
        for(Matiere matiere : matieres) {
            if(matiere.getNom().equals(name)) {
                return matiere;
            }
        }
        System.out.println("Matiere not found :"  + name);
        throw new MatiereNotFoundException(name);
    }
}
