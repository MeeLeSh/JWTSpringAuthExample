package com.example.service;

import com.example.domain.AppUser;
import com.example.domain.Role;
import com.example.repository.RoleRepository;
import com.example.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class SimpleUserService implements UserService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SimpleUserService(AppUserRepository appUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            log.error("User {} not found in the database", username);
            throw new UsernameNotFoundException("User " + username + " not found in the database");
        } else {
            log.info("User {} found in the database", username);
        }
        List<SimpleGrantedAuthority> authorities =
                appUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user {} to databases", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to databases", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        AppUser appUser = appUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        appUser.getRoles().add(role);

    }

    @Override
    public AppUser getAppUser(String username) {
        log.info("Fetching user {}", username);
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getAppUsers() {
        log.info("Fetching all users");
        return appUserRepository.findAll();
    }

}
