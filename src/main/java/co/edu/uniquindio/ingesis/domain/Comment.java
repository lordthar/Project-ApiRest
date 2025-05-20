package co.edu.uniquindio.ingesis.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@RequiredArgsConstructor
@Getter
@Setter
public class Comment extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;  // Relación con la entidad Program (Un comentario pertenece a un programa)

    @Column(nullable = false, length = 500)
    private String content;  // Contenido del comentario

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // Fecha y hora de creación del comentario

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Fecha y hora de actualización del comentario

}
