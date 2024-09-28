package com.challenge.services;

import com.challenge.models.Challenge;
import com.challenge.models.ChallengeDay;

import com.challenge.repositories.ChallengeDayRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class ChallengeDayService {

    private final ChallengeDayRepository challengeDayRepository;
    private final ChallengeService challengeService;

    public ChallengeDay findDayByChallengeAndDate(Long challengeId, LocalDate date) {
        Challenge challenge = challengeService.findById(challengeId);
        return challengeDayRepository.findByChallengeAndDate(challenge, date).orElseThrow(EntityNotFoundException::new);
    }


}
