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
import java.util.ArrayList;

@ApplicationScoped
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    final SubjectRepository subjectRepository;
    final SubjectMapper subjectMapper;

    @Override
    @Transactional
    public SubjectResponse createSubject(SubjectRegistrationRequest request) {
        Subject subject = subjectMapper.parceOf(request);
        subject.persist();
        return subjectMapper.toSubjectResponse(subject);
    }

    @Override
    public SubjectResponse getSubject(Long id) {
        Subject subject = subjectRepository.findById(id);
        return subjectMapper.toSubjectResponse(subject);
    }

    @Override
    @Transactional
    public String deleteSubject(Long id) {
        SubjectResponse subjectResponse = getSubject(id);

        if (subjectResponse == null) {
            throw new NotFoundException("Error");
        }
        subjectRepository.deleteById(Long.valueOf(subjectResponse.id()));

        return "la materia fue eliminada correctamente";
    }

    @Override
    @Transactional
    public SubjectResponse updateSubject(SubjectRegistrationRequest request) {
        Subject subject = subjectRepository.find("name", request.name()).firstResult();
        if(subject == null) {
            throw new NotFoundException("No existe el usuario");
        }
        subject.setName(request.name());
        subject.setDescription(request.description());
        subject.persist();

        return subjectMapper.toSubjectResponse(subject);
    }


    @Override
    @Transactional
    public SubjectResponse updateSubjectPatch(Long id, SubjectRegistrationRequest request) {
        Subject subject = subjectRepository.findById(id);
        if(subject == null) {
            throw new NotFoundException("No existe el usuario");
        }
        subject.setName(request.name());
        subject.setDescription(request.description());
        subject.persist();

        return subjectMapper.toSubjectResponse(subject);
    }

    @Override
    public ArrayList<Subject> getSubjects(PaginationRequest request) {
        PanacheQuery<Subject> query = subjectRepository.findAll();
        query.page(request.offset() / request.limit() ,request.limit());
        return new ArrayList<>(query.list());
    }
}
