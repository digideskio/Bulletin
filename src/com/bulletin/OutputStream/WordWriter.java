package com.bulletin.outputStream;

import com.bulletin.entity.Eleve;
import com.bulletin.entity.Matiere;
import com.bulletin.entity.Note;
import com.bulletin.entity.NoteType;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

/**
 * Created by geoffroy on 13/11/14.
 */
public class WordWriter {


    private List<String> xmlOutputString;
    private List<Matiere> rootMatiere;

    private String templateFileName = "template.xml";
    private String basicBlocTemplate = "basicBloc.xml";
    private String basicLineTemplate = "basicLine.xml";


    public WordWriter(List<Matiere> rootMatiere) {
        xmlOutputString = new ArrayList<String>();
        this.rootMatiere = rootMatiere;
    }


    private String populateHeader(String template) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int nextYear = currentYear + 1;

        Tag firstPartCurrentYear = Tag.FIRST_YEAR_3_CHARS;
        Tag secondtPartCurrentYear = Tag.FIRST_YEAR_LAST_CHAR;

        Tag firstPartNextYear = Tag.SECOND_YEAR_3_CHARS;
        Tag secondtPartNextYear = Tag.SECOND_YEAR_LAST_CHAR;

        String currentYearStr = "" + currentYear;
        String nextYearStr = "" + nextYear;


        template = template.replaceAll(firstPartCurrentYear.name(), currentYearStr.substring(0,3));
        template = template.replaceAll(secondtPartCurrentYear.name(), currentYearStr.substring(3));

        template = template.replaceAll(firstPartNextYear.name(), nextYearStr.substring(0,3));
        template = template.replaceAll(secondtPartNextYear.name(), nextYearStr.substring(3));

        return template;
    }





    public void createBulletin(Eleve eleve) {
        String template = convertXMLFileToString(templateFileName);

        template = populateHeader(template);

        StringBuilder sb = new StringBuilder();
        for(Matiere root : rootMatiere) {
            sb.append(populateMatiere(root, eleve));
        }

        template = template.replace(Tag.CONTENT.name(), sb.toString());

        template = template.replace(Tag.REMARQUE.name(), xmlEncode("REMARQUE"));

        xmlOutputString.add(template);
    }



    private String populateMatiere(Matiere rootMatiere, Eleve eleve) {
        String basicBloc = convertXMLFileToString(basicBlocTemplate);

        basicBloc = basicBloc.replace(Tag.ROOT_MATIERE.name(), rootMatiere.getNom());

        for(Matiere matiere : rootMatiere.getChildrenMatieres()) {
            if(matiere.getChildrenMatieres() == null || matiere.getChildrenMatieres().size() ==  0) {
                basicBloc = basicBloc.replace(Tag.BASIC_LINE.name(), populateMatiere(matiere, eleve));
            } else {
                String basicLine = convertXMLFileToString(basicLineTemplate);

                basicLine = populateBasicLine(basicLine, matiere, eleve);

                basicBloc = basicBloc.replace(Tag.BASIC_LINE.name(), basicLine);

            }
        }
        return basicBloc;

    }



    private String populateBasicLine(String template, Matiere matiere, Eleve eleve) {
        template = template.replace(Tag.MATIERE_NAME.name(), matiere.getNom());

        Note note = eleve.getNoteByMatiere(matiere);

        return replaceTagWithNoteInTemplate(template, note);

    }


    private String replaceTagWithNoteInTemplate(String template, Note note) {

        Map<Tag, String> tagValue = getTagValueMap(note);

        for(Map.Entry<Tag,String> entry : tagValue.entrySet()) {
            template = template.replace(entry.getKey().name(),entry.getValue());
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
                tagValue.replace(Tag.R, NoteType.R.getShortName());
                break;
            case RF:
                tagValue.replace(Tag.RF, NoteType.RF.getShortName());
                break;
            case RM:
                tagValue.replace(Tag.RM, NoteType.RM.getShortName());
                break;
            case RR:
                tagValue.replace(Tag.RR, NoteType.RR.getShortName());
                break;

            case NR:
                tagValue.replace(Tag.NR, NoteType.NR.getShortName());
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

            for(String bulletin : xmlOutputString) {

                writer.write(bulletin);
            }
            writer.close();

        }
        catch (Exception e) {
            System.out.println("exception!=" + e);
        }
    }

    /**
     * Encodes regular text to XML.
     * @param text
     * @return string
     * @author http://dinoch.dyndns.org:7070/WordML/AboutWordML.jsp
     * @date 20050328
     */
    private String xmlEncode(String text) {
        int[] charsRequiringEncoding = {38, 60, 62, 34, 61, 39};
        for(int i = 0; i < charsRequiringEncoding.length - 1; i++) {
            text = text.replaceAll(String.valueOf((char)charsRequiringEncoding[i]),"&#"+charsRequiringEncoding[i]+";");
        }
        return text;


    }


    private String convertXMLFileToString(String fileName)
    {
        try{
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
        return null;
    }

}