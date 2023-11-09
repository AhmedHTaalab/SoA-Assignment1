package com.fox.Assignment1;
import org.springframework.web.bind.annotation.*;
import java.lang.String;


@RestController
@RequestMapping("/Student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String getAllStudents(){
       return "test test i love you";
    }

    @GetMapping("/ByGPA")
    public String getByGPA(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/ByName")
    public String getByName(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
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


