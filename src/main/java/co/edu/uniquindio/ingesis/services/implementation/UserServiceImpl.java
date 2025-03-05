package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.User;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.UserResponse;
import co.edu.uniquindio.ingesis.mappers.UserMapper;
import co.edu.uniquindio.ingesis.repositories.UserRepository;
import co.edu.uniquindio.ingesis.services.interfaces.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@ApplicationScoped
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserMapper userMapper;
    final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse createUser(UserRegistrationRequest request) {
        return null;
    }

    @Override
    public UserResponse getUser(Long id) {
        return null;
    }

    @Override
    @Transactional
    public String deleteUser(Long id) throws Exception {
        UserResponse user = getUser(id);

        if (user == null) {
            throw new Exception("Error");
        }
        userRepository.deleteById(Long.valueOf(user.id()));

        return "El usuario fue eliminado correctamente";
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserRegistrationRequest request) {
        return null;
    }

    @Override
    public ArrayList<User> getUser(PaginationRequest request) {
        return null;
    }

}
