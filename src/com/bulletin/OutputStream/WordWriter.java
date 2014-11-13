package com.bulletin.outputStream;

import com.bulletin.inputStream.InputStreamParser;
import com.sun.org.apache.xpath.internal.jaxp.XPathExpressionImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by geoffroy on 13/11/14.
 */
public class WordWriter {


    public WordWriter() {

    }



    public Document createDocument() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


        Document document = docBuilder.newDocument();

        File templateXML = new File("../bin/bulletin.xml");

        document = docBuilder.parse(templateXML);


        // /pkg:package/pkg:part/pkg:xmlData/w:document/w:body/w:tbl/w:tr


        Element rootElement = document.createElement("pkg:package");
        rootElement.setAttribute("xmlns:pkg", "http://schemas.microsoft.com/office/2006/xmlPackage");


        return document;

    }



    private List<Node> evaluateXpath(Document doc, String xpathExpr) {
        List<Node> list = new ArrayList<Node>();
        try {
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            //create XPathExpression object
            XPathExpression expr = xpath.compile(xpathExpr);
            //evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++)
                list.add(nodes.item(i));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }





  /*  public static void generateWordDoc(HashMap ht, String templatePathFilename, String outputPathFilename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(templatePathFilename));

            File destination = new File(outputPathFilename);
            BufferedWriter writer = new BufferedWriter(new FileWriter(destination));

            String thisLine;
            int i = 0;

            while ((thisLine = reader.readLine()) != null) {
                System.out.println(i);

                for (java.util.Enumeration e = ht.keys(); e.hasMoreElements();) {
                    String name = (String) e.nextElement();
                    String value = ht.get(name).toString();
                    // Use this if we need to XML-encode the string in hashtable value...
                    thisLine = thisLine.replaceAll("##" + name.toUpperCase() + "##", XmlEncode(value));
                    // ... or this if we do not need to do XML-encode.
                    //thisLine= thisLine.replaceAll("##" + name.toUpperCase() + "##", value);
                }
                writer.write(thisLine);
                writer.newLine();
                i++;
            }
            writer.close();
            System.out.println("done");
        }
        catch (Exception e) {
            System.out.println("exception!=" + e);
        }
    }

    *//**
     * Encodes regular text to XML.
     * @param text
     * @return string
     * @author http://dinoch.dyndns.org:7070/WordML/AboutWordML.jsp
     * @date 20050328
     *//*
    private static String XmlEncode(String text) {
        int[] charsRequiringEncoding = {38, 60, 62, 34, 61, 39};
        for(int i = 0; i < charsRequiringEncoding.length - 1; i++) {
            text = text.replaceAll(String.valueOf((char)charsRequiringEncoding[i]),"&#"+charsRequiringEncoding[i]+";");
        }
        return text;


    }
*/

}