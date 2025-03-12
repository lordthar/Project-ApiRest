package co.edu.uniquindio.ingesis.repositories;

import co.edu.uniquindio.ingesis.domain.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface StudentRepository extends PanacheRepository<Student> {
}
