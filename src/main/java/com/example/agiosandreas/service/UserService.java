package com.example.agiosandreas.service;

import com.example.agiosandreas.repositories.UserRepository;
import com.example.agiosandreas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

     private final JwtService service;


    private final AuthenticationManager authenticationManager;


    private final UserRepository userRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(JwtService service, AuthenticationManager authenticationManager, UserRepository userRepo) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
    }


    public String register(User user) {
        var jwtToken ="";
        if (!userRepo.existsUserByUsername(user.getUsername())) {
            user.setPassword(encoder.encode(user.getPassword()));
            var savedUser = userRepo.save(user);
             jwtToken = service.generateToken(user.getUsername());
            return jwtToken;
        }return jwtToken;
    }

    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return service.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }
}
