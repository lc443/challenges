package com.challenge.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean completed;
    private LocalDateTime completedTime;
    private String description;
    private String failureReason;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "challenge_day_id")
    private ChallengeDay challengeDay;
}
