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
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
private boolean completed;
    @ManyToOne
    @JsonBackReference
    private User user;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ChallengeDay> challengeDays = new ArrayList<>();

    private double progress;

    // Method to update overall progress based on all challenge days
    public void updateProgress() {
        if (challengeDays.isEmpty()) {
            this.progress = 0.0;
        } else {
            double totalProgress = challengeDays.stream()
                    .mapToDouble(ChallengeDay::getProgress)
                    .sum();
            this.progress = totalProgress / challengeDays.size();
        }
    }
}
