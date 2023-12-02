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
import java.util.*;

import java.io.FileWriter;

@Service
public class StudentService {

    public List<StudentModel> XMLRead() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Load the input XML document, parse it and return
            // an instance of the Document class.
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

    public List<StudentModel> sortStudents(List<StudentModel> students, String attribute, int order) {
        Comparator<StudentModel> comparator = switch (attribute.toLowerCase()) {
            case "id" -> Comparator.comparing(StudentModel::getID);
            case "firstname" -> Comparator.comparing(StudentModel::getFirstName);
            case "lastname" -> Comparator.comparing(StudentModel::getLastName);
            case "gpa" -> Comparator.comparing(StudentModel::getGPA);
            case "level" -> Comparator.comparing(StudentModel::getLevel);
            case "address" -> Comparator.comparing(StudentModel::getAddress);
            case "gender" -> Comparator.comparing(StudentModel::getGender);
            default -> throw new IllegalArgumentException("Invalid attribute: " + attribute);
        };

        if (order == 1) {
            comparator = comparator.reversed();
        } else if (order != 0) {
            throw new IllegalArgumentException("Invalid order: " + order);
        }

        students.sort(comparator);
        return students;
    }

    public void rewriteFile(List<StudentModel> students, String filePath) {
        try {
            // Create a new Document object using DocumentBuilderFactory and DocumentBuilder
            // classes
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Create a new Document
            Document doc = dBuilder.newDocument();

            // Create the root element (University)
            Element universityElement = doc.createElement("University");
            doc.appendChild(universityElement);

            // Create student elements and add them to the root element
            for (StudentModel student : students) {
                Element newStudentElement = createStudentElement(doc, student);
                universityElement.appendChild(newStudentElement);
            }

            // Write the updated XML file
            writeXmlFile(doc, filePath);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // public List<StudentModel> XMLRead() {
    // try {
    // DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    // DocumentBuilder builder = factory.newDocumentBuilder();
    //
    // // Load the input XML document, parse it and return an instance of the
    // // Document class.
    // // TODO: Change the path to the XML file
    // Document document = builder.parse(new File(Settings.XML_FILE_PATH));
    //
    // List<StudentModel> Students = new ArrayList<StudentModel>();
    // NodeList nodeList = document.getDocumentElement().getChildNodes();
    // for (int i = 0; i < nodeList.getLength(); i++) {
    // Node node = nodeList.item(i);
    //
    // if (node.getNodeType() == Node.ELEMENT_NODE) {
    // Element elem = (Element) node;
    //
    // // Get the value of the ID attribute.
    // String ID = node.getAttributes().getNamedItem("ID").getNodeValue();
    //
    // // Get the value of all sub-elements.
    // String firstname = elem.getElementsByTagName("FirstName")
    // .item(0).getChildNodes().item(0).getNodeValue();
    //
    // String lastname = elem.getElementsByTagName("LastName").item(0)
    // .getChildNodes().item(0).getNodeValue();
    //
    // String Gender = elem.getElementsByTagName("Gender").item(0)
    // .getChildNodes().item(0).getNodeValue();
    //
    // double GPA = Double.parseDouble(elem.getElementsByTagName("GPA")
    // .item(0).getChildNodes().item(0).getNodeValue());
    //
    // int Level = Integer.parseInt(elem.getElementsByTagName("Level")
    // .item(0).getChildNodes().item(0).getNodeValue());
    //
    // String Address = elem.getElementsByTagName("Address").item(0)
    // .getChildNodes().item(0).getNodeValue();
    //
    // Students.add(new StudentModel(ID, firstname, lastname, Gender, GPA, Level,
    // Address));
    // }
    //
    // }
    // return Students;
    // } catch (IOException | ParserConfigurationException | SAXException e) {
    // // Handle the exception or log it
    // e.printStackTrace();
    // return new ArrayList<>(); // Return an empty list or handle the error
    // accordingly
    // }
    // }

    public void addStudent(
            String filePath,
            StudentModel student) throws IllegalArgumentException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(filePath));

            // Create a new student element
            Element newStudentElement = createStudentElement(doc, student);

            // Add the new student element to the existing university
            addStudentToUniversity(filePath, doc, newStudentElement);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isStudentIDExists(Document doc, String studentID) {
        NodeList studentList = doc.getElementsByTagName("Student");
        for (int i = 0; i < studentList.getLength(); i++) {
            Node studentNode = studentList.item(i);
            if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element studentElement = (Element) studentNode;
                String existingID = studentElement.getAttribute("ID");
                if (existingID.equals(studentID)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Element createStudentElement(
            Document doc,
            StudentModel student) {
        // Create student element
        Element studentElement = doc.createElement("Student");
        String ID = student.getID();

        System.out.println(ID);
        ensureFieldNotNull(ID, "ID");
        ensureStringNotEmpty(ID, "ID");

        if (isStudentIDExists(doc, ID)) {
            throw new IllegalArgumentException("Student with ID " + ID + " already exists.");
        }

        studentElement.setAttribute("ID", String.valueOf(ID));

        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String gender = student.getGender();
        Double gpa = student.getGPA();
        Integer level = student.getLevel();
        String address = student.getAddress();

        ensureFieldNotNull(firstName, "FirstName");
        ensureFieldNotNull(lastName, "LastName");
        ensureFieldNotNull(gender, "Gender");
        ensureFieldNotNull(gpa, "GPA");
        ensureFieldNotNull(level, "Level");
        ensureFieldNotNull(address, "Address");

        ensureStringNotEmpty(firstName, "FirstName");
        ensureStringNotEmpty(lastName, "LastName");
        ensureStringNotEmpty(gender, "Gender");
        ensureStringNotEmpty(address, "Address");

        ensureAlphabeticString(firstName, "FirstName");
        ensureAlphabeticString(lastName, "LastName");
        ensureAlphabeticString(address, "Address");

        ensureGPAValid(gpa);

        // Create child elements for the student
        createElementWithTextContent(doc, studentElement, "FirstName", String.valueOf(firstName));
        createElementWithTextContent(doc, studentElement, "LastName", String.valueOf(lastName));
        createElementWithTextContent(doc, studentElement, "Gender", String.valueOf(gender));
        createElementWithTextContent(doc, studentElement, "GPA", String.valueOf(gpa));
        createElementWithTextContent(doc, studentElement, "Level", String.valueOf(level));
        createElementWithTextContent(doc, studentElement, "Address", String.valueOf(address));

        return studentElement;
    }

    void ensureAlphabeticString(String fieldValue, String fieldName) {
        if (!fieldValue.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException(fieldName + " must contain only alphabetic characters");
        }
    }

    void ensureFieldNotNull(Object fieldValue, String fieldName) {
        if (Objects.isNull(fieldValue)) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }

    void ensureStringNotEmpty(String fieldValue, String fieldName) {
        if (fieldValue.isEmpty() || fieldValue.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    void ensureGPAValid(Double gpa) {
        if (gpa < 0 || gpa > 4) {
            throw new IllegalArgumentException("GPA must be between 0 and 4");
        }
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
        StudentModel foundStudent = Students.stream()
                .filter(studentModel -> studentModel.getID().equals(studentID))
                .findFirst()
                .orElse(null);

        if (foundStudent != null) {
            if (newFirstName != null)
                foundStudent.setFirstName(newFirstName);

            if (newLastName != null)
                foundStudent.setLastName(newLastName);

            if (newGender != null)
                foundStudent.setGender(newGender);

            if (newGPA != null)
                foundStudent.setGPA(newGPA);

            if (newLevel != null)
                foundStudent.setLevel(newLevel);

            if (newAddress != null)
                foundStudent.setAddress(newAddress);
        } else {
            return null;
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

        return foundStudent;
    }

    public void deleteStudentByID(String studentID, String filePath) {
        try {
            // Open the XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));

            // Find the student by ID
            NodeList studentList = doc.getElementsByTagName("Student");
            for (int i = 0; i < studentList.getLength(); i++) {
                Element studentElement = (Element) studentList.item(i);
                String id = studentElement.getAttribute("ID");
                if (id.equals(studentID)) {
                    // Delete the student
                    studentElement.getParentNode().removeChild(studentElement);
                    // Save the changes
                    writeXmlFile(doc, filePath);
                    return;
                }
            }

            // If student not found, throw exception
            throw new IllegalArgumentException("Student with ID " + studentID + " not found.");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }
}
