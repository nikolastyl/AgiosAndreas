package com.example.agiosandreas.controllers;

import com.example.agiosandreas.repositories.UserRepository;
import com.example.agiosandreas.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/check-username")
    public String checkUsername(@RequestParam String username) {
        // check the length of username
        if (username.length() > 15) {
            return "Το όνομα χρήστη είναι πολύ μεγάλο, δοκίμασε ένα κατω απο 15 χαρακτήρες";
        }
        if (username.equals("")){
            return "Παρακαλώ συμπλήρωσε ένα όνομα χρήστη";
        }
        boolean exists = userRepository.existsByUsername(username);
        if(exists){
            return "Το όνομα χρήστη υπάρχει ήδη. Παρακαλώ διάλεξε άλλο όνομα.";}
            else{
                createUser(new User(username));
            return "";
            }
    }

    @GetMapping("/find-id")
    public Long findId(@RequestParam("username") String username){
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getId();
        } else {
            return null;
        }
    }



    public void createUser( User user) {
        System.out.println(user.getUsername());
        userRepository.save(user);
    }
}
