package co.edu.uniquindio.ingesis.repositories;

import co.edu.uniquindio.ingesis.domain.Comment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface CommentRepository extends PanacheRepository<Comment> {
}
