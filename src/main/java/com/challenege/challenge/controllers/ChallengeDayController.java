package com.challenege.challenge.controllers;

import com.challenege.challenge.models.Challenge;
import com.challenege.challenge.models.ChallengeDay;
import com.challenege.challenge.repositories.ChallengeDayRepository;
import com.challenege.challenge.repositories.ChallengeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenges")
public class ChallengeDayController {

    private final ChallengeDayRepository challengeDayRepository;
    private final ChallengeRepository   challengeRepository;

    @GetMapping("/today")
    public ChallengeDay getTodayChallenges() {
        LocalDate today = LocalDate.now();
        return challengeDayRepository.findById(today)
                .orElseGet(() -> createNewChallengeDay(today));
    }

    @PutMapping("/update/{challengeId}")
    public Challenge updateChallenge(@PathVariable Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("Challenge not found"));
        challenge.setCompleted(true);
        challenge.setCompletedTime(LocalDateTime.now());
        return challengeRepository.save(challenge);
    }

    private ChallengeDay createNewChallengeDay(LocalDate date) {
        ChallengeDay challengeDay = new ChallengeDay();
        challengeDay.setId(date);
        List<Challenge> challenges = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Challenge challenge = new Challenge();
            challenge.setName("Challenge " + (i + 1));
            challenge.setCompleted(false);
            challenges.add(challenge);
        }
        challengeDay.setChallenges(challenges);
        challengeDayRepository.save(challengeDay);
        return challengeDay;
    }
}
