package io.github.edsonisaac.beemonitor.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.utils.JWTUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * The type Authentication filter.
 *
 * @author edson
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    /**
     * Instantiates a new Authentication filter.
     *
     * @param authenticationManager the authentication manager
     * @param jwtUtils              the jwt utils
     */
    public AuthenticationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
    }

    /**
     * Attempt authentication.
     *
     * @param request  the request
     * @param response the response
     * @return the authentication
     * @throws AuthenticationException the authentication exception
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            var authToken = getAuth(request);
            setDetails(request, authToken);
            var auth = authenticationManager.authenticate(authToken);
            return auth;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Successful authentication.
     *
     * @param request    the request
     * @param response   the response
     * @param chain      the chain
     * @param authResult the auth result
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        var username = ((User) authResult.getPrincipal()).getUsername();
        var token = jwtUtils.generateToken(username);

        response.getWriter().write(token);
        /*
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", token);
        response.addHeader("Access-Control-Expose-Headers", "Setor");
        response.addHeader("Department", ((User) authResult.getPrincipal()).getDepartment().toString());
        */
    }

    private UsernamePasswordAuthenticationToken getAuth(HttpServletRequest request) throws IOException {
        var user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        /**
         * On authentication failure.
         *
         * @param httpServletRequest  the http servlet request
         * @param httpServletResponse the http servlet response
         * @param ex                  the ex
         * @throws IOException the io exception
         */
        @Override
        public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException ex) throws IOException {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().append(json());
        }

        private String json() {
            var date = new Date().getTime();

            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Usuário e/ou senha inválidos!\", "
                + "\"path\": \"/login\"}"
            ;
        }
    }
}