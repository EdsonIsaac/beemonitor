package io.github.edsonisaac.beemonitor.models;

import io.github.edsonisaac.beemonitor.enums.Department;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_users")
public class User extends AbstractModel implements UserDetails {

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
    @Column(name = "department", nullable = false, length = 25)
    private Department department;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Image photo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(department.name()));
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