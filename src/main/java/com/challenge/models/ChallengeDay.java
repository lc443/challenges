package com.challenge.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "challenge_id"})  // Ensure unique date per challenge
})
public class ChallengeDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Use a generated ID instead of using date as the primary key

    private LocalDate date;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    @ToString.Exclude
    private Challenge challenge;

    @JsonManagedReference
    @OneToMany(mappedBy = "challengeDay", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
    private boolean completed;
    private double progress;

    // Method to update progress when tasks are updated
    public void updateProgress() {
        if (tasks.isEmpty()) {
            this.progress = 0.0;
        } else {
            long completedTasks = tasks.stream().filter(Task::isCompleted).count();
            this.progress = (double) completedTasks / tasks.size() * 100;
        }
    }

    public void updateCompletionStatus() {
        // Check if all tasks are completed
        this.completed = tasks.stream().allMatch(Task::isCompleted);
    }
}