package com.bulletin.inputStream;

import com.bulletin.entity.Eleve;
import com.bulletin.entity.Matiere;
import com.bulletin.exception.MatiereNotFoundException;
import com.bulletin.helper.MatiereHelper;
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
public class ExcelParser {

    private XSSFWorkbook workbook;

    private MatiereHelper matiereHelper;

    public ExcelParser(String path) throws IOException {
        FileInputStream file = new FileInputStream(new File(path));
        workbook = new XSSFWorkbook(file);
        matiereHelper = new MatiereHelper();

    }

    // http://howtodoinjava.com/2013/06/19/readingwriting-excel-files-in-java-poi-tutorial/
    public List<Eleve> getClasse() {

        XSSFSheet sheet = workbook.getSheet(SheetName.ELEVE);

        List<Eleve> classe = new ArrayList<Eleve>();
        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            if(!row.getCell(0).getStringCellValue().equalsIgnoreCase(ColumnName.NOM)) {
                Eleve eleve = new Eleve();
                eleve.setNom(row.getCell(0).getStringCellValue());
                eleve.setPrenom(row.getCell(1).getStringCellValue());
                classe.add(eleve);
            }
        }
        return classe;
    }


    public List<Matiere> getMatieres() throws MatiereNotFoundException {
        XSSFSheet sheet = workbook.getSheet(SheetName.MATIERE);

        List<Matiere> matieres = new ArrayList<Matiere>();
        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            if(!row.getCell(0).getStringCellValue().equalsIgnoreCase(ColumnName.NOM)) {
                Matiere matiere = new Matiere();
                matiere.setNom(row.getCell(0).getStringCellValue());

                if(row.getCell(1) != null) {
                    String parentName = row.getCell(1).getStringCellValue();
                    matiere.setParentMatiere(matiereHelper.getMatiereByName(parentName, matieres));
                } else {
                    matiere.setParentMatiere(null);
                }
                matieres.add(matiere);
            }
        }
        return matieres;
    }
}