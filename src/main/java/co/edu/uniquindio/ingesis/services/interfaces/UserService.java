package co.edu.uniquindio.ingesis.services.interfaces;

import co.edu.uniquindio.ingesis.domain.User;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.UserResponse;

import java.util.ArrayList;

public interface UserService {
    UserResponse createUser(UserRegistrationRequest request);
    UserResponse getUser(Long id);
    String deleteUser(Long id);
    UserResponse updateUser(UserRegistrationRequest request);
    UserResponse updateUserPatch(Long id,UserRegistrationRequest request);
    ArrayList<User> getUsers(PaginationRequest request);

}
