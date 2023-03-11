package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type User details service.
 *
 * @author Edson Isaac
 */
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {

            var user = service.findByUsername(username);
            user.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getDepartment().toString())));

            return user;
        } catch (ObjectNotFoundException ex) {
            throw new UsernameNotFoundException(MessageUtils.USER_NOT_FOUND);
        }
    }
}