package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.dtos.UserDTO;
import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.repositories.UserRepository;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
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

@Service
@RequiredArgsConstructor
public class UserService implements AbstractService<User, UserDTO>, UserDetailsService {
    
    private final PasswordEncoder encoder;
    private final UserRepository repository;

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

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User encodePassword(User user) {

        if (user.getId() != null) {

            final var user_findById = repository.findById(user.getId());

            if (user_findById.isPresent() && !user_findById.get().getPassword().equals(user.getPassword())) {
                user.setPassword(encoder.encode(user.getPassword()));
            }
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
        }

        return user;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UserDTO> findAll(Integer page, Integer size, String sort, String direction) {
        final var users = repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
        return users.map(UserDTO::toDTO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDTO findById(UUID id) {
        final var user = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.USER_NOT_FOUND));
        return UserDTO.toDTO(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDTO findByUsername(String username) {
        final var user = repository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.USER_NOT_FOUND));
        return UserDTO.toDTO(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessageUtils.AUTHENTICATION_FAIL));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO save(User user) {
    
        if (user == null) {
            throw new ValidationException(MessageUtils.USER_NULL);
        }

        user = encodePassword(user);
        user = repository.save(user);
        return UserDTO.toDTO(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UserDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        final var users = repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
        return users.map(UserDTO::toDTO);
    }
}