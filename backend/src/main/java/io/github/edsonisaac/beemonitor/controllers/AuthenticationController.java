package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Authentication controller.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final TokenUtils tokenUtils;

    /**
     * Instantiates a new Authentication controller.
     *
     * @param tokenUtils the token utils
     */
    @Autowired
    public AuthenticationController(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    /**
     * Login response entity.
     *
     * @param authentication the authentication
     * @return the response entity
     */
    @PostMapping("/token")
    public ResponseEntity login(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(tokenUtils.generateToken(authentication));
    }
}