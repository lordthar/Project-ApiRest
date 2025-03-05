package co.edu.uniquindio.ingesis.repositories;

import co.edu.uniquindio.ingesis.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface UserRepository extends PanacheRepository<User> {
}
