package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.constants.ConstantsForTest.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    private FacultyRepository testRepository;
    private FacultyService out;

    @BeforeEach
    public void init() {
        out = new FacultyService(testRepository);
    }

    @Test
    public void shouldReturnFacultyWhenSaveFaculty() { // create
        when (testRepository.save(FACULTY1)).thenReturn(FACULTY1);
        assertEquals(FACULTY1,out.createFaculty(FACULTY1));
    }

    @Test
    public  void shouldReturnFacultyById() { //read
        when(testRepository.findById(ID)).thenReturn(Optional.of((FACULTY1)));
        assertEquals(FACULTY1,out.getFaculty(ID));
    }

    @Test
    public void shouldReturnChangedFaculty() { // update
        when(testRepository.save(FACULTY2)).thenReturn(FACULTY2);
        assertEquals(FACULTY2,out.changeFaculty(FACULTY2));
    }

    @Test
    public void shouldRemoveFacultyById() {   //delete
       doNothing().when(testRepository).deleteById(ID);
       out.removeFaculty(ID);
       verify(testRepository,times(1)).deleteById(ID);

    }

    @Test
    public void shouldFindFacultyOrListOfFacultiesByColor() {
        when(testRepository.findAll().stream()
                .filter(faculty -> faculty.getColor()
                        .equalsIgnoreCase(FACULTY_COLOR))
                .collect(Collectors.toList())).thenReturn((List<Faculty>) FACULTY_COLLECTION);
        assertEquals(FACULTY_COLLECTION,out.findByColor(FACULTY_COLOR));
    }

    @Test
    public void shouldShowAllFaculties() {
        when(testRepository.findAll()).thenReturn((List<Faculty>) REPOSITORY_FACULTY_LIST);
        assertEquals(REPOSITORY_FACULTY_LIST,out.getAllFac());
    }

    @Test
     public void shouldGetFacultiesByNameAndColorIgnorcase () {
        when(testRepository.getFacultiesByColorAndAndNameIgnoreCase(FACULTY_NAME,FACULTY_COLOR)).thenReturn(FACULTY2);
        assertEquals(FACULTY2,out.getFacultiesByNameAndColorIgnorcase(FACULTY_NAME,FACULTY_COLOR));
    }
}