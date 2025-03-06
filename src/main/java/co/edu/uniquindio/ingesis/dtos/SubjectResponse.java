package co.edu.uniquindio.ingesis.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SubjectResponse(
        @NotBlank(message = "El id es requerido")
        String id,

        @NotBlank(message = "El nombre de la asignatura es requerido")
        String name,

        @NotBlank(message = "La descripción de la asignatura es requerida")
        String description,

        @Min(value = 1, message = "Los créditos deben ser al menos 1")
        int credits
) {

}
