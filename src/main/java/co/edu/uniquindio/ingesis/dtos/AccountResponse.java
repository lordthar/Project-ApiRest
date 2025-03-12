package co.edu.uniquindio.ingesis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AccountResponse(
        @Size(min=4,max = 20,message = "El username debe tener mínimo 4 caracteres y máximo 20")
        @NotBlank(message = "El user es requerido")
        String username,
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$")
        String password
) {
}
