package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.Example;
import co.edu.uniquindio.ingesis.domain.Professor;
import co.edu.uniquindio.ingesis.dtos.ExampleRequest;
import co.edu.uniquindio.ingesis.dtos.ExampleResponse;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.ingesis.mappers.ExampleMapper;
import co.edu.uniquindio.ingesis.repositories.ExampleRepository;
import co.edu.uniquindio.ingesis.repositories.ProfessorRepository;
import co.edu.uniquindio.ingesis.services.interfaces.ExampleService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@ApplicationScoped
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService {

    private static final Logger logger = LoggerFactory.getLogger(ExampleServiceImpl.class);

    private final ExampleRepository exampleRepository;

    private final ProfessorRepository professorRepository;

    private final ExampleMapper exampleMapper;

    @Override
    @Transactional
    public ExampleResponse createExample(ExampleRequest exampleRequest, Long professorId) {
        logger.info("Creando un nuevo ejemplo para profesor con id: {}", professorId);
        Professor professor = professorRepository.findById(professorId);
        if (professor == null) {
            throw new ResourceNotFoundException("Profesor no encontrado");
        }

        Example example = exampleMapper.parseOf(exampleRequest);
        example.setProfessor(professor);
        example.persist();

        logger.info("Ejemplo creado con éxito: {}", example);
        return exampleMapper.toExampleResponse(example);
    }

    @Override
    public ExampleResponse getExample(Long exampleId) {
        logger.info("Obteniendo ejemplo con ID: {}", exampleId);
        Example example = exampleRepository.findById(exampleId);
        if (example == null) {
            throw new ResourceNotFoundException("Ejemplo no encontrado");
        }
        return exampleMapper.toExampleResponse(example);
    }

    @Override
    @Transactional
    public String deleteExample(Long exampleId) {
        logger.info("Eliminando ejemplo con ID: {}", exampleId);
        Example example = exampleRepository.findById(exampleId);
        if (example == null) {
            throw new ResourceNotFoundException("Ejemplo no encontrado");
        }
        exampleRepository.delete(example);
        logger.info("Ejemplo eliminado con éxito con ID: {}", exampleId);
        return "Ejemplo eliminado correctamente";
    }

    @Override
    @Transactional
    public ExampleResponse updateExample(Long exampleId, ExampleRequest exampleRequest) {
        logger.info("Actualizando ejemplo con ID: {}", exampleId);
        Example example = exampleRepository.findById(exampleId);
        if (example == null) {
            throw new ResourceNotFoundException("Ejemplo no encontrado");
        }

        example.setTitle(exampleRequest.title());
        example.setDescription(exampleRequest.description());
        example.setCode(exampleRequest.code());
        example.persist();

        logger.info("Ejemplo actualizado: {}", example);
        return exampleMapper.toExampleResponse(example);
    }

    @Override
    public List<ExampleResponse> listExamples(PaginationRequest paginationRequest, Long professorId) {
        logger.info("Listando ejemplos para profesor con id: {} con offset: {} y limit: {}",
                professorId, paginationRequest.offset(), paginationRequest.limit());

        Professor professor = professorRepository.findById(professorId);
        if (professor == null) {
            throw new ResourceNotFoundException("Profesor no encontrado");
        }

        var query = exampleRepository.find("professor", professor);
        query.page(paginationRequest.offset() / paginationRequest.limit(), paginationRequest.limit());
        List<Example> examples = query.list();

        return exampleMapper.toExampleResponseList(examples);
    }
}
