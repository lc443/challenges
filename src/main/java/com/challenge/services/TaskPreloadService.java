package com.challenge.services;

import com.challenge.models.Task;
import com.challenge.repositories.TaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class TaskPreloadService {


    private final TaskRepository taskRepository;

    private static final Map<String, String> TASK_TEMPLATE_MAP = Map.of(
            "Meditate", "Take time to clear your mind and meditate for 10 minutes.",
            "Follow a Diet", "Maintain a strict diet with no cheat meals or alcohol.",
            "Hydrate", "Ensure you drink between 72 ounces to a gallon of water today.",
            "Workout", "Complete a 95-minute workout to stay active.",
            "Read", "Read at least 10 pages of a book focused on self-improvement.",
            "Progress Picture", "Take pictures to track your progress visually.",
            "Sleep", "Make sure to get 6-8 hours of restful sleep tonight."
    );

    // Preload tasks into the database as templates (these are not associated with any ChallengeDay yet)
    @PostConstruct // You can also call this method manually if needed
    public void preloadTemplateTasks() {
        // Check if tasks are already preloaded
        if (taskRepository.count() == 0) {
            for (Map.Entry<String, String> taskEntry : TASK_TEMPLATE_MAP.entrySet()) {
                Task task = new Task();
                task.setName(taskEntry.getKey());
                task.setDescription(taskEntry.getValue());
                task.setCompleted(false);  // Default state
                taskRepository.save(task); // Save as a template task
            }
            System.out.println("Template tasks preloaded into the database.");
        } else {
            System.out.println("Template tasks are already preloaded.");
        }
    }
}

