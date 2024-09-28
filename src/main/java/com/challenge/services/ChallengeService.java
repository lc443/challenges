package com.challenge.services;

import com.challenge.models.Challenge;
import com.challenge.models.ChallengeDay;
import com.challenge.models.Task;
import com.challenge.models.User;
import com.challenge.repositories.ChallengeDayRepository;
import com.challenge.repositories.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ChallengeService {


    private final ChallengeRepository challengeRepository;

    private final ChallengeDayRepository challengeDayRepository;



    public  Challenge findById(Long id) {
        return challengeRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    private static final Map<String, String> TASK_TEMPLATE_MAP = Map.of(
            "Meditate", "Take time to clear your mind and meditate for 10 minutes.",
            "Follow a Diet", "Maintain a strict diet with no cheat meals or alcohol.",
            "Hydrate", "Ensure you drink between 72 ounces to a gallon of water today.",
            "Workout", "Complete a 95-minute workout to stay active.",
            "Read", "Read at least 10 pages of a book focused on self-improvement.",
            "Progress Picture", "Take pictures to track your progress visually.",
            "Sleep", "Make sure to get 6-8 hours of restful sleep tonight."
    );

    public User createChallenge(User user, String name) {
        Challenge challenge = Challenge.builder()
                .name(name)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(74)) // 75 days challenge
                .user(user)
                .build();

        // Create challenge days
        for (int i = 0; i < 75; i++) {
            LocalDate challengeDate = LocalDate.now().plusDays(i);
            ChallengeDay challengeDay = ChallengeDay.builder()
                    .date(challengeDate)
                    .challenge(challenge)
                    .build();

            // Add tasks only for today's challenge day
            if (i == 0) {
                List<Task> tasks = TASK_TEMPLATE_MAP.entrySet().stream()
                        .map(entry -> Task.builder()
                                .name(entry.getKey())
                                .description(entry.getValue())
                                .completed(false)
                                .build())
                        .toList();

                tasks.forEach(task -> task.setChallengeDay(challengeDay));
                challengeDay.setTasks(tasks);
            }

            challenge.getChallengeDays().add(challengeDay);
            challenge.updateProgress();
        }

       challengeRepository.save(challenge);
        return user;
    }




    // Scheduler to populate tasks daily at 6 AM if empty
    @Scheduled(cron = "0 0 6 * * ?") // Runs every day at 6 AM
    public void populateDailyTasks() {
        LocalDate today = LocalDate.now();
        List<ChallengeDay> emptyChallengeDays = challengeDayRepository.findByDateAndTasksIsEmpty(today);

        for (ChallengeDay challengeDay : emptyChallengeDays) {
            List<Task> tasks = TASK_TEMPLATE_MAP.entrySet().stream()
                    .map(entry -> Task.builder()
                            .name(entry.getKey())
                            .description(entry.getValue())
                            .completed(false)
                            .build())
                    .toList();

            tasks.forEach(task -> task.setChallengeDay(challengeDay));
            challengeDay.setTasks(tasks);
            challengeDayRepository.save(challengeDay);
        }
    }
}
