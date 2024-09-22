package com.challenge.controllers;

import com.challenge.models.Challenge;
import com.challenge.models.ChallengeDay;
import com.challenge.models.User;
import com.challenge.services.ChallengeService;
import com.challenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private UserService userService;

    // Endpoint to get the progress of a specific challenge
    @GetMapping("/{challengeId}/progress")
    public ResponseEntity<?> getChallengeProgress(@PathVariable Long challengeId) {
        try {
            double progress = challengeService.calculateProgress(challengeId);
            return new ResponseEntity<>(progress, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Challenge not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/start")
    public ResponseEntity<Challenge> startChallenge(@RequestParam String username, @RequestParam String challengeName) {
        Optional<User> user = userService.getUserByUsername(username);
            return new ResponseEntity<>(challengeService.createChallenge(user.get(), challengeName), HttpStatus.CREATED);
    }

    @GetMapping("/{challengeId}/days")
    public ResponseEntity<List<ChallengeDay>> getChallengeDays(@PathVariable Long challengeId) {
        List<ChallengeDay> challengeDays = challengeService.getChallengeDays(challengeId);
        return new ResponseEntity<>(challengeDays, HttpStatus.OK);
    }
}
