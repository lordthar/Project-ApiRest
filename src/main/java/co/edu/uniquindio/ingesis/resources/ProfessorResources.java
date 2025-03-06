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

import java.util.ArrayList;

@Path("/professors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ProfessorResources {

    final ProfessorService professorService;
    private final String string;

    @POST
    public Response createProfessor(@Valid ProfessorRegistrationRequest request) {
        ProfessorResponse professorResponse = professorService.createProfessor(request);

        return Response.status(Response.Status.CREATED).entity(professorResponse).build();
    }

    @GET
    @Path("/{id}")
    public Response getProfessor(@PathParam("id") String id) {
        ProfessorResponse professorResponse = professorService.getProfessor(Long.parseLong(id));
        return Response.status(Response.Status.OK).entity(professorResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProfessor(@PathParam("id") String id) {
        String professorResponse = professorService.deleteProfessor(Long.valueOf(id));
        return Response.status(Response.Status.OK).entity(professorResponse).build();
    }

    @PUT
    @Path("/{id}")
    public Response highUpdateProfessor(@Valid ProfessorRegistrationRequest request, @PathParam("id") String id) {
        ProfessorResponse professorResponse = professorService.updateProfessor(request);
        return Response.status(Response.Status.OK).entity(professorResponse).build();
    }

    @PATCH
    @Path("/{id}")
    public Response lowUpdateProfessor(@Valid ProfessorRegistrationRequest request,  @PathParam("id") String id) {
        ProfessorResponse professorResponse = professorService.updateProfessorPatch(Long.valueOf(id),request);
        return Response.status(Response.Status.OK).entity(professorResponse).build();
    }

    @GET
    public Response getProfessors(@QueryParam("offset") @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("20") Integer limit) {
        PaginationRequest paginationRequest = new PaginationRequest(offset, limit);
        ArrayList<Professor> professors = professorService.getProfessors(paginationRequest);
        return Response.status(Response.Status.OK).entity(professors).build();
    }
}
