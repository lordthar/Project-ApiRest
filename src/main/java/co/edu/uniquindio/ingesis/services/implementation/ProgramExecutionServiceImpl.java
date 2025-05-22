package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.Program;
import co.edu.uniquindio.ingesis.services.interfaces.ProgramExecutionService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProgramExecutionServiceImpl implements ProgramExecutionService {

    @Override
    public String ejecutarProgramaPorId(Long idPrograma) throws Exception {
        Program programa = Program.findById(idPrograma);
        System.out.println(programa.getCode());

        if (programa == null) {
            throw new IllegalArgumentException("Programa con ID " + idPrograma + " no encontrado.");
        }

        return DockerCodeExecutor.ejecutarCodigoEnContenedor(programa.getCode());
    }
}
