package com.bulletin.outputStream;

import com.bulletin.Constants;
import com.bulletin.entity.Eleve;
import com.bulletin.entity.Matiere;
import com.bulletin.entity.Note;
import com.bulletin.entity.NoteType;
import com.bulletin.exception.NoteNotFoundException;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by geoffroy on 13/11/14.
 */
public class WordWriter {

    private List<String> xmlOutputString;
    private List<Matiere> rootMatiere;

    private String studentTemplateFileName = "oneStudent.xml";
    private String basicBlocTemplate = "basicBloc.xml";
    private String basicLineTemplate = "basicLine.xml";
    private String subMatiereTemplate = "";
    private String finalTemplateFileName = "template.xml";

    private int lineCounter = 0;

    public WordWriter(List<Matiere> rootMatiere, String studentTemplateFileName, String basicBlocTemplate, String basicLineTemplate, String finalTemplateFileName, String subMatiereTemplate) {
        xmlOutputString = new ArrayList<String>();
        this.rootMatiere = rootMatiere;
        this.studentTemplateFileName = studentTemplateFileName;
        this.basicBlocTemplate = basicBlocTemplate;
        this.basicLineTemplate = basicLineTemplate;
        this.finalTemplateFileName = finalTemplateFileName;
        this.subMatiereTemplate =  subMatiereTemplate;
    }


    private String populateHeader(String template) {

        Tag firstYear = Tag.FIRST_YEAR;
        Tag secondYear = Tag.SECOND_YEAR;

        String currentYearStr = "" + Constants.FIRST_YEAR;
        String nextYearStr = "" + Constants.SECOND_YEAR;


        template = template.replace(firstYear.getShortName(), currentYearStr);
        template = template.replace(secondYear.getShortName(), nextYearStr);

        return template;
    }


    private String populateNomPrenom(String template, Eleve eleve) {
        template = template.replace(Tag.PRENOM.getShortName(),eleve.getPrenom());
        return template.replace(Tag.NOM.getShortName(),eleve.getNom());
    }




    public void createBulletin(Eleve eleve) throws IOException, NoteNotFoundException {
        String template = convertXMLFileToString(studentTemplateFileName);

        template = populateNomPrenom(template, eleve);
        template = populateTrimester(template);

        StringBuilder sb = new StringBuilder();
        int nbMatiere = 0;
        this.lineCounter = 0;
        for(Matiere root : rootMatiere) {
            sb.append(populateMatiere(root, eleve));
            nbMatiere++;
            sb.append(populateEmptyLines(1));

            if(nbMatiere == 6 && this.lineCounter < Constants.NUMBER_OF_LINE_ON_FIRST_PAGE) {
                sb.append(populateEmptyLines(Constants.NUMBER_OF_LINE_ON_FIRST_PAGE -this.lineCounter));
            }
        }

        template = template.replace(Tag.CONTENT.getShortName(), sb.toString());

        template = template.replace(Tag.REMARQUE.getShortName(), xmlEncode(eleve.getPrenom() + " " + eleve.getRemarque()));

        xmlOutputString.add(template);
    }

    private String populateTrimester(String template) {
        template = template.replace(Tag.TRIMESTER_NUMBER.getShortName(), Constants.CURRENT_TRIMESTER.getNumber().toString());
        return template.replace(Tag.TRIMESTER_UPPER.getShortName(), Constants.CURRENT_TRIMESTER.getUpperText());
    }


    private String populateMatiere(Matiere rootMatiere, Eleve eleve) throws IOException, NoteNotFoundException {
        String basicBloc = convertXMLFileToString(basicBlocTemplate);

        basicBloc = basicBloc.replace(Tag.ROOT_MATIERE.getShortName(), rootMatiere.getNom());
        this.lineCounter++;

        StringBuilder sb = new StringBuilder();
        for(Matiere matiere : rootMatiere.getChildrenMatieres()) {
            sb.append(populateSubMatiere(matiere, eleve));
        }
        basicBloc = basicBloc.replace(Tag.BASIC_LINE.getShortName(), sb.toString());
        return basicBloc;

    }

