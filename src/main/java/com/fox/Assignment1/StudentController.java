package com.fox.Assignment1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/")
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
            if (student.getFirstName() == targetName) {
                studentsWithTargetName.add(student);
            }
        }

        return studentsWithTargetName;
    }

    @PostMapping("/")
    public void addStudent(@RequestBody StudentModel student) {
        String filePath = "/Users/Youssef/Projects/SoA-Assignment1/students.xml";
        studentService.addStudent(filePath, student);
    }

    @DeleteMapping("/deleteByID/{ID}")
    public String deleteStudent(@PathVariable int ID){
        return String.format("Test");
    }

}


