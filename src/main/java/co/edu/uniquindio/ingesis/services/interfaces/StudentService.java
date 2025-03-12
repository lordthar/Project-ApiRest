package co.edu.uniquindio.ingesis.services.interfaces;

import co.edu.uniquindio.ingesis.domain.Student;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.StudentRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.StudentResponse;

import java.util.ArrayList;

public interface StudentService {
    StudentResponse createStudent(StudentRegistrationRequest request);
    StudentResponse getStudent(Long id);
    String deleteStudent(Long id);
    StudentResponse updateStudent(StudentRegistrationRequest request);
    StudentResponse updateStudentPatch(Long id, StudentRegistrationRequest request);
    ArrayList<Student> getStudents(PaginationRequest request);

}
