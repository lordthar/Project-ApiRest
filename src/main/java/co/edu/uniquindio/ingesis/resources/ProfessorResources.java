package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.domain.Professor;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.ProfessorRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.ProfessorResponse;
import co.edu.uniquindio.ingesis.services.interfaces.ProfessorService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@Path("/professors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ProfessorResources {

    private static final Logger logger = LoggerFactory.getLogger(ProfessorResources.class);

    final ProfessorService professorService;

    @POST
    public Response createProfessor(@Valid ProfessorRegistrationRequest request) {
        logger.info("Creando un nuevo profesor con los detalles: {}", request);
        ProfessorResponse professorResponse = professorService.createProfessor(request);
        logger.info("Profesor creado con éxito: {}", professorResponse);
        return Response.status(Response.Status.CREATED).entity(professorResponse).build();
    }

    @GET
    @Path("/{id}")
    public Response getProfessor(@PathParam("id") String id) {
        logger.info("Obteniendo información del profesor con ID: {}", id);
        ProfessorResponse professorResponse = professorService.getProfessor(Long.parseLong(id));
        logger.info("Profesor obtenido con éxito: {}", professorResponse);
        return Response.status(Response.Status.OK).entity(professorResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProfessor(@PathParam("id") String id) {
        logger.info("Eliminando profesor con ID: {}", id);
        String professorResponse = professorService.deleteProfessor(Long.valueOf(id));
        logger.info("Profesor eliminado con éxito, ID: {}", id);
        return Response.status(Response.Status.OK).entity(professorResponse).build();
    }

    @PUT
    @Path("/{id}")
    public Response highUpdateProfessor(@Valid ProfessorRegistrationRequest request, @PathParam("id") String id) {
        logger.info("Actualizando profesor con ID: {}", id);
        ProfessorResponse professorResponse = professorService.updateProfessor(request);
        logger.info("Profesor actualizado con éxito: {}", professorResponse);
        return Response.status(Response.Status.OK).entity(professorResponse).build();
    }

    @PATCH
    @Path("/{id}")
    public Response lowUpdateProfessor(@Valid ProfessorRegistrationRequest request, @PathParam("id") String id) {
        logger.info("Realizando una actualización parcial del profesor con ID: {}", id);
        ProfessorResponse professorResponse = professorService.updateProfessorPatch(Long.valueOf(id), request);
        logger.info("Profesor actualizado parcialmente con éxito: {}", professorResponse);
        return Response.status(Response.Status.OK).entity(professorResponse).build();
    }

    @GET
    public Response getProfessors(@QueryParam("offset") @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("20") Integer limit) {
        logger.info("Obteniendo lista de profesores con offset: {} y limit: {}", offset, limit);
        PaginationRequest paginationRequest = new PaginationRequest(offset, limit);
        ArrayList<Professor> professors = professorService.getProfessors(paginationRequest);
        logger.info("Total de profesores obtenidos: {}", professors.size());
        return Response.status(Response.Status.OK).entity(professors).build();
    }
}
