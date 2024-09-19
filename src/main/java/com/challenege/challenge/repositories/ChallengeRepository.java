package com.challenege.challenge.repositories;

import com.challenege.challenge.models.Challenge;
import com.challenege.challenge.models.ChallengeDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
