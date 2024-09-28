package com.challenge.controllers;


import com.challenge.models.User;
import com.challenge.services.ChallengeService;
import com.challenge.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;
    private final  UserService userService;


    @PostMapping("/{userId}/start")
    public ResponseEntity<?> startChallenge(@PathVariable Long userId, @RequestParam String challengeName) {
        Optional<User> user = userService.getUserById(userId);
            return new ResponseEntity<>(challengeService.createChallenge(user.get(), challengeName), HttpStatus.CREATED);
    }

}
