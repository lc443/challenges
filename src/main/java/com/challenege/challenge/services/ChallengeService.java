package com.challenege.challenge.services;

import com.challenege.challenge.models.Challenge;
import com.challenege.challenge.models.ChallengeDay;
import com.challenege.challenge.repositories.ChallengeDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChallengeService {
    private final ChallengeDayRepository challengeDayRepository;

    public void createChallengePeriod() {
        LocalDate startDate = LocalDate.now();

        for (int i = 0; i < 75; i++) {
            LocalDate challengeDayDate = startDate.plusDays(i);
            ChallengeDay challengeDay = new ChallengeDay();
            challengeDay.setId(challengeDayDate);

            List<Challenge> challenges = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                Challenge challenge = new Challenge();
                challenge.setName("Challenge " + (j + 1));
                challenge.setCompleted(false);
                challenges.add(challenge);
            }

            challengeDay.setChallenges(challenges);
            challengeDayRepository.save(challengeDay);
        }
    }

}
