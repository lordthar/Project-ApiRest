package co.edu.uniquindio.ingesis.mappers;

import co.edu.uniquindio.ingesis.domain.Comment;
import co.edu.uniquindio.ingesis.dtos.CommentRequest;
import co.edu.uniquindio.ingesis.dtos.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface CommentMapper {

    // No mapeamos directamente el ID porque al crear un Comment se usa el objeto Program completo
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "program.id", source = "programId")  // Esto ayuda si estás creando la entidad desde un request
    Comment toComment(CommentRequest request);

    // Aquí mapeamos el ID del programa (program.id) al campo programId del response
    @Mapping(target = "programId", source = "program.id")
    CommentResponse toCommentResponse(Comment comment);

    List<CommentResponse> toCommentResponseList(List<Comment> comments);
}
