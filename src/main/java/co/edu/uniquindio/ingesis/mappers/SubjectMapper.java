package co.edu.uniquindio.ingesis.mappers;

import co.edu.uniquindio.ingesis.domain.Subject;
import co.edu.uniquindio.ingesis.dtos.SubjectRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.SubjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface SubjectMapper {
    Subject parceOf(SubjectRegistrationRequest registrationRequest);
    SubjectResponse toSubjectResponse(Subject subject);
}
