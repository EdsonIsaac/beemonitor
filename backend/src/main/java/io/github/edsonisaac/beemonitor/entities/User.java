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

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_users")
public class User extends AbstractEntity implements UserDetails {

    @NotEmpty
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotEmpty
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

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
}