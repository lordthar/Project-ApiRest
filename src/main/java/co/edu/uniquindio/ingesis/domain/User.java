package co.edu.uniquindio.ingesis.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends PanacheEntity {

    private String name;
    private String lastName;
    private String email;
    private int semester;
    private String identification;
    private String phoneNumber;
    private String password;
    private String username;
    private LocalDate birthDate;
}
