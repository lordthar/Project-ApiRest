package co.edu.uniquindio.ingesis.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "examples")
@Getter
@Setter
public class Example extends PanacheEntity {
    private String title;
    private String description;
    private String code;
    private String topic;
    private String difficulty;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
}
