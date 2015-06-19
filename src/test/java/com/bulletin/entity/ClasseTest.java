package com.bulletin.entity;

import com.bulletin.exception.EleveNotFoundException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by geoffroy on 18/06/15.
 */
public class ClasseTest {

    @Test
    public void testGetEleveByNomAndPrenom() throws Exception {
        Classe classe = new Classe();
        Eleve eleve = new Eleve();
        eleve.setNom("nom");
        eleve.setPrenom("prenom");
        classe.addEleve(eleve);

        assertThat(classe.getEleveByNomAndPrenom("nom","prenom"),is(eleve));

    }


    @Test(expected = EleveNotFoundException.class)
    public void testGetEleveByNomAndPrenom_eleveNotFound() throws Exception {
        Classe classe = new Classe();
        Eleve eleve = new Eleve();
        eleve.setNom("nom");
        eleve.setPrenom("prenom");
        classe.addEleve(eleve);

        classe.getEleveByNomAndPrenom("nexiste","pas");

    }
}