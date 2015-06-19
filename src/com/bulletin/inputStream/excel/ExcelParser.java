package com.bulletin.inputStream.excel;

import com.bulletin.entity.*;
import com.bulletin.exception.EleveNotFoundException;
import com.bulletin.exception.MatiereNotFoundException;
import com.bulletin.helper.MatiereHelper;
import com.bulletin.inputStream.InputStreamParser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by geoffroy on 12/11/14.
 */
public class ExcelParser implements InputStreamParser {

    private XSSFWorkbook workbook;

    public ExcelParser(String path) throws IOException {
        FileInputStream file = new FileInputStream(new File(path));
        workbook = new XSSFWorkbook(file);
    }

    // http://howtodoinjava.com/2013/06/19/readingwriting-excel-files-in-java-poi-tutorial/
    public Classe getClasse() {

        XSSFSheet sheet = workbook.getSheet(SheetName.ELEVE);

        List<Eleve> classe = new ArrayList<Eleve>();
        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();

            if(row.getCell(0) == null || row.getCell(1) == null ) {
                continue;
            }

            String nom = row.getCell(0).getStringCellValue();
            String prenom = row.getCell(1).getStringCellValue();

            if(nom != null) {
                nom = nom.trim();
            }

            if(prenom != null) {
                prenom = prenom.trim();
            }

            if(!ColumnName.NOM.equalsIgnoreCase(nom) && !ColumnName.PRENOM.equalsIgnoreCase(prenom)
                    && !"/".equalsIgnoreCase(nom) && !"/".equalsIgnoreCase(prenom)) {
                Eleve eleve = new Eleve();
                eleve.setNom(nom);
                eleve.setPrenom(prenom);
                classe.add(eleve);
            }
        }

        return new Classe(classe);
    }


    public List<Matiere> getMatieres() throws MatiereNotFoundException {
        XSSFSheet sheet = workbook.getSheet(SheetName.MATIERE);

        List<Matiere> matieres = new ArrayList<Matiere>();
        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();

            if(row.getCell(0) == null) {
                continue;
            }


            if(!row.getCell(0).getStringCellValue().equalsIgnoreCase(ColumnName.NOM_MATIERE)) {
                Matiere matiere = new Matiere();
                matiere.setNom(row.getCell(0).getStringCellValue());

                if(row.getCell(1) != null && !row.getCell(1).getStringCellValue().equals("")) {
                    String parentName = row.getCell(1).getStringCellValue();
                    matiere.setParentMatiere(MatiereHelper.getMatiereByName(parentName, matieres));

                    if(row.getCell(2) != null) {
                        matiere.setSheetName(row.getCell(2).getStringCellValue());
                    }
                } else {
                    matiere.setParentMatiere(null);
                }
                matieres.add(matiere);
            }
        }
        return matieres;
    }



    public Classe getAllNotes(List<Matiere> matieres, Classe classe) throws EleveNotFoundException {
        for(Matiere matiere : matieres) {
            if(matiere.getChildrenMatieres().size() ==0 && matiere.getSheetName() != null && !matiere.getSheetName().trim().equals("")) {
                getNotesOfMatiere(matiere, classe);
            }
        }
        return classe;

    }


    private List<Note> getNotesOfMatiere(Matiere matiere, Classe classe) throws EleveNotFoundException {
        XSSFSheet sheet = workbook.getSheet(matiere.getSheetName());


        List<Note> notes = new ArrayList<Note>();
        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();

            if(row.getCell(0) == null || row.getCell(1) == null ) {
                continue;
            }

            String nom = row.getCell(0).getStringCellValue();
            String prenom = row.getCell(1).getStringCellValue();

            if(nom != null) {
                nom = nom.trim();
            }

            if(prenom != null) {
                prenom = prenom.trim();
            }

            if("/".equalsIgnoreCase(nom) || "/".equalsIgnoreCase(prenom) ) {
                continue;
            }

            if(!ColumnName.NOM.equalsIgnoreCase(nom)) {
                Eleve eleve = classe.getEleveByNomAndPrenom(nom, prenom);

                String noteString = row.getCell(2).getStringCellValue();
                Note note;
                if(noteString != null && !noteString.trim().equals("")) {
                    note = new Note(eleve, matiere, NoteType.fromShortName(noteString));
                } else {
                    note = new Note(eleve, matiere, NoteType.ABS);
                }

                notes.add(note);
            }
        }
        return notes;
    }


    public Classe getAllRemarques(Classe classe) throws EleveNotFoundException {
        XSSFSheet sheet = workbook.getSheet(ColumnName.REMARQUES);


        List<Note> notes = new ArrayList<Note>();
        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            if(row.getCell(0) == null || row.getCell(1) == null ) {
                continue;
            }

            String nom = row.getCell(0).getStringCellValue();
            String prenom = row.getCell(1).getStringCellValue();

            if(nom != null) {
                nom = nom.trim();
            }

            if(prenom != null) {
                prenom = prenom.trim();
            }

            if("/".equalsIgnoreCase(nom) || "/".equalsIgnoreCase(prenom) ) {
                continue;
            }
            if(!ColumnName.NOM.equalsIgnoreCase(nom)) {

                Eleve eleve = classe.getEleveByNomAndPrenom(nom, prenom);
                eleve.setRemarque(row.getCell(2).getStringCellValue());
            }
        }
        return classe;
    }
}