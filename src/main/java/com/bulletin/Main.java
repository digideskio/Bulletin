package com.bulletin;

import com.bulletin.entity.Classe;
import com.bulletin.entity.Eleve;
import com.bulletin.entity.Matiere;
import com.bulletin.exception.EleveNotFoundException;
import com.bulletin.exception.MatiereNotFoundException;
import com.bulletin.exception.NoteNotFoundException;
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

    public static void main(String[] args) throws IOException, MatiereNotFoundException, EleveNotFoundException, NoteNotFoundException {

        if(args.length != 7) {
            System.out.println("Usage incorrect");
            System.out.println("---------------");
            System.out.println("Usage correct : ");
            System.out.println("exec pathToExcel pathToOuput studentTemplateFileName basicBlocTemplate basicLineTemplate subMatiereTemplate finalTemplateFileName");
            return;
        }


        String pathToExcel = args[0];
        String pathToOutput = args[1];
        String studentTemplateFileName = args[2];
        String basicBlocTemplate = args[3];
        String basicLineTemplate = args[4];
        String subMatiereTemplate = args[5];
        String finalTemplateFileName = args[6];



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


        WordWriter wordWriter = new WordWriter(rootMatieres, studentTemplateFileName, basicBlocTemplate, basicLineTemplate, finalTemplateFileName, subMatiereTemplate);

        for(Eleve eleve : classe.getEleves()) {
            //if(!eleve.getNom().equals("El Aroui") && !eleve.getPrenom().equals("Kanza")) {
                wordWriter.createBulletin(eleve);
            //}
        }

        wordWriter.generateWordDoc(pathToOutput);


        System.out.println("Bulletin crées");

    }
}
