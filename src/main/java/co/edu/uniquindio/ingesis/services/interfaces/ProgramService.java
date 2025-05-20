package co.edu.uniquindio.ingesis.services.interfaces;

import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.ProgramRequest;
import co.edu.uniquindio.ingesis.dtos.ProgramResponse;

import java.util.List;

public interface ProgramService {

    String createProgram(ProgramRequest request);

    ProgramResponse getProgramById(String id);

    List<ProgramResponse> listPrograms(PaginationRequest request);

    String updateProgram(String id, ProgramRequest request);

    void deleteProgram(String id);
}
