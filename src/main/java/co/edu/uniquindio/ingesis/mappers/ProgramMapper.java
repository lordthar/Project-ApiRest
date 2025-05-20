package co.edu.uniquindio.ingesis.mappers;

import co.edu.uniquindio.ingesis.domain.Program;
import co.edu.uniquindio.ingesis.dtos.ProgramRequest;
import co.edu.uniquindio.ingesis.dtos.ProgramResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface ProgramMapper {

    @Mapping(target = "id", ignore = true)
    Program parseOf(ProgramRequest request);

    ProgramResponse toProgramResponse(Program program);
}
