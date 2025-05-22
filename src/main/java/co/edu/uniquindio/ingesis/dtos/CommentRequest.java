package co.edu.uniquindio.ingesis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        @NotNull(message = "El ID del programa es requerido")
        Long programId,

        @NotBlank(message = "El contenido del comentario no puede estar vacío")
        String content
) {}
