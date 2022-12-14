package io.github.edsonisaac.beemonitor.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * The type Cors configuration.
 *
 * @author edson
 */
@Configuration
public class CorsConfiguration {

    /**
     * Cors configuration source cors configuration source.
     *
     * @return cors configuration source
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        var corsConfiguration = new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}