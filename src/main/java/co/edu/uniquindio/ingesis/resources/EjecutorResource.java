package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.services.interfaces.ProgramExecutionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/compilator")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class EjecutorResource {

    @Inject
    ProgramExecutionService programExecutionService;

    @POST
    @Path("/program/{id}")
    public Response ejecutarPrograma(@PathParam("id") Long idPrograma) {
        try {
            String resultado = programExecutionService.ejecutarProgramaPorId(idPrograma);
            return Response.ok(resultado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("Error al ejecutar el programa: " + e.getMessage()).build();
        }
    }
}
