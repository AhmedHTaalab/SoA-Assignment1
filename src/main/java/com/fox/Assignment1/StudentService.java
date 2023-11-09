package com.fox.Assignment1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Service
public class StudentService {
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
        createElementWithTextContent(doc, studentElement, "FirstName", student.getFirstname());
        createElementWithTextContent(doc, studentElement, "LastName", student.getLastname());
        createElementWithTextContent(doc, studentElement, "Gender", student.getGender());
        createElementWithTextContent(doc, studentElement, "GPA", String.valueOf(student.getGPA()));
        createElementWithTextContent(doc, studentElement, "Level", String.valueOf(student.getLevel()));
        createElementWithTextContent(doc, studentElement, "Address", student.getAddress());

        return studentElement;
    }

    private void createElementWithTextContent(Document doc, Element parentElement, String tagName,
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
