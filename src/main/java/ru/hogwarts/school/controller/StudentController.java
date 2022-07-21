package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) { // create
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) { // read
        if (studentService.getAll().isEmpty() == false) {
            return ResponseEntity.ok(studentService.getStudent(id));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping ("/update")
    public ResponseEntity<Student> changeStudent(@RequestBody Student changedStudent) { //update
        return ResponseEntity.ok(studentService.changeStudent(changedStudent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeStudent(@PathVariable long id) { //delete
        if (id > 0 && studentService.getAll().isEmpty() == false) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/by_age")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam int age) {
        if (age > 0 & studentService.findByAge(age) != null) {
            return ResponseEntity.ok(studentService.findByAge(age));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/age_between")
    public Collection<Student> getStudentsByAgeBetween(@RequestParam int min,
                                                       @RequestParam int max) {

        return studentService.findByAgeBetween(min, max);
    }


    @GetMapping("/faculties")
    public ResponseEntity<Faculty> getFacultyByStudentsId(@RequestParam long id) {
        Faculty faculty = studentService.getFacultyByStudentsId(id);
        if (faculty == null) {
            return ResponseEntity.badRequest().build();

        }
        return ResponseEntity.ok(faculty);
    }


    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        if (studentService.getAll().isEmpty() == false) {
            return ResponseEntity.ok(studentService.getAll());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


