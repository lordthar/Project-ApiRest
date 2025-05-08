package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.Professor;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.ProfessorRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.ProfessorResponse;
import co.edu.uniquindio.ingesis.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.ingesis.mappers.ProfessorMapper;
import co.edu.uniquindio.ingesis.repositories.ProfessorRepository;
import co.edu.uniquindio.ingesis.services.interfaces.ProfessorService;
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
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@ApplicationScoped
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private static final Logger logger = LoggerFactory.getLogger(ProfessorServiceImpl.class);
    @Inject
    MqttService mqttService;
    ObjectMapper objectMapper = new ObjectMapper();
    final ProfessorRepository professorRepository;
    final ProfessorMapper professorMapper;

    @Override
    @Transactional
    public ProfessorResponse createProfessor(ProfessorRegistrationRequest request) {
        logger.info("Creando un nuevo profesor con el request: {}", request);
        Professor professor = professorMapper.parseOf(request);
        professor.persist();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String jsonPayload = "";
        try {
            jsonPayload = objectMapper.writeValueAsString(professor);
        } catch (JsonProcessingException e) {
            logger.error("Error al convertir el objeto Student a JSON", e);
            throw new RuntimeException("Error al procesar el JSON del estudiante", e);
        }

        mqttService.publicar("professor/new",jsonPayload);

        logger.info("Profesor creado con éxito: {}", professor);
        return professorMapper.toProfessorResponse(professor);
    }

    @Override
    public ProfessorResponse getProfessor(Long id) {
        logger.info("Obteniendo profesor con ID: {}", id);
        Professor professor = professorRepository.findById(id);

        if (professor == null) {
            throw new ResourceNotFoundException("Profesor no encontrado");
        }

        ProfessorResponse professorResponse = professorMapper.toProfessorResponse(professor);
        logger.info("Profesor obtenido: {}", professorResponse);
        return professorResponse;
    }

    @Override
    @Transactional
    public String deleteProfessor(Long id) {
        try {
            logger.info("Eliminando profesor con ID: {}", id);
            ProfessorResponse professorResponse = getProfessor(id);
            if (professorResponse == null) {
                logger.warn("Profesor no encontrado con ID: {}", id);
                throw new ResourceNotFoundException("Profesor no encontrado");
            }
            professorRepository.deleteById(id);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String jsonPayload = "";
            try {
                jsonPayload = objectMapper.writeValueAsString(professorResponse);
            } catch (JsonProcessingException e) {
                logger.error("Error al convertir el objeto Student a JSON", e);
                throw new RuntimeException("Error al procesar el JSON del estudiante", e);
            }

            mqttService.publicar("professor/delete", jsonPayload);
            logger.info("Profesor eliminado exitosamente con ID: {}", id);
            return "El profesor fue eliminado correctamente";
        } catch (ResourceNotFoundException e) {
            logger.error("Error al eliminar profesor con ID: {}", id, e);
            throw new ResourceNotFoundException("Error al eliminar el profesor");
        } catch (Exception e2) {
            logger.error("Error al eliminar profesor con ID: {}", id, e2);
            return "Error al eliminar el profesor";
        }
    }

    @Override
    @Transactional
    public ProfessorResponse updateProfessor(ProfessorRegistrationRequest request) {
        logger.info("Actualizando profesor con request: {}", request);
        Professor professor = professorRepository.find("identification", request.identification()).firstResult();
        if (professor == null) {
            logger.warn("Profesor no encontrado para actualizar: {}", request.identification());
            throw new ResourceNotFoundException("Profesor no encontrado");
        }
        professor.setName(request.name());
        professor.setLastName(request.lastName());
        professor.setEmail(request.email());
        professor.setPhoneNumber(request.phoneNumber());
        professor.persist();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String jsonPayload = "";
        try {
            jsonPayload = objectMapper.writeValueAsString(professor);
        } catch (JsonProcessingException e) {
            logger.error("Error al convertir el objeto Student a JSON", e);
            throw new RuntimeException("Error al procesar el JSON del estudiante", e);
        }

        mqttService.publicar("professor/update",jsonPayload);
        logger.info("Profesor actualizado: {}", professor);
        return professorMapper.toProfessorResponse(professor);
    }

    @Override
    @Transactional
    public ProfessorResponse updateProfessorPatch(Long id, ProfessorRegistrationRequest request) {
        logger.info("Realizando actualización parcial del profesor con ID: {}", id);
        Professor professor = professorRepository.findById(id);
        if (professor == null) {
            logger.warn("Profesor no encontrado para actualización parcial con ID: {}", id);
            throw new NotFoundException("Profesor no encontrado");
        }
        professor.setName(request.name());
        professor.setLastName(request.lastName());
        professor.setEmail(request.email());
        professor.setPhoneNumber(request.phoneNumber());
        professor.persist();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String jsonPayload = "";
        try {
            jsonPayload = objectMapper.writeValueAsString(professor);
        } catch (JsonProcessingException e) {
            logger.error("Error al convertir el objeto Student a JSON", e);
            throw new RuntimeException("Error al procesar el JSON del estudiante", e);
        }

        mqttService.publicar("professor/soft-update",jsonPayload);
        logger.info("Profesor actualizado parcialmente: {}", professor);
        return professorMapper.toProfessorResponse(professor);
    }

    @Override
    public ArrayList<Professor> getProfessors(PaginationRequest request) {
        logger.info("Obteniendo lista de profesores con offset: {} y limit: {}", request.offset(), request.limit());
        PanacheQuery<Professor> query = professorRepository.findAll();
        query.page(request.offset() / request.limit(), request.limit());
        ArrayList<Professor> professors = new ArrayList<>(query.list());
        logger.info("Profesores obtenidos: {}", professors.size());
        return professors;
    }
}
