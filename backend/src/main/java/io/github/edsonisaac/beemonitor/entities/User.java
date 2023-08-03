package io.github.edsonisaac.beemonitor.entities;

import io.github.edsonisaac.beemonitor.enums.Authority;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a user entity.
 *
 * @author Edson Isaac
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_users")
public class User extends AbstractEntity implements UserDetails {

    /**
     * The name of the user.
     */
    @NotEmpty
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * The username of the user (unique).
     */
    @NotEmpty
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    /**
     * The password of the user.
     */
    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Indicates whether the user is enabled.
     */
    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    /**
     * Indicates whether the user's account is non-expired.
     */
    @Column(name = "account_non_expired", columnDefinition = "boolean default true")
    private Boolean accountNonExpired;

    /**
     * Indicates whether the user's account is non-locked.
     */
    @Column(name = "account_non_locked", columnDefinition = "boolean default true")
    private Boolean accountNonLocked;

    /**
     * Indicates whether the user's credentials are non-expired.
     */
    @Column(name = "credentials_non_expired", columnDefinition = "boolean default true")
    private Boolean credentialsNonExpired;

    /**
     * The set of authorities (roles) associated with the user.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_authorities")
    private Set<Authority> authorities;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image photo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
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