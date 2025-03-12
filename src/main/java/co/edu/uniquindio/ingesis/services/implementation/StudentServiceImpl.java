package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.Student;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.StudentRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.StudentResponse;
import co.edu.uniquindio.ingesis.mappers.StudentMapper;
import co.edu.uniquindio.ingesis.repositories.StudentRepository;
import co.edu.uniquindio.ingesis.services.interfaces.StudentService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@ApplicationScoped
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    final StudentMapper studentMapper;
    final StudentRepository studentRepository;

    @Override
    @Transactional
    public StudentResponse createStudent(StudentRegistrationRequest request) {
        Student student = studentMapper.parseOf(request);
        student.persist();
        return studentMapper.toUserResponse(student);
    }

    @Override
    public StudentResponse getStudent(Long id) {
        Student student = studentRepository.findById(id);
        return studentMapper.toUserResponse(student);
    }

    @Override
    @Transactional
    public String deleteStudent(Long id) {
        StudentResponse user = getStudent(id);

        if (user == null) {
            throw new NotFoundException("Error");
        }
        studentRepository.deleteById(Long.valueOf(user.id()));

        return "El usuario fue eliminado correctamente";
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(StudentRegistrationRequest request) {
        Student student = studentRepository.find("identification", request.identification()).firstResult();
        if(student == null) {
            throw new NotFoundException("No existe el usuario");
       }
       student.setName(request.name());
       student.setLastName(request.lastName());
       student.setEmail(request.email());
       student.setPhoneNumber(request.phoneNumber());
       student.persist();

       return studentMapper.toUserResponse(student);

    }

    @Override
    @Transactional
    public StudentResponse updateStudentPatch(Long id, StudentRegistrationRequest request) {
        Student student = studentRepository.findById(id);
        if(student == null) {
            throw new NotFoundException("No existe el usuario");
        }
        student.setName(request.name());
        student.setLastName(request.lastName());
        student.setEmail(request.email());
        student.setPhoneNumber(request.phoneNumber());
        student.persist();

        return studentMapper.toUserResponse(student);

    }

    @Override
    public ArrayList<Student> getStudents(PaginationRequest request) {
        PanacheQuery<Student> query = studentRepository.findAll();
        query.page(request.offset() / request.limit() ,request.limit());
        return new ArrayList<>(query.list());
    }

}
