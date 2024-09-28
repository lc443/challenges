package com.challenge.repositories;

import com.challenge.models.Challenge;
import com.challenge.models.ChallengeDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChallengeDayRepository extends JpaRepository<ChallengeDay, Long> {
    List<ChallengeDay> findAllByChallengeId(Long challengeId);
    List<ChallengeDay> findByDateAndTasksIsEmpty(LocalDate date); // Custom qu
    Optional<ChallengeDay> findByChallengeAndDate(Challenge challenge, LocalDate date);
    List<ChallengeDay> findAllByDate(LocalDate day);
}
