package io.github.edsonisaac.beemonitor.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.edsonisaac.beemonitor.utils.JWTTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenUtils jwtTokenUtil;

    @PostMapping("/token")
    @ResponseStatus(OK)
    @Operation(summary = "Token", description = "Generate an authentication token")
    HashMap<String, Object> token(@RequestBody ObjectNode object) {

        final var username = object.get("username").asText();
        final var password = object.get("password").asText();
        final var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
        final var responseBody = new HashMap<String, Object>();
        final var token = jwtTokenUtil.generateToken(authentication);

        responseBody.put("access_token", token);

        return responseBody;
    }
}