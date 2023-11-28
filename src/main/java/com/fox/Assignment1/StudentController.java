package com.fox.Assignment1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/Student")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public List<StudentModel> getAllStudents() {

        List<StudentModel> Students = new ArrayList<>();
        Students = studentService.XMLRead();
        return Students;
    }


    @GetMapping("/ByGPA/{targetGPA}")
    public List<StudentModel> getStudentsByGPA(@PathVariable double targetGPA) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetGPA = new ArrayList<>();

        for (StudentModel student : students) {
            if (student.getGPA() == targetGPA) {
                studentsWithTargetGPA.add(student);
            }
        }

        return studentsWithTargetGPA;
    }

    @GetMapping("/ByName/{targetName}")
    public List<StudentModel> getStudentsByName(@PathVariable String targetName) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetName = new ArrayList<>();

        for (StudentModel student : students) {
            if (Objects.equals(student.getFirstName(), targetName)) {
                studentsWithTargetName.add(student);
            }
        }

        return studentsWithTargetName;
    }

    @GetMapping("/ByLastName/{LastName}")
    public List<StudentModel> getStudentsByLastName(@PathVariable String LastName) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetLastName = new ArrayList<>();

        for (StudentModel student : students) {
            if (Objects.equals(student.getLastName(), LastName)) {
                studentsWithTargetLastName.add(student);
            }
        }

        return studentsWithTargetLastName;
    }

    @GetMapping("/ByGender/{Gender}")
    public List<StudentModel> getStudentsByGender(@PathVariable String Gender) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetGender = new ArrayList<>();

        for (StudentModel student : students) {
            if (Objects.equals(student.getGender(), Gender)) {
                studentsWithTargetGender.add(student);
            }
        }

        return studentsWithTargetGender;
    }
    @GetMapping("/ByID/{ID}")
    public List<StudentModel> getStudentsByID(@PathVariable String ID) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetID = new ArrayList<>();

        for (StudentModel student : students) {
            if (Objects.equals(student.getID(), ID)) {
                studentsWithTargetID.add(student);
            }
        }

        return studentsWithTargetID;
    }

    @GetMapping("/ByLevel/{Level}")
    public List<StudentModel> getStudentsLevel(@PathVariable int Level) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetLevel = new ArrayList<>();

        for (StudentModel student : students) {
            if (student.getLevel() == Level) {
                studentsWithTargetLevel.add(student);
            }
        }

        return studentsWithTargetLevel;
    }

    @GetMapping("/ByAddress/{Address}")
    public List<StudentModel> getStudentsByAddress(@PathVariable String Address) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetAddress = new ArrayList<>();

        for (StudentModel student : students) {
            if (Objects.equals(student.getAddress(), Address)) {
                studentsWithTargetAddress.add(student);
            }
        }

        return studentsWithTargetAddress;
    }
    @GetMapping("/sort")
    public List<StudentModel> sortStudents2(
            @RequestParam("attribute") String attribute,
            @RequestParam("order") int order) {
        // 0 Ascending , 1 Descending

        try {
            System.out.println("Starting sorting process...");

            // Read student data from XML file
            List<StudentModel> students = studentService.XMLRead();

            System.out.println("Read students from XML...");

            // Sort the students based on attribute and order
            List<StudentModel> sortedStudents = studentService.sortStudents(students, attribute, order);

            System.out.println("Sorted students...");

            // Write sorted content back to XML file

            studentService.rewriteFile(sortedStudents,Settings.XML_FILE_PATH);

            System.out.println("Sorting completed.");

            return sortedStudents;
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
            // Handle the exception or return a default value as needed
            return Collections.emptyList(); // Return an empty list or handle the error accordingly
        }
    }

    @PostMapping("/")
    public String addStudent(@RequestBody StudentModel student) {
        studentService.addStudent(Settings.XML_FILE_PATH, student);
        return "Student Added";
    }

    @DeleteMapping("/deleteByID/{ID}")
    public String deleteStudent(@PathVariable String ID) {
        studentService.deleteStudentByID(ID, Settings.XML_FILE_PATH);
        return String.format("Student with ID %s deleted successfully", ID);

    }

}
