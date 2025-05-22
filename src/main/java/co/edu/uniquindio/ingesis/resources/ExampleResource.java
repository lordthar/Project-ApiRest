package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.dtos.ExampleRequest;
import co.edu.uniquindio.ingesis.dtos.ExampleResponse;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.services.implementation.ExampleServiceImpl;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/examples")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class ExampleResource {
    private static final Logger logger = LoggerFactory.getLogger(ExampleResource.class);

    ExampleServiceImpl exampleService;

    @POST
    @Path("/professor/{professorId}")
    public Response createExample(@Valid ExampleRequest request, @PathParam("professorId") Long professorId) {
        logger.info("Creando un nuevo ejemplo para profesor con ID: {}", professorId);
        ExampleResponse exampleResponse = exampleService.createExample(request, professorId);
        logger.info("Ejemplo creado con éxito: {}", exampleResponse);
        return Response.status(Response.Status.CREATED).entity(exampleResponse).build();
    }

    @GET
    @Path("/{id}")
    public Response getExample(@PathParam("id") Long id) {
        logger.info("Obteniendo ejemplo con ID: {}", id);
        ExampleResponse exampleResponse = exampleService.getExample(id);
        logger.info("Ejemplo obtenido: {}", exampleResponse);
        return Response.ok(exampleResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteExample(@PathParam("id") Long id) {
        logger.info("Eliminando ejemplo con ID: {}", id);
        String result = exampleService.deleteExample(id);
        logger.info("Ejemplo eliminado con éxito, ID: {}", id);
        return Response.ok(result).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateExample(@Valid ExampleRequest request, @PathParam("id") Long id) {
        logger.info("Actualizando ejemplo con ID: {}", id);
        ExampleResponse exampleResponse = exampleService.updateExample(id, request);
        logger.info("Ejemplo actualizado con éxito: {}", exampleResponse);
        return Response.ok(exampleResponse).build();
    }

    @GET
    @Path("/professor/{professorId}")
    public Response listExamples(@PathParam("professorId") Long professorId,
                                 @QueryParam("offset") @DefaultValue("0") Integer offset,
                                 @QueryParam("limit") @DefaultValue("20") Integer limit) {
        logger.info("Listando ejemplos para profesor con ID: {} con offset: {} y limit: {}", professorId, offset, limit);
        PaginationRequest paginationRequest = new PaginationRequest(offset, limit);
        List<ExampleResponse> examples = exampleService.listExamples(paginationRequest, professorId);
        logger.info("Total ejemplos obtenidos: {}", examples.size());
        return Response.ok(examples).build();
    }
}
