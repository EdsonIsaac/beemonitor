package io.github.edsonisaac.beemonitor.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.edsonisaac.beemonitor.utils.JWTTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;

/**
 * REST controller handling authentication endpoints.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenUtil jwtTokenUtil;

    /**
     * Login method to generate an authentication token.
     *
     * @param object the JSON object containing the username and password
     * @return ResponseEntity with the authentication token in the response body
     */
    @Operation(summary = "Login", description = "Generate an authentication token", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/token")
    ResponseEntity<?> login(@RequestBody ObjectNode object) {

        final var username = object.get("username").asText();
        final var password = object.get("password").asText();
        final var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
        final var responseBody = new HashMap<String, Object>();
        final var token = jwtTokenUtil.generateToken(authentication);

        responseBody.put("access_token", token);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}