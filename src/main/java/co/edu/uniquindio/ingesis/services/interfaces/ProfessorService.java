package co.edu.uniquindio.ingesis.services.interfaces;

import co.edu.uniquindio.ingesis.domain.Professor;
import co.edu.uniquindio.ingesis.domain.User;
import co.edu.uniquindio.ingesis.dtos.*;

import java.util.ArrayList;

public interface ProfessorService {
    ProfessorResponse createProfessor(ProfessorRegistrationRequest request);
    ProfessorResponse getProfessor(Long id);
    String deleteProfessor(Long id);
    ProfessorResponse updateProfessor(ProfessorRegistrationRequest request);
    ProfessorResponse updateProfessorPatch(Long id,ProfessorRegistrationRequest request);
    ArrayList<Professor> getProfessors(PaginationRequest request);
}
