package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.services.implementation.DockerCodeExecutor;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ejecutar")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class EjecutorResource {

    @POST
    @Path("/codigo")
    public Response ejecutarCodigo(@QueryParam("codigoJava") String codigoJava) {
        try {
            String resultado = DockerCodeExecutor.ejecutarCodigoEnContenedor(codigoJava);
            return Response.ok(resultado).build();
        } catch (Exception e) {
            return Response.serverError().entity("Error al ejecutar el código: " + e.getMessage()).build();
        }
    }
}
