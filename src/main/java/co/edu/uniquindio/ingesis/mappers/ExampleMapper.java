package co.edu.uniquindio.ingesis.mappers;

import co.edu.uniquindio.ingesis.domain.Example;
import co.edu.uniquindio.ingesis.dtos.ExampleRequest;
import co.edu.uniquindio.ingesis.dtos.ExampleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface ExampleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "professor.id", source = "professorId")
    Example parseOf(ExampleRequest request);

    @Mapping(target = "professorId", source = "professor.id")
    @Mapping(target = "professorName", source = "professor.name")
    ExampleResponse toExampleResponse(Example example);

    List<ExampleResponse> toExampleResponseList(List<Example> examples);
}