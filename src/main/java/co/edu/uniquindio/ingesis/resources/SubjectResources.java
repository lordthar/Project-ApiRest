package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.domain.Subject;
import co.edu.uniquindio.ingesis.dtos.*;
import co.edu.uniquindio.ingesis.services.interfaces.SubjectService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@Path("/subjects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class SubjectResources {

    private static final Logger logger = LoggerFactory.getLogger(SubjectResources.class);

    final SubjectService subjectService;

    @POST
    public Response createSubject(@Valid SubjectRegistrationRequest request) {
        logger.info("Creando una nueva materia con los detalles: {}", request);
        SubjectResponse subjectResponse = subjectService.createSubject(request);
        logger.info("Materia creada con éxito: {}", subjectResponse);
        return Response.status(Response.Status.CREATED).entity(subjectResponse).build();
    }

    @GET
    @Path("/{id}")
    public Response getSubject(@PathParam("id") String id) {
        logger.info("Obteniendo información de la materia con ID: {}", id);
        SubjectResponse subjectResponse = subjectService.getSubject(Long.valueOf(id));
        logger.info("Materia obtenida con éxito: {}", subjectResponse);
        return Response.status(Response.Status.OK).entity(subjectResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSubject(@PathParam("id") String id) {
        logger.info("Eliminando la materia con ID: {}", id);
        String subjectResponse = subjectService.deleteSubject(Long.valueOf(id));
        logger.info("Materia eliminada con éxito, ID: {}", id);
        return Response.status(Response.Status.OK).entity(subjectResponse).build();
    }

    @PUT
    @Path("/{id}")
    public Response highUpdateSubject(@Valid SubjectRegistrationRequest request, @PathParam("id") String id) {
        logger.info("Actualizando materia con ID: {}", id);
        SubjectResponse subjectResponse = subjectService.updateSubject(request);
        logger.info("Materia actualizada con éxito: {}", subjectResponse);
        return Response.status(Response.Status.OK).entity(subjectResponse).build();
    }

    @PATCH
    @Path("/{id}")
    public Response lowUpdateSubject(@Valid SubjectRegistrationRequest request, @PathParam("id") String id) {
        logger.info("Realizando una actualización parcial de la materia con ID: {}", id);
        SubjectResponse subjectResponse = subjectService.updateSubjectPatch(Long.valueOf(id), request);
        logger.info("Materia actualizada parcialmente con éxito: {}", subjectResponse);
        return Response.status(Response.Status.OK).entity(subjectResponse).build();
    }

    @GET
    public Response getSubjects(@QueryParam("offset") @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("20") Integer limit) {
        logger.info("Obteniendo lista de materias con offset: {} y limit: {}", offset, limit);
        PaginationRequest paginationRequest = new PaginationRequest(offset, limit);
        ArrayList<Subject> subjects = subjectService.getSubjects(paginationRequest);
        logger.info("Total de materias obtenidas: {}", subjects.size());
        return Response.status(Response.Status.OK).entity(subjects).build();
    }
}
