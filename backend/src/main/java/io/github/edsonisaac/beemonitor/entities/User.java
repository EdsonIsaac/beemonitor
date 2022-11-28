package io.github.edsonisaac.beemonitor.entities;

import io.github.edsonisaac.beemonitor.enums.Department;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * The type User.
 *
 * @author Edson Isaac
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_users")
public class User extends AbstractEntity implements UserDetails {

    @NotEmpty(message = "{field.name.invalid}")
    private String name;

    @NotEmpty(message = "{field.username.invalid}")
    private String username;

    @NotEmpty(message = "{field.password.invalid}")
    private String password;

    @NotNull(message = "{field.enabled.invalid}")
    private Boolean enabled;

    @NotNull(message = "{field.department.invalid}")
    @Enumerated(EnumType.STRING)
    private Department department;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}