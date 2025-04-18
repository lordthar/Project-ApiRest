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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class StudentResources {


    private static final Logger logger = LoggerFactory.getLogger(StudentResources.class);
    final StudentService studentService;

    @POST
    public Response createStudent(@Valid StudentRegistrationRequest request) {
        logger.info("Creaando un nuevo estudiante con el request : {}", request);
        StudentResponse studentResponse = studentService.createStudent(request);
        logger.info("Se a creado un nuevo estudiante con exito : {} " , request);
        return Response.status(Response.Status.CREATED).entity(studentResponse).build();
    }

    @GET
    public Response getStudents(@QueryParam("offset") @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("20") Integer limit) {
        logger.info("obteniedo estudiantes con offset : {} y limit : {}", offset, limit);
        PaginationRequest paginationRequest = new PaginationRequest(offset, limit);
        ArrayList<Student> students = studentService.getStudents(paginationRequest);
        logger.info("estudiantes obtenidos : {}", students.size());
        return Response.status(Response.Status.OK).entity(students).build();
    }

    @PUT
    @Path("/{id}")
    public Response highUpdateStudent(@Valid StudentRegistrationRequest request, @PathParam("id") String id) {
        logger.info("actualizando un estudiante con id : {}", id);
        StudentResponse studentResponse = studentService.updateStudent(request);
        logger.info("estudiante actualizado : {}", studentResponse);
        return Response.status(Response.Status.OK).entity(studentResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") String id) {
        try {
            logger.info("Eliminando al estudiante con la id: {}", id);
            String response = studentService.deleteStudent(Long.valueOf(id));
            logger.info("El estudiante fue eliminado exitosamente: {}", id);
            return Response.status(Response.Status.OK).entity(response).build();
        } catch (Exception e) {
            logger.warn("error al eliminar al estudiante con el id : {} debido al error : {}", id, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar el estudiante").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getStudent(@PathParam("id") String id){
        logger.info("Obteniendo un estudiante con id : {}", id);
        StudentResponse studentResponse = studentService.getStudent(Long.valueOf(id));
        logger.info("estudiante obtenido : {}", studentResponse);
        return Response.status(Response.Status.OK).entity(studentResponse).build();
    }

    @PATCH
    @Path("/{id}")
    public Response lowUpdateStudent(@PathParam("id") String id, @Valid StudentRegistrationRequest request) {
        logger.info("Realizando una modificación parcial a un estudiante con id : {}", id);
        StudentResponse studentResponse = studentService.updateStudentPatch(Long.valueOf(id),request);
        logger.info("estudiante actualizado exitosamente : {}", studentResponse);
        return Response.status(Response.Status.OK).entity(studentResponse).build();
    }
}