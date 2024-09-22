package com.challenge.repositories;
import com.challenge.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TaskRepository extends JpaRepository<Task, Long> {
}
