package io.github.edsonisaac.beemonitor.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * CorsConfiguration is a configuration class that defines the CORS (Cross-Origin Resource Sharing) configuration
 * for the application. It allows configuring the allowed methods for CORS requests.
 *
 * @author Edson Isaac
 */
@Configuration
public class CorsConfiguration {

    /**
     * Creates and configures the CORS configuration source.
     *
     * @return the CORS configuration source
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final var source = new UrlBasedCorsConfigurationSource();
        final var corsConfiguration = new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}