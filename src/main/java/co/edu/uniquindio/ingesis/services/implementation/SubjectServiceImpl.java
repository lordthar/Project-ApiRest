package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.Subject;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.SubjectRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.SubjectResponse;
import co.edu.uniquindio.ingesis.mappers.SubjectMapper;
import co.edu.uniquindio.ingesis.repositories.SubjectRepository;
import co.edu.uniquindio.ingesis.services.interfaces.SubjectService;
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
public class SubjectServiceImpl implements SubjectService {

    private static final Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

    final SubjectRepository subjectRepository;
    final SubjectMapper subjectMapper;

    @Override
    @Transactional
    public SubjectResponse createSubject(SubjectRegistrationRequest request) {
        logger.info("Creando una nueva materia con el request: {}", request);
        Subject subject = subjectMapper.parceOf(request);
        subject.persist();
        logger.info("Materia creada con éxito: {}", subject);
        return subjectMapper.toSubjectResponse(subject);
    }

    @Override
    public SubjectResponse getSubject(Long id) {
        logger.info("Obteniendo materia con ID: {}", id);
        Subject subject = subjectRepository.findById(id);
        SubjectResponse subjectResponse = subjectMapper.toSubjectResponse(subject);
        logger.info("Materia obtenida: {}", subjectResponse);
        return subjectResponse;
    }

    @Override
    @Transactional
    public String deleteSubject(Long id) {
        try {
            logger.info("Eliminando materia con ID: {}", id);
            SubjectResponse subjectResponse = getSubject(id);
            if (subjectResponse == null) {
                logger.warn("Materia no encontrada con ID: {}", id);
                throw new NotFoundException("Materia no encontrada");
            }
            subjectRepository.deleteById(id);
            logger.info("Materia eliminada correctamente con ID: {}", id);
            return "Materia eliminada correctamente";
        } catch (Exception e) {
            logger.error("Error al eliminar materia con ID: {}", id, e);
            return "Error al eliminar la materia";
        }
    }

    @Override
    @Transactional
    public SubjectResponse updateSubject(SubjectRegistrationRequest request) {
        logger.info("Actualizando materia con request: {}", request);
        Subject subject = subjectRepository.find("name", request.name()).firstResult();
        if (subject == null) {
            logger.warn("Materia no encontrada para actualización: {}", request.name());
            throw new NotFoundException("Materia no encontrada");
        }
        subject.setName(request.name());
        subject.setDescription(request.description());
        subject.persist();
        logger.info("Materia actualizada: {}", subject);
        return subjectMapper.toSubjectResponse(subject);
    }

    @Override
    @Transactional
    public SubjectResponse updateSubjectPatch(Long id, SubjectRegistrationRequest request) {
        logger.info("Realizando actualización parcial de materia con ID: {}", id);
        Subject subject = subjectRepository.findById(id);
        if (subject == null) {
            logger.warn("Materia no encontrada para actualización parcial con ID: {}", id);
            throw new NotFoundException("Materia no encontrada");
        }
        subject.setName(request.name());
        subject.setDescription(request.description());
        subject.persist();
        logger.info("Materia actualizada parcialmente: {}", subject);
        return subjectMapper.toSubjectResponse(subject);
    }

    @Override
    public ArrayList<Subject> getSubjects(PaginationRequest request) {
        logger.info("Obteniendo lista de materias con offset: {} y limit: {}", request.offset(), request.limit());
        PanacheQuery<Subject> query = subjectRepository.findAll();
        query.page(request.offset() / request.limit(), request.limit());
        ArrayList<Subject> subjects = new ArrayList<>(query.list());
        logger.info("Materias obtenidas: {}", subjects.size());
        return subjects;
    }
}
