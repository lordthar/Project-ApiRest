package co.edu.uniquindio.ingesis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AccountRequest(
        @Size(min = 4, max = 20, message = "El username debe tener mínimo 4 caracteres y máximo 20")
        @NotBlank(message = "El usuario es requerido")
        String username,
        @NotBlank(message = "La contraseña es requerida")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$")
        @Size(min = 8, message = "La longitud minima es de 8")
        String password
) {
}
