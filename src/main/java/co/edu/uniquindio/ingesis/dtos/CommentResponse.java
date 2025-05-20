package co.edu.uniquindio.ingesis.dtos;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,  // ID del comentario

        Long programId,  // ID del programa al que pertenece el comentario

        String content,  // Contenido del comentario

        LocalDateTime createdAt,  // Fecha de creación del comentario

        LocalDateTime updatedAt  // Fecha de última actualización del comentario
) {}
