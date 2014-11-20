package com.bulletin;

import com.bulletin.entity.Classe;
import com.bulletin.entity.Eleve;
import com.bulletin.entity.Matiere;
import com.bulletin.exception.EleveNotFoundException;
import com.bulletin.exception.MatiereNotFoundException;
import com.bulletin.inputStream.InputStreamParser;
import com.bulletin.inputStream.excel.ExcelParser;
import com.bulletin.outputStream.WordWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoffroy on 13/11/14.
 */
public class Main {

    public static void main(String[] args) throws IOException, MatiereNotFoundException, EleveNotFoundException {

        String pathToExcel = args[0];


        InputStreamParser inputStreamParser = new ExcelParser(pathToExcel);


        Classe classe = inputStreamParser.getClasse();

        List<Matiere> matieres = inputStreamParser.getMatieres();

        classe = inputStreamParser.getAllNotes(matieres, classe);

        classe = inputStreamParser.getAllRemarques(classe);

        List<Matiere> rootMatieres =  new ArrayList<Matiere>();

        for(Matiere m : matieres) {
            if(m.getParentMatiere() == null) {
                rootMatieres.add(m);
            }
        }


        WordWriter wordWriter = new WordWriter(rootMatieres);

        for(Eleve eleve : classe.getEleves()) {
            wordWriter.createBulletin(eleve);
        }

        wordWriter.generateWordDoc("test.xml");

    }
}
