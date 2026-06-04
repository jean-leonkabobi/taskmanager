// TaskRepository.java
package com.jeanleon.taskmanager.repository;

import com.jeanleon.taskmanager.model.Task;
import com.jeanleon.taskmanager.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByUserIdOrderByPositionAsc(String userId);

    List<Task> findByUserIdAndStatusOrderByPositionAsc(String userId, TaskStatus status);

    long countByUserId(String userId);

    long countByUserIdAndStatus(String userId, TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId ORDER BY t.position ASC")
    List<Task> findAllByUserId(@Param("userId") String userId);

    Optional<Task> findByIdAndUserId(String id, String userId);
}