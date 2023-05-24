package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.dtos.UserDTO;
import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The UserService class provides business logic operations for managing User entities.
 * It implements the AbstractService interface for CRUD operations and the UserDetailsService interface
 * for user authentication and authorization.
 *
 * @author Edson Isaac
 */
@Service
@RequiredArgsConstructor
public class UserService implements AbstractService<User, UserDTO>, UserDetailsService {
    
    private final PasswordEncoder encoder;
    private final UserRepository repository;

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to be deleted
     * @throws ObjectNotFoundException if the user with the given ID is not found
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {
        
        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }
        
        throw new ObjectNotFoundException("Usuário não encontrado!");
    }

    /**
     * Encodes the password of a user.
     *
     * @param user the user to encode the password for
     * @return The user with the encoded password
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User encodePassword(User user) {

        if (user.getId() != null) {

            var user_findById = repository.findById(user.getId()).get();

            if (!user_findById.getPassword().equals(user.getPassword())) {
                user.setPassword(encoder.encode(user.getPassword()));
            }
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
        }

        return user;
    }

    /**
     * Finds all users.
     *
     * @param page      the page number
     * @param size      the number of users per page
     * @param sort      the field to sort the users
     * @param direction the sorting direction ("asc" or "desc")
     * @return A page object containing the list of users
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UserDTO> findAll(Integer page, Integer size, String sort, String direction) {
        var users = repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
        return users.map(UserDTO::toDTO);
    }

    /**
     * Finds a user by ID.
     *
     * @param id the ID of the user to be found
     * @return The found user
     * @throws ObjectNotFoundException if the user with the given ID is not found
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDTO findById(UUID id) {
        var user = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado!"));
        return UserDTO.toDTO(user);
    }

    /**
     * Finds a user by username.
     *
     * @param username the username to search for
     * @return The found user
     * @throws ObjectNotFoundException if the user with the given username is not found
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDTO findByUsername(String username) {
        var user = repository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado!"));
        return UserDTO.toDTO(user);
    }

    /**
     * Loads a user by username for authentication and authorization.
     *
     * @param username the username of the user to load
     * @return The loaded user details
     * @throws UsernameNotFoundException if the user with the given username is not found
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário inexistente ou senha inválida!"));
    }

    /**
     * Saves a user.
     *
     * @param user the user to be saved
     * @return The saved user
     * @throws ValidationException if the user is not valid
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO save(User user) {
    
        if (user == null) {
            throw new ValidationException("Usuário nulo!");
        }

        if (validate(user)) {
            user = encodePassword(user);
            user = repository.save(user);
        }
        
        return UserDTO.toDTO(user);
    }

    /**
     * Validates a user.
     *
     * @param user the user to be validated
     * @return True if the user is valid
     * @throws ValidationException if the user is not valid
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(User user) {
        return true;
    }
}