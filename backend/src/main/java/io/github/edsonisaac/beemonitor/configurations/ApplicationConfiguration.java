package io.github.edsonisaac.beemonitor.configurations;

import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.enums.Authority;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import lombok.RequiredArgsConstructor;
import java.util.Collections;

/**
 * ApplicationConfiguration is a Spring component that implements the CommandLineRunner interface.
 * It is responsible for checking and saving a default user during application startup.
 *
 * @author Edson Isaac
 */
@Component
@RequiredArgsConstructor
public class ApplicationConfiguration implements CommandLineRunner {

    private final UserService service;

    /**
     * Executes the logic to check and save a default user during application startup.
     *
     * @param args the command line arguments
     */
    @Override
    public void run(String... args) {
        checkDefaultUser();
    }

    /**
     * Checks if a default user with the username "admin" exists in the system.
     * If not found, a default User instance is created and saved.
     */
    void checkDefaultUser() {

        final var user = new User();

        try {
            var userDTO = service.findByUsername("admin");
            BeanUtils.copyProperties(userDTO, user);
        } catch (ObjectNotFoundException ex) { }

        saveDefaultUser(user);
    }

    /**
     * Saves the default user with predefined attributes.
     *
     * @param user the User instance representing the default user
     */
    void saveDefaultUser(User user) {

        user.setName("ADMINISTRADOR");
        user.setUsername("admin");
        user.setPassword("admin");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setAuthorities(Collections.singleton(Authority.SUPPORT));

        service.save(user);
    }
}