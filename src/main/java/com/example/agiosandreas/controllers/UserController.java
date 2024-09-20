package com.example.agiosandreas.controllers;

import com.example.agiosandreas.repositories.UserRepository;
import com.example.agiosandreas.service.JwtService;
import com.example.agiosandreas.service.UserService;
import com.example.agiosandreas.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    //private static final Logger logger = LoggerFactory.getLogger(UserController.class);



    private  final UserService userService;


    public UserController(UserRepository userRepository, UserService userService, JwtService jwtService) {
        this.userService = userService;

    }

/*
    @GetMapping("/check-username")
    public String checkUsername(@RequestParam String username,String password) {
        // check the length of username
        if (username.length() > 15) {
            return "Το όνομα χρήστη είναι πολύ μεγάλο, δοκίμασε ένα κατω απο 15 χαρακτήρες";
        }
        if (username.equals("")){
            return "Παρακαλώ συμπλήρωσε ένα όνομα χρήστη";
        }
        boolean exists = userRepository.existsUserByUsername(username);
        if(exists){
            return "Το όνομα χρήστη υπάρχει ήδη. Παρακαλώ διάλεξε άλλο όνομα.";}
            else{
                createUser(new User(username));
            return "";
            }
    }


*/
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        var token = userService.register(user);
        if(token!=""){
            Map<String,String> response= new HashMap<>();
            response.put("token",token);
            response.put("userId",String.valueOf(user.getId()));
            return ResponseEntity.ok(response);
        }
        return (ResponseEntity<?>) ResponseEntity.badRequest();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid User user ){
        var token = userService.verify(user);
        Map<String,String> response= new HashMap<>();
        response.put("token",token);
        response.put("userId",String.valueOf(user.getId()));
        return ResponseEntity.ok(response);

    }

/*
    public void createUser( User user) {
        System.out.println(user.getUsername());
        userRepository.save(user);
    }
    */

}
