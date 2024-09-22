package com.challenge.repositories;

import com.challenge.models.ChallengeDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ChallengeDayRepository extends JpaRepository<ChallengeDay, LocalDate> {
    List<ChallengeDay> findAllByChallengeId(Long challengeId);
    List<ChallengeDay> findAllByDate(LocalDate day);
}
