package io.github.edsonisaac.beemonitor.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Bee Monitor")
                        .description("Projeto para monitoramento de colmeias com o auxílio de um Arduino para o ProMel, " +
                                "Instituto Federal de Educação, Ciência e Tecnologia da Bahia - Campus Irecê")
                );
    }
}