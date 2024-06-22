package com.resellerapp.service;

import com.resellerapp.model.dtos.UserLoginDTO;
import com.resellerapp.model.dtos.UserRegisterDTO;
import com.resellerapp.model.entity.User;
import com.resellerapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUser loggedUser;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
    }

    public boolean register(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()){
            return false;
        }

        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setEmail(userRegisterDTO.getEmail());

        this.userRepository.save(user);
        return true;
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<User> byUsername = userRepository.findByUsername(userLoginDTO.getUsername());
        boolean matches = passwordEncoder.matches(userLoginDTO.getPassword(), byUsername.get().getPassword());
        if (byUsername.isEmpty() || !matches){
            return false;
        }

        loggedUser.setUsername(byUsername.get().getUsername());
        loggedUser.setLogged(true);

        return true;
    }

    public void logout() {
        loggedUser.setLogged(false);
        loggedUser.setUsername("");
    }
}
