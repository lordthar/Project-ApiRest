package co.edu.uniquindio.ingesis.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDate birthDate;
    private String degree;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Example> examples = new ArrayList<>();
}
