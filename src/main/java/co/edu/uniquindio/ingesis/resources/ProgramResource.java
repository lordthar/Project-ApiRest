package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.ProgramRequest;
import co.edu.uniquindio.ingesis.dtos.ProgramResponse;
import co.edu.uniquindio.ingesis.services.interfaces.ProgramService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/programs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ProgramResource {

    private static final Logger logger = LoggerFactory.getLogger(ProgramResource.class);

    final ProgramService programService;

    @POST
    public Response createProgram(@Valid ProgramRequest request) {
        logger.info("Creando un nuevo programa con los detalles: {}", request);
        String programId = programService.createProgram(request);
        logger.info("Programa creado con éxito con ID: {}", programId);
        return Response.status(Response.Status.CREATED)
                .entity(programId)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getProgram(@PathParam("id") String id) {
        logger.info("Obteniendo información del programa con ID: {}", id);
        ProgramResponse programResponse = programService.getProgramById(id);
        logger.info("Programa obtenido con éxito: {}", programResponse);
        return Response.status(Response.Status.OK)
                .entity(programResponse)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProgram(@PathParam("id") String id) {
        logger.info("Eliminando programa con ID: {}", id);
        programService.deleteProgram(id);
        logger.info("Programa eliminado con éxito con ID: {}", id);
        return Response.status(Response.Status.OK).entity("Programa eliminado con éxito").build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProgram(@Valid ProgramRequest request, @PathParam("id") String id) {
        logger.info("Actualizando programa con ID: {}", id);
        String programId = programService.updateProgram(id, request);
        logger.info("Programa actualizado con éxito con ID: {}", programId);
        return Response.status(Response.Status.OK)
                .entity("Programa actualizado con éxito")
                .build();
    }

    @PATCH
    @Path("/{id}")
    public Response partialUpdateProgram(@Valid ProgramRequest request, @PathParam("id") String id) {
        logger.info("Realizando una actualización parcial del programa con ID: {}", id);
        String programId = programService.updateProgram(id, request);
        logger.info("Programa actualizado parcialmente con éxito con ID: {}", programId);
        return Response.status(Response.Status.OK)
                .entity("Programa actualizado parcialmente con éxito")
                .build();
    }

    @GET
    public Response listPrograms(@QueryParam("offset") @DefaultValue("0") Integer offset,
                                 @QueryParam("limit") @DefaultValue("20") Integer limit) {
        logger.info("Obteniendo lista de programas con offset: {} y limit: {}", offset, limit);
        PaginationRequest paginationRequest = new PaginationRequest(offset, limit);
        List<ProgramResponse> programs = programService.listPrograms(paginationRequest);
        logger.info("Total de programas obtenidos: {}", programs.size());
        return Response.status(Response.Status.OK)
                .entity(programs)
                .build();
    }
}
