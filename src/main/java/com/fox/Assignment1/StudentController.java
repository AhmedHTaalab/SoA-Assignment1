package com.fox.Assignment1;
import org.springframework.web.bind.annotation.*;
import java.lang.String;


@RestController
@RequestMapping("/Student")
public class StudentController {

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

    @PostMapping("/Student")
    public String addStudent(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @DeleteMapping("/deleteByID/{ID}")
    public String deleteStudent(@PathVariable int ID){
        return String.format("Test");
    }

}


