package com.challenge.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ChallengeDay {
    @Id
    private LocalDate date; // Use date as the ID (assuming the challenge day is unique per date)

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;
@JsonManagedReference
    @OneToMany(mappedBy = "challengeDay", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
