package co.edu.uniquindio.ingesis.dtos;

import co.edu.uniquindio.ingesis.domain.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record UserRegistrationRequest(
        @NotBlank(message = "El nombre es requerido")
        String name,
        @NotBlank(message = "El apellido es requerido")
        String lastName,
        @NotBlank(message = "La cedula es requerida")
        String identification,
        @NotBlank(message = "El email es requerido")
        @Email
        String email,
        @Size(min=4,max = 20,message = "El username debe tener mínimo 4 caracteres y máximo 20")
        @NotBlank(message = "El usuario es requerido")
        String username,
        @NotBlank(message = "La contraseña es requerida")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$")
        @Size(min = 8, message = "La longitud minima es de 8")
        String password,
        Collection<Rol> roles
) {
    public UserRegistrationRequest {
        roles = Objects.requireNonNullElse(roles,List.of(Rol.USER));
    }
}
