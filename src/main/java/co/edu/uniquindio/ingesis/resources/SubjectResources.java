package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.domain.Subject;
import co.edu.uniquindio.ingesis.dtos.*;
import co.edu.uniquindio.ingesis.services.interfaces.SubjectService;
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
public class SubjectResources {
    final SubjectService subjectService;

    @POST
    public Response createSubject(@Valid SubjectRegistrationRequest request) {
        SubjectResponse subjectResponse = subjectService.createSubject(request);
        return Response.status(Response.Status.CREATED).entity(subjectResponse).build();
    }

    @GET
    @Path("/{id}")
    public Response  getSubject(@PathParam("id") String id) {
       SubjectResponse subjectResponse = subjectService.getSubject(Long.valueOf(id));
       return Response.status(Response.Status.OK).entity(subjectResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response  deleteSubject(@PathParam("id") String id) {
        String subjectResponse = subjectService.deleteSubject(Long.valueOf(id));
        return Response.status(Response.Status.OK).entity(subjectResponse).build();
    }

    @PUT
    @Path("/{id}")
    public Response  highUpdateSubject(@Valid SubjectRegistrationRequest request,  @PathParam("id") String id) {
        SubjectResponse subjectResponse = subjectService.updateSubject(request);
        return Response.status(Response.Status.OK).entity(subjectResponse).build();
    }

    @PATCH
    @Path("/{id}")
    public Response  lowUpdateSubject(@Valid SubjectRegistrationRequest request,  @PathParam("id") String id) {
        SubjectResponse subjectResponse = subjectService.updateSubjectPatch(Long.valueOf(id),request);
        return Response.status(Response.Status.OK).entity(subjectResponse).build();
    }

    @GET
    public Response  getSubjects(@QueryParam("offset") @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("20") Integer limit) {
        PaginationRequest paginationRequest = new PaginationRequest(offset, limit);
        ArrayList<Subject> subjects = subjectService.getSubjects(paginationRequest);
        return Response.status(Response.Status.OK).entity(subjects).build();
    }
}
