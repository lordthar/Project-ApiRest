package co.edu.uniquindio.ingesis.mappers;
import co.edu.uniquindio.ingesis.domain.Professor;
import co.edu.uniquindio.ingesis.dtos.ProfessorRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.ProfessorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface ProfessorMapper {
    @Mapping(target = "id", ignore = true)
    Professor parseOf(ProfessorRegistrationRequest request);
    ProfessorResponse toProfessorResponse(Professor professor);
}
