package com.challenge.services;

import com.challenge.models.Challenge;
import com.challenge.models.ChallengeDay;
import com.challenge.models.Task;
import com.challenge.models.User;
import com.challenge.repositories.ChallengeDayRepository;
import com.challenge.repositories.ChallengeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final  ChallengeDayRepository challengeDayRepository;
    private final TaskService taskService;

    public Challenge createChallenge(User user, String name) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(75);

        Challenge challenge = new Challenge();
        challenge.setUser(user);
        challenge.setName(name);
        challenge.setStartDate(startDate);
        challenge.setEndDate(endDate);
        challenge = challengeRepository.save(challenge);
        preloadChallengeDays(challenge);

        return challenge;
    }

    private void preloadChallengeDays(Challenge challenge) {
        LocalDate startDate = challenge.getStartDate();
        LocalDate endDate = challenge.getEndDate();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            ChallengeDay challengeDay = new ChallengeDay();
            challengeDay.setDate(date);
            challengeDay.setChallenge(challenge);
            challengeDayRepository.save(challengeDay);
        }
    }

//    @Scheduled(cron = "0 0 6 * * ?") // Runs daily at 6 AM
    @Scheduled(fixedRate = 20000)
    @Transactional
    public void preloadTasksForChallengeDay() {
        LocalDate today = LocalDate.now();


        // Find all ChallengeDays for today
        List<ChallengeDay> todayChallengeDays = challengeDayRepository.findAllByDate(today);

        for (ChallengeDay challengeDay : todayChallengeDays) {
            taskService.assignTasksToChallengeDay(challengeDay);
        }


    }

    public double calculateProgress(Long challengeId) throws NoSuchElementException {
        Optional<Challenge> challengeOptional = challengeRepository.findById(challengeId);
        if (challengeOptional.isEmpty()) {
            throw new NoSuchElementException("Challenge not found with ID: " + challengeId);
        }
        List<ChallengeDay> challengeDays = challengeDayRepository.findAllByChallengeId(challengeId);

        long totalTasks = 0;
        long completedTasks = 0;

        for (ChallengeDay day : challengeDays) {
            List<Task> tasks = day.getTasks();
            totalTasks += tasks.size();

            completedTasks += tasks.stream().filter(Task::isCompleted).count();
        }
        if (totalTasks == 0) {
            return 0.0;
        }
        return (double) completedTasks / totalTasks * 100;
    }

    public List<ChallengeDay> getChallengeDays(Long challengeId) {
        return challengeDayRepository.findAllByChallengeId(challengeId);
    }
}
