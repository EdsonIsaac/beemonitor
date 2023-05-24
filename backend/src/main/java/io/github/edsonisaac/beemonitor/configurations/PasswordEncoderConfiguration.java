package io.github.edsonisaac.beemonitor.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordEncoderConfiguration is a configuration class that defines the configuration for the password encoder
 * used in the application. It provides a bean for the BCryptPasswordEncoder.
 *
 * @author Edson Isaac
 */
@Configuration
public class PasswordEncoderConfiguration {

    /**
     * Creates and configures the password encoder bean using BCryptPasswordEncoder.
     *
     * @return the BCryptPasswordEncoder instance
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}