package com.challenge.services;

import com.challenge.models.ChallengeDay;
import com.challenge.models.Task;
import com.challenge.repositories.ChallengeDayRepository;
import com.challenge.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskService {

    public static final Map<String, String> TASK_MAP = Map.of(
            "Meditate", "Take time to clear your mind and meditate for 10 minutes.",
            "Follow a Diet", "Maintain a strict diet with no cheat meals or alcohol.",
            "Hydrate", "Ensure you drink between 72 ounces to a gallon of water today.",
            "Workout", "Complete a 95-minute workout to stay active.",
            "Read", "Read at least 10 pages of a book focused on self-improvement.",
            "Progress Picture", "Take pictures to track your progress visually.",
            "Sleep", "Make sure to get 6-8 hours of restful sleep tonight.");

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ChallengeDayRepository challengeDayRepository;

    // Get all tasks for a specific challenge day (by date)
    public List<Task> getTasksByChallengeDay(LocalDate date) throws NoSuchElementException {
        Optional<ChallengeDay> challengeDay = challengeDayRepository.findById(date);
        if (challengeDay.isEmpty()) {
            throw new NoSuchElementException("No ChallengeDay found for date: " + date);
        }
        return challengeDay.get().getTasks();
    }

    // Mark a specific task as completed
    public ChallengeDay completeTask(Long taskId, boolean isComplete) throws NoSuchElementException {
       Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));

        task.setCompleted(isComplete);
        task.setCompletedTime(isComplete ? LocalDateTime.now() : null);


        taskRepository.save(task);
        ChallengeDay challengeDay = task.getChallengeDay();
        challengeDayRepository.save(challengeDay);

        return challengeDay;
    }

    // Get a single task by ID
    public Task getTaskById(Long taskId) throws NoSuchElementException {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task with ID " + taskId + " not found."));
    }

    // Optionally, you can add a method to update task details
    public Task updateTask(Long taskId, String failureReason) throws NoSuchElementException {
        Task task = getTaskById(taskId);
        task.setFailureReason(failureReason);
        return taskRepository.save(task);
    }

    public void assignTasksToChallengeDay(ChallengeDay challengeDay) {
        // Check if the ChallengeDay already has tasks
        if (challengeDay.getTasks().isEmpty()) {
            TASK_MAP.forEach((name, description) -> {
                Task task = new Task();
                task.setName(name);
                task.setDescription(description);
                task.setCompleted(false); // Not completed when assigned
                task.setChallengeDay(challengeDay);

                // Save the task
                taskRepository.save(task);

                // Add the task to the challengeDay's task list
                challengeDay.getTasks().add(task);
            });

            // Save the updated ChallengeDay
            challengeDayRepository.save(challengeDay);
        }
    }
}