package co.edu.uniquindio.ingesis.services.implementation;

import co.edu.uniquindio.ingesis.domain.User;
import co.edu.uniquindio.ingesis.dtos.PaginationRequest;
import co.edu.uniquindio.ingesis.dtos.UserRegistrationRequest;
import co.edu.uniquindio.ingesis.dtos.UserResponse;
import co.edu.uniquindio.ingesis.mappers.UserMapper;
import co.edu.uniquindio.ingesis.repositories.UserRepository;
import co.edu.uniquindio.ingesis.services.interfaces.UserService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
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
        User user = userMapper.parseOf(request);
        user.persist();
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUsers(Long id) {
        User user = userRepository.findById(id);
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public String deleteUser(Long id) {
        UserResponse user = getUsers(id);

        if (user == null) {
            throw new NotFoundException("Error");
        }
        userRepository.deleteById(Long.valueOf(user.id()));

        return "El usuario fue eliminado correctamente";
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserRegistrationRequest request) {
        User user = userRepository.find("identification", request.identification()).firstResult();
        if(user == null) {
            throw new NotFoundException("No existe el usuario");
       }
       user.setName(request.name());
       user.setLastName(request.lastName());
       user.setEmail(request.email());
       user.setPassword(request.password());
       user.setUsername(request.username());
       user.setPhoneNumber(request.phoneNumber());
       user.persist();

       return userMapper.toUserResponse(user);

    }

    @Override
    @Transactional
    public UserResponse updateUserPatch(Long id, UserRegistrationRequest request) {
        User user = userRepository.findById(id);
        if(user == null) {
            throw new NotFoundException("No existe el usuario");
        }
        user.setName(request.name());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setUsername(request.username());
        user.setPhoneNumber(request.phoneNumber());
        user.persist();

        return userMapper.toUserResponse(user);

    }

    @Override
    public ArrayList<User> getUsers(PaginationRequest request) {
        PanacheQuery<User> query = userRepository.findAll();
        query.page(request.offset() / request.limit() ,request.limit());
        return new ArrayList<>(query.list());
    }

}
