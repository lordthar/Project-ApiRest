package co.edu.uniquindio.ingesis.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
}
