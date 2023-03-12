package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.repositories.UserRepository;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type User service.
 *
 * @author Edson Isaac
 */
@Service
@RequiredArgsConstructor
public class UserService implements AbstractService<User> {

    private final PasswordEncoder encoder;
    private final UserRepository repository;

    /**
     * Delete.
     *
     * @param id the id
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

        throw new ObjectNotFoundException(MessageUtils.USER_NOT_FOUND);
    }

    /**
     * Encode password.
     *
     * @param user the user
     * @return the user
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
     * Find all.
     *
     * @return the user list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<User> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the user
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.USER_NOT_FOUND);
        });
    }

    /**
     * Find by username.
     *
     * @param username the username
     * @return the user
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User findByUsername(String username) {

        return repository.findByUsername(username).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.USER_NOT_FOUND);
        });
    }

    /**
     * Save.
     *
     * @param user the user
     * @return the user
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save (User user) {

        if (user == null) {
            throw new ValidationException(MessageUtils.USER_NULL);
        }

        if (validate(user)) {
            user = encodePassword(user);
            user = repository.save(user);
        }

        return user;
    }

    /**
     * Search.
     *
     * @param value     the value
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the user list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<User> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Validate.
     *
     * @param user the user
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate (User user) {

        var user_findByUsername = repository.findByUsername(user.getUsername());

        if (user_findByUsername.isPresent()) {

            if (!user_findByUsername.get().equals(user)) {
                throw new ValidationException(MessageUtils.USER_ALREADY_SAVE);
            }
        }

        return true;
    }
}