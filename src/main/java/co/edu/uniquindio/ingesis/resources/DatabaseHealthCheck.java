package co.edu.uniquindio.ingesis.resources;

import io.quarkiverse.nagios.health.NagiosCheck;
import io.quarkiverse.nagios.health.NagiosCheckResponse;
import io.smallrye.health.api.Wellness;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.eclipse.microprofile.health.HealthCheck;

@Wellness
@Singleton
public class DatabaseHealthCheck implements HealthCheck {

    @Inject
    EntityManager entityManager;

    @Override
    public NagiosCheckResponse call() {
        int dbStatus = checkDatabaseStatus();
        return DATABASE_STATUS.result(dbStatus).asResponse();
    }

    private int checkDatabaseStatus() {
        try {
            // Consulta para verificar conexión a PostgreSQL
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return 0; // OK
        } catch (PersistenceException e) {
            return 2; // CRITICAL
        }
    }

    private static final NagiosCheck DATABASE_STATUS = NagiosCheck.named("database connection")
            .performance()       // OK si el resultado es 0
            .criticalIf().above(0)    // CRITICAL si el resultado es mayor a 0
            .build();
}