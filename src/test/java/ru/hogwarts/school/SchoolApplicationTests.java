package ru.hogwarts.school;


import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static ru.hogwarts.school.constants.ConstantsForTest.STUDENT1;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {


	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private StudentController studentController;

	@Test
	public void contextLoads() {
		Assertions.assertThat(studentController).isNotNull();
	}

	@Test
	public void createStudentTest() {
		Assertions
				.assertThat(this.testRestTemplate
						.postForObject("http://localhost:" + port + "/student", STUDENT1, String.class))
				.isNotNull();
		studentController.removeStudent(STUDENT1.getId());
	}

	@Test
	public void getStudetTest() {
		Assertions
				.assertThat(this.testRestTemplate
						.getForObject("http://localhost:" + port + "/student/1", String.class))
				.contains("\"id\":1");
	}

	@Test
	public void changeStudent() {
		Student student = studentController.createStudent(new Student(2L, "Test Student", 49));

		Assertions
				.assertThat(student.getName().equals("Test Student")).isTrue();

		student.setName("Измененный студент");
		testRestTemplate.put("http://localhost:" + port + "/student", student, String.class);
		Collection<Student> studentsByAge = studentController.getStudentsByAge(49);
		Student testStudent = (Student) studentsByAge.stream().filter(student1 -> student.getName().contains("ИВАН"));
		Assertions.assertThat(testStudent.getName().equals("ИВАН")).isTrue();

		studentController.removeStudent(testStudent.getId());


	}
}





