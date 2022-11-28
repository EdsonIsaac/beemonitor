package io.github.edsonisaac.beemonitor;

import io.github.edsonisaac.beemonitor.configurations.RsaKeyProperties;
import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.enums.Department;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.services.FacadeService;
import io.github.edsonisaac.beemonitor.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The type Bee monitor application.
 *
 * @author Edson Isaac
 */
@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BeeMonitorApplication implements CommandLineRunner {

	private final FacadeService facade;
	private final BCryptPasswordEncoder encoder;

	/**
	 * Instantiates a new Bee monitor application.
	 *
	 * @param facade  the facade
	 * @param encoder the encoder
	 */
	@Autowired
	public BeeMonitorApplication(FacadeService facade, BCryptPasswordEncoder encoder) {
		this.facade = facade;
		this.encoder = encoder;
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BeeMonitorApplication.class, args);
	}

	@Override
	public void run(String... args) {
		checkDefaultUser();
	}

	/**
	 * Check default user
	 */
	private void checkDefaultUser () {

		try {
			saveDefaultUser(UserUtils.toUser(facade.userFindByUsername("cooperativa")));
		} catch (ObjectNotFoundException ex) {
			saveDefaultUser(new User());
		}
	}

	/**
	 * Save default user
	 *
	 * @param user the user
	 */
	private void saveDefaultUser (User user) {

		user.setName("Cooperativa");
		user.setUsername("cooperativa");
		user.setPassword("$2a$12$DrBrf0spqOqKyvDPC2fTV.Q/y3HPKkfBeF49YQV4I2TjcowKaPEmi"); //cooperativa
		user.setEnabled(true);
		user.setDepartment(Department.SUPPORT);

		facade.userSave(user);
	}
}