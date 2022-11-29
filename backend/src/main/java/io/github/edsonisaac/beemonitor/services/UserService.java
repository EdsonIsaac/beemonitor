package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.projections.UserProjection;
import io.github.edsonisaac.beemonitor.repositories.UserRepository;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import io.github.edsonisaac.beemonitor.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type User service.
 *
 * @author Edson Isaac
 */
@Service
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository repository;

    /**
     * Instantiates a new User service.
     *
     * @param encoder
     * @param repository the repository
     */
    @Autowired
    public UserService(PasswordEncoder encoder, UserRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<UserProjection> findAll() {
        return repository.findAlll();
    }

    /**
     * Find by id user.
     *
     * @param id the id
     * @return the user
     */
    public User findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.USER_NOT_FOUND);
        });
    }

    /**
     * Find by username user projection.
     *
     * @param username the username
     * @return the user projection
     */
    public UserProjection findByUsername (String username) {

        return repository.findByUsername(username).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.USER_NOT_FOUND);
        });
    }

    /**
     * Save user.
     *
     * @param user the user
     * @return the user
     */
    public User save (User user) {

        if (user == null) {
            throw new ValidationException(MessageUtils.USER_NULL);
        }

        if (validateUser(user)) {
            user = checkPassword(user);
            repository.save(user);
        }

        return user;
    }

    /**
     * Check Password
     *
     * @param user the user
     * @return the user
     */
    private User checkPassword(User user) {

        if (user.getId() != null) {

            var user_findById = repository.findById(user.getId()).orElseThrow(() -> {
                throw new ObjectNotFoundException(MessageUtils.USER_NOT_FOUND);
            });

            if (!user_findById.getPassword().equals(user.getPassword())) {
                user.setPassword(encoder.encode(user.getPassword()));
            }
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
        }

        return user;
    }

    /**
     * Validate User
     *
     * @param user the user
     * @return the boolean
     */
    private boolean validateUser (User user) {

        var user_findByUsername = repository.findByUsername(user.getUsername());

        if (user_findByUsername.isPresent()) {

            if (!user.equals(UserUtils.toUser(user_findByUsername.get()))) {
                throw new ValidationException(MessageUtils.USER_ALREADY_SAVE);
            }
        }

        return true;
    }
}