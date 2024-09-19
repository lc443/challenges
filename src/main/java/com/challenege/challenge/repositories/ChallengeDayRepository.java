package com.challenege.challenge.repositories;

import com.challenege.challenge.models.ChallengeDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ChallengeDayRepository extends JpaRepository<ChallengeDay, LocalDate> {
}
