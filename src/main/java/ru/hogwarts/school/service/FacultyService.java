package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

}
