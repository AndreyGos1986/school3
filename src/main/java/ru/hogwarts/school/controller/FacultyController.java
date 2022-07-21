package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty( @RequestBody Faculty faculty) { // create
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Faculty>> getAllFac() {
        if (facultyService.getAllFac().isEmpty() == false) {
            return ResponseEntity.ok(facultyService.getAllFac());
        } else return ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultytInfoById(@PathVariable long id) { // read
        if (id > 0) {
            return ResponseEntity.ok(facultyService.getFaculty(id));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getFacByColor(@RequestParam(value = "color", required = false) String color) {
        if (color == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.findByColor(color));
    }
//    @GetMapping ("/students")
//    public ResponseEntity<Collection <Student>> getStudentListByFacultyId(@RequestParam long id) {
//        List <Student> students = facultyService.getAllStudentsByFacultyId(id);
//        if (students==null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(students);
//    }

    @GetMapping ("/get_by_color_and_name")
    public ResponseEntity<Faculty> getFacByColorAndNameIgnorCase(@RequestParam String color,
                                                                 @RequestParam String name) {
        if (color == null||name==null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.getFacultiesByNameAndColorIgnorcase(color,name));
    }

    @PutMapping
    public ResponseEntity<Faculty> changeFaculty(@RequestBody Faculty changedFaculty) { //update
        if (changedFaculty != null) {
            return ResponseEntity.ok(facultyService.changeFaculty(changedFaculty));
        } else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeFaculty(@PathVariable("id") long id) { //delete
        if (id > 0) {
            facultyService.removeFaculty(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
