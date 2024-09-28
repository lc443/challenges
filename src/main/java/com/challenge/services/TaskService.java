package com.challenge.services;

import com.challenge.models.ChallengeDay;
import com.challenge.models.Task;
import com.challenge.repositories.ChallengeDayRepository;
import com.challenge.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

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


    // Mark a specific task as completed
    public ChallengeDay completeTask(Long taskId, boolean isComplete) throws NoSuchElementException {
       Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));

        task.setCompleted(isComplete);
        task.setCompletedTime(isComplete ? LocalDateTime.now() : null);


        taskRepository.save(task);
        ChallengeDay challengeDay = task.getChallengeDay();

        challengeDay.updateCompletionStatus();
        challengeDay.updateProgress();

        challengeDayRepository.save(challengeDay);


        return challengeDay;
    }

    // Get a single task by ID
    public Task getTaskById(Long taskId) throws NoSuchElementException {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task with ID " + taskId + " not found."));
    }

    // Optionally, you can add a method to update task details
}