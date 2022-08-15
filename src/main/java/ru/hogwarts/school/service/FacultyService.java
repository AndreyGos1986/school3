package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepositoriy;
    Logger facultyServiceLogger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepositoriy) {
        this.facultyRepositoriy = facultyRepositoriy;
        }

    public Faculty createFaculty(Faculty faculty) { // create
        facultyServiceLogger.info("Создание факультета");
        return facultyRepositoriy.save(faculty);
    }

    public Faculty getFaculty(Long id) { // read
        facultyServiceLogger.info("Получить факультет по переданному идентификатору");
        return facultyRepositoriy.getFacultyById(id);

    }

    public Faculty changeFaculty(Faculty changedFaculty) { //update
        facultyServiceLogger.info("Заменить имеющийся факультет новым");
        return facultyRepositoriy.save(changedFaculty);
    }

    public void removeFaculty(Long id) { // delete
        facultyServiceLogger.info("Удалить факультет");
        facultyRepositoriy.deleteById(id);

    }

    public Collection<Faculty> findByColor(String color) {
        facultyServiceLogger.info("Вывод списка факультетов по соответствующему цвету");
        return facultyRepositoriy.findAll()
                .stream()
                .filter(faculty -> faculty.getColor()
                        .equals(color))
                .collect(Collectors
                        .toList());
    }

    public Collection<Faculty> getAllFac() {
        facultyServiceLogger.info("Получить список всех факультетов");
        return facultyRepositoriy.findAll();
    }

    public Faculty getFacultiesByNameAndColorIgnorcase(String color, String name) {
        facultyServiceLogger.info("Получить факультет по названию и цвету");
        return facultyRepositoriy.getFacultiesByColorAndAndNameIgnoreCase(color, name);
    }

    public List<Student> getAllStudentsByFacultyId(long id) {
        facultyServiceLogger.info("Получить список студентов, находящихся в одном факультете");
        Faculty faculty = facultyRepositoriy.getFacultyById(id);
        if (faculty == null) {
            facultyServiceLogger.info("Факультет на найден");
            return null;
        }
        facultyServiceLogger.info("Формирование списка найденных студентов");
        return faculty.getStudents().stream().toList();
    }

    public String getTheMostLongerNameOfTheFaculty() {
        return facultyRepositoriy.findAll().stream()
                .parallel() // в данном случае бессмысленно
                .map(a -> a.getName())
                .max((a,b) -> a.length() - b.length()).orElseThrow();
    }

    public Integer getSum(Integer variants) {
        final int size = 1_000_000;
        switch(variants) {
            case 0:
                return Stream.iterate(1, a -> a + 1).limit(size).reduce(0, (a, b) -> a + b );
            case 1:
                return Stream.iterate(1, a -> a + 1).parallel().limit(size).reduce(0, (a, b) -> a + b );
            case 2:
                int sum = 0;
                for (int i = 0; i < size; i++) {
                    sum += (i + 1);
                }
                return sum;
            case 3:
                int[] testArray = new int[size];
                for (int i = 0; i < size; i++) {
                    testArray[i] = (i + 1);
                }
                return Arrays.stream(testArray).sum();
        }
        throw new RuntimeException("Что-то пошло не так");
    }

}
