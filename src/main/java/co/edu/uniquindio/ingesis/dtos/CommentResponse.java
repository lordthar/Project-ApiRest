package co.edu.uniquindio.ingesis.dtos;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,

        Long programId,

        String content,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {}
