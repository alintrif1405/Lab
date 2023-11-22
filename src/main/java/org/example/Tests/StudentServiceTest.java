package org.example.Tests;

import org.example.model.Students;
import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;
    private Students student;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void getAllStudents(){
//      List<Students> students = studentService.getAllStudents();
//      students.add(new Students());students.add(new Students());
//      assertEquals(2, students.size());
        when(studentRepository.findAll()).thenReturn(Arrays.asList(new Students(), new Students()));
        assertEquals(2, studentService.getAllStudents().size());
    }

    @Test
    void getById(){
        int StudentId=1;
        Students student=new Students();
        when(studentRepository.findById(StudentId)).thenReturn(Optional.of(student));

        assertEquals(Optional.of(student),studentService.getStudentById(StudentId));
    }

}
