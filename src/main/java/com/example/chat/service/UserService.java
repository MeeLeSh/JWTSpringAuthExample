package com.example.chat.service;

import com.example.chat.model.dto.RegistrationUserRequest;
import com.example.chat.model.entity.Friend;
import com.example.chat.model.entity.Role;
import com.example.chat.model.entity.User;
import com.example.chat.repository.RoleRepository;
import com.example.chat.repository.UserRepository;
import com.example.chat.validator.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private EmailValidator emailValidator;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       EmailValidator emailValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.emailValidator = emailValidator;
    }

    public ResponseEntity registrationUser(RegistrationUserRequest registrationUserRequest) throws IllegalArgumentException{
        String username = registrationUserRequest.getUsername();
        String email = registrationUserRequest.getEmail();
        String password = registrationUserRequest.getPassword();
        String confirmPassword = registrationUserRequest.getConfirmPassword();
        Optional<User> userByUsername = userRepository.findByUsername(username);
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByUsername.isPresent()) return new ResponseEntity("User with this login already exists", HttpStatus.CONFLICT);
        if (userByEmail.isPresent()) return new ResponseEntity("User with this email already exists", HttpStatus.CONFLICT);
        if (!password.equals(confirmPassword)) return new ResponseEntity("Passwords are not equal", HttpStatus.CONFLICT);
        if (!emailValidator.validate(email)) return new ResponseEntity("Email address is not in the correct format", HttpStatus.CONFLICT);
        String passwordHash = passwordEncoder.encode(password);
        Optional<Role> role = roleRepository.findByRole("ROLE_USER");
        if (role.isEmpty()) {
            Role newRole = new Role("ROLE_USER");
            roleRepository.save(newRole);
            role = Optional.of(newRole);
        }

        User user = new User(username, email, passwordHash, false, role.orElseThrow(
                () -> new IllegalArgumentException("Role doesn't exist")));
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    public boolean findUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    public Set<String> getAllFriends(String username) {
        Set<Friend> friends = userRepository.findByUsername(username).get().getFiendsId();
        return friends
                .stream()
                .map(friend -> userRepository.findById(friend.getFriendId()).get().getUsername())
                .collect(Collectors.toSet());
    }

}
