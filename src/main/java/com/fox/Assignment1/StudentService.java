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

import java.io.FileWriter;

@Service
public class StudentService {
    public List<StudentModel> XMLRead() {
        try {
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
        } catch (IOException | ParserConfigurationException | SAXException e) {
            // Handle the exception or log it
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list or handle the error accordingly
        }
    }

    public void addStudent(
            String filePath,
            StudentModel student) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(filePath));

            // Create a new student element
            Element newStudentElement = createStudentElement(doc, student);

            // Add the new student element to the existing university
            addStudentToUniversity(filePath, doc, newStudentElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element createStudentElement(
            Document doc,
            StudentModel student) {
        // Create student element
        Element studentElement = doc.createElement("Student");
        studentElement.setAttribute("ID", student.getID());

        // Create child elements for the student
        createElementWithTextContent(doc, studentElement, "FirstName", student.getFirstName());
        createElementWithTextContent(doc, studentElement, "LastName", student.getLastName());
        createElementWithTextContent(doc, studentElement, "Gender", student.getGender());
        createElementWithTextContent(doc, studentElement, "GPA", String.valueOf(student.getGPA()));
        createElementWithTextContent(doc, studentElement, "Level", String.valueOf(student.getLevel()));
        createElementWithTextContent(doc, studentElement, "Address", student.getAddress());

        return studentElement;
    }

    private void createElementWithTextContent(
            Document doc, Element parentElement, String tagName,
            String textContent) {
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        parentElement.appendChild(element);
    }

    private void addStudentToUniversity(String filePath, Document doc, Element newStudentElement) {
        try {
            // Get the root element (University)
            Element universityElement = doc.getDocumentElement();

            // Append the new student element to the university
            universityElement.appendChild(newStudentElement);

            // Write the updated XML file
            writeXmlFile(doc, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeXmlFile(Document doc, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Use a Transformer to write the XML document back to the file
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory
                    .newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(writer);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
