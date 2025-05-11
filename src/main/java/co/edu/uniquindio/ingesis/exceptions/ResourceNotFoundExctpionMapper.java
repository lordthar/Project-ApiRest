package co.edu.uniquindio.ingesis.exceptions;

import co.edu.uniquindio.ingesis.dtos.ErrorModelResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExctpionMapper implements ExceptionMapper<ResourceNotFoundException> {
    @Override
    public Response toResponse(ResourceNotFoundException e) {
        var reponse =new ErrorModelResponse("Error",e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(reponse).build();
    }
}
