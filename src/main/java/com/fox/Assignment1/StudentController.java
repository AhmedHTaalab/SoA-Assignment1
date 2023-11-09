package com.fox.Assignment1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/Student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/TEST")
    public List<StudentModel> getAllStudents() {

        List<StudentModel> Students = new ArrayList<>();
        Students = studentService.XMLRead();
        return  Students;
    }


    @GetMapping("/ByGPA/{targetGPA}")
    public List<StudentModel> getStudentsByGPA(@PathVariable  double targetGPA) {
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
    public List<StudentModel> getStudentsByName(@PathVariable  String targetName) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetName= new ArrayList<>();

        for (StudentModel student : students) {
            if (student.getFirstname() == targetName) {
                studentsWithTargetName.add(student);
            }
        }

        return studentsWithTargetName;
    }

    @PostMapping("/Student")
    public String addStudent(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @DeleteMapping("/deleteByID/{ID}")
    public String deleteStudent(@PathVariable int ID){
        return String.format("Test");
    }

}


