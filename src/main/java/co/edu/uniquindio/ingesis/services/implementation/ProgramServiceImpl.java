package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.Program;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.ProgramRequest;
import co.edu.uniquindio.ingesis.dtos.ProgramResponse;
import co.edu.uniquindio.ingesis.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.ingesis.mappers.ProgramMapper;
import co.edu.uniquindio.ingesis.services.interfaces.ProgramService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private static final Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);

    final ProgramMapper programMapper;

    @Override
    @Transactional
    public String createProgram(ProgramRequest request) {
        logger.info("Creando nuevo programa: {}", request);
        Program program = programMapper.parseOf(request);
        program.persist();
        logger.info("Programa creado con ID: {}", program.id);
        return String.valueOf(program.id);
    }

    @Override
    public ProgramResponse getProgramById(String id) {
        logger.info("Buscando programa con ID: {}", id);
        Program program = Program.findById(id);
        if (program == null) {
            logger.warn("Programa no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Programa no encontrado con ID: " + id);
        }
        ProgramResponse response = programMapper.toProgramResponse(program);
        logger.info("Programa obtenido: {}", response);
        return response;
    }

    @Override
    public List<ProgramResponse> listPrograms(PaginationRequest request) {
        logger.info("Listando programas con offset: {} y limit: {}", request.offset(), request.limit());

        PanacheQuery<Program> query = Program.findAll();
        query.page(request.offset() / request.limit(), request.limit());

        List<Program> programs = query.list();
        logger.info("Cantidad de programas obtenidos: {}", programs.size());

        return programs.stream()
                .map(programMapper::toProgramResponse)
                .toList();
    }

    @Override
    @Transactional
    public String updateProgram(String id, ProgramRequest request) {
        logger.info("Actualizando programa con ID: {}", id);
        Program program = Program.findById(id);
        if (program == null) {
            logger.warn("Programa no encontrado para actualización: {}", id);
            throw new ResourceNotFoundException("Programa no encontrado con ID: " + id);
        }
        program.setTitle(request.title());
        program.setDescription(request.description());
        program.setCode(request.code());
        program.persist();
        logger.info("Programa actualizado con éxito: {}", program.id);
        return String.valueOf(program.id);
    }

    @Override
    @Transactional
    public void deleteProgram(String id) {
        logger.info("Eliminando programa con ID: {}", id);
        boolean deleted = Program.deleteById(id);
        if (!deleted) {
            logger.error("No se encontró programa para eliminar con ID: {}", id);
            throw new ResourceNotFoundException("No se pudo eliminar el programa con ID: " + id);
        }
        logger.info("Programa eliminado correctamente");
    }
}
