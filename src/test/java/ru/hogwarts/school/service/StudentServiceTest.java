package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.constants.ConstantsForTest.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest  {
    @Mock
    private StudentRepository testRepository;
    private StudentService out;

    @BeforeEach
    public void init() {
        out = new StudentService(testRepository);
    }

    @Test
    void shouldCreateStudent() {
        when (testRepository.save(STUDENT1)).thenReturn(STUDENT1);
        assertEquals(STUDENT1,out.createStudent(STUDENT1));
    }

    @Test
    void shouldFindStudendById() {
        when(testRepository.findById(ID)).thenReturn(Optional.of(STUDENT1));
        assertEquals(STUDENT1,out.getStudent(ID));

    }

    @Test
    void shouldChangeStudent() {
        when (testRepository.save(STUDENT2)).thenReturn(STUDENT2);
        assertEquals(STUDENT2,out.changeStudent(STUDENT2));
    }

    @Test
    void shouldRemoveStudent() {
        doNothing().when(testRepository).deleteById(ID);
        out.removeStudent(ID);
        verify(testRepository,times(1)).deleteById(ID);
    }

    @Test
    void shouldShowAllStudents() {
        when(testRepository.findAll()).thenReturn(STUDENT_LIST);
        assertEquals(STUDENT_LIST,out.getAll());
    }

    @Test
    void shouldFindStudentByAge() {
        when(testRepository.findAll()
                .stream()
                .filter(student -> student.getAge()==AGE)
                .collect(Collectors.toList()))
                .thenReturn(STUDENT_LIST_25);
        assertEquals(STUDENT_LIST_25,out.findByAge(AGE));
    }

    @Test
    void shouldFindStudentsBetweenAge (){
        when(testRepository.findStudentByAgeBetween(MIN_AGE,MAX_AGE)).thenReturn(STUDENT_AGE_BETWEEN_LIST);
        assertEquals(STUDENT_AGE_BETWEEN_LIST,out.findByAgeBetween(MIN_AGE,MAX_AGE));
    }
//    @Test
//    void shouldGetFacultyByStudenId (){
//    when(testRepository.findById(ID).get().getFaculty()).thenReturn(FACULTY4);
//        assertEquals(FACULTY4,out.getFacultyByStudentsId(ID));
//    }

}