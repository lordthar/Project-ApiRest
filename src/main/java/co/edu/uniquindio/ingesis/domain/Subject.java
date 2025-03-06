package co.edu.uniquindio.ingesis.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subject")
@Getter
@Setter
public class Subject extends PanacheEntity {

    private String name;
    private String description;
    private int credits;

}
