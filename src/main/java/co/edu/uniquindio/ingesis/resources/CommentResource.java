package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.dtos.CommentRequest;
import co.edu.uniquindio.ingesis.dtos.CommentResponse;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.services.interfaces.CommentService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class CommentResource {

    private static final Logger logger = LoggerFactory.getLogger(CommentResource.class);

    private final CommentService commentService;

    @POST
    public Response createComment(@Valid CommentRequest request) {
        logger.info("Solicitud para crear comentario: {}", request);
        CommentResponse response = commentService.createComment(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getComment(@PathParam("id") Long id) {
        logger.info("Solicitud para obtener comentario con ID: {}", id);
        CommentResponse response = commentService.getComment(id);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteComment(@PathParam("id") Long id) {
        logger.info("Solicitud para eliminar comentario con ID: {}", id);
        String message = commentService.deleteComment(id);
        return Response.ok(message).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateComment(@PathParam("id") Long id, @Valid CommentRequest request) {
        logger.info("Solicitud para actualizar comentario con ID: {}", id);
        CommentResponse response = commentService.updateComment(id, request);
        return Response.ok(response).build();
    }

    @GET
    public Response listComments(
            @QueryParam("offset") @DefaultValue("0") Integer offset,
            @QueryParam("limit") @DefaultValue("20") Integer limit,
            @QueryParam("programId") Long programId
    ) {
        logger.info("Solicitud para listar comentarios del programa {} con offset {} y limit {}", programId, offset, limit);
        if (programId == null) {
            throw new BadRequestException("El parámetro 'programId' es obligatorio");
        }
        PaginationRequest pagination = new PaginationRequest(offset, limit);
        List<CommentResponse> comments = commentService.listComments(pagination, programId);
        return Response.ok(comments).build();
    }
}
