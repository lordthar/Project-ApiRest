package co.edu.uniquindio.ingesis.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "programs")
@Getter
@Setter
public class Program extends PanacheEntity {
    private String title;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String code;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
