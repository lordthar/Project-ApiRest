package co.edu.uniquindio.ingesis.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "professor")
@Getter
@Setter
public class Professor extends PanacheEntity {
    private String Name;
    private String lastName;
    private String identification;
    private String email;
    private String phoneNumber;
    private String subject;
    private int yearsOfExperience;
    private String degree;
    private String password;
}
