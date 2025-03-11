package co.edu.uniquindio.ingesis.resources;

import co.edu.uniquindio.ingesis.domain.User;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.UserResponse;
import co.edu.uniquindio.ingesis.services.interfaces.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResources {

    final UserService userService;

    @POST
    public Response createUser(@Valid UserRegistrationRequest request) {
        UserResponse userResponse = userService.createUser(request);
        return Response.status(Response.Status.CREATED).entity(userResponse).build();
    }

    @GET
    public Response getUsers(@QueryParam("offset") @DefaultValue("0") Integer offset, @QueryParam("limit") @DefaultValue("20") Integer limit) {
        PaginationRequest paginationRequest = new PaginationRequest(offset, limit);
        ArrayList<User> users = userService.getUsers(paginationRequest);
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @PUT
    @Path("/{id}")
    public Response highUpdateUser(@Valid UserRegistrationRequest request, @PathParam("id") String id) {
        UserResponse userResponse = userService.updateUser(request);
        return Response.status(Response.Status.OK).entity(userResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        String response = userService.deleteUser(Long.valueOf(id));
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") String id){
        UserResponse userResponse = userService.getUser(Long.valueOf(id));
        return Response.status(Response.Status.OK).entity(userResponse).build();
    }

    @PATCH
    @Path("/{id}")
    public Response lowUpdateUser(@PathParam("id") String id, @Valid UserRegistrationRequest request) {
        UserResponse userResponse = userService.updateUserPatch(Long.valueOf(id),request);
        return Response.status(Response.Status.OK).entity(userResponse).build();
    }
}