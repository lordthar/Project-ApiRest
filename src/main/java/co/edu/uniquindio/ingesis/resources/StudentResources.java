package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.domain.Student;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.StudentRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.StudentResponse;
import co.edu.uniquindio.ingesis.services.interfaces.StudentService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Path("/Students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class StudentResources {

    final StudentService studentService;

    @POST
    public Response createStudent(@Valid StudentRegistrationRequest request) {
        StudentResponse studentResponse = studentService.createStudent(request);
        return Response.status(Response.Status.CREATED).entity(studentResponse).build();
    }

    @GET
    public Response getStudents(@QueryParam("offset") @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("20") Integer limit) {
        PaginationRequest paginationRequest = new PaginationRequest(offset, limit);
        ArrayList<Student> students = studentService.getStudents(paginationRequest);
        return Response.status(Response.Status.OK).entity(students).build();
    }

    @PUT
    @Path("/{id}")
    public Response highUpdateStudent(@Valid StudentRegistrationRequest request, @PathParam("id") String id) {
        StudentResponse studentResponse = studentService.updateStudent(request);
        return Response.status(Response.Status.OK).entity(studentResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") String id) {
        String response = studentService.deleteStudent(Long.valueOf(id));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") String id){
        StudentResponse studentResponse = studentService.getStudent(Long.valueOf(id));
        return Response.status(Response.Status.OK).entity(studentResponse).build();
    }

    @PATCH
    @Path("/{id}")
    public Response lowUpdateStudent(@PathParam("id") String id, @Valid StudentRegistrationRequest request) {
        StudentResponse studentResponse = studentService.updateStudentPatch(Long.valueOf(id),request);
        return Response.status(Response.Status.OK).entity(studentResponse).build();
    }
}