package co.edu.uniquindio.ingesis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ExampleResponse(
        @NotBlank(message = "El título no puede estar vacío")
        @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
        String title,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(max = 300, message = "La descripción no puede tener más de 300 caracteres")
        String description,

        @NotBlank(message = "El código no puede estar vacío")
        String code,

        @NotBlank(message = "El tema no puede estar vacío")
        String topic,

        @NotBlank(message = "La dificultad no puede estar vacía")
        String difficulty,

        @NotNull(message = "El ID del profesor no puede ser nulo")
        Long professorId,

        @NotBlank(message = "El nombre del profesor no puede estar vacío")
        String professorName,

        @NotNull(message = "La fecha de creación no puede ser nula")
        LocalDateTime createdAt
) {
}
