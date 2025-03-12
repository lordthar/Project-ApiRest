package co.edu.uniquindio.ingesis.dtos;

import co.edu.uniquindio.ingesis.domain.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Collection;

public record ProfessorResponse(
        @NotBlank(message = "El id es requerido")
        String id,
        @NotBlank(message = "El nombre es requerido")
        String name,
        @NotBlank(message = "El apellido es requerido")
        String lastName,
        @NotBlank(message = "El email es requerido")
        @Email
        String email,
        @NotBlank(message = "La cedula es requerida")
        String identification,
        String phoneNumber,
        Collection<Rol> roles
) {
}
