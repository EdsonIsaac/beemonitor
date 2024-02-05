package io.github.edsonisaac.beemonitor.configurations;

import io.github.edsonisaac.beemonitor.enums.Department;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.models.User;
import io.github.edsonisaac.beemonitor.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class ApplicationConfiguration implements CommandLineRunner {

    private final UserService service;

    @Override
    public void run(String... args) {
        checkDefaultUser();
        createFolders();
    }

    void checkDefaultUser() {

        final var user = new User();

        try {
            final var userDTO = service.findByUsername("admin");
            BeanUtils.copyProperties(userDTO, user);
        } catch (ObjectNotFoundException ex) { }

        saveDefaultUser(user);
    }

    void createFolders() {

        final var data = new File("data");

        if (!data.exists()) {
            data.mkdir();
        }

        final var files = new File("data/files");

        if (!files.exists()) {
            files.mkdir();
        }
    }

    void saveDefaultUser(User user) {

        user.setName("ADMINISTRADOR");
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEnabled(true);
        user.setDepartment(Department.SUPPORT);

        service.save(user);
    }
}