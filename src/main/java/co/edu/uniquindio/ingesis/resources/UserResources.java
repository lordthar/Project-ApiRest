package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.services.interfaces.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResources {

    final UserService userService;

    @POST
    public Response createUser(@Valid UserRegistrationRequest request) {
        return null;
    }

    @GET
    public Response getUsers(@QueryParam("offset") @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("20") Integer limit) {
        return null;
    }

    @PUT
    @Path("/{id}")
    public Response highUpdateUser(@Valid UserRegistrationRequest request, @PathParam("id") String id) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) throws Exception {
        userService.deleteUser(Long.valueOf(id));
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("i  d") String id){
        return null;
    }

    @PATCH
    @Path("/{id}")
    public Response lowUpdateUser(@PathParam("id") String id, @Valid UserRegistrationRequest request) {
        return null;
    }
}