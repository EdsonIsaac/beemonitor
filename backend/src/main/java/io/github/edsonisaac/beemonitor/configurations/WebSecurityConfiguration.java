package io.github.edsonisaac.beemonitor.configurations;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * The type Web security configuration.
 *
 * @author Edson Isaac
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration extends AbstractHttpConfigurer<WebSecurityConfiguration, HttpSecurity> {

    private final CorsConfigurationSource corsConfigurationSource;
    private final RsaKeyProperties rsaKeys;

    /**
     * Instantiates a new Web security configuration.
     *
     * @param corsConfigurationSource the cors configuration source
     * @param rsaKeys                 the rsa keys
     */
    @Autowired
    public WebSecurityConfiguration(CorsConfigurationSource corsConfigurationSource, RsaKeyProperties rsaKeys) {
        this.corsConfigurationSource = corsConfigurationSource;
        this.rsaKeys = rsaKeys;
    }


    /**
     * Filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST,  "/mensurations").permitAll();
                    auth.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();

    }

    /**
     * Jwt decoder jwt decoder.
     *
     * @return the jwt decoder
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    /**
     * Jwt encoder jwt encoder.
     *
     * @return the jwt encoder
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        var jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}