    private String populateEmptyLines(int numberOfLine) throws IOException {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<numberOfLine; i++) {
            String basicLine = convertXMLFileToString(basicLineTemplate);
            basicLine = basicLine.replace(Tag.MATIERE_NAME.getShortName(), "");
            basicLine = basicLine.replace(Tag.R.getShortName(), "");
            basicLine = basicLine.replace(Tag.RF.getShortName(), "");
            basicLine = basicLine.replace(Tag.RM.getShortName(), "");
            basicLine = basicLine.replace(Tag.RR.getShortName(), "");
            basicLine = basicLine.replace(Tag.NR.getShortName(), "");
            this.lineCounter++;

            sb.append(basicLine);
        }
        return sb.toString();
    }


    private String populateSubMatiere(Matiere subMatiere, Eleve eleve) throws IOException, NoteNotFoundException {
        String subBloc = convertXMLFileToString(subMatiereTemplate);

        if(subMatiere.getSheetName() != null && (subMatiere.getChildrenMatieres() == null || subMatiere.getChildrenMatieres().isEmpty())) {
            String basicLine = convertXMLFileToString(basicLineTemplate);
            basicLine = populateBasicLine(basicLine, subMatiere, eleve);
            this.lineCounter++;
            return basicLine;
        }


        subBloc = subBloc.replace(Tag.SUB_MATIERE.getShortName(), subMatiere.getNom());
        this.lineCounter++;
        StringBuilder sb = new StringBuilder();
        for(Matiere m : subMatiere.getChildrenMatieres()) {
            sb.append(populateSubMatiere(m, eleve));

        }
        sb.append(populateEmptyLines(1));
        return subBloc.replace(Tag.BASIC_LINE.getShortName(), sb.toString());

    }


    private String populateBasicLine(String template, Matiere matiere, Eleve eleve) throws NoteNotFoundException {
        template = template.replace(Tag.MATIERE_NAME.getShortName(), matiere.getNom());

        if(matiere.getSheetName() != null) {
            if (!matiere.getSheetName().equals("")) {
                Note note = eleve.getNoteByMatiere(matiere);
                return replaceTagWithNoteInTemplate(template, note);
            } else {
                Map<Tag, String> tagValue = new HashMap<Tag, String>();

                tagValue.put(Tag.R, "");
                tagValue.put(Tag.RF, "");
                tagValue.put(Tag.RM, "");
                tagValue.put(Tag.RR, "");
                tagValue.put(Tag.NR, "");

                for(Map.Entry<Tag,String> entry : tagValue.entrySet()) {
                    template = template.replace(entry.getKey().getShortName(),entry.getValue());
                }

                return template;
            }
        }
        else {
            return template;
        }

    }


    private String replaceTagWithNoteInTemplate(String template, Note note) {

        Map<Tag, String> tagValue = getTagValueMap(note);

        for(Map.Entry<Tag,String> entry : tagValue.entrySet()) {
            template = template.replace(entry.getKey().getShortName(),entry.getValue());
        }

        return template;
    }

    private Map<Tag, String> getTagValueMap(Note note) {
        Map<Tag, String> tagValue = new HashMap<Tag, String>();

        tagValue.put(Tag.R, "");
        tagValue.put(Tag.RF, "");
        tagValue.put(Tag.RM, "");
        tagValue.put(Tag.RR, "");
        tagValue.put(Tag.NR, "");

        switch (note.getNoteType()) {
            case R:
                tagValue.replace(Tag.R, NoteType.R.getDisplayValue());
            break;
            case RF:
                tagValue.replace(Tag.RF, NoteType.RF.getDisplayValue());
                break;
            case RM:
                tagValue.replace(Tag.RM, NoteType.RM.getDisplayValue());
                break;
            case RR:
                tagValue.replace(Tag.RR, NoteType.RR.getDisplayValue());
                break;

            case NR:
                tagValue.replace(Tag.NR, NoteType.NR.getDisplayValue());
                break;
            default:
                break;
        }
        return tagValue;
    }



    public void generateWordDoc(String outputPathFilename) {
        try {
            File destination = new File(outputPathFilename);

            BufferedWriter writer = new BufferedWriter(new FileWriter(destination));

            StringBuilder sb = new StringBuilder();
            for(String bulletin : xmlOutputString) {
                sb.append(bulletin);
            }

            String fullTemplate = convertXMLFileToString(finalTemplateFileName);

            fullTemplate = populateHeader(fullTemplate);

            String bulletin = fullTemplate.replace(Tag.ONE_STUDENT.getShortName(), sb.toString());

            writer.write(bulletin);
            writer.close();

        }
        catch (Exception e) {
            System.out.println("exception!=" + e);
        }
    }

    /**
     * Encodes regular text to XML.
     * @param text text to encode as XML
     * @return string
     *
     */
    private String xmlEncode(String text) {
        int[] charsRequiringEncoding = {38, 60, 62, 34, 61, 39};
        for(int i = 0; i < charsRequiringEncoding.length - 1; i++) {
            text = text.replaceAll(String.valueOf((char)charsRequiringEncoding[i]),"&#"+charsRequiringEncoding[i]+";");
        }
        return text;


    }


    private String convertXMLFileToString(String fileName) throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get(fileName));
        return new String(encoded);

        /*try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            InputStream inputStream = new FileInputStream(new File(fileName));
            org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
            StringWriter stw = new StringWriter();
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.transform(new DOMSource(doc), new StreamResult(stw));
            return stw.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/
    }

}