package io.github.edsonisaac.beemonitor.configurations;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfiguration is a configuration class that defines the security configuration for the application.
 * It configures authentication, authorization, and other security-related settings.
 *
 * @author Edson Isaac
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final PasswordEncoder passwordEncoder;
    private final RSAKeyProperties rsaKeyProperties;
    private final UserDetailsService userDetailsService;

    /**
     * Creates and configures the authentication provider bean using DaoAuthenticationProvider.
     *
     * @return the authentication provider instance
     */
    @Bean
    AuthenticationProvider authenticationProvider() {

        final var authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }

    /**
     * Creates and configures the authentication manager bean.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the authentication manager instance
     * @throws Exception if an error occurs while creating the authentication manager
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity object
     * @return the SecurityFilterChain instance
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/auth/**").permitAll();
                    requests.anyRequest().authenticated();
                })
                .cors()
                .and()
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions().sameOrigin())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    /**
     * Creates and configures the JWT decoder bean using NimbusJwtDecoder.
     *
     * @return the JwtDecoder instance
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getPublicKey()).build();
    }

    /**
     * Creates and configures the JWT encoder bean using NimbusJwtEncoder.
     *
     * @return the JwtEncoder instance
     */
    @Bean
    JwtEncoder jwtEncoder() {
        final var jwk = new RSAKey.Builder(rsaKeyProperties.getPublicKey()).privateKey(rsaKeyProperties.getPrivateKey()).build();
        final var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}