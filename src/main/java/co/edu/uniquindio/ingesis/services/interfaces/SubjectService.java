package co.edu.uniquindio.ingesis.services.interfaces;


import co.edu.uniquindio.ingesis.domain.Subject;
import co.edu.uniquindio.ingesis.dtos.*;

import java.util.ArrayList;

public interface SubjectService {

    SubjectResponse createSubject(SubjectRegistrationRequest request);
    SubjectResponse getSubject(Long id);
    String deleteSubject(Long id);
    SubjectResponse updateSubject(SubjectRegistrationRequest request);
    SubjectResponse updateSubjectPatch(Long id,SubjectRegistrationRequest request);
    ArrayList<Subject> getSubjects(PaginationRequest request);
}
