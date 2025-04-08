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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@ApplicationScoped
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    final StudentMapper studentMapper;
    final StudentRepository studentRepository;

    @Override
    @Transactional
    public StudentResponse createStudent(StudentRegistrationRequest request) {
        logger.info("Creando un nuevo estudiante con el request: {}", request);
        Student student = studentMapper.parseOf(request);
        student.persist();
        logger.info("Estudiante creado con éxito: {}", student);
        return studentMapper.toUserResponse(student);
    }

    @Override
    public StudentResponse getStudent(Long id) {
        logger.info("Obteniendo estudiante con ID: {}", id);
        Student student = studentRepository.findById(id);
        if (student == null) {
            logger.warn("Estudiante no encontrado con ID: {}", id);
            throw new NotFoundException("Estudiante no encontrado");
        }
        StudentResponse studentResponse = studentMapper.toUserResponse(student);
        logger.info("Estudiante obtenido: {}", studentResponse);
        return studentResponse;
    }

    @Override
    @Transactional
    public String deleteStudent(Long id) {
        logger.info("Eliminando estudiante con ID: {}", id);
        StudentResponse studentResponse = getStudent(id);

        if (studentResponse == null) {
            logger.warn("No se pudo encontrar el estudiante con ID: {}", id);
            throw new NotFoundException("Estudiante no encontrado");
        }
        studentRepository.deleteById(Long.valueOf(studentResponse.id()));
        logger.info("Estudiante eliminado correctamente con ID: {}", id);
        return "El estudiante fue eliminado correctamente";
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(StudentRegistrationRequest request) {
        logger.info("Actualizando estudiante con el request: {}", request);
        Student student = studentRepository.find("identification", request.identification()).firstResult();
        if (student == null) {
            logger.warn("Estudiante no encontrado con identificación: {}", request.identification());
            throw new NotFoundException("Estudiante no encontrado");
        }
        student.setName(request.name());
        student.setLastName(request.lastName());
        student.setEmail(request.email());
        student.setPhoneNumber(request.phoneNumber());
        student.persist();
        logger.info("Estudiante actualizado: {}", student);
        return studentMapper.toUserResponse(student);
    }

    @Override
    @Transactional
    public StudentResponse updateStudentPatch(Long id, StudentRegistrationRequest request) {
        logger.info("Realizando actualización parcial del estudiante con ID: {}", id);
        Student student = studentRepository.findById(id);
        if (student == null) {
            logger.warn("Estudiante no encontrado con ID: {}", id);
            throw new NotFoundException("Estudiante no encontrado");
        }
        student.setName(request.name());
        student.setLastName(request.lastName());
        student.setEmail(request.email());
        student.setPhoneNumber(request.phoneNumber());
        student.persist();
        logger.info("Estudiante actualizado parcialmente: {}", student);
        return studentMapper.toUserResponse(student);
    }

    @Override
    public ArrayList<Student> getStudents(PaginationRequest request) {
        logger.info("Obteniendo lista de estudiantes con offset: {} y limit: {}", request.offset(), request.limit());
        PanacheQuery<Student> query = studentRepository.findAll();
        query.page(request.offset() / request.limit(), request.limit());
        ArrayList<Student> students = new ArrayList<>(query.list());
        logger.info("Estudiantes obtenidos: {}", students.size());
        return students;
    }
}
