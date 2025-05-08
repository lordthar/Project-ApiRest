package co.edu.uniquindio.ingesis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record ErrorModelResponse(
    @Size(min = 100, max = 600)
    @NotBlank
    String code,
    @NotBlank
    String message
) {
}
