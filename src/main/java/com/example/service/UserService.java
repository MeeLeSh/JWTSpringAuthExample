package com.example.service;

import com.example.domain.AppUser;
import com.example.domain.Role;

import java.util.List;

public interface UserService {

    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getAppUser(String username);
    List<AppUser> getAppUsers();

}
