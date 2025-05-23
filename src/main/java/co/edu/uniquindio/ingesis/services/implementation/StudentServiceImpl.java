package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.Student;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.StudentRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.StudentResponse;
import co.edu.uniquindio.ingesis.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.ingesis.mappers.StudentMapper;
import co.edu.uniquindio.ingesis.repositories.StudentRepository;
import co.edu.uniquindio.ingesis.services.interfaces.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@ApplicationScoped
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    @Inject
    MqttService mqttService;
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    ObjectMapper objectMapper = new ObjectMapper();
    final StudentMapper studentMapper;
    final StudentRepository studentRepository;

    @Override
    @Transactional
    public StudentResponse createStudent(StudentRegistrationRequest request)  {
        logger.info("Creando un nuevo estudiante con el request: {}", request);
        Student student = studentMapper.parseOf(request);
        student.persist();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String jsonPayload = "";
        try {
            jsonPayload = objectMapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            logger.error("Error al convertir el objeto Student a JSON", e);
            throw new RuntimeException("Error al procesar el JSON del estudiante", e);
        }
        mqttService.publicar("student/new",jsonPayload);
        StudentResponse studentresponse = studentMapper.toUserResponse(student);
        logger.info("Estudiante creado con éxito: {}", student);
        return studentresponse;
    }

    @Override
    public StudentResponse getStudent(Long id) {
        logger.info("Obteniendo estudiante con ID: {}", id);
        Student student = studentRepository.findById(id);
        if (student == null) {
            logger.warn("Estudiante no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Estudiante no encontrado");
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
            throw new ResourceNotFoundException("Estudiante no encontrado");
        }
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonPayload = "";
        try {
            jsonPayload = objectMapper.writeValueAsString(studentResponse);
        } catch (JsonProcessingException e) {
            logger.error("Error al convertir el objeto Student a JSON", e);
            throw new RuntimeException("Error al procesar el JSON del estudiante", e);
        }
        mqttService.publicar("student/delete",jsonPayload);
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
            throw new ResourceNotFoundException("Estudiante no encontrado");
        }
        student.setName(request.name());
        student.setLastName(request.lastName());
        student.setEmail(request.email());
        student.setPhoneNumber(request.phoneNumber());
        student.persist();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String jsonPayload = "";
        try {
            jsonPayload = objectMapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            logger.error("Error al convertir el objeto Student a JSON", e);
            throw new RuntimeException("Error al procesar el JSON del estudiante", e);
        }

        mqttService.publicar("student/update", jsonPayload);
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
            throw new ResourceNotFoundException("Estudiante no encontrado");
        }
        student.setName(request.name());
        student.setLastName(request.lastName());
        student.setEmail(request.email());
        student.setPhoneNumber(request.phoneNumber());
        student.persist();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String jsonPayload = "";
        try {
            jsonPayload = objectMapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            logger.error("Error al convertir el objeto Student a JSON", e);
            throw new RuntimeException("Error al procesar el JSON del estudiante", e);
        }

        mqttService.publicar("student/soft-update", jsonPayload);
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
