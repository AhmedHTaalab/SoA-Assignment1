package com.fox.Assignment1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.String;
import java.util.*;

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
    public ResponseEntity<Map<String, Object>> getStudentsByGPA(@PathVariable double targetGPA) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetGPA = new ArrayList<>();

        for (StudentModel student : students) {
            if (student.getGPA() == targetGPA) {
                studentsWithTargetGPA.add(student);
            }
        }

        int count = studentsWithTargetGPA.size();

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("students", studentsWithTargetGPA);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/ByName/{targetName}")
    public ResponseEntity<Map<String, Object>> getStudentsByName(@PathVariable String targetName) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetName = new ArrayList<>();

        for (StudentModel student : students) {
            if (Objects.equals(student.getFirstName(), targetName)) {
                studentsWithTargetName.add(student);
            }
        }

        int count = studentsWithTargetName.size();

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("students", studentsWithTargetName);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/ByLastName/{LastName}")
    public ResponseEntity<Map<String, Object>> getStudentsByLastName(@PathVariable String LastName) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetLastName = new ArrayList<>();

        for (StudentModel student : students) {
            if (Objects.equals(student.getLastName(), LastName)) {
                studentsWithTargetLastName.add(student);
            }
        }

        int count = studentsWithTargetLastName.size();

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("students", studentsWithTargetLastName);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/ByGender/{Gender}")
    public ResponseEntity<Map<String, Object>> getStudentsByGender(@PathVariable String Gender) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetGender = new ArrayList<>();

        for (StudentModel student : students) {
            if (Objects.equals(student.getGender(), Gender)) {
                studentsWithTargetGender.add(student);
            }
        }

        int count = studentsWithTargetGender.size();

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("students", studentsWithTargetGender);

        return ResponseEntity.ok().body(response);
    }
//    @GetMapping("/ByID/{ID}")
//    public List<StudentModel> getStudentsByID(@PathVariable String ID) {
//        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students
//
//        List<StudentModel> studentsWithTargetID = new ArrayList<>();
//
//        for (StudentModel student : students) {
//            if (Objects.equals(student.getID(), ID)) {
//                studentsWithTargetID.add(student);
//            }
//        }
//
//        return studentsWithTargetID;
//    }

//Return The Count in the body
    @GetMapping("/ByID/{ID}")
    public ResponseEntity<Map<String, Object>> getStudentsByID(@PathVariable String ID) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetID = new ArrayList<>();

        for (StudentModel student : students) {
            if (Objects.equals(student.getID(), ID)) {
                studentsWithTargetID.add(student);
            }
        }

        int count = studentsWithTargetID.size();

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("students", studentsWithTargetID);

        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/ByLevel/{Level}")
    public ResponseEntity<Map<String, Object>> getStudentsLevel(@PathVariable int Level) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetLevel = new ArrayList<>();

        for (StudentModel student : students) {
            if (student.getLevel() == Level) {
                studentsWithTargetLevel.add(student);
            }
        }


        int count = studentsWithTargetLevel.size();

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("students", studentsWithTargetLevel);

        return ResponseEntity.ok().body(response);
    }




    @GetMapping("/ByAddress/{Address}")
    public ResponseEntity<Map<String, Object>> getStudentsByAddress(@PathVariable String Address) {
        List<StudentModel> students = studentService.XMLRead(); // Assuming XMLRead returns a list of all students

        List<StudentModel> studentsWithTargetAddress = new ArrayList<>();

        for (StudentModel student : students) {
            if (Objects.equals(student.getAddress(), Address)) {
                studentsWithTargetAddress.add(student);
            }
        }

        int count = studentsWithTargetAddress.size();

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("students", studentsWithTargetAddress);

        return ResponseEntity.ok().body(response);
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
