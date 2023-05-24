package io.github.edsonisaac.beemonitor.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPIConfiguration is a configuration class that defines the OpenAPI configuration for the application.
 * It provides the OpenAPI instance with basic project information.
 *
 * @author Edson Isaac
 */
@Configuration
public class OpenAPIConfiguration {

    /**
     * Creates and configures the OpenAPI instance.
     *
     * @return the OpenAPI instance
     */
    @Bean
    OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("BeeMonitor")
                        .description("")
                );
    }
}