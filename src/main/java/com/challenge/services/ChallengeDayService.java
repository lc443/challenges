package com.challenge.services;

import com.challenge.models.ChallengeDay;
import com.challenge.models.Task;
import com.challenge.repositories.ChallengeDayRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ChallengeDayService {

    private final ChallengeDayRepository challengeDayRepository;

    public ChallengeDay findById(LocalDate date) {
        return challengeDayRepository.findById(date).orElseThrow(EntityNotFoundException::new);
    }

    public ChallengeDay completeTask(LocalDate date, Long id) {
        ChallengeDay challengeDay = findById(date);

        for(Task task : challengeDay.getTasks()) {
            if(task.getId().equals(id)) {
                task.setCompleted(true);
                challengeDayRepository.save(challengeDay);
            }
        }
        return challengeDay;
    }
}
