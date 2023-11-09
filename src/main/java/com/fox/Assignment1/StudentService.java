package com.fox.Assignment1;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class StudentService {

    public List<StudentModel> XMLRead() {
        try{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Load the input XML document, parse it and return an instance of the
        // Document class.
        // TODO: Change the path to the XML file
        Document document = builder.parse(new File("/Users/Youssef/Projects/SoA-Assignment1/students.xml"));

        List<StudentModel> Students = new ArrayList<StudentModel>();
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                // Get the value of the ID attribute.
                String ID = node.getAttributes().getNamedItem("ID").getNodeValue();

                // Get the value of all sub-elements.
                String firstname = elem.getElementsByTagName("FirstName")
                        .item(0).getChildNodes().item(0).getNodeValue();

                String lastname = elem.getElementsByTagName("LastName").item(0)
                        .getChildNodes().item(0).getNodeValue();

                String Gender = elem.getElementsByTagName("Gender").item(0)
                        .getChildNodes().item(0).getNodeValue();

                double GPA = Double.parseDouble(elem.getElementsByTagName("GPA")
                        .item(0).getChildNodes().item(0).getNodeValue());

                int Level = Integer.parseInt(elem.getElementsByTagName("Level")
                        .item(0).getChildNodes().item(0).getNodeValue());

                String Address = elem.getElementsByTagName("Address").item(0)
                        .getChildNodes().item(0).getNodeValue();

                Students.add(new StudentModel(ID, firstname, lastname, Gender, GPA, Level, Address));
            }

        }
        return Students;
    }catch (IOException | ParserConfigurationException | SAXException e) {
            // Handle the exception or log it
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list or handle the error accordingly
        }
    }
}
