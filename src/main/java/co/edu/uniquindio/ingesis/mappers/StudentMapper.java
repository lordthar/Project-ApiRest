package co.edu.uniquindio.ingesis.mappers;

import co.edu.uniquindio.ingesis.domain.Student;
import co.edu.uniquindio.ingesis.dtos.StudentRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = ProgramMapper.class)
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "semester", constant = "1")
    Student parseOf(StudentRegistrationRequest userDTO);

    @Mapping(source = "programs", target = "programs")
    StudentResponse toUserResponse(Student student);
}
