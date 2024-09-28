package com.challenge.controllers;

import com.challenge.models.User;
import com.challenge.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor()
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> loginUser(@PathVariable String username) {
        User user =  userService.getUserByUsername(username).orElse(null);
        if(user == null) {
            return ResponseEntity.ok().body("User not found with username: " + username);
        }
        return ResponseEntity.ok().body(user);


    }


}
