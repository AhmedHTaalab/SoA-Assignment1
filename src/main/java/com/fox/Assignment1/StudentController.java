package com.fox.Assignment1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.String;
import java.util.ArrayList;
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

    @PostMapping("/")
    public ResponseEntity<String> addStudent(@RequestBody StudentModel student) {
        try {
            studentService.addStudent(Settings.XML_FILE_PATH, student);
            return ResponseEntity.ok("Student Added");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/addStudents")
    public ResponseEntity<String> addStudents(@RequestBody List<StudentModel> students) {
        for (StudentModel student : students) {
            try {
                studentService.addStudent(Settings.XML_FILE_PATH, student);
            } catch (Exception e) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
        }

        return ResponseEntity.ok("Students Added");
    }

    @PutMapping("/updateByID/{ID}")
    public ResponseEntity<StudentModel> updateStudent(@PathVariable String ID, @RequestBody StudentModel student) {
        try {
            StudentModel s = studentService.updateStudentByID(ID, Settings.XML_FILE_PATH,
                    student.getFirstName(),
                    student.getLastName(),
                    student.getGender(),
                    student.getGPA(),
                    student.getLevel(),
                    student.getAddress());

            if (s == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(s);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @DeleteMapping("/deleteByID/{ID}")
    public ResponseEntity<String> deleteStudent(@PathVariable String ID) {
        try {
            studentService.deleteStudentByID(ID, Settings.XML_FILE_PATH);
            return ResponseEntity.ok("Student with ID " + ID + " is deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Student with ID " + ID + " is not found");
        }
    }
}
