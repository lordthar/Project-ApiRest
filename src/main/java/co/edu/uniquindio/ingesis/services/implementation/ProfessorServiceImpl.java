package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.Professor;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.ProfessorRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.ProfessorResponse;
import co.edu.uniquindio.ingesis.mappers.ProfessorMapper;
import co.edu.uniquindio.ingesis.repositories.ProfessorRepository;
import co.edu.uniquindio.ingesis.services.interfaces.ProfessorService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@ApplicationScoped
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    final ProfessorRepository professorRepository;
    final ProfessorMapper professorMapper;

    @Override
    @Transactional
    public ProfessorResponse createProfessor(ProfessorRegistrationRequest request) {
        Professor professor = professorMapper.parseOf(request);
        professor.persist();
        return professorMapper.toProfessorResponse(professor);
    }

    @Override
    public ProfessorResponse getProfessor(Long id) {
        Professor professor = professorRepository.findById(id);
        return professorMapper.toProfessorResponse(professor);
    }

    @Override
    @Transactional
    public String deleteProfessor(Long id) {
        ProfessorResponse professorResponse = getProfessor(id);

        if (professorResponse == null) {
            throw new NotFoundException("Error");
        }
        professorRepository.deleteById(Long.valueOf(professorResponse.id()));

        return "El profesor fue eliminado correctamente";
    }

    @Override
    @Transactional
    public ProfessorResponse updateProfessor(ProfessorRegistrationRequest request) {
       Professor professor = professorRepository.find("identification", request.identification()).firstResult();
        if(professor == null) {
            throw new NotFoundException("No existe el usuario");
        }
        professor.setName(request.name());
        professor.setLastName(request.lastName());
        professor.setEmail(request.email());
        professor.setPhoneNumber(request.phoneNumber());
        professor.persist();

        return professorMapper.toProfessorResponse(professor);
    }

    @Override
    @Transactional
    public ProfessorResponse updateProfessorPatch(Long id, ProfessorRegistrationRequest request) {
        Professor professor = professorRepository.findById(id);
        if(professor == null) {
            throw new NotFoundException("No existe el usuario");
        }
        professor.setName(request.name());
        professor.setLastName(request.lastName());
        professor.setEmail(request.email());
        professor.setPhoneNumber(request.phoneNumber());
        professor.persist();

        return professorMapper.toProfessorResponse(professor);
    }

    @Override
    public ArrayList<Professor> getProfessors(PaginationRequest request) {
        PanacheQuery<Professor> query = professorRepository.findAll();
        query.page(request.offset() / request.limit() ,request.limit());
        return new ArrayList<>(query.list());
    }
}
