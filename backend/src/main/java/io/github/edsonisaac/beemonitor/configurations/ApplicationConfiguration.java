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

@Component
@RequiredArgsConstructor
public class ApplicationConfiguration implements CommandLineRunner {

    private final UserService service;

    @Override
    public void run(String... args) {
        checkDefaultUser();
    }

    void checkDefaultUser() {

        final var user = new User();

        try {
            final var userDTO = service.findByUsername("admin");
            BeanUtils.copyProperties(userDTO, user);
        } catch (ObjectNotFoundException ex) { }

        saveDefaultUser(user);
    }

    void saveDefaultUser(User user) {

        user.setName("ADMINISTRADOR");
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEnabled(true);
        user.setAuthorities(Collections.singleton(Authority.SUPPORT));

        service.save(user);
    }
}