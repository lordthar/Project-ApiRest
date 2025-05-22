package co.edu.uniquindio.ingesis.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends PanacheEntity {

    private String name;
    private String lastName;
    private String email;
    private int semester;
    private String identification;
    private String phoneNumber;
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Program> programs;
}
