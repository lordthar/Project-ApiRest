package co.edu.uniquindio.ingesis.mappers;

import co.edu.uniquindio.ingesis.domain.Program;
import co.edu.uniquindio.ingesis.domain.Student;
import co.edu.uniquindio.ingesis.dtos.ProgramRequest;
import co.edu.uniquindio.ingesis.dtos.ProgramResponse;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface ProgramMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", source = "studentId", qualifiedByName = "mapStudent")
    Program parseOf(ProgramRequest request);

    @Mapping(target = "studentId", source = "student.id")
    ProgramResponse toProgramResponse(Program program);

    @Named("mapStudent")
    default Student mapStudent(String studentId) {
        return new Student();
    }
}
