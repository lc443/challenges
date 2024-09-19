package com.challenege.challenge.models;

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
    private LocalDate id;
    @OneToMany(mappedBy = "challengeDay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Challenge> challenges;
}
