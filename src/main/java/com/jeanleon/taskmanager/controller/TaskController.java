// TaskController.java
package com.jeanleon.taskmanager.controller;

import com.jeanleon.taskmanager.dto.request.TaskRequest;
import com.jeanleon.taskmanager.dto.response.TaskResponse;
import com.jeanleon.taskmanager.enums.TaskStatus;
import com.jeanleon.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) TaskStatus status) {
        return ResponseEntity.ok(taskService.getAllTasks(userDetails.getUsername(), status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id) {
        return ResponseEntity.ok(taskService.getTaskById(userDetails.getUsername(), id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.createTask(userDetails.getUsername(), request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id,
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(userDetails.getUsername(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id) {
        taskService.deleteTask(userDetails.getUsername(), id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id,
            @RequestBody Map<String, String> statusUpdate) {
        TaskStatus status = TaskStatus.valueOf(statusUpdate.get("status"));
        return ResponseEntity.ok(taskService.updateTaskStatus(userDetails.getUsername(), id, status));
    }

    @PutMapping("/reorder")
    public ResponseEntity<Void> reorderTasks(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody List<String> taskIds) {
        taskService.reorderTasks(userDetails.getUsername(), taskIds);
        return ResponseEntity.ok().build();
    }
}