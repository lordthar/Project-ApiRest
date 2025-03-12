package co.edu.uniquindio.ingesis.dtos;

import co.edu.uniquindio.ingesis.domain.Rol;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record ProfessorRegistrationRequest(
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
        @NotNull(message = "La fecha no puede ser nula")
        @PastOrPresent(message = "La fecha no puede ser futura")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthDate,
        @Pattern(regexp = "^[0-9]+$", message = "El número de teléfono solo debe contener dígitos.")
        String phoneNumber,
        Collection<Rol> roles
) {
    public ProfessorRegistrationRequest {
        roles = Objects.requireNonNullElse(roles, List.of(Rol.PROFESSOR));
    }
}



