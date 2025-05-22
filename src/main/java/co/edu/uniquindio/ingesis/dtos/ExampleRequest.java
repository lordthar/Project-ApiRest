package co.edu.uniquindio.ingesis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExampleRequest(
        @NotBlank(message = "El título no puede estar vacío")
        @Size(max = 100, message = "El título no debe superar los 100 caracteres")
        String title,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(max = 300, message = "La descripción no debe superar los 300 caracteres")
        String description,

        @NotBlank(message = "El código no puede estar vacío")
        String code,

        @NotBlank(message = "El tema del ejemplo es obligatorio")
        @Size(max = 50, message = "El tema no debe superar los 50 caracteres")
        String topic,

        @NotBlank(message = "Debe especificar un nivel de dificultad")
        String difficulty,

        @NotNull(message = "Debe asociar el ejemplo a un profesor")
        Long professorId
) {
}
