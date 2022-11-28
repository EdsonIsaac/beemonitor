package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import io.github.edsonisaac.beemonitor.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final FacadeService facadeService;

    /**
     * Instantiates a new User details service.
     *
     * @param facadeService the facade service
     */
    @Autowired
    public UserDetailsService(FacadeService facadeService) {
        this.facadeService = facadeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            var user = UserUtils.toUser(facadeService.userFindByUsername(username));
            user.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getDepartment().toString())));
            return user;
        } catch (ObjectNotFoundException ex) { }

        throw new UsernameNotFoundException(MessageUtils.USER_NOT_FOUND);
    }
}