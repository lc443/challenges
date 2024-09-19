package com.challenege.challenge;

import com.challenege.challenge.models.Challenge;
import com.challenege.challenge.models.ChallengeDay;
import com.challenege.challenge.repositories.ChallengeDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ChallengeApp {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApp.class, args);
    }
    @Autowired
    private ChallengeDayRepository challengeDayRepository;

    private void initializeChallengeData() {
        LocalDate startDate = LocalDate.now();

        // Check if the data already exists to avoid duplicates
        if (challengeDayRepository.count() > 0) {
            System.out.println("Challenge data already exists. Skipping initialization.");
            return;
        }

        // Define a list of challenge names for each challenge day
        List<List<String>> challengeNames = getChallengeNames();

        for (int i = 0; i < 75; i++) {
            LocalDate challengeDayDate = startDate.plusDays(i);
            ChallengeDay challengeDay = new ChallengeDay();
            challengeDay.setId(challengeDayDate);

            List<Challenge> challenges = new ArrayList<>();
            List<String> dayChallengeNames = challengeNames.get(i % challengeNames.size()); // Cycle through the list of names
            for (int j = 0; j < dayChallengeNames.size(); j++) {
                Challenge challenge = new Challenge();
                challenge.setName(dayChallengeNames.get(j)); // Set the unique challenge name
                challenge.setCompleted(false);
                challenges.add(challenge);
            }

            challengeDay.setChallenges(challenges);
            challengeDayRepository.save(challengeDay);
        }

        System.out.println("Challenge data has been initialized successfully.");
    }

    // Method to return a list of challenge names for each day
    private List<List<String>> getChallengeNames() {
        return Arrays.asList(
                Arrays.asList("Drink 8 glasses of water",
                        "Exercise for 30 minutes",
                        "Read 10 pages of a book",
                        "Meditate for 10 minutes",
                        "Write in a journal",
                        "Eat a healthy meal",
                        "Go to bed early"),
                Arrays.asList("Take a 15-minute walk",
                        "Do a random act of kindness",
                        "Avoid sugar for the day",
                        "Practice deep breathing",
                        "Plan your next day",
                        "Stretch for 15 minutes",
                        "Listen to a podcast")
                // Add more lists of names for each challenge day
                // You can define 75 different sets of names or cycle through a smaller set
        );
    }

}
