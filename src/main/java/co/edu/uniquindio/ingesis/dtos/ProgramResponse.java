package co.edu.uniquindio.ingesis.dtos;

import jakarta.validation.constraints.NotBlank;

public record ProgramResponse(
        @NotBlank(message = "El id es requerido")
        String id,

        @NotBlank(message = "El título es requerido")
        String title,

        @NotBlank(message = "La descripción es requerida")
        String description,

        @NotBlank(message = "El código es requerido")
        String code
) {
}
