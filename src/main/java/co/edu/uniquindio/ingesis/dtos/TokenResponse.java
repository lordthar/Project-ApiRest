package co.edu.uniquindio.ingesis.dtos;

import jakarta.validation.constraints.NotBlank;

public record TokenResponse(
        @NotBlank
        String token
) {
}
