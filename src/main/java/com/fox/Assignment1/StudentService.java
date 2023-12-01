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
import java.util.Objects;

@Service
public class StudentService {

    public List<StudentModel> XMLRead() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Load the input XML document, parse it and return an instance of the Document
            // class.
            // TODO: Change the path to the XML file
            Document document = builder.parse(new File(Settings.XML_FILE_PATH));

            List<StudentModel> students = new ArrayList<>();
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    // Get the value of the ID attribute.
                    String ID = elem.getAttribute("ID");

                    // Get values of sub-elements with null checks
                    String firstName = getNodeValue(elem, "FirstName");
                    String lastName = getNodeValue(elem, "LastName");
                    String gender = getNodeValue(elem, "Gender");
                    Double gpa = getDoubleNodeValue(elem, "GPA");
                    Integer level = getIntNodeValue(elem, "Level");
                    String address = getNodeValue(elem, "Address");

                    // Check for null values before adding to the list
                    if (ID != null && firstName != null && lastName != null && gender != null &&
                            gpa != null && level != null && address != null) {
                        students.add(new StudentModel(ID, firstName, lastName, gender, gpa, level, address));
                    } else {
                        // Handle missing or null values if needed
                    }
                }
            }
            return students;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            // Handle the exception or log it
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list or handle the error accordingly
        }
    }

    // Helper method to get string node value with null check
    private String getNodeValue(Element element, String tagName) {
        Node node = element.getElementsByTagName(tagName).item(0);
        if (node != null && node.getFirstChild() != null) {
            return node.getFirstChild().getNodeValue();
        }
        return null;
    }

    // Helper method to get double node value with null check
    private Double getDoubleNodeValue(Element element, String tagName) {
        String value = getNodeValue(element, tagName);
        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                // Handle parsing error if needed
            }
        }
        return null;
    }

    // Helper method to get integer node value with null check
    private Integer getIntNodeValue(Element element, String tagName) {
        String value = getNodeValue(element, tagName);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // Handle parsing error if needed
            }
        }
        return null;
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

        System.out.println(student.toString());

        // Create child elements for the student
        createElementWithTextContent(doc, studentElement, "FirstName", String.valueOf(student.getFirstName()));
        createElementWithTextContent(doc, studentElement, "LastName", String.valueOf(student.getLastName()));
        createElementWithTextContent(doc, studentElement, "Gender", String.valueOf(student.getGender()));
        createElementWithTextContent(doc, studentElement, "GPA", String.valueOf(student.getGPA()));
        createElementWithTextContent(doc, studentElement, "Level", String.valueOf(student.getLevel()));
        createElementWithTextContent(doc, studentElement, "Address", String.valueOf(student.getAddress()));

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

    public StudentModel updateStudentByID(String studentID, String filePath,
            String newFirstName,
            String newLastName,
            String newGender,
            Double newGPA,
            Integer newLevel,
            String newAddress) {
        // Read XML file
        List<StudentModel> Students = new ArrayList<>();
        Students = XMLRead();

        // find the student and copy the new data
        StudentModel fonudStudent = Students.stream()
                .filter(studentModel -> studentModel.getID().equals(studentID))
                .findFirst()
                .orElse(null);

        if (fonudStudent != null) {
            if (newFirstName != null)
                fonudStudent.setFirstName(newFirstName);

            if (newLastName != null)
                fonudStudent.setLastName(newLastName);

            if (newGender != null)
                fonudStudent.setGender(newGender);

            if (newGPA != null)
                fonudStudent.setGPA(newGPA);

            if (newLevel != null)
                fonudStudent.setLevel(newLevel);

            if (newAddress != null)
                fonudStudent.setAddress(newAddress);
        }

        try {
            // Create a new Document object using DocumentBuilderFactory and DocumentBuilder
            // classes
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse the XML file using the Document object
            Document doc = dBuilder.parse(new File(filePath));

            // Get the root element (University)
            Element universityElement = doc.getDocumentElement();

            // Remove all child nodes of the root element
            NodeList childNodes = universityElement.getChildNodes();
            for (int i = childNodes.getLength() - 1; i >= 0; i--) {
                Node node = childNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    universityElement.removeChild(node);
                }
            }

            // Create new student elements and add them to the root element
            for (StudentModel student1 : Students) {
                Element newStudentElement = createStudentElement(doc, student1);
                universityElement.appendChild(newStudentElement);
            }

            // Write the updated XML file
            writeXmlFile(doc, filePath);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return fonudStudent;
    }

    public void deleteStudentByID(String studentID, String filePath) {
        // Read XML file
        List<StudentModel> Students = new ArrayList<>();
        Students = XMLRead();

        Students.removeIf(student -> Objects.equals(student.getID(), studentID));

        try {
            // Create a new Document object using DocumentBuilderFactory and DocumentBuilder
            // classes
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse the XML file using the Document object
            Document doc = dBuilder.parse(new File(filePath));

            // Get the root element (University)
            Element universityElement = doc.getDocumentElement();

            // Remove all child nodes of the root element
            NodeList childNodes = universityElement.getChildNodes();
            for (int i = childNodes.getLength() - 1; i >= 0; i--) {
                Node node = childNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    universityElement.removeChild(node);
                }
            }

            // Create new student elements and add them to the root element
            for (StudentModel student : Students) {
                Element newStudentElement = createStudentElement(doc, student);
                universityElement.appendChild(newStudentElement);
            }

            // Write the updated XML file
            writeXmlFile(doc, filePath);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
