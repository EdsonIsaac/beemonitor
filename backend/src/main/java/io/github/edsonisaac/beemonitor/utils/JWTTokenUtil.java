package io.github.edsonisaac.beemonitor.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * Utility class for JWT token generation.
 * This class provides a method to generate a JWT token based on the authentication information.
 * It uses the provided JwtEncoder to encode the token with the necessary claims.
 *
 * @author Edson Isaac
 */
@Component
@RequiredArgsConstructor
public class JWTTokenUtil {

    private final JwtEncoder encoder;

    /**
     * Generates a JWT token based on the provided authentication.
     *
     * @param authentication the authentication object
     * @return the generated JWT token as a string
     */
    public String generateToken(Authentication authentication) {

        final var now = Instant.now();
        final var scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        final var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(12, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}