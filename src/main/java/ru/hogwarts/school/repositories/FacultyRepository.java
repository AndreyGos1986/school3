package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;


public interface FacultyRepository extends JpaRepository<Faculty,Long> {
   Faculty getFacultiesByColorAndAndNameIgnoreCase (String color,String name);
   Faculty getFacultyById (long id);
   Collection <Faculty> findByColor(String color);

}
