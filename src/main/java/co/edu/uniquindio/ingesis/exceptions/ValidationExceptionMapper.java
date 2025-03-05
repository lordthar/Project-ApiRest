package co.edu.uniquindio.ingesis.exceptions;

import co.edu.uniquindio.ingesis.dtos.ErrorModelResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        var response = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).map(m->new ErrorModelResponse("Error",m)).toList();
        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }
}

