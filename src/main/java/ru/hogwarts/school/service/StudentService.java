package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    Logger studentServiceLogger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) { // create
        studentServiceLogger.info("Добавление студента");
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) { // read
        studentServiceLogger.info("Получение студента по идентификационному номеру");
        return studentRepository.findById(id).get();
    } // read

    public Student changeStudent(Student changedStudent) { //update
        studentServiceLogger.info("Заменить студента");
        return studentRepository.save(changedStudent);
    }


    public void removeStudent(Long id) { // delete
        studentServiceLogger.info("Исключить студента по идентификационному номеру");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {
        studentServiceLogger.info("Получить список всех студентов");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        studentServiceLogger.info("Получить список студентов с определённым возрастом");
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age).collect(Collectors.toList());

    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        studentServiceLogger.info("Получить список студентов в указанном возрастном диапазоне");
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudentsId(long id) {
        studentServiceLogger.info("Получение факультета по идентификатору студента");
        Student student = getStudent(id);
        if (student == null) {
            studentServiceLogger.info("Вернуть пустое значение, если студент не найден");
            return null;
        }
        return student.getFaculty();
    }

    public Integer getStudentsQuantity() {
        studentServiceLogger.info("Получить общее количество студентов");
        return studentRepository.getAll();
    }

    public Double getAverageAgeOfStudents() {
        studentServiceLogger.info("Получить средний возраст студентов");
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getLastFiveStudents() {
        studentServiceLogger.info("Получить список из последних пяти студентов");
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getAllStudentsWhoNameBeginsFromA() {
        return studentRepository
                .findAll()
                .stream()
                .filter(Objects::nonNull)
            //    .parallel() //в принципе, тут ни к чему параллелить, т.к. полтора студента
                .map(student -> student.getName().toUpperCase())
                .filter(student -> student.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getAverageAgeOfStudentsByStream() {
        return studentRepository.findAll().stream()
                .filter(Objects::nonNull)
            //    .parallel() //в принципе, тут ни к чему параллелить, также, как и выше
                .mapToDouble(value -> value.getAge())
                .average().orElseThrow();
    }
}
