package io.github.edsonisaac.beemonitor;

import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.enums.Department;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * The type Bee monitor application.
 *
 * @author Edson Isaac
 */
@SpringBootApplication
@RequiredArgsConstructor
public class BeeMonitorApplication implements CommandLineRunner {

	private final UserService service;

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BeeMonitorApplication.class, args);
	}

	/**
	 * Run.
	 *
	 * @param args
	 */
	@Override
	public void run(String... args) {
		checkDefaultUser();
		createFolders();
	}

	/**
	 * Check default user.
	 */
	private void checkDefaultUser () {

		try {
			var user = service.findByUsername("cooperativa");
			saveDefaultUser(user);
		} catch (ObjectNotFoundException ex) {
			saveDefaultUser(new User());
		}
	}

	/**
	 * Create folders.
	 */
	private void createFolders() {

		var files = new File("files");

		if (!files.exists()) {
			files.mkdir();
		}
	}

	/**
	 * Save default user.
	 *
	 * @param user the user
	 */
	private void saveDefaultUser (User user) {

		user.setName("Cooperativa");
		user.setUsername("cooperativa");
		user.setPassword("cooperativa");
		user.setEnabled(true);
		user.setDepartment(Department.SUPPORT);

		service.save(user);
	}
}