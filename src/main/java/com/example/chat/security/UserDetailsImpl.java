package com.example.chat.security;

import com.example.chat.model.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String passwordHash;
    private Boolean isBanned;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(Optional<User> user) {
        if (user.isEmpty()) {
            this.isBanned = true;
        } else {
            this.username = user.get().getUsername();
            this.passwordHash = user.get().getPasswordHash();
            this.isBanned = user.get().getIsBanned();
            this.authorities = Arrays.asList(new SimpleGrantedAuthority(user.get().getRole().getRole()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isBanned;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBanned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isBanned;
    }

    @Override
    public boolean isEnabled() {
        return !isBanned;
    }
}
