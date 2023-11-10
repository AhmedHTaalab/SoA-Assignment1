package com.fox.Assignment1;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


@RestController
@RequestMapping("/Student")
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
        return  Students;
    }


    @GetMapping("/ByGPA/{targetGPA}")
    public List<StudentModel> getStudentsByGPA(@PathVariable  double targetGPA) {
        List<StudentModel> students = StudentService.XMLRead(); // Assuming XMLRead returns a list of all students

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
            if (Objects.equals(student.getFirstName(), targetName)) {
                studentsWithTargetName.add(student);
            }
        }

        return studentsWithTargetName;
    }

    @PostMapping("/")
    public String addStudent(@RequestBody StudentModel student) {
        String filePath = "D:\\College\\SOA\\JavaSpring\\Assignment1\\test.xml";
        studentService.addStudent(filePath, student);
        return "Student Added";
    }

//    @DeleteMapping("/deleteByID/{ID}")
//    public String deleteStudent(@PathVariable int ID){
//        return String.format("Test");
//    }
    @DeleteMapping("/deleteByID/{ID}")
    public String deleteStudent(@PathVariable String ID) {
        try {
            String filePath = "D:\\College\\SOA\\JavaSpring\\Assignment1\\test.xml";
            StudentService.deleteStudentByID(ID, filePath);
            return String.format("Student with ID %d deleted successfully", ID);
        } catch (JAXBException e) {
            e.printStackTrace();
            return "Error deleting student.";
        }
    }



}


