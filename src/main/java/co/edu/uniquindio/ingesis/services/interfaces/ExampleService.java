package co.edu.uniquindio.ingesis.services.interfaces;

import co.edu.uniquindio.ingesis.dtos.ExampleRequest;
import co.edu.uniquindio.ingesis.dtos.ExampleResponse;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;

import java.util.List;

public interface ExampleService {
    ExampleResponse createExample(ExampleRequest request, Long professorId);

    ExampleResponse getExample(Long id);

    String deleteExample(Long id);

    ExampleResponse updateExample(Long id, ExampleRequest request);

    List<ExampleResponse> listExamples(PaginationRequest paginationRequest, Long professorId);
}
