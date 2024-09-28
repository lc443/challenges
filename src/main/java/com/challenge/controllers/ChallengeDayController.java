package com.challenge.controllers;

import com.challenge.services.ChallengeDayService;

import com.challenge.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge-days")
@CrossOrigin(
        origins = {
                     // Allows all subdomains of example.com
                "http://localhost:4200",
                // Allows any port on localhost
                 // Allows all subdomains of example.com over HTTPS and any port
        },
        allowedHeaders = "*"              // Allows all headers        // Allows credentials (like cookies)
)
public class ChallengeDayController {

    private final ChallengeDayService challengeDayService;

    private final TaskService taskService;

    // Get all tasks for a specific challenge day by the date
    @GetMapping("/{date}/{id}")
    public ResponseEntity<?> getChallengeDay(@PathVariable String date, @PathVariable Long id) {
            LocalDate challengeDate = LocalDate.parse(date);
            return new ResponseEntity<>(challengeDayService.findDayByChallengeAndDate(id, challengeDate), HttpStatus.OK);
    }

    // Mark a task as completed for a specific challenge day
    @PutMapping("/tasks/{taskId}/{completed}/complete")
    public ResponseEntity<?> markTaskAsCompletedOrIncomplete( @PathVariable Long taskId, @PathVariable boolean completed ) {
            return new ResponseEntity<>(taskService.completeTask(taskId, completed), HttpStatus.OK);
    }


//    @PutMapping("/tasks/{taskId}/reason")
//    public ResponseEntity<?> updateFailureReason( @PathVariable Long taskId, @RequestBody boolean isComplete ) {
//        return new ResponseEntity<>(taskService.completeTask(taskId, isComplete), HttpStatus.OK);
//    }



}
