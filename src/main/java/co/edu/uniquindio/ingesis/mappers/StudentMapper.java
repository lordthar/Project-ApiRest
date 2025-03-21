package co.edu.uniquindio.ingesis.mappers;

import co.edu.uniquindio.ingesis.domain.Student;
import co.edu.uniquindio.ingesis.dtos.StudentResponse;
import co.edu.uniquindio.ingesis.dtos.StudentRegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface StudentMapper {
    @Mapping(target = "id", ignore = true)
    Student parseOf(StudentRegistrationRequest userDTO);

    StudentResponse toUserResponse(Student student);
}