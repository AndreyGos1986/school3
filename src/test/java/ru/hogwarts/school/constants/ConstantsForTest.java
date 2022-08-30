package ru.hogwarts.school.constants;

import org.aspectj.weaver.patterns.TypePatternQuestions;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public class ConstantsForTest {
    public static final long ID=1;
    public static final int AGE = 25;
    public static final int MIN_AGE=15;
    public static final int MAX_AGE=25;
    public static final Student STUDENT1 = new Student(1L,"ПЕТР",25);
    public static final Student STUDENT2 = new Student(2L,"СЕМЕН",15);
    public static final Student STUDENT3 = new Student(3L,"ВАЛЕРА",16);
    public static final Student STUDENT5 = new Student(3L,"ВАЛЕРА",19);
    public static final Student STUDENT6 = new Student(3L,"ВАЛЕРА",37);
    public static final Student STUDENT7 = new Student(3L,"ВАЛЕРА",39);

    public static final Student STUDENT4=null;

    public static final List<Student> STUDENT_LIST = List.of(STUDENT1,STUDENT2,STUDENT3);
    public static final List<Student> STUDENT_AGE_BETWEEN_LIST = List.of(STUDENT1,STUDENT2,STUDENT3,STUDENT5);
    public static final List<Student> STUDENT_LIST_25  = List.of(STUDENT1,STUDENT3);



    public static final Faculty FACULTY1 = new Faculty(1L,"физтех","красный");
    public static final Faculty FACULTY2 = new Faculty(2L,"мехмат","синий");
    public static final Faculty FACULTY3 = new Faculty(3L,"история","черный");
    public static final Faculty FACULTY4 = new Faculty(4L,"история","синий");
    public static final String FACULTY_COLOR  = "синий";
    public static final String FACULTY_NAME = "мехмат";

    public static final Collection <Faculty> FACULTY_COLLECTION = List.of(FACULTY2,FACULTY4);

    public static final Collection<Faculty> REPOSITORY_FACULTY_LIST = List.of(FACULTY1, FACULTY2, FACULTY3,FACULTY4);


}
