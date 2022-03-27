package ru.maruchekas.micromessage.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.maruchekas.micromessage.model.enumeration.UserType;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "is_blocked")
    private Integer isBlocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getUserType().getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isBlocked == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isBlocked == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.isBlocked == 0;
    }
    }

