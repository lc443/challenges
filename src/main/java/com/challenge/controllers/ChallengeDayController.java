package com.challenge.controllers;

import com.challenge.services.ChallengeDayService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge-days")
@CrossOrigin(origins = "http://localhost:4200")
public class ChallengeDayController {

    private final ChallengeDayService challengeDayService;

    // Get all tasks for a specific challenge day by the date
    @GetMapping("/{date}")
    public ResponseEntity<?> getChallengeDay(@PathVariable String date) {
        try {
            LocalDate challengeDate = LocalDate.parse(date);
            return new ResponseEntity<>(challengeDayService.findById(challengeDate), HttpStatus.OK);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Invalid date format. Please use 'YYYY-MM-DD'.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No ChallengeDay found for date: " + date, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mark a task as completed for a specific challenge day
    @PutMapping("/tasks/{date}/{taskId}/complete")
    public ResponseEntity<?> markTaskAsCompleted(@PathVariable LocalDate date, @PathVariable Long taskId) {
        try {
            return new ResponseEntity<>(challengeDayService.completeTask(date, taskId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Task not found with ID: " + taskId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tasks/{date}/{taskId}/revert")
    public ResponseEntity<?> markAsIncomplete(@PathVariable LocalDate date, @PathVariable Long taskId) {
        try {
            return new ResponseEntity<>(challengeDayService.markAsIncomplete(date, taskId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Task not found with ID: " + taskId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
