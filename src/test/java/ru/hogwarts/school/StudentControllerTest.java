package ru.hogwarts.school;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest (controllers = StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository repository;

    @SpyBean
    private StudentService service;

    @InjectMocks
    private StudentController controller;

    @Test
    public void testStudents() throws Exception {
        final String name = "Student";
        final int age = 15;
        final long id = 3;

        Student student = new Student(id, name, age);

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);


        when(repository.save(any(Student.class))).thenReturn(student);
        when(repository.findStudentByAge(eq(age))).thenReturn((Student) Collections.singleton(student));
        when(repository.findById(eq(id))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?age" + age)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
